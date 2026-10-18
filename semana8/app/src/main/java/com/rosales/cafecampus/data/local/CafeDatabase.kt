package com.rosales.cafecampus.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rosales.cafecampus.data.local.dao.ProductoDao
import com.rosales.cafecampus.data.local.entity.Producto

@Database(
    entities = [Producto::class ],
    version = 1,
    exportSchema = false
)
abstract class CafeDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao


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