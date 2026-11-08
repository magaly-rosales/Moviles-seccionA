package com.tecsup.auramakeup.ui.lista

import com.tecsup.auramakeup.domain.model.Item

private const val CDN = "https://cdn.dummyjson.com/product-images/beauty"

val datosPrueba = listOf(
    Item(1, "Essence Mascara Lash Princess", "Essence", 9.99, "$CDN/essence-mascara-lash-princess/thumbnail.webp", "beauty", 4.9),
    Item(2, "Eyeshadow Palette with Mirror", "Glamour Beauty", 19.99, "$CDN/eyeshadow-palette-with-mirror/thumbnail.webp", "beauty", 3.3),
    Item(3, "Powder Canister", "Velvet Touch", 14.99, "$CDN/powder-canister/thumbnail.webp", "beauty", 3.8, esFavorito = true),
    Item(4, "Red Lipstick", "Chic Cosmetics", 12.99, "$CDN/red-lipstick/thumbnail.webp", "beauty", 4.4),
    Item(5, "Red Nail Polish", "Nail Couture", 8.99, "$CDN/red-nail-polish/thumbnail.webp", "beauty", 4.3)
)