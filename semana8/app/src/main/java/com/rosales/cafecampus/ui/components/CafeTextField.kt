package com.rosales.cafecampus.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CafeTextField(
    valor: String,
    onValor: (String) -> Unit,
    etiqueta: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    esClave: Boolean = false,
    teclado: KeyboardType = KeyboardType.Text,
    lineas: Int = 1
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValor,
        label = { Text(etiqueta) },
        modifier = modifier,
        singleLine = lineas == 1,
        minLines = lineas,
        shape = RoundedCornerShape(10.dp),
        visualTransformation = if (esClave) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (esClave) KeyboardType.Password else teclado)
    )
}