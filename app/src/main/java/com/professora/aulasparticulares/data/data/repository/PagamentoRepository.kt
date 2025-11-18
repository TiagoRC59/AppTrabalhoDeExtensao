package com.professora.aulasparticulares.data.data.repository

import com.professora.aulasparticulares.data.data.dao.PagamentoDao
import com.professora.aulasparticulares.data.data.entity.Pagamento
import kotlinx.coroutines.flow.Flow

class PagamentoRepository(private val pagamentoDao: PagamentoDao) {

    fun obterTodosPagamentos(): Flow<List<Pagamento>> = pagamentoDao.obterTodos()

    fun obterPagamentosPorAluno(alunoId: Int): Flow<List<Pagamento>> =
        pagamentoDao.obterPorAluno(alunoId)

    suspend fun inserirPagamento(pagamento: Pagamento) = pagamentoDao.inserir(pagamento)

    suspend fun atualizarPagamento(pagamento: Pagamento) = pagamentoDao.atualizar(pagamento)

    suspend fun deletarPagamento(pagamento: Pagamento) = pagamentoDao.deletar(pagamento)

    suspend fun obterTotalMes(mes: String): Double = pagamentoDao.totalMes(mes) ?: 0.0
}
