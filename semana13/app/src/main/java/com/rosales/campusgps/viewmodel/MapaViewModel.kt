package com.rosales.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosales.campusgps.data.LugaresRepository
import com.rosales.campusgps.data.UbicacionNoDisponibleException
import com.rosales.campusgps.data.UbicacionRepository
import com.rosales.campusgps.model.Lugar
import com.rosales.campusgps.model.LugarConDistancia
import com.rosales.campusgps.model.Posicion
import com.rosales.campusgps.model.TipoError
import com.rosales.campusgps.model.UbicacionUi
import com.rosales.campusgps.util.distanciaMetros
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MapaViewModel(
    private val ubicacionRepo: UbicacionRepository,
    private val lugaresRepo: LugaresRepository
) : ViewModel() {

    private val _estado = MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)
    val estado: StateFlow<UbicacionUi> = _estado.asStateFlow()

    private val _lugares = MutableStateFlow(
        lugaresRepo.obtenerLugares().map { LugarConDistancia(it, null) }
    )
    val lugares: StateFlow<List<LugarConDistancia>> = _lugares.asStateFlow()

    private val _seleccionado = MutableStateFlow<Int?>(null)
    val seleccionado: StateFlow<Int?> = _seleccionado.asStateFlow()

    private val _categoriaSeleccionada = MutableStateFlow<String?>(null)
    val categoriaSeleccionada: StateFlow<String?> = _categoriaSeleccionada.asStateFlow()

    private val _radioMetros = MutableStateFlow(200)
    val radioMetros: StateFlow<Int> = _radioMetros.asStateFlow()

    val lugaresFiltrados: StateFlow<List<LugarConDistancia>> = combine(_lugares, _categoriaSeleccionada) { lista, cat ->
        if (cat == null) lista
        else lista.filter { it.lugar.categoria.equals(cat, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _lugares.value)

    val masCercano: StateFlow<Lugar?> = _lugares
        .map { lista -> lista.firstOrNull { it.metros != null }?.lugar }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun buscar() {
        if (!ubicacionRepo.playServicesDisponible()) {
            _estado.value = UbicacionUi.Error(
                "Este dispositivo no tiene Google Play Services",
                TipoError.SIN_PLAY_SERVICES
            )
            return
        }
        _estado.value = UbicacionUi.Buscando
        viewModelScope.launch {
            ubicacionRepo.obtenerPosicion()
                .onSuccess {
                    _estado.value = UbicacionUi.Lista(it)
                    actualizarDistancias(it)
                }
                .onFailure { _estado.value = errorDe(it) }
        }
    }

    fun permisoDenegado() {
        _estado.value = UbicacionUi.Error(
            "Sin permiso de ubicación no puedo mostrar dónde estás",
            TipoError.SIN_PERMISO
        )
    }

    fun seleccionar(id: Int?) {
        _seleccionado.value = id
    }

    fun filtrar(categoria: String?) {
        _categoriaSeleccionada.value = categoria
    }

    fun cambiarRadio(metros: Int) {
        _radioMetros.value = metros
        _lugares.value = _lugares.value.map { item ->
            val dentro = item.metros != null && item.metros <= metros
            item.copy(dentroDelRadio = dentro)
        }
    }

    private fun actualizarDistancias(pos: Posicion) {
        val radio = _radioMetros.value
        _lugares.value = lugaresRepo.obtenerLugares()
            .map { lugar ->
                val dist = distanciaMetros(pos, lugar)
                LugarConDistancia(lugar, dist, dentroDelRadio = dist <= radio)
            }
            .sortedBy { it.metros }
    }

    private fun errorDe(e: Throwable): UbicacionUi.Error = when (e) {
        is SecurityException -> UbicacionUi.Error("Falta el permiso de ubicación", TipoError.SIN_PERMISO)
        is UbicacionNoDisponibleException -> UbicacionUi.Error("Activa el GPS e intenta de nuevo", TipoError.GPS_APAGADO)
        else -> UbicacionUi.Error(e.message ?: "No se pudo obtener la ubicación", TipoError.OTRO)
    }
}
