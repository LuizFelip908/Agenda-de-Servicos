package com.example.agendasdeservios.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendasdeservios.data.local.AppDatabase
import com.example.agendasdeservios.data.local.entity.ServicoEntity
import com.example.agendasdeservios.data.repository.ServicoRepository
import com.example.agendasdeservios.utils.CurrencyUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ServicoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ServicoRepository

    val searchQuery = MutableStateFlow("")

    val servicos: StateFlow<List<ServicoEntity>>

    private val _isDialogOpen = MutableStateFlow(false)
    val isDialogOpen: StateFlow<Boolean> = _isDialogOpen.asStateFlow()

    private val _editingServico = MutableStateFlow<ServicoEntity?>(null)
    val editingServico: StateFlow<ServicoEntity?> = _editingServico.asStateFlow()

    val nomeInput = MutableStateFlow("")
    val descricaoInput = MutableStateFlow("")
    val duracaoInput = MutableStateFlow("")
    val precoInput = MutableStateFlow("")

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        val servicoDao = AppDatabase.getDatabase(application).servicoDao()
        repository = ServicoRepository(servicoDao)

        servicos = searchQuery.flatMapLatest { query ->
            if (query.isBlank()) {
                repository.getAllServicos()
            } else {
                repository.searchServicos(query)
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
        _editingServico.value = null
        nomeInput.value = ""
        descricaoInput.value = ""
        duracaoInput.value = "30"
        precoInput.value = ""
        _errorMessage.value = null
        _isDialogOpen.value = true
    }

    fun openEditDialog(servico: ServicoEntity) {
        _editingServico.value = servico
        nomeInput.value = servico.nome
        descricaoInput.value = servico.descricao
        duracaoInput.value = servico.duracaoMinutos.toString()
        precoInput.value = String.format("%.2f", servico.preco).replace(".", ",")
        _errorMessage.value = null
        _isDialogOpen.value = true
    }

    fun closeDialog() {
        _isDialogOpen.value = false
        _errorMessage.value = null
    }

    fun saveServico() {
        val nome = nomeInput.value.trim()
        val descricao = descricaoInput.value.trim()
        val duracaoStr = duracaoInput.value.trim()
        val precoStr = precoInput.value.trim()

        if (nome.isBlank()) {
            _errorMessage.value = "O nome do serviço é obrigatório."
            return
        }

        val duracaoMin = duracaoStr.toIntOrNull()
        if (duracaoMin == null || duracaoMin <= 0) {
            _errorMessage.value = "Informe uma duração válida em minutos (ex: 30)."
            return
        }

        val precoDouble = CurrencyUtils.parseCurrency(precoStr)
        if (precoDouble == null || precoDouble < 0) {
            _errorMessage.value = "Informe um preço válido (ex: 50,00)."
            return
        }

        viewModelScope.launch {
            val servicoToSave = ServicoEntity(
                id = _editingServico.value?.id ?: 0,
                nome = nome,
                descricao = descricao,
                duracaoMinutos = duracaoMin,
                preco = precoDouble
            )

            if (_editingServico.value == null) {
                repository.insertServico(servicoToSave)
            } else {
                repository.updateServico(servicoToSave)
            }

            closeDialog()
        }
    }

    fun deleteServico(servico: ServicoEntity) {
        viewModelScope.launch {
            repository.deleteServico(servico)
        }
    }
}
