package com.rosales.campusgps.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rosales.campusgps.data.LugaresRepositoryLocal
import com.rosales.campusgps.data.UbicacionRepositoryGoogle

class CampusViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapaViewModel::class.java)) {
            return MapaViewModel(
                UbicacionRepositoryGoogle(context.applicationContext),
                LugaresRepositoryLocal()
            ) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}