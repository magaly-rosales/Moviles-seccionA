package com.tecsup.mibodega.ui.cliente.screens.detalle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.SelectorCantidad
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.RojoPrecio

@Composable
fun DetalleProductoScreen(
    producto: Producto,
    esFavorito: Boolean,
    onVolver: () -> Unit,
    onFavorito: () -> Unit,
    onAgregarAlCarrito: (Producto, Int) -> Unit
) {
    var cantidad by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        EncabezadoDetalle(
            esFavorito = esFavorito,
            onVolver = onVolver,
            onFavorito = onFavorito
        )

        // Foto grande del producto centrada
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(producto.imagen),
                contentDescription = producto.nombre,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )

            Spacer(Modifier.height(2.dp))

            Text(
                text = producto.presentacion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "S/ %.2f".format(producto.precio),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = RojoPrecio
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = producto.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(12.dp)
                    )
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                SelectorCantidad(
                    cantidad = cantidad,
                    onIncrementar = { cantidad++ },
                    onDecrementar = { if (cantidad > 1) cantidad-- }
                )
            }

            Spacer(Modifier.weight(1f))

            BotonPrimario(
                texto = "Agregar al carrito",
                icono = rememberVectorPainter(Icons.Default.ShoppingCart),
                onClick = { onAgregarAlCarrito(producto, cantidad) }
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun EncabezadoDetalle(
    esFavorito: Boolean,
    onVolver: () -> Unit,
    onFavorito: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onVolver) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
        }
        IconButton(onClick = onFavorito) {
            Icon(
                imageVector = if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = if (esFavorito) "Quitar de favoritos" else "Agregar a favoritos",
                tint = if (esFavorito) RojoPrecio else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetalleProductoPreview() {
    BodegaTheme {
        DetalleProductoScreen(
            producto = listaProductosFake.first { it.nombre == "Coca-Cola Original" },
            esFavorito = false,
            onVolver = {},
            onFavorito = {},
            onAgregarAlCarrito = { _, _ -> }
        )
    }
}
