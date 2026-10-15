package com.example.agendasdeservios.ui.screens.servicos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agendasdeservios.data.local.entity.ServicoEntity
import com.example.agendasdeservios.ui.components.AppTopBar
import com.example.agendasdeservios.ui.components.EmptyState
import com.example.agendasdeservios.ui.viewmodel.ServicoViewModel
import com.example.agendasdeservios.utils.CurrencyUtils

@Composable
fun ServicosScreen(
    modifier: Modifier = Modifier,
    viewModel: ServicoViewModel = viewModel()
) {
    val servicos by viewModel.servicos.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isDialogOpen by viewModel.isDialogOpen.collectAsState()
    val editingServico by viewModel.editingServico.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val nomeInput by viewModel.nomeInput.collectAsState()
    val descricaoInput by viewModel.descricaoInput.collectAsState()
    val duracaoInput by viewModel.duracaoInput.collectAsState()
    val precoInput by viewModel.precoInput.collectAsState()

    var servicoToDelete by remember { mutableStateOf<ServicoEntity?>(null) }

    Scaffold(
        topBar = {
            AppTopBar(title = "Serviços")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.openAddDialog() }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Cadastrar Serviço"
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Campo de Pesquisa
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Pesquisar serviço por nome...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Limpar")
                        }
                    }
                },
                singleLine = true
            )

            if (servicos.isEmpty()) {
                EmptyState(
                    message = if (searchQuery.isBlank()) {
                        "Nenhum serviço cadastrado até o momento."
                    } else {
                        "Nenhum serviço encontrado para \"$searchQuery\"."
                    },
                    icon = Icons.Outlined.Build,
                    actionLabel = "Cadastrar Serviço",
                    onActionClick = { viewModel.openAddDialog() }
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(servicos, key = { it.id }) { servico ->
                        ServicoCard(
                            servico = servico,
                            onEdit = { viewModel.openEditDialog(servico) },
                            onDelete = { servicoToDelete = servico }
                        )
                    }
                }
            }
        }
    }

    // Modal Form Dialog
    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = { viewModel.closeDialog() },
            title = {
                Text(
                    text = if (editingServico == null) "Novo Serviço" else "Editar Serviço",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    if (errorMessage != null) {
                        Surface(
                            color = MaterialTheme.colorScheme.errorContainer,
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                        ) {
                            Text(
                                text = errorMessage!!,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    OutlinedTextField(
                        value = nomeInput,
                        onValueChange = { viewModel.nomeInput.value = it },
                        label = { Text("Nome do Serviço *") },
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Build, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = descricaoInput,
                        onValueChange = { viewModel.descricaoInput.value = it },
                        label = { Text("Descrição (opcional)") },
                        maxLines = 2,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = duracaoInput,
                            onValueChange = { viewModel.duracaoInput.value = it },
                            label = { Text("Duração (min) *") },
                            singleLine = true,
                            placeholder = { Text("30") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            leadingIcon = { Icon(Icons.Default.AccessTime, contentDescription = null) },
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        OutlinedTextField(
                            value = precoInput,
                            onValueChange = { viewModel.precoInput.value = it },
                            label = { Text("Preço (R$) *") },
                            singleLine = true,
                            placeholder = { Text("50,00") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.saveServico() }) {
                    Text("Salvar")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.closeDialog() }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Confirmation Dialog Delete
    if (servicoToDelete != null) {
        AlertDialog(
            onDismissRequest = { servicoToDelete = null },
            title = { Text("Excluir Serviço") },
            text = { Text("Deseja realmente excluir o serviço \"${servicoToDelete?.nome}\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        servicoToDelete?.let { viewModel.deleteServico(it) }
                        servicoToDelete = null
                    }
                ) {
                    Text("Excluir", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { servicoToDelete = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun ServicoCard(
    servico: ServicoEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = servico.nome,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                if (servico.descricao.isNotBlank()) {
                    Text(
                        text = servico.descricao,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "⏱ ${servico.duracaoMinutos} min",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = CurrencyUtils.formatCurrency(servico.preco),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Excluir",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
