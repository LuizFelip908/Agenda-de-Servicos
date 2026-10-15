package com.example.agendasdeservios.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.agendasdeservios.data.local.dao.AgendamentoDao
import com.example.agendasdeservios.data.local.dao.ClienteDao
import com.example.agendasdeservios.data.local.dao.ServicoDao
import com.example.agendasdeservios.data.local.entity.AgendamentoEntity
import com.example.agendasdeservios.data.local.entity.ClienteEntity
import com.example.agendasdeservios.data.local.entity.ServicoEntity

@Database(
    entities = [
        ClienteEntity::class,
        ServicoEntity::class,
        AgendamentoEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun clienteDao(): ClienteDao
    abstract fun servicoDao(): ServicoDao
    abstract fun agendamentoDao(): AgendamentoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "agenda_servicos_db"
                )
                .fallbackToDestructiveMigration(dropAllTables = true)
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
