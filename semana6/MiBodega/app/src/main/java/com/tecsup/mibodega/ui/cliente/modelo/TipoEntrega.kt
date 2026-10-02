package com.tecsup.mibodega.ui.cliente.modelo


enum class TipoEntrega(val etiqueta: String, val costo: Double) {
    DELIVERY("Delivery", 4.00),
    RECOJO("Recojo en tienda", 0.0)
}