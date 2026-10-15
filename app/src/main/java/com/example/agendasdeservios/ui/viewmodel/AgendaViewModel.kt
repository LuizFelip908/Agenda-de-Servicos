package com.example.agendasdeservios.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendasdeservios.data.local.AppDatabase
import com.example.agendasdeservios.data.local.entity.AgendamentoEntity
import com.example.agendasdeservios.data.local.relation.AgendamentoComDetalhes
import com.example.agendasdeservios.data.repository.AgendamentoRepository
import com.example.agendasdeservios.utils.DateUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class AgendaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AgendamentoRepository

    val selectedDateMillis = MutableStateFlow(DateUtils.getStartOfDay())
    val selectedStatusFilter = MutableStateFlow("TODOS")
    val searchQuery = MutableStateFlow("")

    val agendamentos: StateFlow<List<AgendamentoComDetalhes>>

    private val _isDatePickerOpen = MutableStateFlow(false)
    val isDatePickerOpen: StateFlow<Boolean> = _isDatePickerOpen.asStateFlow()

    init {
        val agendamentoDao = AppDatabase.getDatabase(application).agendamentoDao()
        repository = AgendamentoRepository(agendamentoDao)

        val rawAgendamentosFlow = selectedDateMillis.flatMapLatest { dateMillis ->
            val start = DateUtils.getStartOfDay(dateMillis)
            val end = DateUtils.getEndOfDay(dateMillis)
            repository.getAgendamentosPorRange(start, end)
        }

        agendamentos = combine(
            rawAgendamentosFlow,
            selectedStatusFilter,
            searchQuery
        ) { list, status, query ->
            list.filter { item ->
                val matchesStatus = if (status == "TODOS") true else item.agendamento.status.equals(status, ignoreCase = true)
                val matchesQuery = if (query.isBlank()) true else {
                    item.cliente.nome.contains(query, ignoreCase = true) ||
                    item.servico.nome.contains(query, ignoreCase = true) ||
                    item.cliente.telefone.contains(query, ignoreCase = true)
                }
                matchesStatus && matchesQuery
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun onDateSelected(millis: Long) {
        selectedDateMillis.value = millis
        _isDatePickerOpen.value = false
    }

    fun openDatePicker() {
        _isDatePickerOpen.value = true
    }

    fun closeDatePicker() {
        _isDatePickerOpen.value = false
    }

    fun onStatusFilterSelected(status: String) {
        selectedStatusFilter.value = status
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery.value = newQuery
    }

    fun updateAgendamentoStatus(agendamentoId: Long, newStatus: String) {
        viewModelScope.launch {
            repository.updateStatus(agendamentoId, newStatus)
        }
    }

    fun deleteAgendamento(agendamento: AgendamentoEntity) {
        viewModelScope.launch {
            repository.deleteAgendamento(agendamento)
        }
    }
}
