package com.professora.aulasparticulares.data.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.professora.aulasparticulares.data.data.entity.Aluno
import kotlinx.coroutines.flow.Flow

@Dao
interface AlunoDao {
    @Insert
    suspend fun inserir(aluno: Aluno): Long

    @Update
    suspend fun atualizar(aluno: Aluno)

    @Delete
    suspend fun deletar(aluno: Aluno)

    @Query("SELECT * FROM alunos ORDER BY nome ASC")
    fun obterTodos(): Flow<List<Aluno>>

    @Query("SELECT * FROM alunos WHERE id = :id")
    suspend fun obterPorId(id: Int): Aluno?

    @Query("SELECT * FROM alunos WHERE nome LIKE '%' || :busca || '%'")
    fun buscarPorNome(busca: String): Flow<List<Aluno>>
}
