package com.rosales.cafecampus.data.local

import com.rosales.cafecampus.data.local.entity.Producto

object DatosIniciales {

    private val productos = listOf(
        Producto(nombre = "Café americano", descripcion = "Café pasado de altura, 8 oz", categoria = "Bebidas", precio = 4.00, icono = "cafe"),
        Producto(nombre = "Sándwich de pollo", descripcion = "Pan ciabatta con pollo y lechuga", categoria = "Comidas", precio = 9.00, icono = "pan"),
        Producto(nombre = "Menú del día", descripcion = "Entrada, segundo y refresco", categoria = "Comidas", precio = 12.00, icono = "plato"),
        Producto(nombre = "Jugo de naranja", descripcion = "Jugo natural recién exprimido", categoria = "Bebidas", precio = 5.00, icono = "jugo"),
        Producto(nombre = "Empanada", descripcion = "Empanada de carne horneada", categoria = "Snacks", precio = 4.50, icono = "dulce"),
        Producto(nombre = "Chicha morada", descripcion = "Vaso de chicha morada helada", categoria = "Bebidas", precio = 3.50, icono = "botella")
    )

    suspend fun sembrar(db: CafeDatabase) {
        val productoDao = db.productoDao()
        if (productoDao.contar() == 0) {
            productos.forEach { productoDao.insertar(it) }
        }
    }
}