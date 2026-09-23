package com.rosales.tecsupfit.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Esquema de colores basado en la identidad visual de TECSUP Fit
private val TecsupFitColorScheme = lightColorScheme(
    primary = VerdeOscuro,
    onPrimary = Color.White,
    primaryContainer = VerdeClaro,
    onPrimaryContainer = VerdeOscuro,
    secondary = VerdeOscuro,
    onSecondary = Color.White,
    secondaryContainer = VerdeClaro,
    onSecondaryContainer = VerdeOscuro,
    background = FondoBlanco,
    onBackground = Color.Black,
    surface = FondoBlanco,
    onSurface = Color.Black,
    surfaceVariant = GrisTarjetas,
    onSurfaceVariant = Color.Black
)

@Composable
fun TecsupFitTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = TecsupFitColorScheme,
        typography = Typography,
        content = content
    )
}
