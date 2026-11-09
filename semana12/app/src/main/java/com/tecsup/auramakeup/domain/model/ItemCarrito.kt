package com.tecsup.auramakeup.domain.model

data class ItemCarrito(
    val productoId: Int,
    val titulo: String,
    val precio: Double,
    val imagenUrl: String?,
    val cantidad: Int
) {
    val subtotal: Double get() = precio * cantidad
}