package com.tecsup.auramakeup.data.repository

import com.tecsup.auramakeup.data.mapper.aModelo
import com.tecsup.auramakeup.data.remote.ApiService
import com.tecsup.auramakeup.domain.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import retrofit2.HttpException
import java.io.IOException

class ItemRepositoryImpl(
    private val api: ApiService
) : ItemRepository {

    private val categorias = listOf("beauty", "skin-care", "fragrances")
    private val cache = MutableStateFlow<List<Item>>(emptyList())

    override val items: Flow<List<Item>> = cache
    override val favoritos: Flow<List<Item>> = cache.map { lista -> lista.filter { it.esFavorito } }

    override suspend fun refrescar(): Result<Int> = try {
        val favoritos = cache.value.filter { it.esFavorito }.map { it.id }.toSet()
        val nuevos = categorias
            .flatMap { api.listar(it).products }
            .map { it.aModelo(esFavorito = it.id in favoritos) }
        cache.value = nuevos
        Result.success(nuevos.size)
    } catch (e: IOException) {
        Result.failure(e)
    } catch (e: HttpException) {
        Result.failure(e)
    }

    override suspend fun obtenerDetalle(id: Int): Result<Item> = try {
        val esFavorito = cache.value.any { it.id == id && it.esFavorito }
        Result.success(api.detalle(id).aModelo(esFavorito))
    } catch (e: IOException) {
        Result.failure(e)
    } catch (e: HttpException) {
        Result.failure(e)
    }

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