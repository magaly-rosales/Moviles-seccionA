package com.tecsup.auramakeup.data.repository

import com.tecsup.auramakeup.data.local.CarritoDao
import com.tecsup.auramakeup.data.local.CarritoEntity
import com.tecsup.auramakeup.data.mapper.aModelo
import com.tecsup.auramakeup.domain.model.Item
import com.tecsup.auramakeup.domain.model.ItemCarrito
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CarritoRepositoryImpl(
    private val dao: CarritoDao
) : CarritoRepository {

    override val items: Flow<List<ItemCarrito>> = dao.todos().map { lista -> lista.map { it.aModelo() } }

    override suspend fun agregar(item: Item) {
        val actual = dao.porId(item.id)
        if (actual == null) {
            dao.guardar(CarritoEntity(item.id, item.titulo, item.precio, item.imagenUrl, 1))
        } else {
            dao.cambiarCantidad(item.id, actual.cantidad + 1)
        }
    }

    override suspend fun aumentar(id: Int) {
        val actual = dao.porId(id) ?: return
        dao.cambiarCantidad(id, actual.cantidad + 1)
    }

    override suspend fun disminuir(id: Int) {
        val actual = dao.porId(id) ?: return
        if (actual.cantidad <= 1) dao.eliminar(id) else dao.cambiarCantidad(id, actual.cantidad - 1)
    }

    override suspend fun eliminar(id: Int) = dao.eliminar(id)

    override suspend fun vaciar() = dao.vaciar()
}