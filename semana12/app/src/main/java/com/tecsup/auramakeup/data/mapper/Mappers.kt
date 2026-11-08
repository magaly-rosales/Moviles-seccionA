package com.tecsup.auramakeup.data.mapper

import com.tecsup.auramakeup.data.remote.dto.ProductoDto
import com.tecsup.auramakeup.domain.model.Item

fun ProductoDto.aModelo(esFavorito: Boolean = false) = Item(
    id = id,
    titulo = title,
    marca = brand ?: "Aura",
    precio = price,
    imagenUrl = thumbnail,
    categoria = category,
    rating = rating,
    esFavorito = esFavorito,
    descripcion = description,
    stock = stock,
    imagenes = images
)