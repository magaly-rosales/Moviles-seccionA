package com.tecsup.auramakeup

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.tecsup.auramakeup.ui.navigation.AppNav
import com.tecsup.auramakeup.ui.theme.AuraMakeupTheme

class MainActivity : ComponentActivity() {

    private val permisoNotificaciones =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        pedirPermisoNotificaciones()
        val container = (application as AuraApp).container
        setContent {
            AuraMakeupTheme {
                AppNav(container)
            }
        }
    }

    private fun pedirPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            permisoNotificaciones.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}