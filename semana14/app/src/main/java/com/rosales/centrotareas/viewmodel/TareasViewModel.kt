package com.rosales.centrotareas.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosales.centrotareas.data.TareasRepository
import com.rosales.centrotareas.model.CargaUi
import com.rosales.centrotareas.model.PrimosUi
import com.rosales.centrotareas.model.RegistroTiempo
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TareasViewModel(private val repo: TareasRepository) : ViewModel() {

    // ----- Tarea con progreso (pasos 1 a 4)
    private val _mensaje = MutableStateFlow("Listo")
    val mensaje: StateFlow<String> = _mensaje.asStateFlow()

    private val _progreso = MutableStateFlow(0f)
    val progreso: StateFlow<Float> = _progreso.asStateFlow()

    private var trabajo: Job? = null
    private val _corriendo = MutableStateFlow(false)
    val corriendo: StateFlow<Boolean> = _corriendo.asStateFlow()

    fun tareaLarga() {
        if (_corriendo.value) return
        _corriendo.value = true
        trabajo = viewModelScope.launch {
            try {
                _progreso.value = 0f
                _mensaje.value = "Trabajando..."
                Log.d("HILO", "Antes: ${Thread.currentThread().name}")
                withContext(Dispatchers.Default) {
                    Log.d("HILO", "Dentro: ${Thread.currentThread().name}")
                    for (i in 1..100) {
                        delay(40)
                        _progreso.value = i / 100f
                    }
                }
                Log.d("HILO", "Despues: ${Thread.currentThread().name}")
                _mensaje.value = "Terminó"
            } finally {
                _corriendo.value = false
            }
        }
    }

    fun cancelar() {
        trabajo?.cancel()
        _mensaje.value = "Cancelado"
    }

    // ----- Contar primos (paso 5)
    private val _primos = MutableStateFlow<PrimosUi>(PrimosUi.Inicial)
    val primos: StateFlow<PrimosUi> = _primos.asStateFlow()

    private val _progresoPrimos = MutableStateFlow(0f)
    val progresoPrimos: StateFlow<Float> = _progresoPrimos.asStateFlow()

    private var trabajoPrimos: Job? = null

    fun contar(hasta: Int) {
        if (_primos.value is PrimosUi.Calculando) return
        _primos.value = PrimosUi.Calculando
        _progresoPrimos.value = 0f
        trabajoPrimos = viewModelScope.launch {
            val inicio = System.currentTimeMillis()
            try {
                val cantidad = repo.contarPrimos(hasta) { progreso ->
                    _progresoPrimos.value = progreso
                }
                _primos.value = PrimosUi.Listo(cantidad, System.currentTimeMillis() - inicio)
            } catch (e: CancellationException) {
                _primos.value = PrimosUi.Cancelado
                throw e
            }
        }
    }

    fun cancelarPrimos() {
        trabajoPrimos?.cancel()
    }

    // ----- Dos cargas (pasos 6 y 7)
    private val _carga = MutableStateFlow<CargaUi>(CargaUi.Inicial)
    val carga: StateFlow<CargaUi> = _carga.asStateFlow()

    private val _simularError = MutableStateFlow(false)
    val simularError: StateFlow<Boolean> = _simularError.asStateFlow()

    fun cambiarSimularError(valor: Boolean) {
        _simularError.value = valor
    }

    private suspend fun <T> ejecutarConReintento(
        maxIntentos: Int = 3,
        bloque: suspend () -> T
    ): T {
        var ultimoError: Exception? = null
        for (intento in 1..maxIntentos) {
            if (intento > 1) {
                _carga.value = CargaUi.Reintentando(intento)
                delay(500L * (intento - 1))
            }
            try {
                return bloque()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                ultimoError = e
            }
        }
        throw ultimoError ?: IllegalStateException("Error al reintentar")
    }

    // ----- Comparación de tiempos (Mejora 3)
    private val _tiempos = MutableStateFlow<List<RegistroTiempo>>(emptyList())
    val tiempos: StateFlow<List<RegistroTiempo>> = _tiempos.asStateFlow()

    private val _promedioUnaPorUna = MutableStateFlow(0.0)
    val promedioUnaPorUna: StateFlow<Double> = _promedioUnaPorUna.asStateFlow()

    private val _promedioALaVez = MutableStateFlow(0.0)
    val promedioALaVez: StateFlow<Double> = _promedioALaVez.asStateFlow()

    private fun agregarTiempo(modo: String, milisegundos: Long) {
        val nuevaLista = _tiempos.value + RegistroTiempo(modo, milisegundos)
        _tiempos.value = nuevaLista
        recalcularPromedios(nuevaLista)
    }

    private fun recalcularPromedios(lista: List<RegistroTiempo>) {
        val unaPorUnaList = lista.filter { it.modo == "Una por una" }
        _promedioUnaPorUna.value = if (unaPorUnaList.isNotEmpty()) unaPorUnaList.map { it.milisegundos }.average() else 0.0

        val aLaVezList = lista.filter { it.modo == "A la vez" }
        _promedioALaVez.value = if (aLaVezList.isNotEmpty()) aLaVezList.map { it.milisegundos }.average() else 0.0
    }

    fun limpiarTiempos() {
        _tiempos.value = emptyList()
        _promedioUnaPorUna.value = 0.0
        _promedioALaVez.value = 0.0
    }

    fun cargarUnaPorUna() {
        if (_carga.value is CargaUi.Cargando || _carga.value is CargaUi.Reintentando) return
        _carga.value = CargaUi.Cargando
        viewModelScope.launch {
            val inicio = System.currentTimeMillis()
            try {
                val (perfil, pedidos) = ejecutarConReintento {
                    val p = repo.cargarPerfil()
                    val ped = repo.cargarPedidos(_simularError.value)
                    Pair(p, ped)
                }
                val ms = System.currentTimeMillis() - inicio
                _carga.value = CargaUi.Exito(
                    "Una por una", perfil, pedidos.size, ms
                )
                agregarTiempo("Una por una", ms)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _carga.value = CargaUi.Error("No se pudo cargar: ${e.message}")
            }
        }
    }

    fun cargarALaVez() {
        if (_carga.value is CargaUi.Cargando || _carga.value is CargaUi.Reintentando) return
        _carga.value = CargaUi.Cargando
        viewModelScope.launch {
            val inicio = System.currentTimeMillis()
            try {
                val (perfil, pedidos) = ejecutarConReintento {
                    coroutineScope {
                        val a = async { repo.cargarPerfil() }
                        val b = async { repo.cargarPedidos(_simularError.value) }
                        Pair(a.await(), b.await())
                    }
                }
                val ms = System.currentTimeMillis() - inicio
                _carga.value = CargaUi.Exito(
                    "A la vez", perfil, pedidos.size, ms
                )
                agregarTiempo("A la vez", ms)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _carga.value = CargaUi.Error("No se pudo cargar: ${e.message}")
            }
        }
    }
}
