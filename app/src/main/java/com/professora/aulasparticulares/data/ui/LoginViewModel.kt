package com.professora.aulasparticulares.data.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.professora.aulasparticulares.data.data.entity.Usuario
import com.professora.aulasparticulares.data.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UsuarioRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val usuario = repository.autenticar(email, senha)
                if (usuario != null) {
                    _loginState.value = LoginState.Success(usuario)
                } else {
                    _loginState.value = LoginState.Error("Email ou senha incorretos")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Erro ao fazer login")
            }
        }
    }

    fun cadastrar(nome: String, email: String, senha: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                if (repository.verificarEmailExiste(email)) {
                    _loginState.value = LoginState.Error("Email já cadastrado")
                    return@launch
                }
                val usuario = Usuario(nome = nome, email = email, senha = senha)
                repository.cadastrar(usuario)
                _loginState.value = LoginState.Success(usuario)
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Erro ao cadastrar")
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val usuario: Usuario) : LoginState()
    data class Error(val message: String) : LoginState()
}
