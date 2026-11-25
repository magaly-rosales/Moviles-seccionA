# Registro de Prompts - Proyecto MuroCampus (Fase 2)

| Prompt que escribí | Qué respondió Gemini | Qué cambié o descarté | Por qué |
| --- | --- | --- | --- |
| Implementar función de borrado con DELETE en API, Repository, ViewModel y View con Snackbar de Material 3 | Generó la función `@DELETE("posts/{id}")` en PostApi, manejó el borrado en PostRepositoryRemote retornando `Result<Unit>`, implementó `borrar()` en PostsViewModel usando `UiState<Unit>?` y añadió el botón "Eliminar" con Snackbar en PantallaDetalle. | (completar) | (completar) |
| Agregar búsqueda por título en PantallaLista combinando StateFlows en ViewModel | Implementó `_textoBusqueda` y combinó la lista original descargada mediante `combine` en `PostsViewModel`, y añadió un `OutlinedTextField` en `PantallaLista`. | (completar) | (completar) |
| Añadir un Interceptor en OkHttpClient para agregar el encabezado Accept: application/json a todas las peticiones | Creó un `Interceptor` de OkHttp que agrega la cabecera `Accept: application/json` al request y lo añadió al `OkHttpClient.Builder()` antes de `HttpLoggingInterceptor`. | (completar) | (completar) |
| Rediseño Material 3 con colores pastel por usuario (1-10), animaciones de elevación/escala/borde en tarjetas y chips, animaciones en PantallaCrear y creación de PROMPTS.md | Creó `ColoresUsuario.kt` con esquema pastel por usuario para tema claro/oscuro, aplicó animaciones interactivas (`animate*AsState`, `hoverable`, `interactionSource`) en `TarjetaPost`, `FilterChip` y `PantallaCrear`, y generó el documento `PROMPTS.md`. | (completar) | (completar) |
