package com.example.recibo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val reciboDao: ReciboDao
    val todosRecibos: LiveData<List<Recibo>>

    init {
        val database = AppDatabase.getDatabase(application)
        reciboDao = database.reciboDao()
        todosRecibos = reciboDao.getAllRecibos()
    }

    fun insert(recibo: Recibo) = viewModelScope.launch {
        reciboDao.insert(recibo)
    }
}