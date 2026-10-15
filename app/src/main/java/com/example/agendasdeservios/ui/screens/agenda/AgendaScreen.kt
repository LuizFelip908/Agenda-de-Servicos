package com.example.agendasdeservios.ui.screens.agenda

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.agendasdeservios.ui.components.AppTopBar
import com.example.agendasdeservios.ui.components.EmptyState

@Composable
fun AgendaScreen(
    onNavigateToNovoAgendamento: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AppTopBar(title = "Agenda de Serviços")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToNovoAgendamento) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Novo Agendamento"
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            EmptyState(
                message = "Nenhum agendamento encontrado para esta data.",
                icon = Icons.Outlined.CalendarMonth,
                actionLabel = "Novo Agendamento",
                onActionClick = onNavigateToNovoAgendamento
            )
        }
    }
}
