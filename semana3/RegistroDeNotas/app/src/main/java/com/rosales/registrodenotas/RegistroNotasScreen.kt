package com.tuapp.registronotas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

data class Curso(val nombre: String, val peso: Float)

val CURSOS = listOf(
    Curso("Fundamentos de Programación", 0.20f),
    Curso("Programación Orientada a Objetos", 0.25f),
    Curso("Programación en Móviles", 0.30f),
    Curso("Base de Datos", 0.25f)
)

val MoradoPrimario = Color(0xFF5E35B1)
val MoradoOscuro = Color(0xFF4527A0)
val GrisDeshabilitado = Color(0xFFBFBFC4)
val FondoDegradadoInicio = Color(0xFFF5F0FA)
val FondoDegradadoFin = Color(0xFFEDE4F5)
val BadgeFondo = Color(0xFFEDE7F6)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroNotasScreen() {
    var nota1 by remember { mutableStateOf(0f) }
    var nota2 by remember { mutableStateOf(0f) }
    var nota3 by remember { mutableStateOf(0f) }
    var nota4 by remember { mutableStateOf(0f) }

    var redondear by remember { mutableStateOf(false) }
    var confirmado by remember { mutableStateOf(false) }
    var mostrarResultado by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Notas", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MoradoPrimario,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Brush.verticalGradient(listOf(FondoDegradadoInicio, FondoDegradadoFin)))
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Text("Notas del ciclo", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Desliza para asignar cada nota (0 a 20)", fontSize = 13.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))

            CursoSliderRow(CURSOS[0], nota1) { nota1 = it }
            CursoSliderRow(CURSOS[1], nota2) { nota2 = it }
            CursoSliderRow(CURSOS[2], nota3) { nota3 = it }
            CursoSliderRow(CURSOS[3], nota4) { nota4 = it }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Redondear promedio final", fontSize = 14.sp)
                Switch(
                    checked = redondear,
                    onCheckedChange = { redondear = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = MoradoPrimario)
                )
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = confirmado,
                    onCheckedChange = { confirmado = it },
                    colors = CheckboxDefaults.colors(checkedColor = MoradoPrimario)
                )
                Text("Confirmo que las notas son correctas", fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))


            Button(
                onClick = { mostrarResultado = true },
                enabled = confirmado,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MoradoPrimario,
                    disabledContainerColor = GrisDeshabilitado
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("CALCULAR PROMEDIO", fontWeight = FontWeight.Bold)
            }



            Spacer(modifier = Modifier.weight(1f))

            Text(
                "Desarrollado por: Magaly Rosales Porras",
                fontSize = 11.sp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
private fun CursoSliderRow(curso: Curso, valor: Float, onValorChange: (Float) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("${curso.nombre} (${(curso.peso * 100).toInt()}%)", fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Box(modifier = Modifier.background(BadgeFondo, CircleShape).padding(horizontal = 12.dp, vertical = 4.dp)) {
                Text(valor.toInt().toString(), color = MoradoOscuro, fontWeight = FontWeight.Bold)
            }
        }
        Slider(
            value = valor,
            onValueChange = onValorChange,
            valueRange = 0f..20f,
            steps = 19,
            colors = SliderDefaults.colors(thumbColor = MoradoPrimario, activeTrackColor = MoradoPrimario)
        )
    }
}

fun calcularPromedioPonderado(nota1: Float, nota2: Float, nota3: Float, nota4: Float): Float {
    return nota1 * CURSOS[0].peso +
            nota2 * CURSOS[1].peso +
            nota3 * CURSOS[2].peso +
            nota4 * CURSOS[3].peso
}

fun calcularPromedioFinal(promedioPonderado: Float, redondear: Boolean): Float {
    return if (redondear) {
        promedioPonderado.roundToInt().toFloat()
    } else {
        promedioPonderado
    }
}
val VerdeOscuro = Color(0xFF1B5E20)
val Verde = Color(0xFF43A047)
val Ambar = Color(0xFFFFA000)
val Rojo = Color(0xFFE53935)

data class Observacion(val texto: String, val color: Color)

fun obtenerObservacion(promedioFinal: Float): Observacion {
    return when {
        promedioFinal >= 17f -> Observacion("EXCELENTE", VerdeOscuro)
        promedioFinal >= 13f -> Observacion("APROBADO", Verde)
        promedioFinal >= 10f -> Observacion("EN RECUPERACIÓN", Ambar)
        else -> Observacion("DESAPROBADO", Rojo)
    }
}