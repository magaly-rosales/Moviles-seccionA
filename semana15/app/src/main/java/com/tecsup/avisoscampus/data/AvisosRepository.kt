package com.tecsup.avisoscampus.data

import com.tecsup.avisoscampus.model.Aviso
import com.tecsup.avisoscampus.model.Origen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface AvisosRepository {
    val avisos: StateFlow<List<Aviso>>
    fun registrar(titulo: String, texto: String, origen: Origen, urgente: Boolean = false): Aviso
}

class AvisosRepositoryMemoria : AvisosRepository {
    private val _avisos = MutableStateFlow<List<Aviso>>(emptyList())
    override val avisos: StateFlow<List<Aviso>> = _avisos.asStateFlow()

    private var siguienteId = 1
    private val formatoHora = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    override fun registrar(titulo: String, texto: String, origen: Origen, urgente: Boolean): Aviso {
        val horaActual = formatoHora.format(Date())
        val nuevoAviso = Aviso(
            id = siguienteId++,
            titulo = titulo,
            texto = texto,
            origen = origen,
            hora = horaActual,
            urgente = urgente
        )
        _avisos.value = listOf(nuevoAviso) + _avisos.value
        return nuevoAviso
    }
}
