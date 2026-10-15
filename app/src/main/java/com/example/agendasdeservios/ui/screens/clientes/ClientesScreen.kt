package com.example.agendasdeservios.ui.screens.clientes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.agendasdeservios.ui.components.AppTopBar
import com.example.agendasdeservios.ui.components.EmptyState

@Composable
fun ClientesScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AppTopBar(title = "Clientes")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Will open Client Form Dialog */ }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Cadastrar Cliente"
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
                message = "Nenhum cliente cadastrado até o momento.",
                icon = Icons.Outlined.People,
                actionLabel = "Cadastrar Cliente",
                onActionClick = { /* Will open Client Form Dialog */ }
            )
        }
    }
}
