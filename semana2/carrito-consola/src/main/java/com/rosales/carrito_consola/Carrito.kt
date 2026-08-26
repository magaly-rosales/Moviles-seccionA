package com.rosales.carrito_consola

data class Producto(
    val nombre:String,
    val precio: Double,
    var cantidad: Int
)
fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.precio * p.cantidad
    }
    return subtotal
}
fun calcularIGV(subtotal: Double): Double {
    val igv = subtotal * 0.18
    return igv
}
fun calcularTotal(subtotal: Double, igv: Double): Double {
    val totalapagar = subtotal + igv
    return totalapagar
}
fun main(){
    println("=================================")
    println("CARRITO DE COMPRAS - TIENDA TECSUP")
    println("==================================")
    val nombreCliente = "Magaly Rosales"
    val carrito = mutableListOf<Producto>()
    println("Cliente: $nombreCliente")
    println()
    carrito.add(Producto("Laptop HP", 2500.0, 1))
    carrito.add(Producto("Mouse Logitech", 45.5, 2))
    carrito.add(Producto("Laptop Mac", 5000.0, 4))
    carrito.add(Producto("Mesa", 20.0, 10))
    for (producto in carrito) {
        println("Producto agregado: ${producto.nombre}")
    }



    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)

    println()
    println("Subtotal: $subtotal")
    println("IGV: $igv")
    println("TOTAL A PAGAR: $total")
}