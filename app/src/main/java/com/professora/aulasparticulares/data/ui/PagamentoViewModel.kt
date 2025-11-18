package com.professora.aulasparticulares.data.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.professora.aulasparticulares.data.data.entity.Pagamento
import com.professora.aulasparticulares.data.data.repository.PagamentoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PagamentoViewModel(private val repository: PagamentoRepository) : ViewModel() {

    val pagamentos: StateFlow<List<Pagamento>> = repository.obterTodosPagamentos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun inserirPagamento(pagamento: Pagamento) {
        viewModelScope.launch {
            repository.inserirPagamento(pagamento)
        }
    }

    fun deletarPagamento(pagamento: Pagamento) {
        viewModelScope.launch {
            repository.deletarPagamento(pagamento)
        }
    }
}
