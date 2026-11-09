package com.tecsup.auramakeup.data.mapper

import com.tecsup.auramakeup.data.local.CarritoEntity
import com.tecsup.auramakeup.data.local.ProductoEntity
import com.tecsup.auramakeup.data.remote.dto.ProductoDto
import com.tecsup.auramakeup.domain.model.Item
import com.tecsup.auramakeup.domain.model.ItemCarrito

fun ProductoDto.aEntity(esFavorito: Boolean = false) = ProductoEntity(
    id = id,
    titulo = title,
    marca = brand ?: "Aura",
    precio = price,
    imagenUrl = thumbnail,
    categoria = category,
    rating = rating,
    descripcion = description,
    stock = stock,
    esFavorito = esFavorito
)

fun ProductoEntity.aModelo() = Item(
    id = id,
    titulo = titulo,
    marca = marca,
    precio = precio,
    imagenUrl = imagenUrl,
    categoria = categoria,
    rating = rating,
    esFavorito = esFavorito,
    descripcion = descripcion,
    stock = stock
)

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

fun CarritoEntity.aModelo() = ItemCarrito(
    productoId = productoId,
    titulo = titulo,
    precio = precio,
    imagenUrl = imagenUrl,
    cantidad = cantidad
)