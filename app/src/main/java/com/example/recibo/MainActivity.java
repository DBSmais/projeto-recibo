package com.example.recibo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editNomePagador, editCpfPagador, editNomeRecebedor, 
                    editCpfRecebedor, editValor, editDescricao;
    private Button btnGerarRecibo;
    private TextView textRecibo;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DatabaseManager(this);

        // Inicializar views
        editNomePagador = findViewById(R.id.editNomePagador);
        editCpfPagador = findViewById(R.id.editCpfPagador);
        editNomeRecebedor = findViewById(R.id.editNomeRecebedor);
        editCpfRecebedor = findViewById(R.id.editCpfRecebedor);
        editValor = findViewById(R.id.editValor);
        editDescricao = findViewById(R.id.editDescricao);
        btnGerarRecibo = findViewById(R.id.btnGerarRecibo);
        textRecibo = findViewById(R.id.textRecibo);

        btnGerarRecibo.setOnClickListener(v -> gerarRecibo());
    }

    private void gerarRecibo() {
        String nomePagador = editNomePagador.getText().toString();
        String cpfPagador = editCpfPagador.getText().toString();
        String nomeRecebedor = editNomeRecebedor.getText().toString();
        String cpfRecebedor = editCpfRecebedor.getText().toString();
        String valorStr = editValor.getText().toString();
        String descricao = editDescricao.getText().toString();

        if (nomePagador.isEmpty() || cpfPagador.isEmpty() || 
            nomeRecebedor.isEmpty() || cpfRecebedor.isEmpty() || 
            valorStr.isEmpty() || descricao.isEmpty()) {
            Toast.makeText(this, R.string.erro_campo_vazio, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double valor = Double.parseDouble(valorStr);
            ReciboGenerator recibo = new ReciboGenerator(
                nomePagador, cpfPagador, nomeRecebedor, cpfRecebedor, valor, descricao
            );

            dbManager.salvarRecibo(recibo);
            textRecibo.setText(recibo.gerarRecibo());
            
            // Limpar campos
            editNomePagador.setText("");
            editCpfPagador.setText("");
            editNomeRecebedor.setText("");
            editCpfRecebedor.setText("");
            editValor.setText("");
            editDescricao.setText("");
            
            Toast.makeText(this, R.string.recibo_gerado, Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
        }
    }
} 