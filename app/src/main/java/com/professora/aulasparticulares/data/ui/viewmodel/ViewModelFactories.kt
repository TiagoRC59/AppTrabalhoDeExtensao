package com.professora.aulasparticulares.data.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.professora.aulasparticulares.data.data.repository.AgendamentoRepository
import com.professora.aulasparticulares.data.data.repository.AlunoRepository
import com.professora.aulasparticulares.data.data.repository.PagamentoRepository
import com.professora.aulasparticulares.data.data.repository.UsuarioRepository
import com.professora.aulasparticulares.data.ui.AgendamentoViewModel
import com.professora.aulasparticulares.data.ui.AlunoViewModel
import com.professora.aulasparticulares.data.ui.LoginViewModel
import com.professora.aulasparticulares.data.ui.PagamentoViewModel

class LoginViewModelFactory(
    private val repository: UsuarioRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class AlunoViewModelFactory(
    private val repository: AlunoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlunoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlunoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class PagamentoViewModelFactory(
    private val repository: PagamentoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PagamentoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PagamentoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class AgendamentoViewModelFactory(
    private val repository: AgendamentoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AgendamentoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AgendamentoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
