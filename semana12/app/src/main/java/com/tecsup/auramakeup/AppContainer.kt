package com.tecsup.auramakeup

import android.content.Context
import androidx.room.Room
import com.tecsup.auramakeup.data.local.AppDatabase
import com.tecsup.auramakeup.data.network.MonitorConexion
import com.tecsup.auramakeup.data.network.MonitorConexionAndroid
import com.tecsup.auramakeup.data.remote.RetrofitClient
import com.tecsup.auramakeup.data.repository.ItemRepository
import com.tecsup.auramakeup.data.repository.ItemRepositoryImpl

class AppContainer(private val context: Context) {

    val database: AppDatabase by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "aura_makeup.db").build()
    }

    val itemRepository: ItemRepository by lazy {
        ItemRepositoryImpl(RetrofitClient.api, database.productoDao())
    }

    val monitorConexion: MonitorConexion by lazy {
        MonitorConexionAndroid(context)
    }
}