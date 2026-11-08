package com.tecsup.auramakeup.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    @Query("SELECT * FROM productos ORDER BY categoria, titulo")
    fun todos(): Flow<List<ProductoEntity>>

    @Query("SELECT * FROM productos WHERE esFavorito = 1 ORDER BY titulo")
    fun favoritos(): Flow<List<ProductoEntity>>

    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun porId(id: Int): ProductoEntity?

    @Query("SELECT id FROM productos WHERE esFavorito = 1")
    suspend fun idsFavoritos(): List<Int>

    @Query("SELECT COUNT(*) FROM productos")
    suspend fun contar(): Int

    @Query("SELECT COUNT(*) FROM productos WHERE esFavorito = 1")
    suspend fun contarFavoritos(): Int

    @Upsert
    suspend fun guardarTodos(productos: List<ProductoEntity>)

    @Query("UPDATE productos SET esFavorito = NOT esFavorito WHERE id = :id")
    suspend fun alternarFavorito(id: Int)

    @Query("DELETE FROM productos")
    suspend fun borrarTodo()
}