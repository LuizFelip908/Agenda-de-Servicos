package com.example.agendasdeservios.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "agendamentos",
    foreignKeys = [
        ForeignKey(
            entity = ClienteEntity::class,
            parentColumns = ["id"],
            childColumns = ["clienteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ServicoEntity::class,
            parentColumns = ["id"],
            childColumns = ["servicoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("clienteId"),
        Index("servicoId")
    ]
)
data class AgendamentoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val clienteId: Long,
    val servicoId: Long,
    val dataHoraStart: Long,
    val dataHoraEnd: Long,
    val status: String = "PENDENTE",
    val observacoes: String = ""
)
