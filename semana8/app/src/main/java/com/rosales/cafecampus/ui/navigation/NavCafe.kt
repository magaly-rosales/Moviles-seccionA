package com.rosales.cafecampus.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rosales.cafecampus.CafeApp
import com.rosales.cafecampus.model.Rol
import com.rosales.cafecampus.ui.cliente.LoginScreen
import com.rosales.cafecampus.ui.cliente.MenuScreen
import com.rosales.cafecampus.ui.cliente.SplashScreen
import com.rosales.cafecampus.ui.theme.CafeTheme
import com.rosales.cafecampus.viewmodel.CafeViewModelFactory
import com.rosales.cafecampus.viewmodel.MenuViewModel
import com.rosales.cafecampus.viewmodel.SesionViewModel

@Composable
fun NavCafe(app: CafeApp) {
    val nav = rememberNavController()
    val factory = remember { CafeViewModelFactory(app.repository, app.prefs) }
    val sesionVm: SesionViewModel = viewModel(factory = factory)
    val menuVm: MenuViewModel = viewModel(factory = factory)

    val perfil by sesionVm.perfilActivo.collectAsState()
    val oscuro by sesionVm.modoOscuro.collectAsState()
    val rolTema = if (perfil == Rol.ADMIN.name) Rol.ADMIN else Rol.CLIENTE

    CafeTheme(rol = rolTema, oscuro = oscuro) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Box(Modifier.systemBarsPadding()) {
                NavHost(navController = nav, startDestination = Rutas.SPLASH) {

                    composable(Rutas.SPLASH) {
                        SplashScreen {

                            val destino = when (sesionVm.perfilActivo.value) {
                                Rol.CLIENTE.name -> Rutas.MENU
                                Rol.ADMIN.name -> Rutas.PANEL
                                else -> Rutas.LOGIN
                            }
                            nav.navigate(destino) { popUpTo(Rutas.SPLASH) { inclusive = true } }
                        }
                    }

                    composable(Rutas.LOGIN) {
                        LoginScreen(sesionVm) { rol ->
                            val destino = if (rol == Rol.ADMIN) Rutas.PANEL else Rutas.MENU
                            nav.navigate(destino) { popUpTo(Rutas.LOGIN) { inclusive = true } }
                        }
                    }

                    composable(Rutas.MENU) {
                        MenuScreen(menuVm, onProducto = { /* TODO Paso 7 */ })
                    }


                    composable(Rutas.PANEL) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Panel del administrador (Paso 9)")
                        }
                    }
                }
            }
        }
    }
}