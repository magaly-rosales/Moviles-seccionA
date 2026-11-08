package com.tecsup.auramakeup.data.remote

import com.tecsup.auramakeup.data.remote.dto.ProductoDto
import com.tecsup.auramakeup.data.remote.dto.ProductosResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("products/category/{slug}")
    suspend fun listar(@Path("slug") slug: String): ProductosResponse

    @GET("products/{id}")
    suspend fun detalle(@Path("id") id: Int): ProductoDto
}