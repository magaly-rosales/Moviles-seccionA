package com.rosales.cafecampus.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CafeButton(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    enabled: Boolean = true,
    secundario: Boolean = false
) {
    if (secundario) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.height(48.dp),
            enabled = enabled,
            shape = RoundedCornerShape(10.dp)
        ) { Text(texto) }
    } else {
        Button(
            onClick = onClick,
            modifier = modifier.height(48.dp),
            enabled = enabled,
            shape = RoundedCornerShape(10.dp)
        ) { Text(texto, fontWeight = FontWeight.Bold) }
    }
}