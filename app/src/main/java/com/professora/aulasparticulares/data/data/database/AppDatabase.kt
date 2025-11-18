package com.professora.aulasparticulares.data.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.professora.aulasparticulares.data.data.dao.AgendamentoDao
import com.professora.aulasparticulares.data.data.dao.AlunoDao
import com.professora.aulasparticulares.data.data.dao.PagamentoDao
import com.professora.aulasparticulares.data.data.dao.UsuarioDao
import com.professora.aulasparticulares.data.data.entity.Agendamento
import com.professora.aulasparticulares.data.data.entity.Aluno
import com.professora.aulasparticulares.data.data.entity.Pagamento
import com.professora.aulasparticulares.data.data.entity.Usuario

@Database(
    entities = [Usuario::class, Aluno::class, Pagamento::class, Agendamento::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun alunoDao(): AlunoDao
    abstract fun pagamentoDao(): PagamentoDao
    abstract fun agendamentoDao(): AgendamentoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "aulas_particulares_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
