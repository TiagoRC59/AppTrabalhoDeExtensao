package com.professora.aulasparticulares.data.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.professora.aulasparticulares.data.data.entity.Agendamento
import kotlinx.coroutines.flow.Flow

@Dao
interface AgendamentoDao {
    @Insert
    suspend fun inserir(agendamento: Agendamento)

    @Update
    suspend fun atualizar(agendamento: Agendamento)

    @Delete
    suspend fun deletar(agendamento: Agendamento)

    @Query("SELECT * FROM agendamentos ORDER BY data DESC, horario ASC")
    fun obterTodos(): Flow<List<Agendamento>>

    @Query("SELECT * FROM agendamentos WHERE data = :data ORDER BY horario ASC")
    fun obterPorData(data: String): Flow<List<Agendamento>>

    @Query("SELECT * FROM agendamentos WHERE alunoId = :alunoId ORDER BY data DESC")
    fun obterPorAluno(alunoId: Int): Flow<List<Agendamento>>

    @Query("UPDATE agendamentos SET realizada = :realizada WHERE id = :id")
    suspend fun marcarComoRealizada(id: Int, realizada: Boolean)
}
