package com.rosales.carrito_consola

data class Producto(
    val nombre: String,
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
    return subtotal * 0.18
}

fun calcularTotal(subtotal: Double, igv: Double): Double {
    return subtotal + igv
}

fun mostrarDetalle(productos: List<Producto>) {
    println()
    println("--------- DETALLE DEL CARRITO ---------")

    var i = 1

    for (p in productos) {
        val importe = p.precio * p.cantidad

        println(
            String.format(
                "%d. %-20s x%d S/ %8.2f",
                i,
                p.nombre,
                p.cantidad,
                importe
            )
        )

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

fun buscarProducto(productos: List<Producto>, nombre: String): Producto? {
    return productos.find { it.nombre == nombre }
}

fun main(){
    println("=================================")
    println("CARRITO DE COMPRAS - TIENDA TECSUP")
    println("==================================")

    print("Ingrese su nombre: ")
    val nombreCliente = readln()

    val carrito = mutableListOf<Producto>()

    val productosDisponibles = listOf(
        Producto("Laptop HP", 2500.0, 0),
        Producto("Mouse Logitech", 45.5, 0),
        Producto("Laptop Mac", 5000.0, 0),
        Producto("Mesa", 20.0, 0)
    )

    var continuar = true

    while (continuar) {

        println()
        println("--------- PRODUCTOS DISPONIBLES ---------")
        println("A. Laptop HP          S/ 2500.00")
        println("B. Mouse Logitech     S/   45.50")
        println("C. Laptop Mac         S/ 5000.00")
        println("D. Mesa               S/   20.00")
        println("E. Finalizar compra")
        println("-----------------------------------------")

        print("Seleccione una opción: ")
        val opcion = readln().uppercase()

        when (opcion) {
            "A" -> {
                print("Ingrese la cantidad: ")
                val cantidad = readln().toInt()

                carrito.add(
                    Producto(
                        productosDisponibles[0].nombre,
                        productosDisponibles[0].precio,
                        cantidad
                    )
                )

                println("Producto agregado al carrito.")
            }

            "B" -> {
                print("Ingrese la cantidad: ")
                val cantidad = readln().toInt()

                carrito.add(
                    Producto(
                        productosDisponibles[1].nombre,
                        productosDisponibles[1].precio,
                        cantidad
                    )
                )

                println("Producto agregado al carrito.")
            }

            "C" -> {
                print("Ingrese la cantidad: ")
                val cantidad = readln().toInt()

                carrito.add(
                    Producto(
                        productosDisponibles[2].nombre,
                        productosDisponibles[2].precio,
                        cantidad
                    )
                )

                println("Producto agregado al carrito.")
            }

            "D" -> {
                print("Ingrese la cantidad: ")
                val cantidad = readln().toInt()

                carrito.add(
                    Producto(
                        productosDisponibles[3].nombre,
                        productosDisponibles[3].precio,
                        cantidad
                    )
                )

                println("Producto agregado al carrito.")
            }

            "E" -> {
                continuar = false
                println("Finalizando compra...")
            }

            else -> {
                println("Opción no válida.")
            }
        }

    }


    if (carrito.isEmpty()) {
        println("No se realizaron compras.")
        return
    }

    println()
    println("========================================")
    println("             BOLETA DE VENTA")
    println("========================================")
    println("Cliente: $nombreCliente")

    mostrarDetalle(carrito)

    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)

    println(String.format("Subtotal:          S/ %8.2f", subtotal))
    println(String.format("IGV (18%%):         S/ %8.2f", igv))
    println(String.format("TOTAL A PAGAR:     S/ %8.2f", total))

    val descuento = calcularDescuento(total)

    if (descuento > 0.0) {
        println(String.format("Descuento:         S/ %8.2f", descuento))

        val totalFinal = total - descuento

        println(String.format("TOTAL FINAL:       S/ %8.2f", totalFinal))
    } else {
        println("Descuento:         S/ 0.00")
        println(String.format("TOTAL FINAL:       S/ %8.2f", total))
    }
}

