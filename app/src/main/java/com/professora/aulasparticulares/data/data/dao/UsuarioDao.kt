package com.professora.aulasparticulares.data.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.professora.aulasparticulares.data.data.entity.Usuario

@Dao
interface UsuarioDao {
    @Insert
    suspend fun inserir(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE email = :email AND senha = :senha LIMIT 1")
    suspend fun autenticar(email: String, senha: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun buscarPorEmail(email: String): Usuario?
}
