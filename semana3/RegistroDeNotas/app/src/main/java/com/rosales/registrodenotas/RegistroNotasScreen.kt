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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.text.style.TextAlign

data class Curso(val nombre: String, val peso: Float)

val CURSOS = listOf(
    Curso("Fundamentos de Programación", 0.20f),
    Curso("Programación Orientada a Objetos", 0.25f),
    Curso("Programación en Móviles", 0.30f),
    Curso("Base de Datos", 0.25f)
)

val MoradoPrimario = Color(0xFF5E35B1)
val VerdeConfirmacion = Color(0xFF2E7D32)
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

    val promedioPonderado = calcularPromedioPonderado(nota1, nota2, nota3, nota4)
    val promedioFinal = calcularPromedioFinal(promedioPonderado, redondear)
    val observacion = obtenerObservacion(promedioFinal)

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

            CursoSliderRow(CURSOS[0], nota1) { nota1 = it; mostrarResultado = false }
            CursoSliderRow(CURSOS[1], nota2) { nota2 = it; mostrarResultado = false }
            CursoSliderRow(CURSOS[2], nota3) { nota3 = it; mostrarResultado = false }
            CursoSliderRow(CURSOS[3], nota4) { nota4 = it; mostrarResultado = false }

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
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {
                    nota1 = 0f; nota2 = 0f; nota3 = 0f; nota4 = 0f
                    redondear = false
                    confirmado = false
                    mostrarResultado = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("LIMPIAR")
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (!mostrarResultado) {
                Text(
                    "Asigna las notas y confirma para calcular",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Promedio ponderado: ${"%.2f".format(promedioPonderado)}", fontSize = 14.sp)

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            "Promedio final: ${if (redondear) promedioFinal.toInt().toString() else "%.2f".format(promedioFinal)}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MoradoOscuro
                        )
                        if (redondear) {
                            Text("(redondeado)", fontSize = 11.sp, color = Color.Gray)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(8.dp))

                        val notas = listOf(nota1, nota2, nota3, nota4)
                        CURSOS.forEachIndexed { i, curso ->
                            val aporte = notas[i] * curso.peso
                            Text(
                                "${curso.nombre.substringBefore(" ")}: ${notas[i].toInt()} × ${(curso.peso * 100).toInt()}% = ${"%.2f".format(aporte)}",
                                fontSize = 12.sp,
                                color = Color.DarkGray
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .background(observacion.color, RoundedCornerShape(16.dp))
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(observacion.texto, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = VerdeConfirmacion)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Promedio calculado correctamente", color = VerdeConfirmacion, fontSize = 13.sp)
                }
            }



            Spacer(modifier = Modifier.weight(1f))

            Text(
                "Desarrollado por: Magaly Rosales Porras",
                fontSize = 11.sp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                textAlign = TextAlign.Center
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

            val colorBadge = if (valor < 13f) Rojo else Verde

            Box(modifier = Modifier.background(colorBadge.copy(alpha = 0.15f), CircleShape).padding(horizontal = 12.dp, vertical = 4.dp)) {
                Text(valor.toInt().toString(), color = colorBadge, fontWeight = FontWeight.Bold)
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