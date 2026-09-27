package com.rosales.clinicasaludplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rosales.clinicasaludplus.ui.theme.ClinicaSaludPlusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClinicaSaludPlusTheme {
                ClinicaSaludApp()
            }
        }
    }
}
