package com.example.agendasdeservios.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendasdeservios.data.local.AppDatabase
import com.example.agendasdeservios.data.local.entity.ClienteEntity
import com.example.agendasdeservios.data.repository.ClienteRepository
import com.example.agendasdeservios.utils.PhoneMaskUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ClienteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ClienteRepository

    val searchQuery = MutableStateFlow("")

    val clientes: StateFlow<List<ClienteEntity>>

    private val _isDialogOpen = MutableStateFlow(false)
    val isDialogOpen: StateFlow<Boolean> = _isDialogOpen.asStateFlow()

    private val _editingCliente = MutableStateFlow<ClienteEntity?>(null)
    val editingCliente: StateFlow<ClienteEntity?> = _editingCliente.asStateFlow()

    val nomeInput = MutableStateFlow("")
    val telefoneInput = MutableStateFlow("")
    val emailInput = MutableStateFlow("")
    val observacoesInput = MutableStateFlow("")

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        val clienteDao = AppDatabase.getDatabase(application).clienteDao()
        repository = ClienteRepository(clienteDao)

        clientes = searchQuery.flatMapLatest { query ->
            if (query.isBlank()) {
                repository.getAllClientes()
            } else {
                repository.searchClientes(query)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery.value = newQuery
    }

    fun openAddDialog() {
        _editingCliente.value = null
        nomeInput.value = ""
        telefoneInput.value = ""
        emailInput.value = ""
        observacoesInput.value = ""
        _errorMessage.value = null
        _isDialogOpen.value = true
    }

    fun openEditDialog(cliente: ClienteEntity) {
        _editingCliente.value = cliente
        nomeInput.value = cliente.nome
        telefoneInput.value = PhoneMaskUtils.formatPhone(cliente.telefone)
        emailInput.value = cliente.email
        observacoesInput.value = cliente.observacoes
        _errorMessage.value = null
        _isDialogOpen.value = true
    }

    fun closeDialog() {
        _isDialogOpen.value = false
        _errorMessage.value = null
    }

    fun onTelefoneChange(value: String) {
        telefoneInput.value = PhoneMaskUtils.formatPhone(value)
    }

    fun saveCliente() {
        val nome = nomeInput.value.trim()
        val telefoneFormatted = telefoneInput.value.trim()
        val telefoneRaw = PhoneMaskUtils.unformat(telefoneFormatted)
        val email = emailInput.value.trim()
        val observacoes = observacoesInput.value.trim()

        if (nome.isBlank()) {
            _errorMessage.value = "O nome do cliente é obrigatório."
            return
        }

        if (telefoneRaw.isBlank() || !PhoneMaskUtils.isValidPhone(telefoneRaw)) {
            _errorMessage.value = "Informe um telefone válido com DDD (10 ou 11 dígitos)."
            return
        }

        viewModelScope.launch {
            val clienteToSave = ClienteEntity(
                id = _editingCliente.value?.id ?: 0,
                nome = nome,
                telefone = telefoneRaw,
                email = email,
                observacoes = observacoes
            )

            if (_editingCliente.value == null) {
                repository.insertCliente(clienteToSave)
            } else {
                repository.updateCliente(clienteToSave)
            }

            closeDialog()
        }
    }

    fun deleteCliente(cliente: ClienteEntity) {
        viewModelScope.launch {
            repository.deleteCliente(cliente)
        }
    }
}
