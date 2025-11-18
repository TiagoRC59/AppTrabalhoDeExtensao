package com.professora.aulasparticulares.data.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.professora.aulasparticulares.data.data.entity.Agendamento
import com.professora.aulasparticulares.data.data.repository.AgendamentoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AgendamentoViewModel(private val repository: AgendamentoRepository) : ViewModel() {

    val agendamentos: StateFlow<List<Agendamento>> = repository.obterTodosAgendamentos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun inserirAgendamento(agendamento: Agendamento) {
        viewModelScope.launch {
            repository.inserirAgendamento(agendamento)
        }
    }

    fun marcarComoRealizada(id: Int, realizada: Boolean) {
        viewModelScope.launch {
            repository.marcarComoRealizada(id, realizada)
        }
    }

    fun deletarAgendamento(agendamento: Agendamento) {
        viewModelScope.launch {
            repository.deletarAgendamento(agendamento)
        }
    }
}
