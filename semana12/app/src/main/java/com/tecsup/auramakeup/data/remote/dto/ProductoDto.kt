package com.tecsup.auramakeup.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductosResponse(
    val products: List<ProductoDto> = emptyList()
)

@Serializable
data class ProductoDto(
    val id: Int,
    val title: String,
    val description: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val rating: Double = 0.0,
    val stock: Int = 0,
    val brand: String? = null,
    val thumbnail: String? = null,
    val images: List<String> = emptyList()
)