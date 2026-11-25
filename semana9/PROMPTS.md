# Registro de Prompts - Proyecto MuroCampus (Fase 2)



## Prompt utilizado (resumen del prompt completo)

Se pidió trabajar un commit a la vez, sin hacer commits por mi cuenta, respetando MVVM + Repository:

- ViewModel sin Retrofit, `PostApi`, `HttpException` ni `Context`.
- Ningún Composable llama a la API.
- El Repository devuelve `Result` y relanza `CancellationException`.
- Prohibido: `catch` vacío, `GlobalScope` y URLs `http://` innecesarias.
- Solo Material 3, sin librerías nuevas.

Bloques pedidos: (1) Borrar publicación, (2) Búsqueda por título, (3) Interceptor `Accept`, (4) Rediseño visual con colores por usuario y animaciones, más este `PROMPTS.md`.

## Tabla de prompts

| Prompt que escribí | Qué respondió Gemini | Qué cambié o descarté | Por qué |
| --- | --- | --- | --- |
| **Commit 1 · Borrar publicación:** `@DELETE("posts/{id}")` con `Response<Unit>` en `PostApi`; el Repository revisa `isSuccessful` y devuelve `Result.failure(HttpException)` si falla; `borrar(id)` en el ViewModel con `UiState<Unit>?`; botón "Eliminar" en `PantallaDetalle` con Snackbar. | Creó `borrar()` en `PostApi`, `PostRepository` y `PostsViewModel`, y el botón rojo "Eliminar" que vuelve a la lista con un Snackbar. | No descarté código. Revisé que el Repository relance `CancellationException` antes de `catch (e: Exception)`, que no hubiera `catch` vacío ni `GlobalScope`, y que el ViewModel no importara Retrofit. | Mantiene MVVM: el Repository convierte los errores de red en `Result` y el ViewModel nunca conoce Retrofit. JSONPlaceholder responde 200 pero no borra de verdad. |
| **Commit 2 · Búsqueda por título:** campo de búsqueda que filtre sin distinguir mayúsculas las publicaciones ya descargadas, con la lógica en el ViewModel y sin nuevas llamadas de red; debe convivir con el filtro por usuario. | Agregó un `StateFlow` con el texto de búsqueda y lo combinó con la lista y el filtro mediante `combine` en el ViewModel; añadió un `OutlinedTextField` en `PantallaLista`. | No descarté código. Comprobé que el filtrado estuviera en el ViewModel y no en el Composable, y en Logcat que escribir no genera peticiones nuevas. | La vista solo dibuja el estado. Filtrar datos ya descargados no necesita red y debe vivir en el ViewModel para sobrevivir a rotaciones. |
| **Commit 3 · Interceptor:** `Interceptor` de OkHttp que agregue `Accept: application/json` a todas las peticiones, manteniendo el `HttpLoggingInterceptor`. | Creó `acceptInterceptor` con `addHeader("Accept", "application/json")` y lo añadió al `OkHttpClient` antes del logging. | No cambié el código. Al principio el Logcat no mostraba el encabezado porque lo estaba mirando una ejecución anterior (antes de reinstalar la app); tras volver a ejecutar, apareció `Accept: application/json` entre `--> GET` y `--> END GET`. | Los interceptores se ejecutan en orden: si el logging va primero, no muestra el encabezado agregado después. Va antes para que el log refleje la petición real. |
| **Commit 4 · Rediseño Material 3:** colores pastel distintos por usuario (1 a 10) para tema claro y oscuro, animaciones de elevación, borde y escala en tarjetas y chips, pantalla Crear más animada, todo solo en `ui/` y sin librerías nuevas. | Creó `ColoresUsuario.kt` y aplicó animaciones (`animate*AsState`, `hoverable`, `MutableInteractionSource`) en `TarjetaPost`, `FilterChip` y `PantallaCrear`. | No descarté código. Comprobé con `git status` que solo cambiaron archivos de `ui/` y `PROMPTS.md`, y probé en el emulador que borrar, búsqueda y filtro siguen funcionando. | El rediseño es de presentación: no debe tocar ViewModel, Repository ni API, para no romper la arquitectura. |

## Revisión de calidad del código generado

Revisé el código de Gemini buscando los problemas que pide el enunciado y no encontré: `catch` vacío, `GlobalScope`, llamadas a la API desde un Composable ni URLs `http://`.

## Nota sobre la API

JSONPlaceholder simula `POST` y `DELETE` (responde 201 con `id = 101` y 200), pero no guarda nada, por eso las publicaciones creadas no aparecen al recargar la lista.