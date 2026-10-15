package com.example.agendasdeservios.`data`.local

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.example.agendasdeservios.`data`.local.dao.AgendamentoDao
import com.example.agendasdeservios.`data`.local.dao.AgendamentoDao_Impl
import com.example.agendasdeservios.`data`.local.dao.ClienteDao
import com.example.agendasdeservios.`data`.local.dao.ClienteDao_Impl
import com.example.agendasdeservios.`data`.local.dao.ServicoDao
import com.example.agendasdeservios.`data`.local.dao.ServicoDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _clienteDao: Lazy<ClienteDao> = lazy {
    ClienteDao_Impl(this)
  }

  private val _servicoDao: Lazy<ServicoDao> = lazy {
    ServicoDao_Impl(this)
  }

  private val _agendamentoDao: Lazy<AgendamentoDao> = lazy {
    AgendamentoDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(2, "4fe2b2830713acaf26bdf8eeea872400", "e7295a8f88423b69eda1f3e042c062c6") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `clientes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT NOT NULL, `telefone` TEXT NOT NULL, `email` TEXT NOT NULL, `observacoes` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `servicos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT NOT NULL, `descricao` TEXT NOT NULL, `duracaoMinutos` INTEGER NOT NULL, `preco` REAL NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `agendamentos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `clienteId` INTEGER NOT NULL, `servicoId` INTEGER NOT NULL, `dataHoraStart` INTEGER NOT NULL, `dataHoraEnd` INTEGER NOT NULL, `status` TEXT NOT NULL, `observacoes` TEXT NOT NULL, FOREIGN KEY(`clienteId`) REFERENCES `clientes`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`servicoId`) REFERENCES `servicos`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_agendamentos_clienteId` ON `agendamentos` (`clienteId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_agendamentos_servicoId` ON `agendamentos` (`servicoId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '4fe2b2830713acaf26bdf8eeea872400')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `clientes`")
        connection.execSQL("DROP TABLE IF EXISTS `servicos`")
        connection.execSQL("DROP TABLE IF EXISTS `agendamentos`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        connection.execSQL("PRAGMA foreign_keys = ON")
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsClientes: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsClientes.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsClientes.put("nome", TableInfo.Column("nome", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsClientes.put("telefone", TableInfo.Column("telefone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsClientes.put("email", TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsClientes.put("observacoes", TableInfo.Column("observacoes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysClientes: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesClientes: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoClientes: TableInfo = TableInfo("clientes", _columnsClientes, _foreignKeysClientes, _indicesClientes)
        val _existingClientes: TableInfo = read(connection, "clientes")
        if (!_infoClientes.equals(_existingClientes)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |clientes(com.example.agendasdeservios.data.local.entity.ClienteEntity).
              | Expected:
              |""".trimMargin() + _infoClientes + """
              |
              | Found:
              |""".trimMargin() + _existingClientes)
        }
        val _columnsServicos: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsServicos.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsServicos.put("nome", TableInfo.Column("nome", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsServicos.put("descricao", TableInfo.Column("descricao", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsServicos.put("duracaoMinutos", TableInfo.Column("duracaoMinutos", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsServicos.put("preco", TableInfo.Column("preco", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysServicos: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesServicos: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoServicos: TableInfo = TableInfo("servicos", _columnsServicos, _foreignKeysServicos, _indicesServicos)
        val _existingServicos: TableInfo = read(connection, "servicos")
        if (!_infoServicos.equals(_existingServicos)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |servicos(com.example.agendasdeservios.data.local.entity.ServicoEntity).
              | Expected:
              |""".trimMargin() + _infoServicos + """
              |
              | Found:
              |""".trimMargin() + _existingServicos)
        }
        val _columnsAgendamentos: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsAgendamentos.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAgendamentos.put("clienteId", TableInfo.Column("clienteId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAgendamentos.put("servicoId", TableInfo.Column("servicoId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAgendamentos.put("dataHoraStart", TableInfo.Column("dataHoraStart", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAgendamentos.put("dataHoraEnd", TableInfo.Column("dataHoraEnd", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAgendamentos.put("status", TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAgendamentos.put("observacoes", TableInfo.Column("observacoes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysAgendamentos: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysAgendamentos.add(TableInfo.ForeignKey("clientes", "CASCADE", "NO ACTION", listOf("clienteId"), listOf("id")))
        _foreignKeysAgendamentos.add(TableInfo.ForeignKey("servicos", "CASCADE", "NO ACTION", listOf("servicoId"), listOf("id")))
        val _indicesAgendamentos: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesAgendamentos.add(TableInfo.Index("index_agendamentos_clienteId", false, listOf("clienteId"), listOf("ASC")))
        _indicesAgendamentos.add(TableInfo.Index("index_agendamentos_servicoId", false, listOf("servicoId"), listOf("ASC")))
        val _infoAgendamentos: TableInfo = TableInfo("agendamentos", _columnsAgendamentos, _foreignKeysAgendamentos, _indicesAgendamentos)
        val _existingAgendamentos: TableInfo = read(connection, "agendamentos")
        if (!_infoAgendamentos.equals(_existingAgendamentos)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |agendamentos(com.example.agendasdeservios.data.local.entity.AgendamentoEntity).
              | Expected:
              |""".trimMargin() + _infoAgendamentos + """
              |
              | Found:
              |""".trimMargin() + _existingAgendamentos)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "clientes", "servicos", "agendamentos")
  }

  public override fun clearAllTables() {
    super.performClear(true, "clientes", "servicos", "agendamentos")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(ClienteDao::class, ClienteDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(ServicoDao::class, ServicoDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(AgendamentoDao::class, AgendamentoDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun clienteDao(): ClienteDao = _clienteDao.value

  public override fun servicoDao(): ServicoDao = _servicoDao.value

  public override fun agendamentoDao(): AgendamentoDao = _agendamentoDao.value
}
