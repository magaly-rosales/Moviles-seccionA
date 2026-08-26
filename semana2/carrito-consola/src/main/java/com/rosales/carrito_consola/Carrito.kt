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

class ProductoElectronico(
    nombre: String,
    precioBase: Double,
    cantidad: Int,
    val garantiaMeses: Int
) : Producto(nombre, precioBase, cantidad) {

    override fun calcularImporte(): Double = getPrecio() * cantidad

    override fun mostrarInfo(): String =
        "$nombre (Electrónico, garantía $garantiaMeses meses)"
}

class ProductoMobiliario(
    nombre: String,
    precioBase: Double,
    cantidad: Int,
    val material: String
) : Producto(nombre, precioBase, cantidad) {

    override fun calcularImporte(): Double = getPrecio() * cantidad

    override fun mostrarInfo(): String =
        "$nombre (Mobiliario, material: $material)"
}

fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP (POO) ")
    println("=========================================")
}