package com.example.agendasdeservios.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.MiscellaneousServices
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.agendasdeservios.ui.screens.agenda.AgendaScreen
import com.example.agendasdeservios.ui.screens.agendamento_form.AgendamentoFormScreen
import com.example.agendasdeservios.ui.screens.clientes.ClientesScreen
import com.example.agendasdeservios.ui.screens.servicos.ServicosScreen

enum class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    AGENDA("agenda", "Agenda", Icons.Default.CalendarToday),
    CLIENTES("clientes", "Clientes", Icons.Default.People),
    SERVICOS("servicos", "Serviços", Icons.Default.MiscellaneousServices),
}

object AppDestinations {
    const val AGENDA = "agenda"
    const val CLIENTES = "clientes"
    const val SERVICOS = "servicos"
    const val NOVO_AGENDAMENTO = "novo_agendamento"
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavRoutes = BottomNavItem.entries.map { it.route }
    val showBottomBar = currentRoute in bottomNavRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    BottomNavItem.entries.forEach { item ->
                        val selected = currentRoute == item.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title
                                )
                            },
                            label = { Text(text = item.title) }
                        )
                    }
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestinations.AGENDA,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppDestinations.AGENDA) {
                AgendaScreen(
                    onNavigateToNovoAgendamento = {
                        navController.navigate(AppDestinations.NOVO_AGENDAMENTO)
                    }
                )
            }
            composable(AppDestinations.CLIENTES) {
                ClientesScreen()
            }
            composable(AppDestinations.SERVICOS) {
                ServicosScreen()
            }
            composable(AppDestinations.NOVO_AGENDAMENTO) {
                AgendamentoFormScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
