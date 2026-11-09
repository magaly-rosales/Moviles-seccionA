package com.tecsup.auramakeup.fakes

import com.tecsup.auramakeup.data.network.MonitorConexion
import com.tecsup.auramakeup.data.repository.ItemRepository
import com.tecsup.auramakeup.domain.model.Item
import com.tecsup.auramakeup.notification.Notificador
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

fun item(id: Int, esFavorito: Boolean = false) = Item(
    id = id,
    titulo = "Producto $id",
    marca = "Marca",
    precio = 10.0,
    imagenUrl = null,
    categoria = "beauty",
    rating = 4.0,
    esFavorito = esFavorito
)

class FakeItemRepository(
    private val resultado: Result<Int>,
    private val datosRemotos: List<Item> = emptyList(),
    guardados: List<Item> = emptyList()
) : ItemRepository {

    private val cache = MutableStateFlow(guardados)

    override val items: Flow<List<Item>> = cache
    override val favoritos: Flow<List<Item>> = cache.map { lista -> lista.filter { it.esFavorito } }

    override suspend fun refrescar(): Result<Int> {
        if (resultado.isSuccess) cache.value = datosRemotos
        return resultado
    }

    override suspend fun obtenerDetalle(id: Int): Result<Item> =
        cache.value.find { it.id == id }?.let { Result.success(it) }
            ?: Result.failure(NoSuchElementException())

    override suspend fun alternarFavorito(id: Int) {
        cache.update { lista ->
            lista.map { if (it.id == id) it.copy(esFavorito = !it.esFavorito) else it }
        }
    }

    override suspend fun cantidadGuardada() = cache.value.size

    override suspend fun cantidadFavoritos() = cache.value.count { it.esFavorito }

    override suspend fun limpiarCache() {
        cache.value = emptyList()
    }
}

class FakeNotificador : Notificador {
    val mensajes = mutableListOf<String>()

    override fun mostrar(titulo: String, texto: String) {
        mensajes.add(texto)
    }
}

class FakeMonitorConexion(conectado: Boolean = false) : MonitorConexion {
    override val conectado: Flow<Boolean> = MutableStateFlow(conectado)
}