package com.example.agendasdeservios.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.agendasdeservios.data.local.entity.AgendamentoEntity
import com.example.agendasdeservios.data.local.entity.ClienteEntity
import com.example.agendasdeservios.data.local.entity.ServicoEntity

data class AgendamentoComDetalhes(
    @Embedded
    val agendamento: AgendamentoEntity,

    @Relation(
        parentColumn = "clienteId",
        entityColumn = "id"
    )
    val cliente: ClienteEntity,

    @Relation(
        parentColumn = "servicoId",
        entityColumn = "id"
    )
    val servico: ServicoEntity
)
