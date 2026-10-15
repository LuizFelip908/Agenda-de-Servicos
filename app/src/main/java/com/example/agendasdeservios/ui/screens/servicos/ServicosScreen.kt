package com.example.agendasdeservios.ui.screens.servicos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.agendasdeservios.ui.components.AppTopBar
import com.example.agendasdeservios.ui.components.EmptyState

@Composable
fun ServicosScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AppTopBar(title = "Serviços")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Will open Service Form Dialog */ }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Cadastrar Serviço"
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
                message = "Nenhum serviço cadastrado até o momento.",
                icon = Icons.Outlined.Build,
                actionLabel = "Cadastrar Serviço",
                onActionClick = { /* Will open Service Form Dialog */ }
            )
        }
    }
}
