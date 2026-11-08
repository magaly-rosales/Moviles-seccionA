package com.tecsup.auramakeup.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.auramakeup.AppContainer
import com.tecsup.auramakeup.ui.detalle.DetalleScreen
import com.tecsup.auramakeup.ui.detalle.DetalleViewModel
import com.tecsup.auramakeup.ui.favoritos.FavoritosScreen
import com.tecsup.auramakeup.ui.favoritos.FavoritosViewModel
import com.tecsup.auramakeup.ui.lista.ListaScreen
import com.tecsup.auramakeup.ui.lista.ListaViewModel

@Composable
fun AppNav(container: AppContainer) {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = "lista") {

        composable("lista") {
            val vm: ListaViewModel = viewModel(factory = ListaViewModel.factory(container))
            ListaScreen(
                viewModel = vm,
                onItemClick = { id -> nav.navigate("detalle/$id") },
                onFavoritosClick = { nav.navigate("favoritos") }
            )
        }

        composable(
            route = "detalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt("id") ?: 0
            val vm: DetalleViewModel = viewModel(factory = DetalleViewModel.factory(container, id))
            DetalleScreen(
                viewModel = vm,
                onVolver = { nav.popBackStack() }
            )
        }

        composable("favoritos") {
            val vm: FavoritosViewModel = viewModel(factory = FavoritosViewModel.factory(container))
            FavoritosScreen(
                viewModel = vm,
                onItemClick = { id -> nav.navigate("detalle/$id") },
                onVolver = { nav.popBackStack() }
            )
        }
    }
}