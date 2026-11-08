package com.tecsup.auramakeup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tecsup.auramakeup.ui.navigation.AppNav
import com.tecsup.auramakeup.ui.theme.AuraMakeupTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val container = (application as AuraApp).container
        setContent {
            AuraMakeupTheme {
                AppNav(container)
            }
        }
    }
}