package com.rosales.cuotas_app

data class Producto(
    val nombre: String,
    val precio: Double,
    val cantidad: Int
)

fun main() {
    println("=========================================")
    println("   CALCULADORA DE CUOTAS - TIENDA TECSUP")
    println("=========================================")

    print("Nombre del producto: ")
    val nombreProducto = readln()

    print("Precio: S/ ")
    val precio = readln().toDouble()

    print("Cantidad: ")
    val cantidad = readln().toInt()

    val producto = Producto(nombreProducto, precio, cantidad)
    println("Producto registrado: ${producto.nombre}, S/ ${producto.precio} x ${producto.cantidad}")
}