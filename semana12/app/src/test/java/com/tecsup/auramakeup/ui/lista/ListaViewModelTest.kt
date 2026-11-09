package com.tecsup.auramakeup.ui.lista

import com.tecsup.auramakeup.fakes.FakeItemRepository
import com.tecsup.auramakeup.fakes.FakeMonitorConexion
import com.tecsup.auramakeup.fakes.FakeNotificador
import com.tecsup.auramakeup.fakes.item
import com.tecsup.auramakeup.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ListaViewModelTest {

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun refrescarConExito_muestraListaYNotifica() = runTest {
        val repo = FakeItemRepository(
            resultado = Result.success(2),
            datosRemotos = listOf(item(1), item(2))
        )
        val notificador = FakeNotificador()

        val vm = ListaViewModel(repo, notificador, FakeMonitorConexion())

        val estado = vm.estado.value
        assertTrue(estado is UiState.Exito)
        assertEquals(2, (estado as UiState.Exito).datos.size)
        assertEquals(listOf("Se guardaron 2 productos"), notificador.mensajes)
        assertNull(vm.aviso.value)
    }

    @Test
    fun sinInternetYSinDatos_muestraError() = runTest {
        val repo = FakeItemRepository(resultado = Result.failure(IOException()))
        val notificador = FakeNotificador()

        val vm = ListaViewModel(repo, notificador, FakeMonitorConexion())

        val estado = vm.estado.value
        assertTrue(estado is UiState.Error)
        assertEquals("No hay conexión a internet", (estado as UiState.Error).mensaje)
        assertTrue(notificador.mensajes.isEmpty())
    }

    @Test
    fun sinInternetConDatosGuardados_muestraAviso() = runTest {
        val repo = FakeItemRepository(
            resultado = Result.failure(IOException()),
            guardados = listOf(item(1))
        )

        val vm = ListaViewModel(repo, FakeNotificador(), FakeMonitorConexion())

        assertTrue(vm.estado.value is UiState.Exito)
        assertEquals("Sin conexión: mostrando datos guardados", vm.aviso.value)
    }

    @Test
    fun alternarFavorito_cambiaElItemEnLaLista() = runTest {
        val repo = FakeItemRepository(
            resultado = Result.success(1),
            datosRemotos = listOf(item(1))
        )
        val vm = ListaViewModel(repo, FakeNotificador(), FakeMonitorConexion())

        vm.alternarFavorito(1)

        val estado = vm.estado.value as UiState.Exito
        assertTrue(estado.datos.first().esFavorito)
    }
}