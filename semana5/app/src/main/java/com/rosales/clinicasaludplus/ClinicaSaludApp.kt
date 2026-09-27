package com.rosales.clinicasaludplus

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.rosales.clinicasaludplus.navigation.AppNavigation

@Composable
fun ClinicaSaludApp() {
    val navController = rememberNavController()
    AppNavigation(navController = navController)
}