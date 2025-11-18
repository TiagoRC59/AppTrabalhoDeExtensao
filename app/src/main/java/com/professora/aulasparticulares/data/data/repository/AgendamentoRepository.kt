package com.professora.aulasparticulares.data.data.repository

import com.professora.aulasparticulares.data.data.dao.AgendamentoDao
import com.professora.aulasparticulares.data.data.entity.Agendamento
import kotlinx.coroutines.flow.Flow

class AgendamentoRepository(private val agendamentoDao: AgendamentoDao) {

    fun obterTodosAgendamentos(): Flow<List<Agendamento>> = agendamentoDao.obterTodos()

    fun obterAgendamentosPorData(data: String): Flow<List<Agendamento>> =
        agendamentoDao.obterPorData(data)

    fun obterAgendamentosPorAluno(alunoId: Int): Flow<List<Agendamento>> =
        agendamentoDao.obterPorAluno(alunoId)

    suspend fun inserirAgendamento(agendamento: Agendamento) = agendamentoDao.inserir(agendamento)

    suspend fun atualizarAgendamento(agendamento: Agendamento) = agendamentoDao.atualizar(agendamento)

    suspend fun deletarAgendamento(agendamento: Agendamento) = agendamentoDao.deletar(agendamento)

    suspend fun marcarComoRealizada(id: Int, realizada: Boolean) =
        agendamentoDao.marcarComoRealizada(id, realizada)
}
