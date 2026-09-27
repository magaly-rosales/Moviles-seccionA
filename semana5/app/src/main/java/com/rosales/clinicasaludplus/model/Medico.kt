package com.rosales.clinicasaludplus.model

data class Medico(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val calificacion: Float
)

val listaMedicos = listOf(
    Medico(1, "Dra. Ana Torres", "Cardiología", 4.8f),
    Medico(2, "Dr. Luis Ramos", "Pediatría", 4.6f),
    Medico(3, "Dra. Carla Vega", "Dermatología", 4.9f),
    Medico(4, "Dr. Jorge Paredes", "Cardiología", 4.5f)
)