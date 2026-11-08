package com.tecsup.auramakeup.domain.model

data class Item(
    val id: Int,
    val titulo: String,
    val marca: String,
    val precio: Double,
    val imagenUrl: String?,
    val categoria: String,
    val rating: Double,
    val esFavorito: Boolean = false,
    val descripcion: String = "",
    val stock: Int = 0,
    val imagenes: List<String> = emptyList()
)