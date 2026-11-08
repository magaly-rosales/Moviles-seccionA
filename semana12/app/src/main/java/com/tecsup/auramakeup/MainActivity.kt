package com.tecsup.auramakeup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tecsup.auramakeup.ui.lista.ListaScreen
import com.tecsup.auramakeup.ui.lista.datosPrueba
import com.tecsup.auramakeup.ui.theme.AuraMakeupTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AuraMakeupTheme {
                ListaScreen(items = datosPrueba)
            }
        }
    }
}