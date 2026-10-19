# CafeCampus 

App de pedidos para la cafetería del campus — Laboratorio 08 **MVVM + Room**

## Descripción

Dos perfiles conviven en el mismo dispositivo y comparten la misma base de datos Room:

- **Estudiante:** ve el menú, arma su pedido, lo confirma y sigue su estado en tiempo real.
- **Administrador:** ve el panel del día, atiende pedidos por estado y gestiona el menú.

### Credenciales de prueba

| Perfil | Correo | Clave |
|---|---|---|
| Estudiante | magaly@tecsup.com | 1234 |
| Administrador | admin@tecsup.com | admin |


## Arquitectura MVVM + Repository

```
VIEW (Composable)
   │ eventos (clics)        ▲ collectAsState()
   ▼                        │
VIEWMODEL (StateFlow)
   │ llama                  ▲ Flow / resultados
   ▼                        │
CafeRepository (interface)
   │ la implementa
   ▼
CafeRepositoryRoom ──► DAO ──► SQLite (cafecampus.db)

DataStore (preferencias) ──► ViewModel
```

## Estructura de paquetes

```
com.rosales.cafecampus
├── MainActivity.kt
├── CafeApp.kt
├── data
│   ├── local
│   │   ├── entity      Producto · Usuario · Pedido · DetallePedido
│   │   ├── dao         ProductoDao · UsuarioDao · PedidoDao
│   │   ├── relation    PedidoConDetalle
│   │   ├── CafeDatabase.kt
│   │   └── DatosIniciales.kt
│   ├── repository      CafeRepository (interface) · CafeRepositoryRoom
│   └── prefs           PreferenciasStore
├── model               Rol · EstadoPedido · ItemCarrito
├── viewmodel           CafeViewModelFactory · MenuViewModel · SesionViewModel
│                       PedidosClienteViewModel · PanelAdminViewModel
│                       PedidosAdminViewModel · MenuAdminViewModel
└── ui
    ├── theme           Color · Type · Theme
    ├── components      AppTopBar · CafeButton · CafeTextField · EstadoChip
    │                   ProductoCard · ProductoIcono · PedidoCard · StepperEstado
    ├── navigation      Rutas · NavCafe
    ├── cliente         Splash · Login · Menu · DetalleProducto
    │                   ConfirmarPedido · Seguimiento · MisPedidos
    └── admin           Panel · PedidosAdmin · DetallePedidoAdmin
                        MenuAdmin · FormularioProducto · Ajustes
```

## Capturas

| Estudiante | | |
|---|---|---|
| ![Menú](capturas/cliente-menu.png) | ![Confirmar](capturas/cliente-confirmar.png) | ![Seguimiento](capturas/cliente-seguimiento.png) |

| Administrador | | |
|---|---|---|
| ![Panel](capturas/admin-panel.png) | ![Pedidos](capturas/admin-pedidos.png) | ![Menú admin](capturas/admin-menu.png) |

## Para pensar

**¿Por qué el Repository es una interface?** Porque los ViewModels dependen del contrato y no de Room; así se puede cambiar la fuente de datos sin tocar ViewModels ni pantallas.
**¿Por qué `crearPedido` es suspend y `observarPedidosCliente` es Flow?** `crearPedido` es una acción única que escribe y termina; `observarPedidosCliente` debe avisar a la pantalla cada vez que los datos cambian.
**¿Qué pasaría si dos perfiles usaran bases distintas?** El admin no vería los pedidos del estudiante ni el estudiante los cambios de estado: se rompería la sincronización.
**¿Qué cambiaría si los datos viven en un servidor?** Solo se crea otra implementación de `CafeRepository` (por ejemplo con Retrofit) y se cambia en `CafeApp`; ViewModels y pantallas quedan igual.

## Notas técnicas

- Se usó **Room 2.8.4 y KSP 2.3.4** (en vez de 2.6.1) por compatibilidad con AGP 9 y Kotlin 2.2; el código es el mismo.
- Se guarda también el `usuarioId` en DataStore para saber qué estudiante hace el pedido cuando el Splash salta el login.
- **Extra:** foto de producto opcional (selector de archivos + Coil); si no hay foto se muestra el ícono.
- **Modo práctica:** el botón "Cambiar de perfil" no pide contraseña, tal como indica el laboratorio.