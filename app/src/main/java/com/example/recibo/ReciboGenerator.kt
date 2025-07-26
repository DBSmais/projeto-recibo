package com.example.recibo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "recibos")
data class Recibo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nomePagador: String,
    val cpfPagador: String,
    val nomeRecebedor: String,
    val cpfRecebedor: String,
    val valor: Double,
    val descricao: String,
    val data: Long = System.currentTimeMillis()
) {
    fun gerarReciboTexto(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return """
        ===============================
                   RECIBO             
        ===============================

        Data: ${sdf.format(Date(data))}

        Recebido de $nomePagador
        CPF: $cpfPagador
        A quantia de R$ ${String.format("%.2f", valor)}
        Referente a: $descricao

        Recebedor: $nomeRecebedor
        CPF: $cpfRecebedor

        ===============================        
        """.trimIndent()
    }
}