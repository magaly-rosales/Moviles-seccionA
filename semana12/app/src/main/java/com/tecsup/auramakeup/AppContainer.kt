package com.tecsup.auramakeup

import android.content.Context
import androidx.room.Room
import com.tecsup.auramakeup.data.auth.AuthRepository
import com.tecsup.auramakeup.data.auth.FirebaseAuthRepository
import com.tecsup.auramakeup.data.local.AppDatabase
import com.tecsup.auramakeup.data.network.MonitorConexion
import com.tecsup.auramakeup.data.network.MonitorConexionAndroid
import com.tecsup.auramakeup.data.remote.RetrofitClient
import com.tecsup.auramakeup.data.repository.CarritoRepository
import com.tecsup.auramakeup.data.repository.CarritoRepositoryImpl
import com.tecsup.auramakeup.data.repository.ItemRepository
import com.tecsup.auramakeup.data.repository.ItemRepositoryImpl
import com.tecsup.auramakeup.notification.Notificador
import com.tecsup.auramakeup.notification.NotificadorAndroid

class AppContainer(private val context: Context) {

    val database: AppDatabase by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "aura_makeup.db")
            .fallbackToDestructiveMigration(true)
            .build()
    }

    val itemRepository: ItemRepository by lazy {
        ItemRepositoryImpl(RetrofitClient.api, database.productoDao())
    }

    val carritoRepository: CarritoRepository by lazy {
        CarritoRepositoryImpl(database.carritoDao())
    }

    val monitorConexion: MonitorConexion by lazy {
        MonitorConexionAndroid(context)
    }

    val authRepository: AuthRepository by lazy {
        FirebaseAuthRepository()
    }

    val notificador: Notificador by lazy {
        NotificadorAndroid(context)
    }
}