package com.tecsup.mibodega.ui.cliente.modelo

import com.tecsup.mibodega.R

val listaCategorias = listOf("Todos", "Bebidas", "Abarrotes", "Snacks")

val listaProductosFake = listOf(
    Producto(
        id = 1,
        nombre = "Arroz Costeño",
        presentacion = "1 kg",
        descripcion = "Arroz extra, grano largo, ideal para el día a día.",
        precio = 4.50,
        categoria = "Abarrotes",
        imagen = R.drawable.arroz_costeno
    ),
    Producto(
        id = 2,
        nombre = "Aceite Primor",
        presentacion = "1 L",
        descripcion = "Aceite vegetal 1 L, alto en vitamina E.",
        precio = 8.90,
        categoria = "Abarrotes",
        imagen = R.drawable.aceite_primor
    ),
    Producto(
        id = 3,
        nombre = "Leche Gloria",
        presentacion = "1 L",
        descripcion = "Leche evaporada entera 1 L.",
        precio = 5.20,
        categoria = "Abarrotes",
        imagen = R.drawable.leche_gloria
    ),
    Producto(
        id = 4,
        nombre = "Galleta Oreo",
        presentacion = "126 g",
        descripcion = "Galletas de chocolate rellenas 126 g.",
        precio = 3.50,
        categoria = "Snacks",
        imagen = R.drawable.galleta_oreo
    ),
    Producto(
        id = 5,
        nombre = "Coca-Cola Original",
        presentacion = "1.5 L",
        descripcion = "Bebida gaseosa sabor cola. Ideal para compartir en familia.",
        precio = 6.50,
        categoria = "Bebidas",
        imagen = R.drawable.coca_cola
    )
)
