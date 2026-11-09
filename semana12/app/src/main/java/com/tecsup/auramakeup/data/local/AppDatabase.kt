package com.tecsup.auramakeup.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProductoEntity::class, CarritoEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun carritoDao(): CarritoDao
}