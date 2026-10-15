package com.example.agendasdeservios.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.agendasdeservios.data.local.entity.ClienteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cliente: ClienteEntity): Long

    @Update
    suspend fun update(cliente: ClienteEntity)

    @Delete
    suspend fun delete(cliente: ClienteEntity)

    @Query("SELECT * FROM clientes ORDER BY nome ASC")
    fun getAllClientes(): Flow<List<ClienteEntity>>

    @Query("SELECT * FROM clientes WHERE id = :id LIMIT 1")
    fun getClienteById(id: Long): Flow<ClienteEntity?>

    @Query("SELECT * FROM clientes WHERE nome LIKE '%' || :query || '%' OR telefone LIKE '%' || :query || '%' ORDER BY nome ASC")
    fun searchClientes(query: String): Flow<List<ClienteEntity>>
}
