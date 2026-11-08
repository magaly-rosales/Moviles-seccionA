package com.tecsup.auramakeup.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class ProductoEntity(
    @PrimaryKey val id: Int,
    val titulo: String,
    val marca: String,
    val precio: Double,
    val imagenUrl: String?,
    val categoria: String,
    val rating: Double,
    val descripcion: String,
    val stock: Int,
    val esFavorito: Boolean = false
)