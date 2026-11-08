package com.tecsup.auramakeup.ui.components

fun categoriaEnEspanol(slug: String) = when (slug) {
    "beauty" -> "Maquillaje"
    "skin-care" -> "Cuidado de piel"
    "fragrances" -> "Perfumes"
    else -> slug
}