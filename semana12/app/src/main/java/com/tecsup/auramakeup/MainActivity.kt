package com.tecsup.auramakeup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tecsup.auramakeup.ui.lista.ListaScreen
import com.tecsup.auramakeup.ui.lista.ListaViewModel
import com.tecsup.auramakeup.ui.theme.AuraMakeupTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val container = (application as AuraApp).container
        setContent {
            AuraMakeupTheme {
                val listaViewModel: ListaViewModel = viewModel(factory = ListaViewModel.factory(container))
                ListaScreen(viewModel = listaViewModel)
            }
        }
    }
}