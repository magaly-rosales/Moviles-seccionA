package com.rosales.cafecampus.model

import com.rosales.cafecampus.data.local.entity.Producto

data class ItemCarrito(
    val producto: Producto,
    val cantidad: Int,
    val nota: String = ""
) {
    val subtotal: Double get() = producto.precio * cantidad
}