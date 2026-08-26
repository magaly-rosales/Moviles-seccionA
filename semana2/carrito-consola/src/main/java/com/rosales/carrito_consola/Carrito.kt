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
}