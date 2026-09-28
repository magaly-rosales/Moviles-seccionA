package com.rosales.clinicasaludplus.model

// Modelo de datos para representar a un médico en la aplicación
data class Medico(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val calificacion: Float,
    val experiencia: String = "10 años exp.",
    val resenasCount: Int = 120,
    val descripcion: String = "Especialista con amplia trayectoria y excelente atención médica."
)

// Lista de médicos disponibles con la información mostrada en la interfaz
val listaMedicos = listOf(
    Medico(
        id = 1,
        nombre = "Dra. Ana Torres",
        especialidad = "Cardiología",
        calificacion = 4.9f,
        experiencia = "12 años exp.",
        resenasCount = 128,
        descripcion = "Especialista en arritmias e hipertensión, formación en la Clínica Mayo."
    ),
    Medico(
        id = 2,
        nombre = "Dr. Luis Vega",
        especialidad = "Pediatría",
        calificacion = 4.7f,
        experiencia = "8 años exp.",
        resenasCount = 95,
        descripcion = "Atención integral infantil, control del crecimiento y desarrollo del niño."
    ),
    Medico(
        id = 3,
        nombre = "Dra. Rosa Díaz",
        especialidad = "Dermatología",
        calificacion = 4.8f,
        experiencia = "10 años exp.",
        resenasCount = 110,
        descripcion = "Especialista en dermatología clínica y cosmética, tratamiento de acné y cuidado de la piel."
    ),
    Medico(
        id = 4,
        nombre = "Dr. Jorge Paredes",
        especialidad = "Cardiología",
        calificacion = 4.5f,
        experiencia = "15 años exp.",
        resenasCount = 80,
        descripcion = "Especialista en prevención cardiovascular y diagnóstico por imágenes."
    )
)
