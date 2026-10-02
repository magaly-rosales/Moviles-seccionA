package com.rosales.navlab.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rosales.navlab.model.listaEstudiantes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, itemId: Int) {
    val estudiante = listaEstudiantes.getOrElse(itemId) { listaEstudiantes[0] }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expediente Académico", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4A148C)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8F9FA))
                .verticalScroll(rememberScrollState())
        ) {
            // Cabecera con degradado morado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF4A148C), Color(0xFF7B1FA2))
                        )
                    ),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Avatar circular superpuesto
                Surface(
                    modifier = Modifier
                        .offset(y = 40.dp)
                        .size(100.dp),
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 4.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp)
                            .background(Color(0xFFE1BEE7), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = estudiante.fotoUrl,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                color = Color(0xFF4A148C),
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            // Nombre y Carrera
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = estudiante.nombre,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121)
                    )
                )
                Text(
                    text = estudiante.carrera,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tarjeta inferior con información detallada
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InfoRow(label = "ID Estudiante", value = "2024-000${itemId + 1}")
                    InfoRow(label = "Correo", value = "${estudiante.nombre.lowercase().replace(" ", ".")}@tecsup.edu.pe")
                    InfoRow(label = "Facultad", value = "Ingeniería y Tecnología")
                    
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = Color(0xFFF0F0F0))
                    
                    Text(
                        text = "BIOGRAFÍA",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Color(0xFF6A1B9A),
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Estudiante destacado con alto interés en el desarrollo de soluciones tecnológicas innovadoras y aprendizaje continuo.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF424242),
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFF212121),
                fontWeight = FontWeight.Medium
            )
        )
    }
}
