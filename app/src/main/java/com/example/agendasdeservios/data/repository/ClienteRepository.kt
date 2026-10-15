package com.example.agendasdeservios.data.repository

import com.example.agendasdeservios.data.local.dao.ClienteDao
import com.example.agendasdeservios.data.local.entity.ClienteEntity
import kotlinx.coroutines.flow.Flow

class ClienteRepository(private val clienteDao: ClienteDao) {

    fun getAllClientes(): Flow<List<ClienteEntity>> {
        return clienteDao.getAllClientes()
    }

    fun getClienteById(id: Long): Flow<ClienteEntity?> {
        return clienteDao.getClienteById(id)
    }

    fun searchClientes(query: String): Flow<List<ClienteEntity>> {
        return clienteDao.searchClientes(query)
    }

    suspend fun insertCliente(cliente: ClienteEntity): Long {
        return clienteDao.insert(cliente)
    }

    suspend fun updateCliente(cliente: ClienteEntity) {
        clienteDao.update(cliente)
    }

    suspend fun deleteCliente(cliente: ClienteEntity) {
        clienteDao.delete(cliente)
    }
}
