package com.rosales.cafecampus.ui.cliente

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rosales.cafecampus.ui.components.ProductoCard
import com.rosales.cafecampus.viewmodel.MenuViewModel

@Composable
fun MenuScreen(vm: MenuViewModel, onProducto: (Int) -> Unit) {
    val productos by vm.productos.collectAsState()

    Column(Modifier.fillMaxSize()) {
        Text(
            "Menú de hoy",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
            items(productos) { p -> ProductoCard(p) { onProducto(p.id) } }
        }
    }
}