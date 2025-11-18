package com.professora.aulasparticulares.data.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "pagamentos",
    foreignKeys = [ForeignKey(
        entity = Aluno::class,
        parentColumns = ["id"],
        childColumns = ["alunoId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["alunoId"])]
)
data class Pagamento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val alunoId: Int,
    val valor: Double,
    val dataPagamento: String,
    val metodoPagamento: String,
    val observacoes: String = ""
)
