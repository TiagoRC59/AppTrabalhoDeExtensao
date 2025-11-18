package com.professora.aulasparticulares.data.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.professora.aulasparticulares.data.data.entity.Pagamento
import kotlinx.coroutines.flow.Flow

@Dao
interface PagamentoDao {
    @Insert
    suspend fun inserir(pagamento: Pagamento)

    @Update
    suspend fun atualizar(pagamento: Pagamento)

    @Delete
    suspend fun deletar(pagamento: Pagamento)

    @Query("SELECT * FROM pagamentos ORDER BY dataPagamento DESC")
    fun obterTodos(): Flow<List<Pagamento>>

    @Query("SELECT * FROM pagamentos WHERE alunoId = :alunoId ORDER BY dataPagamento DESC")
    fun obterPorAluno(alunoId: Int): Flow<List<Pagamento>>

    @Query("SELECT SUM(valor) FROM pagamentos WHERE dataPagamento LIKE :mes || '%'")
    suspend fun totalMes(mes: String): Double?
}
