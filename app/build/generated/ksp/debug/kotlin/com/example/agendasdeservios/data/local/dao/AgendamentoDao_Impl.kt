package com.example.agendasdeservios.`data`.local.dao

import androidx.collection.LongSparseArray
import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.appendPlaceholders
import androidx.room.util.getColumnIndex
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.room.util.recursiveFetchLongSparseArray
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.SQLiteStatement
import com.example.agendasdeservios.`data`.local.entity.AgendamentoEntity
import com.example.agendasdeservios.`data`.local.entity.ClienteEntity
import com.example.agendasdeservios.`data`.local.entity.ServicoEntity
import com.example.agendasdeservios.`data`.local.relation.AgendamentoComDetalhes
import javax.`annotation`.processing.Generated
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlin.text.StringBuilder
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AgendamentoDao_Impl(
  __db: RoomDatabase,
) : AgendamentoDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfAgendamentoEntity: EntityInsertAdapter<AgendamentoEntity>

  private val __deleteAdapterOfAgendamentoEntity: EntityDeleteOrUpdateAdapter<AgendamentoEntity>

  private val __updateAdapterOfAgendamentoEntity: EntityDeleteOrUpdateAdapter<AgendamentoEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfAgendamentoEntity = object : EntityInsertAdapter<AgendamentoEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `agendamentos` (`id`,`clienteId`,`servicoId`,`dataHoraStart`,`dataHoraEnd`,`status`,`observacoes`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: AgendamentoEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.clienteId)
        statement.bindLong(3, entity.servicoId)
        statement.bindLong(4, entity.dataHoraStart)
        statement.bindLong(5, entity.dataHoraEnd)
        statement.bindText(6, entity.status)
        statement.bindText(7, entity.observacoes)
      }
    }
    this.__deleteAdapterOfAgendamentoEntity = object : EntityDeleteOrUpdateAdapter<AgendamentoEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `agendamentos` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: AgendamentoEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfAgendamentoEntity = object : EntityDeleteOrUpdateAdapter<AgendamentoEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `agendamentos` SET `id` = ?,`clienteId` = ?,`servicoId` = ?,`dataHoraStart` = ?,`dataHoraEnd` = ?,`status` = ?,`observacoes` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: AgendamentoEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.clienteId)
        statement.bindLong(3, entity.servicoId)
        statement.bindLong(4, entity.dataHoraStart)
        statement.bindLong(5, entity.dataHoraEnd)
        statement.bindText(6, entity.status)
        statement.bindText(7, entity.observacoes)
        statement.bindLong(8, entity.id)
      }
    }
  }

  public override suspend fun insert(agendamento: AgendamentoEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfAgendamentoEntity.insertAndReturnId(_connection, agendamento)
    _result
  }

  public override suspend fun delete(agendamento: AgendamentoEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfAgendamentoEntity.handle(_connection, agendamento)
  }

  public override suspend fun update(agendamento: AgendamentoEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfAgendamentoEntity.handle(_connection, agendamento)
  }

  public override fun getAllAgendamentosComDetalhes(): Flow<List<AgendamentoComDetalhes>> {
    val _sql: String = "SELECT * FROM agendamentos ORDER BY dataHoraStart ASC"
    return createFlow(__db, true, arrayOf("clientes", "servicos", "agendamentos")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfClienteId: Int = getColumnIndexOrThrow(_stmt, "clienteId")
        val _columnIndexOfServicoId: Int = getColumnIndexOrThrow(_stmt, "servicoId")
        val _columnIndexOfDataHoraStart: Int = getColumnIndexOrThrow(_stmt, "dataHoraStart")
        val _columnIndexOfDataHoraEnd: Int = getColumnIndexOrThrow(_stmt, "dataHoraEnd")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfObservacoes: Int = getColumnIndexOrThrow(_stmt, "observacoes")
        val _collectionCliente: LongSparseArray<ClienteEntity?> = LongSparseArray<ClienteEntity?>()
        val _collectionServico: LongSparseArray<ServicoEntity?> = LongSparseArray<ServicoEntity?>()
        while (_stmt.step()) {
          val _tmpKey: Long
          _tmpKey = _stmt.getLong(_columnIndexOfClienteId)
          _collectionCliente.put(_tmpKey, null)
          val _tmpKey_1: Long
          _tmpKey_1 = _stmt.getLong(_columnIndexOfServicoId)
          _collectionServico.put(_tmpKey_1, null)
        }
        _stmt.reset()
        __fetchRelationshipclientesAscomExampleAgendasdeserviosDataLocalEntityClienteEntity(_connection, _collectionCliente)
        __fetchRelationshipservicosAscomExampleAgendasdeserviosDataLocalEntityServicoEntity(_connection, _collectionServico)
        val _result: MutableList<AgendamentoComDetalhes> = mutableListOf()
        while (_stmt.step()) {
          val _item: AgendamentoComDetalhes
          val _tmpAgendamento: AgendamentoEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpClienteId: Long
          _tmpClienteId = _stmt.getLong(_columnIndexOfClienteId)
          val _tmpServicoId: Long
          _tmpServicoId = _stmt.getLong(_columnIndexOfServicoId)
          val _tmpDataHoraStart: Long
          _tmpDataHoraStart = _stmt.getLong(_columnIndexOfDataHoraStart)
          val _tmpDataHoraEnd: Long
          _tmpDataHoraEnd = _stmt.getLong(_columnIndexOfDataHoraEnd)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpObservacoes: String
          _tmpObservacoes = _stmt.getText(_columnIndexOfObservacoes)
          _tmpAgendamento = AgendamentoEntity(_tmpId,_tmpClienteId,_tmpServicoId,_tmpDataHoraStart,_tmpDataHoraEnd,_tmpStatus,_tmpObservacoes)
          val _tmpCliente: ClienteEntity?
          val _tmpKey_2: Long
          _tmpKey_2 = _stmt.getLong(_columnIndexOfClienteId)
          _tmpCliente = _collectionCliente.get(_tmpKey_2)
          if (_tmpCliente == null) {
            error("Relationship item 'cliente' was expected to be NON-NULL but is NULL in @Relation involving a parent column named 'clienteId' and entityColumn named 'id'.")
          }
          val _tmpServico: ServicoEntity?
          val _tmpKey_3: Long
          _tmpKey_3 = _stmt.getLong(_columnIndexOfServicoId)
          _tmpServico = _collectionServico.get(_tmpKey_3)
          if (_tmpServico == null) {
            error("Relationship item 'servico' was expected to be NON-NULL but is NULL in @Relation involving a parent column named 'servicoId' and entityColumn named 'id'.")
          }
          _item = AgendamentoComDetalhes(_tmpAgendamento,_tmpCliente,_tmpServico)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAgendamentosPorRange(startMillis: Long, endMillis: Long): Flow<List<AgendamentoComDetalhes>> {
    val _sql: String = "SELECT * FROM agendamentos WHERE dataHoraStart >= ? AND dataHoraStart <= ? ORDER BY dataHoraStart ASC"
    return createFlow(__db, true, arrayOf("clientes", "servicos", "agendamentos")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, startMillis)
        _argIndex = 2
        _stmt.bindLong(_argIndex, endMillis)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfClienteId: Int = getColumnIndexOrThrow(_stmt, "clienteId")
        val _columnIndexOfServicoId: Int = getColumnIndexOrThrow(_stmt, "servicoId")
        val _columnIndexOfDataHoraStart: Int = getColumnIndexOrThrow(_stmt, "dataHoraStart")
        val _columnIndexOfDataHoraEnd: Int = getColumnIndexOrThrow(_stmt, "dataHoraEnd")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfObservacoes: Int = getColumnIndexOrThrow(_stmt, "observacoes")
        val _collectionCliente: LongSparseArray<ClienteEntity?> = LongSparseArray<ClienteEntity?>()
        val _collectionServico: LongSparseArray<ServicoEntity?> = LongSparseArray<ServicoEntity?>()
        while (_stmt.step()) {
          val _tmpKey: Long
          _tmpKey = _stmt.getLong(_columnIndexOfClienteId)
          _collectionCliente.put(_tmpKey, null)
          val _tmpKey_1: Long
          _tmpKey_1 = _stmt.getLong(_columnIndexOfServicoId)
          _collectionServico.put(_tmpKey_1, null)
        }
        _stmt.reset()
        __fetchRelationshipclientesAscomExampleAgendasdeserviosDataLocalEntityClienteEntity(_connection, _collectionCliente)
        __fetchRelationshipservicosAscomExampleAgendasdeserviosDataLocalEntityServicoEntity(_connection, _collectionServico)
        val _result: MutableList<AgendamentoComDetalhes> = mutableListOf()
        while (_stmt.step()) {
          val _item: AgendamentoComDetalhes
          val _tmpAgendamento: AgendamentoEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpClienteId: Long
          _tmpClienteId = _stmt.getLong(_columnIndexOfClienteId)
          val _tmpServicoId: Long
          _tmpServicoId = _stmt.getLong(_columnIndexOfServicoId)
          val _tmpDataHoraStart: Long
          _tmpDataHoraStart = _stmt.getLong(_columnIndexOfDataHoraStart)
          val _tmpDataHoraEnd: Long
          _tmpDataHoraEnd = _stmt.getLong(_columnIndexOfDataHoraEnd)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpObservacoes: String
          _tmpObservacoes = _stmt.getText(_columnIndexOfObservacoes)
          _tmpAgendamento = AgendamentoEntity(_tmpId,_tmpClienteId,_tmpServicoId,_tmpDataHoraStart,_tmpDataHoraEnd,_tmpStatus,_tmpObservacoes)
          val _tmpCliente: ClienteEntity?
          val _tmpKey_2: Long
          _tmpKey_2 = _stmt.getLong(_columnIndexOfClienteId)
          _tmpCliente = _collectionCliente.get(_tmpKey_2)
          if (_tmpCliente == null) {
            error("Relationship item 'cliente' was expected to be NON-NULL but is NULL in @Relation involving a parent column named 'clienteId' and entityColumn named 'id'.")
          }
          val _tmpServico: ServicoEntity?
          val _tmpKey_3: Long
          _tmpKey_3 = _stmt.getLong(_columnIndexOfServicoId)
          _tmpServico = _collectionServico.get(_tmpKey_3)
          if (_tmpServico == null) {
            error("Relationship item 'servico' was expected to be NON-NULL but is NULL in @Relation involving a parent column named 'servicoId' and entityColumn named 'id'.")
          }
          _item = AgendamentoComDetalhes(_tmpAgendamento,_tmpCliente,_tmpServico)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getConflitosHorario(
    startMillis: Long,
    endMillis: Long,
    ignoreId: Long,
  ): List<AgendamentoEntity> {
    val _sql: String = """
        |
        |        SELECT * FROM agendamentos 
        |        WHERE status != 'CANCELADO' 
        |        AND id != ? 
        |        AND (? < dataHoraEnd AND ? > dataHoraStart)
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, ignoreId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, startMillis)
        _argIndex = 3
        _stmt.bindLong(_argIndex, endMillis)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfClienteId: Int = getColumnIndexOrThrow(_stmt, "clienteId")
        val _columnIndexOfServicoId: Int = getColumnIndexOrThrow(_stmt, "servicoId")
        val _columnIndexOfDataHoraStart: Int = getColumnIndexOrThrow(_stmt, "dataHoraStart")
        val _columnIndexOfDataHoraEnd: Int = getColumnIndexOrThrow(_stmt, "dataHoraEnd")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfObservacoes: Int = getColumnIndexOrThrow(_stmt, "observacoes")
        val _result: MutableList<AgendamentoEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AgendamentoEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpClienteId: Long
          _tmpClienteId = _stmt.getLong(_columnIndexOfClienteId)
          val _tmpServicoId: Long
          _tmpServicoId = _stmt.getLong(_columnIndexOfServicoId)
          val _tmpDataHoraStart: Long
          _tmpDataHoraStart = _stmt.getLong(_columnIndexOfDataHoraStart)
          val _tmpDataHoraEnd: Long
          _tmpDataHoraEnd = _stmt.getLong(_columnIndexOfDataHoraEnd)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpObservacoes: String
          _tmpObservacoes = _stmt.getText(_columnIndexOfObservacoes)
          _item = AgendamentoEntity(_tmpId,_tmpClienteId,_tmpServicoId,_tmpDataHoraStart,_tmpDataHoraEnd,_tmpStatus,_tmpObservacoes)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateStatus(id: Long, status: String) {
    val _sql: String = "UPDATE agendamentos SET status = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, status)
        _argIndex = 2
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  private fun __fetchRelationshipclientesAscomExampleAgendasdeserviosDataLocalEntityClienteEntity(_connection: SQLiteConnection, _map: LongSparseArray<ClienteEntity?>) {
    if (_map.isEmpty()) {
      return
    }
    if (_map.size() > 999) {
      recursiveFetchLongSparseArray(_map, false) { _tmpMap ->
        __fetchRelationshipclientesAscomExampleAgendasdeserviosDataLocalEntityClienteEntity(_connection, _tmpMap)
      }
      return
    }
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT `id`,`nome`,`telefone`,`email`,`observacoes` FROM `clientes` WHERE `id` IN (")
    val _inputSize: Int = _map.size()
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    val _sql: String = _stringBuilder.toString()
    val _stmt: SQLiteStatement = _connection.prepare(_sql)
    var _argIndex: Int = 1
    for (i in 0 until _map.size()) {
      val _item: Long = _map.keyAt(i)
      _stmt.bindLong(_argIndex, _item)
      _argIndex++
    }
    try {
      val _itemKeyIndex: Int = getColumnIndex(_stmt, "id")
      if (_itemKeyIndex == -1) {
        return
      }
      val _columnIndexOfId: Int = 0
      val _columnIndexOfNome: Int = 1
      val _columnIndexOfTelefone: Int = 2
      val _columnIndexOfEmail: Int = 3
      val _columnIndexOfObservacoes: Int = 4
      while (_stmt.step()) {
        val _tmpKey: Long
        _tmpKey = _stmt.getLong(_itemKeyIndex)
        if (_map.containsKey(_tmpKey)) {
          val _item_1: ClienteEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpNome: String
          _tmpNome = _stmt.getText(_columnIndexOfNome)
          val _tmpTelefone: String
          _tmpTelefone = _stmt.getText(_columnIndexOfTelefone)
          val _tmpEmail: String
          _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          val _tmpObservacoes: String
          _tmpObservacoes = _stmt.getText(_columnIndexOfObservacoes)
          _item_1 = ClienteEntity(_tmpId,_tmpNome,_tmpTelefone,_tmpEmail,_tmpObservacoes)
          _map.put(_tmpKey, _item_1)
        }
      }
    } finally {
      _stmt.close()
    }
  }

  private fun __fetchRelationshipservicosAscomExampleAgendasdeserviosDataLocalEntityServicoEntity(_connection: SQLiteConnection, _map: LongSparseArray<ServicoEntity?>) {
    if (_map.isEmpty()) {
      return
    }
    if (_map.size() > 999) {
      recursiveFetchLongSparseArray(_map, false) { _tmpMap ->
        __fetchRelationshipservicosAscomExampleAgendasdeserviosDataLocalEntityServicoEntity(_connection, _tmpMap)
      }
      return
    }
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT `id`,`nome`,`descricao`,`duracaoMinutos`,`preco` FROM `servicos` WHERE `id` IN (")
    val _inputSize: Int = _map.size()
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    val _sql: String = _stringBuilder.toString()
    val _stmt: SQLiteStatement = _connection.prepare(_sql)
    var _argIndex: Int = 1
    for (i in 0 until _map.size()) {
      val _item: Long = _map.keyAt(i)
      _stmt.bindLong(_argIndex, _item)
      _argIndex++
    }
    try {
      val _itemKeyIndex: Int = getColumnIndex(_stmt, "id")
      if (_itemKeyIndex == -1) {
        return
      }
      val _columnIndexOfId: Int = 0
      val _columnIndexOfNome: Int = 1
      val _columnIndexOfDescricao: Int = 2
      val _columnIndexOfDuracaoMinutos: Int = 3
      val _columnIndexOfPreco: Int = 4
      while (_stmt.step()) {
        val _tmpKey: Long
        _tmpKey = _stmt.getLong(_itemKeyIndex)
        if (_map.containsKey(_tmpKey)) {
          val _item_1: ServicoEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpNome: String
          _tmpNome = _stmt.getText(_columnIndexOfNome)
          val _tmpDescricao: String
          _tmpDescricao = _stmt.getText(_columnIndexOfDescricao)
          val _tmpDuracaoMinutos: Int
          _tmpDuracaoMinutos = _stmt.getLong(_columnIndexOfDuracaoMinutos).toInt()
          val _tmpPreco: Double
          _tmpPreco = _stmt.getDouble(_columnIndexOfPreco)
          _item_1 = ServicoEntity(_tmpId,_tmpNome,_tmpDescricao,_tmpDuracaoMinutos,_tmpPreco)
          _map.put(_tmpKey, _item_1)
        }
      }
    } finally {
      _stmt.close()
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
