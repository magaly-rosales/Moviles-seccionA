package com.tecsup.mibodega.ui.componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.tecsup.mibodega.ui.theme.VerdeBodega

enum class DestinoBarra(val etiqueta: String, val icono: ImageVector) {
    INICIO("Inicio", Icons.Default.Home),
    FAVORITOS("Favoritos", Icons.Default.Favorite),
    PEDIDOS("Pedidos", Icons.Default.Receipt),
    PERFIL("Perfil", Icons.Default.Person)
}

@Composable
fun BarraNavegacion(
    actual: DestinoBarra,
    onNavegar: (DestinoBarra) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        DestinoBarra.entries.forEach { destino ->
            val seleccionado = destino == actual
            NavigationBarItem(
                selected = seleccionado,
                onClick = { onNavegar(destino) },
                icon = { Icon(destino.icono, contentDescription = destino.etiqueta) },
                label = { Text(destino.etiqueta) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = VerdeBodega,
                    selectedTextColor = VerdeBodega,
                    indicatorColor = VerdeBodega.copy(alpha = 0.15f),
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
