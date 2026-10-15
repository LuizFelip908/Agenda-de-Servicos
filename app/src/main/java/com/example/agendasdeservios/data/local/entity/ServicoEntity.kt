package com.example.agendasdeservios.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servicos")
data class ServicoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val descricao: String = "",
    val duracaoMinutos: Int,
    val preco: Double
)
