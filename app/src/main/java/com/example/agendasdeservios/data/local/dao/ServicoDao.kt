package com.example.agendasdeservios.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.agendasdeservios.data.local.entity.ServicoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(servico: ServicoEntity): Long

    @Update
    suspend fun update(servico: ServicoEntity)

    @Delete
    suspend fun delete(servico: ServicoEntity)

    @Query("SELECT * FROM servicos ORDER BY nome ASC")
    fun getAllServicos(): Flow<List<ServicoEntity>>

    @Query("SELECT * FROM servicos WHERE id = :id LIMIT 1")
    fun getServicoById(id: Long): Flow<ServicoEntity?>

    @Query("SELECT * FROM servicos WHERE nome LIKE '%' || :query || '%' ORDER BY nome ASC")
    fun searchServicos(query: String): Flow<List<ServicoEntity>>
}
