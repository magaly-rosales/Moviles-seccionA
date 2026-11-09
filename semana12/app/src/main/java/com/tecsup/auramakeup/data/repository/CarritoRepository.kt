package com.tecsup.auramakeup.data.repository

import com.tecsup.auramakeup.domain.model.Item
import com.tecsup.auramakeup.domain.model.ItemCarrito
import kotlinx.coroutines.flow.Flow

interface CarritoRepository {
    val items: Flow<List<ItemCarrito>>
    suspend fun agregar(item: Item)
    suspend fun aumentar(id: Int)
    suspend fun disminuir(id: Int)
    suspend fun eliminar(id: Int)
    suspend fun vaciar()
}