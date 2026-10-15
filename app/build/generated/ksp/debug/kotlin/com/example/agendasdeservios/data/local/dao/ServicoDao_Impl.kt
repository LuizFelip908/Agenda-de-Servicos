package com.example.agendasdeservios.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.agendasdeservios.`data`.local.entity.ServicoEntity
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
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ServicoDao_Impl(
  __db: RoomDatabase,
) : ServicoDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfServicoEntity: EntityInsertAdapter<ServicoEntity>

  private val __deleteAdapterOfServicoEntity: EntityDeleteOrUpdateAdapter<ServicoEntity>

  private val __updateAdapterOfServicoEntity: EntityDeleteOrUpdateAdapter<ServicoEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfServicoEntity = object : EntityInsertAdapter<ServicoEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `servicos` (`id`,`nome`,`descricao`,`duracaoMinutos`,`preco`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ServicoEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.nome)
        statement.bindText(3, entity.descricao)
        statement.bindLong(4, entity.duracaoMinutos.toLong())
        statement.bindDouble(5, entity.preco)
      }
    }
    this.__deleteAdapterOfServicoEntity = object : EntityDeleteOrUpdateAdapter<ServicoEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `servicos` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ServicoEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfServicoEntity = object : EntityDeleteOrUpdateAdapter<ServicoEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `servicos` SET `id` = ?,`nome` = ?,`descricao` = ?,`duracaoMinutos` = ?,`preco` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ServicoEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.nome)
        statement.bindText(3, entity.descricao)
        statement.bindLong(4, entity.duracaoMinutos.toLong())
        statement.bindDouble(5, entity.preco)
        statement.bindLong(6, entity.id)
      }
    }
  }

  public override suspend fun insert(servico: ServicoEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfServicoEntity.insertAndReturnId(_connection, servico)
    _result
  }

  public override suspend fun delete(servico: ServicoEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfServicoEntity.handle(_connection, servico)
  }

  public override suspend fun update(servico: ServicoEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfServicoEntity.handle(_connection, servico)
  }

  public override fun getAllServicos(): Flow<List<ServicoEntity>> {
    val _sql: String = "SELECT * FROM servicos ORDER BY nome ASC"
    return createFlow(__db, false, arrayOf("servicos")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNome: Int = getColumnIndexOrThrow(_stmt, "nome")
        val _columnIndexOfDescricao: Int = getColumnIndexOrThrow(_stmt, "descricao")
        val _columnIndexOfDuracaoMinutos: Int = getColumnIndexOrThrow(_stmt, "duracaoMinutos")
        val _columnIndexOfPreco: Int = getColumnIndexOrThrow(_stmt, "preco")
        val _result: MutableList<ServicoEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ServicoEntity
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
          _item = ServicoEntity(_tmpId,_tmpNome,_tmpDescricao,_tmpDuracaoMinutos,_tmpPreco)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getServicoById(id: Long): Flow<ServicoEntity?> {
    val _sql: String = "SELECT * FROM servicos WHERE id = ? LIMIT 1"
    return createFlow(__db, false, arrayOf("servicos")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNome: Int = getColumnIndexOrThrow(_stmt, "nome")
        val _columnIndexOfDescricao: Int = getColumnIndexOrThrow(_stmt, "descricao")
        val _columnIndexOfDuracaoMinutos: Int = getColumnIndexOrThrow(_stmt, "duracaoMinutos")
        val _columnIndexOfPreco: Int = getColumnIndexOrThrow(_stmt, "preco")
        val _result: ServicoEntity?
        if (_stmt.step()) {
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
          _result = ServicoEntity(_tmpId,_tmpNome,_tmpDescricao,_tmpDuracaoMinutos,_tmpPreco)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun searchServicos(query: String): Flow<List<ServicoEntity>> {
    val _sql: String = "SELECT * FROM servicos WHERE nome LIKE '%' || ? || '%' ORDER BY nome ASC"
    return createFlow(__db, false, arrayOf("servicos")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNome: Int = getColumnIndexOrThrow(_stmt, "nome")
        val _columnIndexOfDescricao: Int = getColumnIndexOrThrow(_stmt, "descricao")
        val _columnIndexOfDuracaoMinutos: Int = getColumnIndexOrThrow(_stmt, "duracaoMinutos")
        val _columnIndexOfPreco: Int = getColumnIndexOrThrow(_stmt, "preco")
        val _result: MutableList<ServicoEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ServicoEntity
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
          _item = ServicoEntity(_tmpId,_tmpNome,_tmpDescricao,_tmpDuracaoMinutos,_tmpPreco)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
