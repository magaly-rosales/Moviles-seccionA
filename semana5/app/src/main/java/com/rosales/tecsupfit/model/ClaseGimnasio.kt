package com.rosales.tecsupfit.model

// Períodos para filtrar las clases en la pantalla de Inicio
enum class Periodo {
    HOY,
    ESTA_SEMANA
}

// Modelo de datos para las clases del gimnasio
data class ClaseGimnasio(
    val id: Int,
    val nombre: String,
    val horario: String,
    val sala: String,
    val duracionMin: Int,
    val descripcion: String,
    val cuposDisponibles: Int,
    val cuposTotales: Int,
    val periodo: Periodo
)

// Catálogo ampliado de 8 clases de ejemplo
val listaClases = listOf(
    ClaseGimnasio(
        id = 1,
        nombre = "Yoga funcional",
        horario = "7:00 am",
        sala = "Sala 2",
        duracionMin = 50,
        descripcion = "Sesión de yoga enfocada en movilidad y respiración.",
        cuposDisponibles = 10,
        cuposTotales = 15,
        periodo = Periodo.HOY
    ),
    ClaseGimnasio(
        id = 2,
        nombre = "Cross Training",
        horario = "6:00 pm",
        sala = "Sala 1",
        duracionMin = 45,
        descripcion = "Entrenamiento funcional de alta intensidad. Cupos limitados.",
        cuposDisponibles = 8,
        cuposTotales = 12,
        periodo = Periodo.HOY
    ),
    ClaseGimnasio(
        id = 3,
        nombre = "Spinning",
        horario = "7:30 pm",
        sala = "Sala 3",
        duracionMin = 40,
        descripcion = "Ciclismo indoor con música y ritmo variable.",
        cuposDisponibles = 5,
        cuposTotales = 20,
        periodo = Periodo.ESTA_SEMANA
    ),
    ClaseGimnasio(
        id = 4,
        nombre = "Pilates Mat",
        horario = "8:30 am",
        sala = "Sala 2",
        duracionMin = 45,
        descripcion = "Ejercicios de postura, fuerza central y estiramiento.",
        cuposDisponibles = 6,
        cuposTotales = 10,
        periodo = Periodo.HOY
    ),
    ClaseGimnasio(
        id = 5,
        nombre = "Boxeo Fit",
        horario = "7:00 pm",
        sala = "Sala Ring",
        duracionMin = 60,
        descripcion = "Entrenamiento de técnicas de boxeo y acondicionamiento.",
        cuposDisponibles = 4,
        cuposTotales = 15,
        periodo = Periodo.HOY
    ),
    ClaseGimnasio(
        id = 6,
        nombre = "Body Pump",
        horario = "6:00 am",
        sala = "Sala 1",
        duracionMin = 55,
        descripcion = "Clase con barras y discos para fortalecer todo el cuerpo.",
        cuposDisponibles = 12,
        cuposTotales = 20,
        periodo = Periodo.ESTA_SEMANA
    ),
    ClaseGimnasio(
        id = 7,
        nombre = "Zumba Dance",
        horario = "5:00 pm",
        sala = "Sala 3",
        duracionMin = 50,
        descripcion = "Rutinas aeróbicas al ritmo de música latina y moderna.",
        cuposDisponibles = 15,
        cuposTotales = 25,
        periodo = Periodo.ESTA_SEMANA
    ),
    ClaseGimnasio(
        id = 8,
        nombre = "Calistenia",
        horario = "8:00 pm",
        sala = "Zona Exterior",
        duracionMin = 60,
        descripcion = "Ejercicios con el propio peso corporal para fuerza y agilidad.",
        cuposDisponibles = 2,
        cuposTotales = 10,
        periodo = Periodo.ESTA_SEMANA
    )
)

// Modelo de datos para la reserva de un usuario
data class Reserva(
    val clase: ClaseGimnasio,
    val estado: String
)

// Lista de reservas iniciales de prueba
val listaReservas = listOf(
    Reserva(clase = listaClases[1], estado = "Confirmada"),
    Reserva(clase = listaClases[0], estado = "Completada")
)
