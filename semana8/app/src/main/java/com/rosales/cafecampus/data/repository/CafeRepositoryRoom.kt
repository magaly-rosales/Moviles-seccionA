package com.rosales.cafecampus.data.repository

import com.rosales.cafecampus.data.local.CafeDatabase
import com.rosales.cafecampus.data.local.entity.Producto
import kotlinx.coroutines.flow.Flow

class CafeRepositoryRoom(db: CafeDatabase) : CafeRepository {

    private val productoDao = db.productoDao()

    override fun observarProductos(): Flow<List<Producto>> = productoDao.observarTodos()

    override suspend fun guardarProducto(p: Producto) {
        if (p.id == 0) productoDao.insertar(p) else productoDao.actualizar(p)
    }

    override suspend fun eliminarProducto(p: Producto) = productoDao.eliminar(p)

    override suspend fun cambiarDisponibilidad(id: Int, disponible: Boolean) =
        productoDao.cambiarDisponibilidad(id, disponible)


}