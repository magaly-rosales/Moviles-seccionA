package com.tecsup.auramakeup.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDao {

    @Query("SELECT * FROM carrito ORDER BY titulo")
    fun todos(): Flow<List<CarritoEntity>>

    @Query("SELECT * FROM carrito WHERE productoId = :id")
    suspend fun porId(id: Int): CarritoEntity?

    @Upsert
    suspend fun guardar(item: CarritoEntity)

    @Query("UPDATE carrito SET cantidad = :cantidad WHERE productoId = :id")
    suspend fun cambiarCantidad(id: Int, cantidad: Int)

    @Query("DELETE FROM carrito WHERE productoId = :id")
    suspend fun eliminar(id: Int)

    @Query("DELETE FROM carrito")
    suspend fun vaciar()
}