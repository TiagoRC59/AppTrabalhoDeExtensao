package com.professora.aulasparticulares.data.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.professora.aulasparticulares.data.data.entity.Aluno
import com.professora.aulasparticulares.data.data.repository.AlunoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AlunoViewModel(private val repository: AlunoRepository) : ViewModel() {

    val alunos: StateFlow<List<Aluno>> = repository.obterTodosAlunos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun inserirAluno(aluno: Aluno) {
        viewModelScope.launch {
            repository.inserirAluno(aluno)
        }
    }

    fun atualizarAluno(aluno: Aluno) {
        viewModelScope.launch {
            repository.atualizarAluno(aluno)
        }
    }

    fun deletarAluno(aluno: Aluno) {
        viewModelScope.launch {
            repository.deletarAluno(aluno)
        }
    }
}
