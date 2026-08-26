package com.rosales.carrito_consola

data class Producto(
    val nombre:String,
    val precio: Double,
    var cantidad: Int
)
// Calcula el subtotal sumando precio x cantidad de cada producto
fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.precio * p.cantidad
    }
    return subtotal
}
//Calcular el IGV subtotal x el porcentaje impuesto que se aplica que es el 18%
fun calcularIGV(subtotal: Double): Double {
    val igv = subtotal * 0.18
    return igv
}
// Calcular el total que es la suma de subtotal + el IGV
fun calcularTotal(subtotal: Double, igv: Double): Double {
    val totalapagar = subtotal + igv
    return totalapagar
}



fun mostrarDetalle(productos: List<Producto>) {
    println("--------- DETALLE DEL CARRITO ---------")
    var i = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println(String.format("%d. %-20s x%d S/ %8.2f",
            i, p.nombre, p.cantidad, importe))
        i++
    }
    println("---------------------------------------")
}


fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
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

    mostrarDetalle(carrito)
    println("Cantidad de productos: ${carrito.size}")

    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)

    println(String.format("Subtotal: S/ %8.2f", subtotal))
    println(String.format("IGV: S/ %8.2f", igv))
    println(String.format("TOTAL A PAGAR: S/ %8.2f", total))


    val masCaro = carrito.maxByOrNull { it.precio }
    if (masCaro != null) {
        println("Producto mas caro: ${masCaro.nombre} " +
                String.format("(S/ %.2f)", masCaro.precio))
    }

    val descuento = calcularDescuento(total)
    val totalConDescuento = total - descuento

    if (descuento > 0.0) {
        println(String.format("Descuento aplicado: S/ %.2f", descuento))
    }
    println(String.format("TOTAL CON DESCUENTO: S/ %8.2f", totalConDescuento))
}


