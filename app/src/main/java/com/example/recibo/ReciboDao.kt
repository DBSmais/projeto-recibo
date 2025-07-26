package com.example.recibo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReciboDao {
    @Insert
    suspend fun insert(recibo: Recibo)

    @Query("SELECT * FROM recibos ORDER BY data DESC")
    fun getAllRecibos(): LiveData<List<Recibo>>
}