package com.rosales.cuotas_app

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

    println()
    println("Monto Inicial: S/ ${"%.2f".format(montoInicial)}")
    println("Numero de cuotas seleccionadas: $numCuotas")
    println("Tasa de interes: ${(tasaInteres * 100).toInt()}%")
}