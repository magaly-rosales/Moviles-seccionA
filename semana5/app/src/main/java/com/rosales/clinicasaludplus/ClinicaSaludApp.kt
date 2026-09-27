package com.rosales.clinicasaludplus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.rosales.clinicasaludplus.model.Cita
import com.rosales.clinicasaludplus.navigation.AppNavigation

@Composable
fun ClinicaSaludApp() {
    val navController = rememberNavController()
    val citas = remember { mutableStateListOf<Cita>() }

    AppNavigation(navController = navController, citas = citas)
}