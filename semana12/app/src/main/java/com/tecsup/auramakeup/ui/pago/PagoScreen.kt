package com.tecsup.auramakeup.ui.pago

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.auramakeup.ui.UiState
import com.tecsup.auramakeup.ui.components.Cargando
import com.tecsup.auramakeup.ui.theme.CarmesiPrimary
import com.tecsup.auramakeup.ui.theme.OroRosaSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagoScreen(
    viewModel: PagoViewModel,
    onVolver: () -> Unit,
    onSeguirComprando: () -> Unit
) {
    val total by viewModel.total.collectAsStateWithLifecycle()
    val titular by viewModel.titular.collectAsStateWithLifecycle()
    val numero by viewModel.numero.collectAsStateWithLifecycle()
    val vencimiento by viewModel.vencimiento.collectAsStateWithLifecycle()
    val cvv by viewModel.cvv.collectAsStateWithLifecycle()
    val estado by viewModel.estado.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pago de pedido", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CarmesiPrimary
                )
            )
        },
        bottomBar = {
            if (estado !is UiState.Cargando && estado !is UiState.Exito) {
                Surface(shadowElevation = 8.dp) {
                    Box(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .padding(16.dp)
                    ) {
                        Button(
                            onClick = viewModel::pagar,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                        ) {
                            Text(
                                text = "Pagar $ %.2f".format(total),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val e = estado) {
                is UiState.Cargando -> Cargando(texto = "Procesando pago...")
                is UiState.Exito -> {
                    ExitoPagoContenido(
                        pedido = e.datos,
                        onSeguirComprando = onSeguirComprando
                    )
                }
                else -> {
                    FormularioPagoContenido(
                        titular = titular,
                        numero = numero,
                        vencimiento = vencimiento,
                        cvv = cvv,
                        errorMensaje = (e as? UiState.Error)?.mensaje,
                        onTitularChange = viewModel::onTitular,
                        onNumeroChange = viewModel::onNumero,
                        onVencimientoChange = viewModel::onVencimiento,
                        onCvvChange = viewModel::onCvv
                    )
                }
            }
        }
    }
}

@Composable
private fun FormularioPagoContenido(
    titular: String,
    numero: String,
    vencimiento: String,
    cvv: String,
    errorMensaje: String?,
    onTitularChange: (String) -> Unit,
    onNumeroChange: (String) -> Unit,
    onVencimientoChange: (String) -> Unit,
    onCvvChange: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VistaPreviaTarjeta(
            titular = titular,
            numero = numero,
            vencimiento = vencimiento
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (errorMensaje != null) {
            Surface(
                color = MaterialTheme.colorScheme.errorContainer,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = errorMensaje,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(14.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        OutlinedTextField(
            value = titular,
            onValueChange = onTitularChange,
            label = { Text("Nombre del titular") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = numero,
            onValueChange = onNumeroChange,
            label = { Text("Número de tarjeta (16 dígitos)") },
            leadingIcon = { Icon(Icons.Filled.CreditCard, contentDescription = null) },
            placeholder = { Text("1234 5678 9012 3456") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = vencimiento,
                onValueChange = onVencimientoChange,
                label = { Text("Vencimiento (MMAA)") },
                placeholder = { Text("MMAA") },
                leadingIcon = { Icon(Icons.Filled.DateRange, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = cvv,
                onValueChange = onCvvChange,
                label = { Text("CVV (3 dígitos)") },
                placeholder = { Text("123") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun VistaPreviaTarjeta(
    titular: String,
    numero: String,
    vencimiento: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(CarmesiPrimary, OroRosaSecondary)
                    )
                )
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Aura Pay",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Icon(
                        imageVector = Icons.Filled.CreditCard,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                val numeroFormateado = if (numero.isEmpty()) {
                    "•••• •••• •••• ••••"
                } else {
                    numero.padEnd(16, '•').chunked(4).joinToString(" ")
                }

                Text(
                    text = numeroFormateado,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "TITULAR",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = if (titular.isNotBlank()) titular.uppercase() else "NOMBRE DEL TITULAR",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "VENCE",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        val fechaFormateada = if (vencimiento.length == 4) {
                            "${vencimiento.substring(0, 2)}/${vencimiento.substring(2, 4)}"
                        } else {
                            vencimiento.ifEmpty { "MMAA" }
                        }
                        Text(
                            text = fechaFormateada,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ExitoPagoContenido(
    pedido: Pedido,
    onSeguirComprando: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = Color(0xFF2E7D32),
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Pago aprobado",
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Código de pedido",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = pedido.codigo,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Monto pagado",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$ %.2f".format(pedido.monto),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onSeguirComprando,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text(
                text = "Seguir comprando",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
