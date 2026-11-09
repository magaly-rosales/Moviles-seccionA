package com.tecsup.auramakeup.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carrito")
data class CarritoEntity(
    @PrimaryKey val productoId: Int,
    val titulo: String,
    val precio: Double,
    val imagenUrl: String?,
    val cantidad: Int
)