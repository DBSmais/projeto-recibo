package com.example.recibo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.example.recibo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGerarRecibo.setOnClickListener {
            gerarRecibo()
        }

        mainViewModel.todosRecibos.observe(this) { recibos ->
            // Aqui você pode atualizar um RecyclerView para exibir os recibos.
            // Por simplicidade, vamos apenas exibir o último no TextView.
            recibos.firstOrNull()?.let {
                binding.textRecibo.text = it.gerarReciboTexto()
            }
        }
    }

    private fun gerarRecibo() {
        val nomePagador = binding.editNomePagador.text.toString()
        val cpfPagador = binding.editCpfPagador.text.toString()
        val nomeRecebedor = binding.editNomeRecebedor.text.toString()
        val valorStr = binding.editValor.text.toString()
        val cpfRecebedor = binding.editCpfRecebedor.text.toString()
        val descricao = binding.editDescricao.text.toString()

        if (nomePagador.isBlank() || cpfPagador.isBlank() || nomeRecebedor.isBlank() || cpfRecebedor.isBlank() || valorStr.isBlank() || descricao.isBlank()) {
            Toast.makeText(this, R.string.erro_campo_vazio, Toast.LENGTH_SHORT).show()
            return
        }

        val valor = valorStr.toDoubleOrNull()
        if (valor == null) {
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show()
            return
        }

        val recibo = Recibo(
            nomePagador = nomePagador,
            cpfPagador = cpfPagador,
            nomeRecebedor = nomeRecebedor,
            cpfRecebedor = cpfRecebedor,
            valor = valor,
            descricao = descricao
        )

        mainViewModel.insert(recibo)
        Toast.makeText(this, R.string.recibo_gerado, Toast.LENGTH_SHORT).show()

        limparCampos()
    }

    private fun limparCampos() {
        binding.editNomePagador.text.clear()
        binding.editCpfPagador.text.clear()
        binding.editNomeRecebedor.text.clear()
        binding.editCpfRecebedor.text.clear()
        binding.editValor.text.clear()
        binding.editDescricao.text.clear()
    }
}