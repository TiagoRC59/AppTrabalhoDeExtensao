package com.professora.aulasparticulares.data.data.repository

import com.professora.aulasparticulares.data.data.dao.UsuarioDao
import com.professora.aulasparticulares.data.data.entity.Usuario

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun cadastrar(usuario: Usuario) {
        usuarioDao.inserir(usuario)
    }

    suspend fun autenticar(email: String, senha: String): Usuario? {
        return usuarioDao.autenticar(email, senha)
    }

    suspend fun verificarEmailExiste(email: String): Boolean {
        return usuarioDao.buscarPorEmail(email) != null
    }
}
