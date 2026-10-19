# Prompt utilizado

**Texto enviado a Gemini:**

```
Eres un agente con acceso a mi proyecto Android. Trabaja DIRECTAMENTE sobre mis archivos: antes de modificar cualquier archivo, ábrelo y léelo completo (no inventes su contenido ni sus firmas); luego aplica los cambios tú mismo, crea los archivos nuevos que hagan falta y verifica que el proyecto compile.

PROYECTO: app Android en Kotlin + Jetpack Compose + Material 3 (sin XML), arquitectura MVVM + Repository, Room y DataStore. Paquete: com.rosales.cafecampus (en app/src/main/java/com/rosales/cafecampus).

ESTRUCTURA ACTUAL (léela antes de empezar):
- data/local/entity: Producto, Usuario(id, nombre, correo, clave, rol), Pedido(id, clienteId, estado, horaRecojo, pago, nota, total, fecha Long millis), DetallePedido
- data/local/dao: ProductoDao, UsuarioDao, PedidoDao
- data/local/relation: PedidoConDetalle (@Embedded pedido, detalles, cliente)
- data/local/CafeDatabase.kt (si cambias entidades o tablas, sube el número de version)
- data/repository: CafeRepository (interface) y CafeRepositoryRoom (implementación)
- viewmodel: MenuViewModel, SesionViewModel, PanelAdminViewModel, CafeViewModelFactory y otros
- ui/cliente: MenuScreen, LoginScreen; ui/admin: PanelScreen; ui/navigation: Rutas, NavCafe
- ui/components: CafeButton, CafeTextField, AppTopBar, PedidoCard, EstadoChip, ProductoIcono, enSoles()
- ui/theme: CafeTheme(rol, oscuro); colores EstadoRecibido, EstadoPreparando, EstadoListo
- MenuViewModel ya tiene productosFiltrados = combine(productos, _categoria)

REGLAS OBLIGATORIAS (no las rompas):
1. La View (Composable) NO usa DAO ni Repository; solo su ViewModel.
2. El ViewModel solo conoce la interface CafeRepository, nunca el DAO ni CafeRepositoryRoom.
3. Si agregas una función al Repository, primero la declaras en la interface CafeRepository y luego la implementas en CafeRepositoryRoom.
4. Lecturas que actualizan la pantalla = Flow (sin suspend). Escrituras o acciones únicas = suspend.
5. Toda la lógica (filtros, validaciones, cálculos) va en el ViewModel, NUNCA en el Composable.
6. Usa MaterialTheme.colorScheme, nunca colores fijos (salvo los colores de estado ya definidos).
7. Sin XML y sin librerías nuevas (el gráfico se dibuja con Compose).
8. No borres ni cambies funcionalidades que ya existen; solo agrega o ajusta lo necesario.
9. No toques archivos fuera de esta app ni de la carpeta del proyecto.

FORMA DE TRABAJO: haz SOLO la MEJORA 1, aplícala en los archivos, verifica que compile y luego dime en una lista qué archivos creaste y cuáles modificaste, y qué capas cambiaste (DAO, Repository, ViewModel, View). Después DETENTE y espera a que yo escriba "siguiente" para hacer la MEJORA 2; luego igual con la MEJORA 3.

MEJORA 1 - BUSCAR EN EL MENÚ
- En MenuScreen, debajo del saludo "¡Hola, Nombre!", agrega una barra de búsqueda redondeada con ícono de lupa y placeholder "Buscar en el menú...".
- Filtra productos por nombre mientras escribo (sin distinguir mayúsculas ni tildes).
- La lógica va en MenuViewModel: agrega _busqueda: MutableStateFlow<String> y súmala al combine existente junto con la categoría.
- Si no hay resultados, muestra "No encontramos productos".

MEJORA 2 - REGISTRO DE ESTUDIANTE
- UsuarioDao: agrega una consulta para saber si un correo ya existe.
- CafeRepository: declara suspend fun registrarEstudiante(nombre, correo, clave): Boolean (false si el correo ya existe) e impleméntala en CafeRepositoryRoom; el rol siempre es Rol.CLIENTE.name.
- Crea RegistroViewModel (y regístralo en CafeViewModelFactory) con validaciones: campos obligatorios, correo con formato válido, clave de mínimo 4 caracteres, confirmar clave igual, y correo único ("Ese correo ya está registrado").
- Crea ui/cliente/RegistroScreen con CafeTextField (nombre, correo, clave, confirmar clave) y CafeButton "Crear cuenta". Al registrarse vuelve al login con un mensaje de éxito.
- En LoginScreen, solo en la pestaña Estudiante, agrega el texto "¿No tienes cuenta? Regístrate".
- Agrega la ruta en Rutas y en NavCafe.

MEJORA 3 - GRÁFICO REAL DE PEDIDOS POR HORA + PANEL IGUAL AL MOCKUP
- PedidoDao: consulta que agrupe los pedidos de hoy (fecha >= inicioDia, sin CANCELADO) por hora local usando strftime('%H', fecha/1000, 'unixepoch', 'localtime'), devolviendo Flow<List<PedidosPorHora>> (data class con hora: String y cantidad: Int).
- PedidoDao: consulta que cuente los pedidos de hoy no cancelados: Flow<Int>.
- Declara ambas en CafeRepository e impleméntalas en CafeRepositoryRoom.
- PanelAdminViewModel: expón pedidosPorHora y pedidosHoy como StateFlow.
- Rediseña PanelScreen así:
  * Encabezado: "Panel de control" (negrita grande) y debajo "Cafetería · Hoy"; a la derecha un ícono de campana.
  * Tres tarjetas en fila (Nuevos, Preparando, Listos) con fondo suave del color de su estado, el número grande centrado y la etiqueta debajo, también centrada y del mismo color.
  * Tarjeta "Ventas del día" con fondo colorScheme.primary y texto onPrimary: monto grande a la izquierda (ej. S/ 248.00) y a la derecha "N pedidos".
  * Sección "Pedidos por hora": gráfico de barras dibujado con Compose dentro de una tarjeta con borde; cada barra muestra la cantidad arriba y la hora abajo ("10h"); la barra con más pedidos usa colorScheme.primary y las demás primary con transparencia. Si no hay datos: "Aún no hay pedidos hoy".
  * Sección "Nuevos pedidos" con "Ver todos" a la derecha. Cada pedido es una tarjeta con "#0047" (id con 4 dígitos) y el nombre del cliente abreviado ("Ana T."), debajo "1 x Sándwich de pollo · 10:45" (primer producto y hora de recojo), y a la derecha un botón redondeado "Preparar".

Empieza ahora con la MEJORA 1.
```

**Que tuve que corregir yo:** Nada, funcionó a la primera

**Qué generó la IA:**
- **Mejora 1 (búsqueda):** en `MenuViewModel` agregó `_busqueda`, `cambiarBusqueda()` y `sinTildes()`, y sumó la búsqueda al `combine`; en `MenuScreen`, la barra redondeada con lupa y el mensaje "No encontramos productos".
- **Mejora 2 (registro):** `existeCorreo()` en `UsuarioDao`, `registrarEstudiante()` en la interface y la implementación, `RegistroViewModel` con validaciones, `RegistroScreen`, el enlace "¿No tienes cuenta? Regístrate" y la ruta nueva.
- **Mejora 3 (gráfico + panel):** `PedidosPorHora`, consultas con `strftime` y `COUNT`, dos StateFlow en `PanelAdminViewModel` y el Panel rediseñado igual al mockup.

**Respeta MVVM?:** Sí, con una observación: para validar el correo usó `Patterns.EMAIL_ADDRESS` dentro del ViewModel, lo que lo hace depender de una clase de Android. Funciona, pero sería más limpio con una expresión regular propia.


## Reflexion 

- **Que mejoro la IA y que no?** 

- Aceleró mucho las mejoras que tocan las 4 capas y respetó el orden de declarar primero en la interface; el diseño visual sí necesitó revisión contra el mockup.

- **¿Qué error cometió?**
- Usó `Patterns.EMAIL_ADDRESS` dentro del ViewModel, lo que lo hace depender de una clase de Android; no afecta el funcionamiento, pero no es lo más limpio para MVVM.

- **Que aprendi sobre MVVM y Room?** 

- Que al tener el Repository como interface pude agregar funciones nuevas sin tocar las pantallas existentes, y que una consulta Room que devuelve `Flow` actualiza el panel sola cuando cambian los pedidos.

- Darle reglas claras en el prompt (Flow vs suspend, lógica en el ViewModel) hizo que el código generado fuera más ordenado.