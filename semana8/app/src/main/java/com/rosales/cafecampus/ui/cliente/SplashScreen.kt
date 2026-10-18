package com.rosales.cafecampus.ui.cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onListo: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1200)
        onListo()
    }
    val cs = MaterialTheme.colorScheme
    Box(
        Modifier.fillMaxSize().background(cs.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Filled.LocalCafe, null, tint = cs.onPrimary, modifier = Modifier.size(72.dp))
            Spacer(Modifier.height(12.dp))
            Text("CafeCampus", color = cs.onPrimary, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Pide y recoge sin colas", color = cs.onPrimary.copy(alpha = 0.85f))
        }
    }
}