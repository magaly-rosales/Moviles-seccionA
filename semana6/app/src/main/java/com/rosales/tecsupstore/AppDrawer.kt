package com.rosales.tecsupstore

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer(onItemClick: (String) -> Unit) {
    val destinos = listOf(
        Triple("inicio", "Inicio", Icons.Default.Home),
        Triple("pedidos", "Mis pedidos", Icons.Default.ShoppingCart),
        Triple("favoritos", "Favoritos", Icons.Default.Favorite),
        Triple("perfil", "Perfil", Icons.Default.Person)
    )

    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "TECSUP Store",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 28.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        destinos.forEach { (ruta, titulo, icono) ->
            NavigationDrawerItem(
                label = { Text(titulo) },
                icon = { Icon(icono, contentDescription = null) },
                selected = false,
                onClick = { onItemClick(ruta) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}