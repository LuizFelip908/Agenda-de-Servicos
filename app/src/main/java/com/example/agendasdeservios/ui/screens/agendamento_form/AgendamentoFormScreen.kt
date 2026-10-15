package com.example.agendasdeservios.ui.screens.agendamento_form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agendasdeservios.ui.components.AppDatePickerDialog
import com.example.agendasdeservios.ui.components.AppTimePickerDialog
import com.example.agendasdeservios.ui.components.AppTopBar
import com.example.agendasdeservios.ui.viewmodel.AgendamentoFormViewModel
import com.example.agendasdeservios.utils.CurrencyUtils
import com.example.agendasdeservios.utils.DateUtils
import com.example.agendasdeservios.utils.PhoneMaskUtils
import java.util.Locale

@Composable
fun AgendamentoFormScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AgendamentoFormViewModel = viewModel(),
) {
    val clientesList by viewModel.clientesList.collectAsState()
    val servicosList by viewModel.servicosList.collectAsState()

    val selectedCliente by viewModel.selectedCliente.collectAsState()
    val selectedServico by viewModel.selectedServico.collectAsState()

    val selectedDateMillis by viewModel.selectedDateMillis.collectAsState()
    val selectedHour by viewModel.selectedHour.collectAsState()
    val selectedMinute by viewModel.selectedMinute.collectAsState()

    val observacoesInput by viewModel.observacoesInput.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isSaving by viewModel.isSaving.collectAsState()

    var isClienteMenuOpen by remember { mutableStateOf(false) }
    var isServicoMenuOpen by remember { mutableStateOf(false) }

    var isDatePickerOpen by remember { mutableStateOf(false) }
    var isTimePickerOpen by remember { mutableStateOf(false) }

    val startAndEndRange = viewModel.calculateStartAndEndMillis()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Novo Agendamento",
                canNavigateBack = true,
                onNavigateBack = onNavigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (errorMessage != null) {
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            // Seleção do Cliente
            Column {
                Text(
                    text = "Cliente *",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isClienteMenuOpen = true },
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            if (selectedCliente != null) {
                                Column {
                                    Text(
                                        text = selectedCliente!!.nome,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = PhoneMaskUtils.formatPhone(selectedCliente!!.telefone),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            } else {
                                Text(
                                    text = if (clientesList.isEmpty()) "Nenhum cliente cadastrado (Cadastre em 'Clientes')" else "Selecione o Cliente",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }

                DropdownMenu(
                    expanded = isClienteMenuOpen,
                    onDismissRequest = { isClienteMenuOpen = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    if (clientesList.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("Nenhum cliente disponível") },
                            onClick = { isClienteMenuOpen = false }
                        )
                    } else {
                        clientesList.forEach { cliente ->
                            DropdownMenuItem(
                                text = { Text("${cliente.nome} (${PhoneMaskUtils.formatPhone(cliente.telefone)})") },
                                onClick = {
                                    viewModel.selectedCliente.value = cliente
                                    isClienteMenuOpen = false
                                }
                            )
                        }
                    }
                }
            }

            // Seleção do Serviço
            Column {
                Text(
                    text = "Serviço *",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isServicoMenuOpen = true },
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Work,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            if (selectedServico != null) {
                                Column {
                                    Text(
                                        text = selectedServico!!.nome,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${selectedServico!!.duracaoMinutos} min • ${CurrencyUtils.formatCurrency(selectedServico!!.preco)}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            } else {
                                Text(
                                    text = if (servicosList.isEmpty()) "Nenhum serviço cadastrado (Cadastre em 'Serviços')" else "Selecione o Serviço",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }

                DropdownMenu(
                    expanded = isServicoMenuOpen,
                    onDismissRequest = { isServicoMenuOpen = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    if (servicosList.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("Nenhum serviço disponível") },
                            onClick = { isServicoMenuOpen = false }
                        )
                    } else {
                        servicosList.forEach { servico ->
                            DropdownMenuItem(
                                text = { Text("${servico.nome} (${servico.duracaoMinutos} min - ${CurrencyUtils.formatCurrency(servico.preco)})") },
                                onClick = {
                                    viewModel.selectedServico.value = servico
                                    isServicoMenuOpen = false
                                }
                            )
                        }
                    }
                }
            }

            // Seleção de Data e Hora
            Row(modifier = Modifier.fillMaxWidth()) {
                // Seletor de Data
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Data *",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isDatePickerOpen = true },
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = DateUtils.formatDateShort(selectedDateMillis),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Seletor de Hora
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Horário de Início *",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isTimePickerOpen = true },
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Resumo do Horário Calculado
            if (startAndEndRange != null) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Resumo do Agendamento",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "⏱ Intervalo: ${DateUtils.formatRange(startAndEndRange.first, startAndEndRange.second)} (${selectedServico?.duracaoMinutos} min)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            // Observações
            OutlinedTextField(
                value = observacoesInput,
                onValueChange = { viewModel.observacoesInput.value = it },
                label = { Text("Observações (opcional)") },
                maxLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botão Salvar
            Button(
                onClick = {
                    viewModel.saveAgendamento(onSuccess = onNavigateBack)
                },
                enabled = !isSaving,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isSaving) "Salvando..." else "Confirmar Agendamento",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    // Modal DatePicker
    if (isDatePickerOpen) {
        AppDatePickerDialog(
            onDateSelected = {
                viewModel.onDateSelected(it)
                isDatePickerOpen = false
            },
            onDismiss = { isDatePickerOpen = false }
        )
    }

    // Modal TimePicker
    if (isTimePickerOpen) {
        AppTimePickerDialog(
            onTimeSelected = { hour, minute ->
                viewModel.onTimeSelected(hour, minute)
                isTimePickerOpen = false
            },
            onDismiss = { isTimePickerOpen = false }
        )
    }
}
