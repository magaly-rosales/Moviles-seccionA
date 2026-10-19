package com.rosales.cafecampus.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rosales.cafecampus.data.local.dao.PedidoDao
import com.rosales.cafecampus.data.local.dao.ProductoDao
import com.rosales.cafecampus.data.local.dao.UsuarioDao
import com.rosales.cafecampus.data.local.entity.DetallePedido
import com.rosales.cafecampus.data.local.entity.Pedido
import com.rosales.cafecampus.data.local.entity.Producto
import com.rosales.cafecampus.data.local.entity.Usuario

@Database(
    entities = [Producto::class, Usuario::class, Pedido::class, DetallePedido::class],
    version = 4,
    exportSchema = false
)
abstract class CafeDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun pedidoDao(): PedidoDao

    companion object {
        @Volatile private var INSTANCE: CafeDatabase? = null

        fun obtener(context: Context): CafeDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                CafeDatabase::class.java,
                "cafecampus.db"
            )
                .fallbackToDestructiveMigration(true)
                .build()
                .also { INSTANCE = it }
        }
    }
}