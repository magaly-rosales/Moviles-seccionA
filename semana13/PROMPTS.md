# PROMPTS.md · Fase 2 con IA (Gemini en Android Studio)

| Prompt que escribí | Qué respondió Gemini | Qué cambié o descarté | Por qué |
|---|---|---|---|
| «Agrega un filtro por categoría con FilterChip (Todas, Estudio, Comida, Eventos, Servicios). El filtrado va solo en el ViewModel con un StateFlow lugaresFiltrados.» | Agregó en MapaViewModel la categoría elegida, la función filtrar() y lugaresFiltrados con combine. Puso los chips en PantallaMapa. | Revisé que "Todas" muestre los 5 lugares y que "Más cercano" se siga calculando con todos los lugares. | El filtro solo cambia lo que se ve; el lugar más cercano debe ser real aunque haya un filtro activo. |
| «Agrega un selector de radio de 100, 200 y 500 m, un Circle en el mapa y la etiqueta "Dentro del radio". El cálculo va en el ViewModel.» | Agregó radioMetros y cambiarRadio() en el ViewModel, un campo dentroDelRadio en el modelo, el Circle en el mapa y la etiqueta en la lista. | Lo acepté. Probé que con 100 m solo la Biblioteca queda dentro y con 200 m entran tres lugares. | El cálculo quedó en el ViewModel y la pantalla solo muestra el resultado, así se respeta MVVM. |
| «El mapa se ve aplastado. Haz que la pantalla tenga scroll vertical y el mapa una altura fija de 300 dp.» | Puso verticalScroll en PantallaMapa, el mapa con 300 dp y cambió ListaLugares de LazyColumn a Column. | Este prompt salió de un problema que vi yo: con los chips el mapa quedaba muy pequeño. | Una LazyColumn no puede ir dentro de un scroll; como solo son 5 lugares, un Column normal es suficiente. |
| «Guarda el último lugar visitado con DataStore. Crea PreferenciasRepository y su implementación. El ViewModel no recibe Context.» | Creó PreferenciasRepository con DataStore, actualizó la Factory y el ViewModel, pero se cortó por el límite de uso antes de terminar la pantalla. | Revisé lo que dejó, confirmó que el ViewModel no recibe Context y en un chat nuevo le pedí solo mostrar "Último visitado" en la pantalla. | El Context solo debe estar en el Repository; la Factory lo pasa, así el ViewModel sigue limpio y se puede probar. |

## Prompt principal usado

```
Eres mi asistente en Android Studio. Proyecto CampusGPS: Kotlin + Jetpack Compose + Material 3, arquitectura MVVM + Repository, paquete com.rosales.campusgps, código en app/src/main/java/com/rosales/campusgps/.

Vamos a hacer 3 mejoras, UNA POR VEZ y en este orden.

REGLAS ESTRICTAS:
1. Haz solo la mejora actual. Al terminar DETENTE: dime qué archivos cambiaste, explícame en 2 líneas qué hiciste y dime el mensaje de commit. Espera a que yo escriba "listo" para seguir con la siguiente.
2. Respeta MVVM: nada de Context en el ViewModel, nada de lógica (filtros, cálculos, distancias) dentro de los Composables. Los Composables solo dibujan el estado.
3. No borres ni rompas lo que ya funciona: permiso, lista ordenada, selección, "Ir al más cercano", marcador verde y mensajes de error.
4. No toques AndroidManifest.xml ni local.properties. No ejecutes git.
5. Si necesitas agregar una dependencia en app/build.gradle.kts, dime cuál antes de agregarla.

MEJORA 1 · Filtrar por categoría (ViewModel + View)
- Chips de Material 3 (FilterChip): "Todas", "Estudio", "Comida", "Eventos", "Servicios", arriba de la lista.
- En MapaViewModel: estado de la categoría elegida (null = Todas), fun filtrar(categoria: String?) y un StateFlow lugaresFiltrados que combine lugares + categoría.
- El mapa y la lista usan lugaresFiltrados. "Más cercano" sigue calculándose con todos los lugares.

MEJORA 2 · Radio de cercanía (ViewModel + View)
- Selector con 3 opciones: 100 m, 200 m, 500 m. Por defecto 200 m.
- En MapaViewModel: estado del radio y fun cambiarRadio(metros: Int).
- En el mapa: un Circle alrededor de mi posición con ese radio.
- En la lista: etiqueta "Dentro del radio". El cálculo se hace en el ViewModel.

MEJORA 3 · Último lugar visitado con DataStore (Repository + ViewModel)
- Dependencia androidx.datastore:datastore-preferences.
- Interface PreferenciasRepository y su implementación PreferenciasRepositoryDataStore en data.
- CampusViewModelFactory crea el repositorio y se lo pasa al ViewModel. El ViewModel NO recibe Context.
- Al seleccionar un lugar se guarda su id y al abrir la app se recupera.
- Mostrar "Último visitado: <nombre>" arriba de la lista.

Empieza ahora por la MEJORA 1.
```
