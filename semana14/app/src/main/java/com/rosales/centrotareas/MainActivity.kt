package com.rosales.centrotareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rosales.centrotareas.data.TareasRepositoryLocal
import com.rosales.centrotareas.ui.PantallaTareas
import com.rosales.centrotareas.ui.theme.CentroTareasTheme
import com.rosales.centrotareas.viewmodel.TareasViewModel
import com.rosales.centrotareas.viewmodel.TareasViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CentroTareasTheme {
                val vm: TareasViewModel = viewModel(
                    factory = TareasViewModelFactory(TareasRepositoryLocal())
                )
                PantallaTareas(vm)
            }
        }
    }
}
