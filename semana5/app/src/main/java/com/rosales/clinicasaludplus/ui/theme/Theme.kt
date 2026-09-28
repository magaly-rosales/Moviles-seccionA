package com.rosales.clinicasaludplus.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Esquema de colores para el tema claro con la paleta morada
private val LightColorScheme = lightColorScheme(
    primary = MoradoPrincipal,
    onPrimary = Color.White,
    primaryContainer = MoradoClaro,
    onPrimaryContainer = MoradoPrincipal,
    secondary = MoradoPrincipal,
    onSecondary = Color.White,
    background = Color.White,
    surface = Color.White,
    surfaceVariant = GrisTarjetas,
    onSurfaceVariant = GrisTextoSecundario
)

// Esquema de colores para el tema oscuro
private val DarkColorScheme = darkColorScheme(
    primary = MoradoPrincipal,
    onPrimary = Color.White,
    primaryContainer = MoradoClaro,
    onPrimaryContainer = MoradoPrincipal
)

@Composable
fun ClinicaSaludPlusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Desactivamos dynamicColor para garantizar el uso de la paleta morada
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
