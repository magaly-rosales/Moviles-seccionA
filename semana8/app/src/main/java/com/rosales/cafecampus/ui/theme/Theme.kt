package com.rosales.cafecampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.rosales.cafecampus.model.Rol

private fun esquemaClaro(rol: Rol) = if (rol == Rol.ADMIN)
    lightColorScheme(
        primary = IndigoPrimario, onPrimary = Color.White,
        primaryContainer = IndigoContenedor, onPrimaryContainer = Color(0xFF1E1B4B),
        background = IndigoFondo, surface = Color.White
    )
else
    lightColorScheme(
        primary = NaranjaPrimario, onPrimary = Color.White,
        primaryContainer = NaranjaContenedor, onPrimaryContainer = Color(0xFF7C2D12),
        background = NaranjaFondo, surface = Color.White
    )

private fun esquemaOscuro(rol: Rol) = if (rol == Rol.ADMIN)
    darkColorScheme(
        primary = Color(0xFF818CF8), onPrimary = Color(0xFF1E1B4B),
        primaryContainer = Color(0xFF3730A3), onPrimaryContainer = IndigoContenedor
    )
else
    darkColorScheme(
        primary = Color(0xFFFB923C), onPrimary = Color(0xFF431407),
        primaryContainer = Color(0xFF9A3412), onPrimaryContainer = NaranjaContenedor
    )

@Composable
fun CafeTheme(
    rol: Rol = Rol.CLIENTE,
    oscuro: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (oscuro) esquemaOscuro(rol) else esquemaClaro(rol),
        typography = Typography,
        content = content
    )
}