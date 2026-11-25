package com.tecsup.avisoscampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.avisoscampus.data.AvisosRepository
import com.tecsup.avisoscampus.model.Aviso
import com.tecsup.avisoscampus.model.Origen
import com.tecsup.avisoscampus.model.PermisoUi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AvisosViewModel(private val repository: AvisosRepository) : ViewModel() {

    val avisos: StateFlow<List<Aviso>> = repository.avisos

    private val _eventos = MutableSharedFlow<Aviso>()
    val eventos: SharedFlow<Aviso> = _eventos.asSharedFlow()

    private val _permiso = MutableStateFlow<PermisoUi>(PermisoUi.SinPedir)
    val permiso: StateFlow<PermisoUi> = _permiso.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    private var trabajo: Job? = null
    private val _faltan = MutableStateFlow<Int?>(null)
    val faltan: StateFlow<Int?> = _faltan.asStateFlow()

    private val _escuchandoAvion = MutableStateFlow(false)
    val escuchandoAvion: StateFlow<Boolean> = _escuchandoAvion.asStateFlow()

    fun permisoRespondido(concedido: Boolean) {
        if (concedido) {
            _permiso.value = PermisoUi.Concedido
            _mensaje.value = null
        } else {
            _permiso.value = PermisoUi.Denegado
        }
    }

    fun avisarAhora() {
        viewModelScope.launch {
            val aviso = repository.registrar("Tarea lista", "Tu entrega fue registrada", Origen.BOTON)
            _eventos.emit(aviso)
        }
    }

    fun recordarEn(segundos: Int = 10) {
        if (trabajo?.isActive == true) return
        trabajo = viewModelScope.launch {
            try {
                for (s in segundos downTo 1) {
                    _faltan.value = s
                    delay(1000)
                }
                val aviso = repository.registrar(
                    "Recordatorio",
                    "Revisa tu laboratorio pendiente. Recuerda subir el README, las capturas y hacer push de la rama antes de la fecha límite.",
                    Origen.RECORDATORIO,
                    urgente = true
                )
                _eventos.emit(aviso)
            } finally {
                _faltan.value = null
            }
        }
    }

    fun cancelarRecordatorio() {
        trabajo?.cancel()
    }

    fun cambiarEscucha(valor: Boolean) {
        _escuchandoAvion.value = valor
    }

    fun alCambiarModoAvion(activo: Boolean) {
        val textoModo = if (activo) "Activado" else "Desactivado"
        viewModelScope.launch {
            val aviso = repository.registrar(
                "Modo avión",
                textoModo,
                Origen.MODO_AVION
            )
            _eventos.emit(aviso)
        }
    }

    fun noSePudoMostrar() {
        _mensaje.value = "Sin permiso: el aviso se guardó en el historial, pero no se mostró"
    }
}
