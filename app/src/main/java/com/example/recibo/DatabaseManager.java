package com.example.recibo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recibos.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_RECIBOS = "recibos";
    
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOME_PAGADOR = "nome_pagador";
    private static final String COLUMN_CPF_PAGADOR = "cpf_pagador";
    private static final String COLUMN_NOME_RECEBEDOR = "nome_recebedor";
    private static final String COLUMN_CPF_RECEBEDOR = "cpf_recebedor";
    private static final String COLUMN_VALOR = "valor";
    private static final String COLUMN_DESCRICAO = "descricao";
    private static final String COLUMN_DATA = "data";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_RECIBOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOME_PAGADOR + " TEXT NOT NULL, " +
                COLUMN_CPF_PAGADOR + " TEXT NOT NULL, " +
                COLUMN_NOME_RECEBEDOR + " TEXT NOT NULL, " +
                COLUMN_CPF_RECEBEDOR + " TEXT NOT NULL, " +
                COLUMN_VALOR + " REAL NOT NULL, " +
                COLUMN_DESCRICAO + " TEXT NOT NULL, " +
                COLUMN_DATA + " TEXT NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIBOS);
        onCreate(db);
    }

    public void salvarRecibo(ReciboGenerator recibo) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        String sql = "INSERT INTO " + TABLE_RECIBOS + " (" +
                COLUMN_NOME_PAGADOR + ", " + COLUMN_CPF_PAGADOR + ", " +
                COLUMN_NOME_RECEBEDOR + ", " + COLUMN_CPF_RECEBEDOR + ", " +
                COLUMN_VALOR + ", " + COLUMN_DESCRICAO + ", " + COLUMN_DATA +
                ") VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        db.execSQL(sql, new Object[]{
                recibo.getNomePagador(),
                recibo.getCpfPagador(),
                recibo.getNomeRecebedor(),
                recibo.getCpfRecebedor(),
                recibo.getValor(),
                recibo.getDescricao(),
                sdf.format(recibo.getData())
        });
        
        db.close();
    }

    public List<ReciboGenerator> listarRecibos() {
        List<ReciboGenerator> recibos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        String query = "SELECT * FROM " + TABLE_RECIBOS + " ORDER BY " + COLUMN_DATA + " DESC";
        Cursor cursor = db.rawQuery(query, null);
        
        if (cursor.moveToFirst()) {
            do {
                try {
                    ReciboGenerator recibo = new ReciboGenerator(
                            cursor.getString(1), // nome_pagador
                            cursor.getString(2), // cpf_pagador
                            cursor.getString(3), // nome_recebedor
                            cursor.getString(4), // cpf_recebedor
                            cursor.getDouble(5), // valor
                            cursor.getString(6)  // descricao
                    );
                    recibo.setData(sdf.parse(cursor.getString(7))); // data
                    recibos.add(recibo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return recibos;
    }

    public void fecharConexao() {
    }
}