package com.rosales.navlab.model

data class Estudiante(
    val nombre: String,
    val carrera: String,
    val fotoUrl: String
)

val listaEstudiantes = listOf(
    Estudiante("Magaly Rosales", "Diseño y Desarrollo de Software", "MR"),
    Estudiante("Juan Pérez", "Redes y Comunicaciones", "JP"),
    Estudiante("María Gómez", "Big Data y Ciencia de Datos", "MG"),
    Estudiante("Kevin Torres", "Diseño y Desarrollo de Software", "KT"),
    Estudiante("Ana Lino", "Operaciones Mineras", "AL")
)
