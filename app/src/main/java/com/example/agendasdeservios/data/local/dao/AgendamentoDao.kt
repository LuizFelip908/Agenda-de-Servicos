package com.example.agendasdeservios.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.agendasdeservios.data.local.entity.AgendamentoEntity
import com.example.agendasdeservios.data.local.relation.AgendamentoComDetalhes
import kotlinx.coroutines.flow.Flow

@Dao
interface AgendamentoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(agendamento: AgendamentoEntity): Long

    @Update
    suspend fun update(agendamento: AgendamentoEntity)

    @Delete
    suspend fun delete(agendamento: AgendamentoEntity)

    @Query("UPDATE agendamentos SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String)

    @Transaction
    @Query("SELECT * FROM agendamentos ORDER BY dataHoraStart ASC")
    fun getAllAgendamentosComDetalhes(): Flow<List<AgendamentoComDetalhes>>

    @Transaction
    @Query("SELECT * FROM agendamentos WHERE dataHoraStart >= :startMillis AND dataHoraStart <= :endMillis ORDER BY dataHoraStart ASC")
    fun getAgendamentosPorRange(startMillis: Long, endMillis: Long): Flow<List<AgendamentoComDetalhes>>

    @Query("""
        SELECT * FROM agendamentos 
        WHERE status != 'CANCELADO' 
        AND id != :ignoreId 
        AND (:startMillis < dataHoraEnd AND :endMillis > dataHoraStart)
    """)
    suspend fun getConflitosHorario(startMillis: Long, endMillis: Long, ignoreId: Long = 0): List<AgendamentoEntity>
}
