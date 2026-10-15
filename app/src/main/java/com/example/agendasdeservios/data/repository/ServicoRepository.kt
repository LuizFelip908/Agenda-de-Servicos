package com.example.agendasdeservios.data.repository

import com.example.agendasdeservios.data.local.dao.ServicoDao
import com.example.agendasdeservios.data.local.entity.ServicoEntity
import kotlinx.coroutines.flow.Flow

class ServicoRepository(private val servicoDao: ServicoDao) {

    fun getAllServicos(): Flow<List<ServicoEntity>> {
        return servicoDao.getAllServicos()
    }

    fun getServicoById(id: Long): Flow<ServicoEntity?> {
        return servicoDao.getServicoById(id)
    }

    fun searchServicos(query: String): Flow<List<ServicoEntity>> {
        return servicoDao.searchServicos(query)
    }

    suspend fun insertServico(servico: ServicoEntity): Long {
        return servicoDao.insert(servico)
    }

    suspend fun updateServico(servico: ServicoEntity) {
        servicoDao.update(servico)
    }

    suspend fun deleteServico(servico: ServicoEntity) {
        servicoDao.delete(servico)
    }
}
