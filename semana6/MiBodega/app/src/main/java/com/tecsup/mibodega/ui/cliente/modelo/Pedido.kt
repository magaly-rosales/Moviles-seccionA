package com.tecsup.mibodega.ui.cliente.modelo

data class Pedido(
    val numero: Int,
    val fecha: String,
    val items: List<ItemCarrito>,
    val tipoEntrega: TipoEntrega,
    val total: Double
)