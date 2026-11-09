package com.tecsup.auramakeup.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = CarmesiPrimaryDark,
    onPrimary = Color.Black,
    primaryContainer = CarmesiPrimary,
    onPrimaryContainer = Color.White,
    secondary = OroRosaSecondaryDark,
    onSecondary = Color.Black,
    tertiary = DoradoTertiaryDark,
    onTertiary = Color.Black,
    background = FondoOscuro,
    onBackground = TextoClaro,
    surface = SuperficieOscura,
    onSurface = TextoClaro,
    surfaceVariant = SuperficieOscura,
    onSurfaceVariant = TextoClaro
)

private val LightColorScheme = lightColorScheme(
    primary = CarmesiPrimary,
    onPrimary = Color.White,
    primaryContainer = RosadoMuyClaro,
    onPrimaryContainer = CarmesiPrimary,
    secondary = OroRosaSecondary,
    onSecondary = Color.White,
    tertiary = DoradoTertiary,
    onTertiary = Color.White,
    background = BlancoCremaBackground,
    onBackground = TextoOscuroCalido,
    surface = BlancoSuperficie,
    onSurface = TextoOscuroCalido,
    surfaceVariant = BlancoCremaBackground,
    onSurfaceVariant = TextoOscuroCalido
)

@Composable
fun AuraMakeupTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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
