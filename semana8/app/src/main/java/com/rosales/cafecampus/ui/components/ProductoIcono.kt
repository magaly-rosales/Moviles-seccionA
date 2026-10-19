package com.rosales.cafecampus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import java.io.File

fun iconoDe(nombre: String): ImageVector = when (nombre) {
    "cafe" -> Icons.Filled.LocalCafe
    "plato" -> Icons.Filled.RestaurantMenu
    "jugo" -> Icons.Filled.LocalDrink
    "pan" -> Icons.Filled.BakeryDining
    "botella" -> Icons.Filled.WaterDrop
    "dulce" -> Icons.Filled.Cake
    else -> Icons.Filled.Fastfood
}

@Composable
fun ProductoIcono(
    icono: String,
    modifier: Modifier = Modifier.size(56.dp),
    imagen: String? = null
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        if (!imagen.isNullOrBlank()) {
            AsyncImage(
                model = File(imagen),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                iconoDe(icono),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize(0.5f)
            )
        }
    }
}