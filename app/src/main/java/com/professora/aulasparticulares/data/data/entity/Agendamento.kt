package com.professora.aulasparticulares.data.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "agendamentos",
    foreignKeys = [ForeignKey(
        entity = Aluno::class,
        parentColumns = ["id"],
        childColumns = ["alunoId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["alunoId"])]
)
data class Agendamento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val alunoId: Int,
    val data: String,
    val horario: String,
    val duracao: Int, // em minutos
    val materia: String,
    val observacoes: String = "",
    val realizada: Boolean = false
)
