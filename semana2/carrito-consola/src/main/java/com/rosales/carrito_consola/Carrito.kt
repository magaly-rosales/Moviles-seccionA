package com.rosales.carrito_consola

abstract class Producto(
    val nombre: String,
    private var precioBase: Double,
    var cantidad: Int
) {
    fun getPrecio(): Double = precioBase

    abstract fun calcularImporte(): Double
    abstract fun mostrarInfo(): String
}

fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP (POO) ")
    println("=========================================")
}