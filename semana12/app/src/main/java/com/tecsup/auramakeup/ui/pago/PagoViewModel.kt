package com.tecsup.auramakeup.ui.pago

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tecsup.auramakeup.AppContainer
import com.tecsup.auramakeup.data.repository.CarritoRepository
import com.tecsup.auramakeup.notification.Notificador
import com.tecsup.auramakeup.ui.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class Pedido(val codigo: String, val monto: Double)

class PagoViewModel(
    private val carrito: CarritoRepository,
    private val notificador: Notificador
) : ViewModel() {

    val total: StateFlow<Double> = carrito.items
        .map { lista -> lista.sumOf { it.subtotal } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    private val _titular = MutableStateFlow("")
    val titular: StateFlow<String> = _titular.asStateFlow()

    private val _numero = MutableStateFlow("")
    val numero: StateFlow<String> = _numero.asStateFlow()

    private val _vencimiento = MutableStateFlow("")
    val vencimiento: StateFlow<String> = _vencimiento.asStateFlow()

    private val _cvv = MutableStateFlow("")
    val cvv: StateFlow<String> = _cvv.asStateFlow()

    private val _estado = MutableStateFlow<UiState<Pedido>?>(null)
    val estado: StateFlow<UiState<Pedido>?> = _estado.asStateFlow()

    fun onTitular(valor: String) {
        _titular.value = valor.take(30)
        limpiarError()
    }

    fun onNumero(valor: String) {
        _numero.value = valor.filter { it.isDigit() }.take(16)
        limpiarError()
    }

    fun onVencimiento(valor: String) {
        _vencimiento.value = valor.filter { it.isDigit() }.take(4)
        limpiarError()
    }

    fun onCvv(valor: String) {
        _cvv.value = valor.filter { it.isDigit() }.take(3)
        limpiarError()
    }

    fun pagar() {
        if (_estado.value is UiState.Cargando || _estado.value is UiState.Exito) return
        val error = validar()
        if (error != null) {
            _estado.value = UiState.Error(error)
            return
        }
        viewModelScope.launch {
            _estado.value = UiState.Cargando
            val monto = total.value
            delay(2000)
            carrito.vaciar()
            val codigo = "AURA-${(1000..9999).random()}"
            notificador.mostrar(
                "Pedido confirmado ✅",
                "Pago de $ %.2f aprobado. Pedido %s".format(monto, codigo)
            )
            _estado.value = UiState.Exito(Pedido(codigo, monto))
        }
    }

    private fun limpiarError() {
        if (_estado.value is UiState.Error) _estado.value = null
    }

    private fun validar(): String? = when {
        total.value <= 0.0 -> "Tu carrito está vacío"
        _titular.value.isBlank() -> "Ingresa el nombre del titular"
        _numero.value.length != 16 -> "El número de tarjeta debe tener 16 dígitos"
        !Regex("^(0[1-9]|1[0-2])\\d{2}$").matches(_vencimiento.value) -> "Vencimiento inválido (MMAA)"
        _cvv.value.length != 3 -> "El CVV debe tener 3 dígitos"
        else -> null
    }

    companion object {
        fun factory(container: AppContainer) = viewModelFactory {
            initializer { PagoViewModel(container.carritoRepository, container.notificador) }
        }
    }
}