package com.example.recibo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReciboGenerator {
    private String nomePagador;
    private String cpfPagador;
    private String nomeRecebedor;
    private String cpfRecebedor;
    private double valor;
    private String descricao;
    private Date data;

    public ReciboGenerator(String nomePagador, String cpfPagador, String nomeRecebedor, 
                          String cpfRecebedor, double valor, String descricao) {
        this.nomePagador = nomePagador;
        this.cpfPagador = cpfPagador;
        this.nomeRecebedor = nomeRecebedor;
        this.cpfRecebedor = cpfRecebedor;
        this.valor = valor;
        this.descricao = descricao;
        this.data = new Date();
    }

    public String gerarRecibo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder recibo = new StringBuilder();
        
        recibo.append("===============================\n");
        recibo.append("           RECIBO             \n");
        recibo.append("===============================\n\n");
        
        recibo.append("Data: ").append(sdf.format(data)).append("\n\n");
        
        recibo.append("Recebi de ").append(nomePagador).append("\n");
        recibo.append("CPF: ").append(cpfPagador).append("\n");
        recibo.append("A quantia de R$ ").append(String.format("%.2f", valor)).append("\n");
        recibo.append("Referente a: ").append(descricao).append("\n\n");
        
        recibo.append("Recebedor: ").append(nomeRecebedor).append("\n");
        recibo.append("CPF: ").append(cpfRecebedor).append("\n\n");
        
        recibo.append("===============================\n");
        
        return recibo.toString();
    }

    // Getters e Setters
    public String getNomePagador() {
        return nomePagador;
    }

    public void setNomePagador(String nomePagador) {
        this.nomePagador = nomePagador;
    }

    public String getCpfPagador() {
        return cpfPagador;
    }

    public void setCpfPagador(String cpfPagador) {
        this.cpfPagador = cpfPagador;
    }

    public String getNomeRecebedor() {
        return nomeRecebedor;
    }

    public void setNomeRecebedor(String nomeRecebedor) {
        this.nomeRecebedor = nomeRecebedor;
    }

    public String getCpfRecebedor() {
        return cpfRecebedor;
    }

    public void setCpfRecebedor(String cpfRecebedor) {
        this.cpfRecebedor = cpfRecebedor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
} 