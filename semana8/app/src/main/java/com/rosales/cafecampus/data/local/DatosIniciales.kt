package com.rosales.cafecampus.data.local

import com.rosales.cafecampus.data.local.entity.Producto
import com.rosales.cafecampus.data.local.entity.Usuario
import com.rosales.cafecampus.model.Rol

object DatosIniciales {

    private val productos = listOf(
        Producto(nombre = "Café americano", descripcion = "Café pasado de altura, 8 oz", categoria = "Bebidas", precio = 4.00, icono = "cafe"),
        Producto(nombre = "Sándwich de pollo", descripcion = "Pan ciabatta con pollo y lechuga", categoria = "Comidas", precio = 9.00, icono = "pan"),
        Producto(nombre = "Menú del día", descripcion = "Entrada, segundo y refresco", categoria = "Comidas", precio = 12.00, icono = "plato"),
        Producto(nombre = "Jugo de naranja", descripcion = "Jugo natural recién exprimido", categoria = "Bebidas", precio = 5.00, icono = "jugo"),
        Producto(nombre = "Empanada", descripcion = "Empanada de carne horneada", categoria = "Snacks", precio = 4.50, icono = "dulce"),
        Producto(nombre = "Chicha morada", descripcion = "Vaso de chicha morada helada", categoria = "Bebidas", precio = 3.50, icono = "botella")
    )

    private val usuarios = listOf(
        Usuario(nombre = "Magaly Rosales", correo = "magaly@tecsup.com", clave = "1234", rol = Rol.CLIENTE.name),
        Usuario(nombre = "Administrador", correo = "admin@tecsup.com", clave = "admin", rol = Rol.ADMIN.name)
    )

    suspend fun sembrar(db: CafeDatabase) {
        if (db.usuarioDao().contar() == 0) {
            val productoDao = db.productoDao()
            if (productoDao.contar() == 0) productos.forEach { productoDao.insertar(it) }
            usuarios.forEach { db.usuarioDao().insertar(it) }
        }
    }
}