package com.example.agendasdeservios.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.agendasdeservios.`data`.local.entity.ClienteEntity
import javax.`annotation`.processing.Generated
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
public class ClienteDao_Impl(
  __db: RoomDatabase,
) : ClienteDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfClienteEntity: EntityInsertAdapter<ClienteEntity>

  private val __deleteAdapterOfClienteEntity: EntityDeleteOrUpdateAdapter<ClienteEntity>

  private val __updateAdapterOfClienteEntity: EntityDeleteOrUpdateAdapter<ClienteEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfClienteEntity = object : EntityInsertAdapter<ClienteEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `clientes` (`id`,`nome`,`telefone`,`email`,`observacoes`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ClienteEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.nome)
        statement.bindText(3, entity.telefone)
        statement.bindText(4, entity.email)
        statement.bindText(5, entity.observacoes)
      }
    }
    this.__deleteAdapterOfClienteEntity = object : EntityDeleteOrUpdateAdapter<ClienteEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `clientes` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ClienteEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfClienteEntity = object : EntityDeleteOrUpdateAdapter<ClienteEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `clientes` SET `id` = ?,`nome` = ?,`telefone` = ?,`email` = ?,`observacoes` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ClienteEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.nome)
        statement.bindText(3, entity.telefone)
        statement.bindText(4, entity.email)
        statement.bindText(5, entity.observacoes)
        statement.bindLong(6, entity.id)
      }
    }
  }

  public override suspend fun insert(cliente: ClienteEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfClienteEntity.insertAndReturnId(_connection, cliente)
    _result
  }

  public override suspend fun delete(cliente: ClienteEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfClienteEntity.handle(_connection, cliente)
  }

  public override suspend fun update(cliente: ClienteEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfClienteEntity.handle(_connection, cliente)
  }

  public override fun getAllClientes(): Flow<List<ClienteEntity>> {
    val _sql: String = "SELECT * FROM clientes ORDER BY nome ASC"
    return createFlow(__db, false, arrayOf("clientes")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNome: Int = getColumnIndexOrThrow(_stmt, "nome")
        val _columnIndexOfTelefone: Int = getColumnIndexOrThrow(_stmt, "telefone")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfObservacoes: Int = getColumnIndexOrThrow(_stmt, "observacoes")
        val _result: MutableList<ClienteEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ClienteEntity
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
          _item = ClienteEntity(_tmpId,_tmpNome,_tmpTelefone,_tmpEmail,_tmpObservacoes)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getClienteById(id: Long): Flow<ClienteEntity?> {
    val _sql: String = "SELECT * FROM clientes WHERE id = ? LIMIT 1"
    return createFlow(__db, false, arrayOf("clientes")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNome: Int = getColumnIndexOrThrow(_stmt, "nome")
        val _columnIndexOfTelefone: Int = getColumnIndexOrThrow(_stmt, "telefone")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfObservacoes: Int = getColumnIndexOrThrow(_stmt, "observacoes")
        val _result: ClienteEntity?
        if (_stmt.step()) {
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
          _result = ClienteEntity(_tmpId,_tmpNome,_tmpTelefone,_tmpEmail,_tmpObservacoes)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun searchClientes(query: String): Flow<List<ClienteEntity>> {
    val _sql: String = "SELECT * FROM clientes WHERE nome LIKE '%' || ? || '%' OR telefone LIKE '%' || ? || '%' ORDER BY nome ASC"
    return createFlow(__db, false, arrayOf("clientes")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _argIndex = 2
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNome: Int = getColumnIndexOrThrow(_stmt, "nome")
        val _columnIndexOfTelefone: Int = getColumnIndexOrThrow(_stmt, "telefone")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfObservacoes: Int = getColumnIndexOrThrow(_stmt, "observacoes")
        val _result: MutableList<ClienteEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ClienteEntity
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
          _item = ClienteEntity(_tmpId,_tmpNome,_tmpTelefone,_tmpEmail,_tmpObservacoes)
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
