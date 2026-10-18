package com.rosales.cafecampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.rosales.cafecampus.model.Rol
import com.rosales.cafecampus.ui.navigation.NavCafe
import com.rosales.cafecampus.ui.theme.CafeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = application as CafeApp
        setContent {
            CafeTheme(rol = Rol.CLIENTE) {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Box(Modifier.systemBarsPadding()) {
                        NavCafe(app)
                    }
                }
            }
        }
    }
}