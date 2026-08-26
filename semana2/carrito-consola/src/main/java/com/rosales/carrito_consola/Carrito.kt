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

class Carrito {
    private val productos = mutableListOf<Producto>()

    fun agregarProducto(p: Producto) {
        productos.add(p)
        println("Producto agregado: ${p.nombre}")
    }

    fun getProductos(): List<Producto> = productos
}

fun Carrito.calcularSubtotal(): Double {
    var subtotal = 0.0
    for (p in getProductos()) {
        subtotal += p.calcularImporte()
    }
    return subtotal
}

fun calcularIGV(subtotal: Double): Double {
    return subtotal * 0.18
}

fun calcularTotal(subtotal: Double, igv: Double): Double {
    return subtotal + igv
}


fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP (POO) ")
    println("=========================================")

    val nombreCliente = "Magaly Rosales"
    val carrito = Carrito()

    println("Cliente: $nombreCliente")
    println()

    carrito.agregarProducto(ProductoElectronico("Laptop HP", 2500.0, 1, 12))
    carrito.agregarProducto(ProductoElectronico("Mouse Logitech", 45.5, 2, 6))
    carrito.agregarProducto(ProductoElectronico("Laptop Mac", 5000.0, 4, 12))
    carrito.agregarProducto(ProductoMobiliario("Mesa", 20.0, 10, "Madera"))

    println("Cantidad de productos: ${carrito.getProductos().size}")


    val subtotal = carrito.calcularSubtotal()
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)

    println(String.format("Subtotal: S/ %8.2f", subtotal))
    println(String.format("IGV: S/ %8.2f", igv))
    println(String.format("TOTAL A PAGAR: S/ %8.2f", total))
}