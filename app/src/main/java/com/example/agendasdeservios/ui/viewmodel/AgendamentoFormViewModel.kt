package com.example.agendasdeservios.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendasdeservios.data.local.AppDatabase
import com.example.agendasdeservios.data.local.entity.AgendamentoEntity
import com.example.agendasdeservios.data.local.entity.ClienteEntity
import com.example.agendasdeservios.data.local.entity.ServicoEntity
import com.example.agendasdeservios.data.repository.AgendamentoRepository
import com.example.agendasdeservios.data.repository.ClienteRepository
import com.example.agendasdeservios.data.repository.ServicoRepository
import com.example.agendasdeservios.notification.NotificationHelper
import com.example.agendasdeservios.utils.DateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class AgendamentoFormViewModel(application: Application) : AndroidViewModel(application) {

    private val clienteRepository: ClienteRepository
    private val servicoRepository: ServicoRepository
    private val agendamentoRepository: AgendamentoRepository

    val clientesList: StateFlow<List<ClienteEntity>>
    val servicosList: StateFlow<List<ServicoEntity>>

    val selectedCliente = MutableStateFlow<ClienteEntity?>(null)
    val selectedServico = MutableStateFlow<ServicoEntity?>(null)

    val selectedDateMillis = MutableStateFlow(DateUtils.getStartOfDay())
    val selectedHour = MutableStateFlow(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + 1)
    val selectedMinute = MutableStateFlow(0)

    val observacoesInput = MutableStateFlow("")

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    init {
        val db = AppDatabase.getDatabase(application)
        clienteRepository = ClienteRepository(db.clienteDao())
        servicoRepository = ServicoRepository(db.servicoDao())
        agendamentoRepository = AgendamentoRepository(db.agendamentoDao())

        clientesList = clienteRepository.getAllClientes().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        servicosList = servicoRepository.getAllServicos().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun onDateSelected(millis: Long) {
        selectedDateMillis.value = millis
    }

    fun onTimeSelected(hour: Int, minute: Int) {
        selectedHour.value = hour
        selectedMinute.value = minute
    }

    fun calculateStartAndEndMillis(): Pair<Long, Long>? {
        val servico = selectedServico.value ?: return null

        val calendar = Calendar.getInstance().apply {
            timeInMillis = selectedDateMillis.value
            set(Calendar.HOUR_OF_DAY, selectedHour.value)
            set(Calendar.MINUTE, selectedMinute.value)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val startMillis = calendar.timeInMillis
        val endMillis = startMillis + (servico.duracaoMinutos * 60 * 1000L)

        return Pair(startMillis, endMillis)
    }

    fun saveAgendamento(onSuccess: () -> Unit) {
        val cliente = selectedCliente.value
        if (cliente == null) {
            _errorMessage.value = "Por favor, selecione um cliente."
            return
        }

        val servico = selectedServico.value
        if (servico == null) {
            _errorMessage.value = "Por favor, selecione um serviço."
            return
        }

        val range = calculateStartAndEndMillis()
        if (range == null) {
            _errorMessage.value = "Ajuste a data e o horário do agendamento."
            return
        }

        val (startMillis, endMillis) = range

        if (startMillis < System.currentTimeMillis() - (5 * 60 * 1000L)) {
            _errorMessage.value = "Não é possível criar agendamentos em datas ou horários passados."
            return
        }

        _isSaving.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            // Check conflict
            val temConflito = agendamentoRepository.checkConflitoHorario(startMillis, endMillis)
            if (temConflito) {
                _isSaving.value = false
                _errorMessage.value = "Já existe outro agendamento neste mesmo horário! Escolha outro horário."
                return@launch
            }

            val newAgendamento = AgendamentoEntity(
                clienteId = cliente.id,
                servicoId = servico.id,
                dataHoraStart = startMillis,
                dataHoraEnd = endMillis,
                status = "PENDENTE",
                observacoes = observacoesInput.value.trim()
            )

            val agendamentoId = agendamentoRepository.insertAgendamento(newAgendamento)

            // Agendar Notificação Local
            NotificationHelper.scheduleAgendamentoNotification(
                context = getApplication(),
                agendamentoId = agendamentoId,
                clienteNome = cliente.nome,
                servicoNome = servico.nome,
                dataHoraStart = startMillis
            )

            _isSaving.value = false
            onSuccess()
        }
    }
}
