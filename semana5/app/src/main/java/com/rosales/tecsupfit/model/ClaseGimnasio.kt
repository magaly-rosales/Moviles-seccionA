package com.rosales.tecsupfit.model

enum class Periodo {
    HOY,
    ESTA_SEMANA
}

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
    )
)
data class Reserva(
    val clase: ClaseGimnasio,
    val estado: String // "Confirmada" o "Completada"
)

val listaReservas = listOf(
    Reserva(clase = listaClases[1], estado = "Confirmada"),
    Reserva(clase = listaClases[0], estado = "Completada")
)