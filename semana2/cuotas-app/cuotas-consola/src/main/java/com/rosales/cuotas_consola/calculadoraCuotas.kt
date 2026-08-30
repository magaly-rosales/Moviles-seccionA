package com.rosales.cuotas_app
import java.time.LocalDate
import java.time.format.DateTimeFormatter
data class Producto(
    val nombre: String,
    val precio: Double,
    val cantidad: Int
)
fun calcularInteres(numCuotas: Int): Double {
    return when (numCuotas) {
        6 -> 0.20
        12 -> 0.40
        24 -> 0.60
        else -> 0.0
    }
}
fun calcularMontoAPagar(montoInicial: Double, tasaInteres: Double): Double {
    return montoInicial + (montoInicial * tasaInteres)
}

fun calcularPagoMensual(montoAPagar: Double, numCuotas: Int): Double {
    return montoAPagar / numCuotas
}
fun mostrarCronograma(montoAPagar: Double, pagoMensual: Double, numCuotas: Int) {
    println()
    println("--------- CRONOGRAMA DE PAGOS ---------")
    println(String.format("%-4s %-12s %10s %12s", "N", "Fecha", "Pago", "Saldo"))

    var saldo = montoAPagar
    var fecha = LocalDate.now()
    val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    for (i in 1..numCuotas) {
        fecha = fecha.plusMonths(1)
        saldo -= pagoMensual
        if (saldo < 0.01) saldo = 0.0

        println(String.format("%-4d %-12s S/ %6.2f S/ %8.2f", i, fecha.format(formato), pagoMensual, saldo))
    }

    println("----------------------------------------")
}
fun leerNombre(): String {
    var nombre: String
    while (true) {
        print("Nombre del producto: ")
        nombre = readln()
        if (nombre.isNotBlank()) {
            return nombre
        }
        println("El nombre no puede estar vacio. Intentelo nuevamente.")
    }
}

fun leerPrecio(): Double {
    while (true) {
        print("Precio: S/ ")
        val entrada = readln()
        val precio = entrada.toDoubleOrNull()

        if (precio == null) {
            println("Debe ingresar un numero valido. Intentelo nuevamente.")
        } else if (precio <= 0) {
            println("El precio debe ser mayor a 0. Intentelo nuevamente.")
        } else {
            return precio
        }
    }
}

fun leerCantidad(): Int {
    while (true) {
        print("Cantidad: ")
        val entrada = readln()
        val cantidad = entrada.toIntOrNull()

        if (cantidad == null) {
            println("Debe ingresar un numero entero valido. Intentelo nuevamente.")
        } else if (cantidad <= 0) {
            println("La cantidad debe ser mayor a 0. Intentelo nuevamente.")
        } else {
            return cantidad
        }
    }
}

fun main() {
    println("=========================================")
    println("   CALCULADORA DE CUOTAS - TIENDA TECSUP")
    println("=========================================")

    val nombreProducto = leerNombre()
    val precio = leerPrecio()
    val cantidad = leerCantidad()

    val producto = Producto(nombreProducto, precio, cantidad)
    val montoInicial = producto.precio * producto.cantidad

    var numCuotas = 0
    var salir = false

    while (numCuotas == 0 && !salir) {
        println()
        println("--------- ELIJA NUMERO DE CUOTAS ---------")
        println("1. 6 cuotas   -> Interes 20%")
        println("2. 12 cuotas  -> Interes 40%")
        println("3. 24 cuotas  -> Interes 60%")
        println("4. Salir")
        println("---------------------------------------")

        print("Seleccione una opcion: ")
        val opcion = readln()

        when (opcion) {
            "1" -> numCuotas = 6
            "2" -> numCuotas = 12
            "3" -> numCuotas = 24
            "4" -> {
                println("Saliendo del programa...")
                salir = true
            }
            else -> println("Opcion no valida. Intentelo nuevamente.")
        }
    }

    if (salir) return

    val tasaInteres = calcularInteres(numCuotas)
    val montoAPagar = calcularMontoAPagar(montoInicial, tasaInteres)
    val interes = montoAPagar - montoInicial
    val pagoMensual = calcularPagoMensual(montoAPagar, numCuotas)

    println()
    println("========================================")
    println("           RESUMEN DE COMPRA")
    println("========================================")
    println(String.format("%-24s %s", "Producto:", producto.nombre))
    println(String.format("%-24s S/ %8.2f", "Precio unitario:", producto.precio))
    println(String.format("%-24s %d", "Cantidad:", producto.cantidad))
    println(String.format("%-24s S/ %8.2f", "Monto Inicial:", montoInicial))
    println(String.format("%-24s %d", "Numero de cuotas:", numCuotas))
    println(String.format("%-24s %d%%", "Tasa de interes:", (tasaInteres * 100).toInt()))
    println(String.format("%-24s S/ %8.2f", "Interes:", interes))
    println(String.format("%-24s S/ %8.2f", "Monto a Pagar:", montoAPagar))
    println(String.format("%-24s S/ %8.2f", "Pago Mensual:", pagoMensual))
    mostrarCronograma(montoAPagar, pagoMensual, numCuotas)
}
