package com.rosales.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rosales.cafecampus.data.prefs.PreferenciasStore
import com.rosales.cafecampus.data.repository.CafeRepository

class CafeViewModelFactory(
    private val repo: CafeRepository,
    private val prefs: PreferenciasStore
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(MenuViewModel::class.java) -> MenuViewModel(repo)
        modelClass.isAssignableFrom(SesionViewModel::class.java) -> SesionViewModel(repo, prefs)
        modelClass.isAssignableFrom(PedidosClienteViewModel::class.java) -> PedidosClienteViewModel(repo, prefs)

        else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    } as T
}