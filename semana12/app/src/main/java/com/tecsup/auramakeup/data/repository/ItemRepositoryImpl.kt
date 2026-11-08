package com.tecsup.auramakeup.data.repository

import com.tecsup.auramakeup.data.local.ProductoDao
import com.tecsup.auramakeup.data.mapper.aEntity
import com.tecsup.auramakeup.data.mapper.aModelo
import com.tecsup.auramakeup.data.remote.ApiService
import com.tecsup.auramakeup.domain.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class ItemRepositoryImpl(
    private val api: ApiService,
    private val dao: ProductoDao
) : ItemRepository {

    private val categorias = listOf("beauty", "skin-care", "fragrances")

    override val items: Flow<List<Item>> = dao.todos().map { lista -> lista.map { it.aModelo() } }
    override val favoritos: Flow<List<Item>> = dao.favoritos().map { lista -> lista.map { it.aModelo() } }

    override suspend fun refrescar(): Result<Int> = try {
        val favoritos = dao.idsFavoritos().toSet()
        val nuevos = categorias
            .flatMap { api.listar(it).products }
            .map { it.aEntity(esFavorito = it.id in favoritos) }
        dao.guardarTodos(nuevos)
        Result.success(nuevos.size)
    } catch (e: IOException) {
        Result.failure(e)
    } catch (e: HttpException) {
        Result.failure(e)
    }

    override suspend fun obtenerDetalle(id: Int): Result<Item> {
        val local = dao.porId(id)
        return try {
            Result.success(api.detalle(id).aModelo(esFavorito = local?.esFavorito ?: false))
        } catch (e: IOException) {
            if (local != null) Result.success(local.aModelo()) else Result.failure(e)
        } catch (e: HttpException) {
            if (local != null) Result.success(local.aModelo()) else Result.failure(e)
        }
    }

    override suspend fun alternarFavorito(id: Int) = dao.alternarFavorito(id)

    override suspend fun cantidadGuardada() = dao.contar()

    override suspend fun cantidadFavoritos() = dao.contarFavoritos()

    override suspend fun limpiarCache() = dao.borrarTodo()
}