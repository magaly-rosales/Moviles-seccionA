package com.tecsup.mibodega.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val BodegaColorScheme = lightColorScheme(
    primary = VerdeBodega,
    onPrimary = Blanco,
    secondary = AzulEnlace,
    background = Blanco,
    onBackground = AzulTexto,
    surface = Blanco,
    onSurface = AzulTexto,
    surfaceVariant = GrisClaro,
    onSurfaceVariant = GrisTexto,
    outline = GrisBorde,
    error = RojoPrecio
)

private val BodegaColorSchemeOscuro = darkColorScheme(
    primary = VerdeBodega,
    onPrimary = Blanco,
    secondary = AzulEnlaceClaro,
    background = FondoOscuro,
    onBackground = TextoClaro,
    surface = SuperficieOscura,
    onSurface = TextoClaro,
    surfaceVariant = VarianteOscura,
    onSurfaceVariant = GrisTextoClaro,
    outline = BordeOscuro,
    error = RojoPrecio
)

@Composable
fun BodegaTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) BodegaColorSchemeOscuro else BodegaColorScheme,
        typography = BodegaTypography,
        content = content
    )
}