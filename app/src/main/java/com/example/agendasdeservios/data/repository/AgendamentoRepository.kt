package com.example.agendasdeservios.data.repository

import com.example.agendasdeservios.data.local.dao.AgendamentoDao
import com.example.agendasdeservios.data.local.entity.AgendamentoEntity
import com.example.agendasdeservios.data.local.relation.AgendamentoComDetalhes
import kotlinx.coroutines.flow.Flow

class AgendamentoRepository(private val agendamentoDao: AgendamentoDao) {

    fun getAllAgendamentosComDetalhes(): Flow<List<AgendamentoComDetalhes>> {
        return agendamentoDao.getAllAgendamentosComDetalhes()
    }

    fun getAgendamentosPorRange(startMillis: Long, endMillis: Long): Flow<List<AgendamentoComDetalhes>> {
        return agendamentoDao.getAgendamentosPorRange(startMillis, endMillis)
    }

    suspend fun checkConflitoHorario(startMillis: Long, endMillis: Long, ignoreId: Long = 0): Boolean {
        val conflitos = agendamentoDao.getConflitosHorario(startMillis, endMillis, ignoreId)
        return conflitos.isNotEmpty()
    }

    suspend fun insertAgendamento(agendamento: AgendamentoEntity): Long {
        return agendamentoDao.insert(agendamento)
    }

    suspend fun updateAgendamento(agendamento: AgendamentoEntity) {
        agendamentoDao.update(agendamento)
    }

    suspend fun updateStatus(id: Long, status: String) {
        agendamentoDao.updateStatus(id, status)
    }

    suspend fun deleteAgendamento(agendamento: AgendamentoEntity) {
        agendamentoDao.delete(agendamento)
    }
}
