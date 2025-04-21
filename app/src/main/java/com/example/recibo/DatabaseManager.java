package com.example.recibo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:recibos.db";
    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL);
            criarTabela();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS recibos (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "nome_pagador TEXT NOT NULL," +
                     "cpf_pagador TEXT NOT NULL," +
                     "nome_recebedor TEXT NOT NULL," +
                     "cpf_recebedor TEXT NOT NULL," +
                     "valor REAL NOT NULL," +
                     "descricao TEXT NOT NULL," +
                     "data TEXT NOT NULL" +
                     ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvarRecibo(ReciboGenerator recibo) {
        String sql = "INSERT INTO recibos (nome_pagador, cpf_pagador, nome_recebedor, " +
                    "cpf_recebedor, valor, descricao, data) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, recibo.getNomePagador());
            pstmt.setString(2, recibo.getCpfPagador());
            pstmt.setString(3, recibo.getNomeRecebedor());
            pstmt.setString(4, recibo.getCpfRecebedor());
            pstmt.setDouble(5, recibo.getValor());
            pstmt.setString(6, recibo.getDescricao());
            pstmt.setString(7, new SimpleDateFormat("yyyy-MM-dd").format(recibo.getData()));
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ReciboGenerator> listarRecibos() {
        List<ReciboGenerator> recibos = new ArrayList<>();
        String sql = "SELECT * FROM recibos ORDER BY data DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                ReciboGenerator recibo = new ReciboGenerator(
                    rs.getString("nome_pagador"),
                    rs.getString("cpf_pagador"),
                    rs.getString("nome_recebedor"),
                    rs.getString("cpf_recebedor"),
                    rs.getDouble("valor"),
                    rs.getString("descricao")
                );
                recibo.setData(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("data")));
                recibos.add(recibo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recibos;
    }

    public void fecharConexao() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}