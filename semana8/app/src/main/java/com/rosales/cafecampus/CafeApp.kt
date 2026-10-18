package com.rosales.cafecampus

import android.app.Application
import com.rosales.cafecampus.data.local.CafeDatabase
import com.rosales.cafecampus.data.local.DatosIniciales
import com.rosales.cafecampus.data.repository.CafeRepository
import com.rosales.cafecampus.data.repository.CafeRepositoryRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.rosales.cafecampus.data.prefs.PreferenciasStore

class CafeApp : Application() {

    val db: CafeDatabase by lazy { CafeDatabase.obtener(this) }
    val repository: CafeRepository by lazy { CafeRepositoryRoom(db) }
    val prefs: PreferenciasStore by lazy { PreferenciasStore(this) }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch { DatosIniciales.sembrar(db) }
    }
}