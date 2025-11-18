package com.professora.aulasparticulares.data.data.repository

import com.professora.aulasparticulares.data.data.dao.AlunoDao
import com.professora.aulasparticulares.data.data.entity.Aluno
import kotlinx.coroutines.flow.Flow

class AlunoRepository(private val alunoDao: AlunoDao) {

    fun obterTodosAlunos(): Flow<List<Aluno>> = alunoDao.obterTodos()

    suspend fun inserirAluno(aluno: Aluno): Long = alunoDao.inserir(aluno)

    suspend fun atualizarAluno(aluno: Aluno) = alunoDao.atualizar(aluno)

    suspend fun deletarAluno(aluno: Aluno) = alunoDao.deletar(aluno)

    suspend fun obterAlunoPorId(id: Int): Aluno? = alunoDao.obterPorId(id)

    fun buscarAlunoPorNome(busca: String): Flow<List<Aluno>> = alunoDao.buscarPorNome(busca)
}
