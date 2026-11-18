# PROMPTS.md · Fase 2 con IA (Gemini en Android Studio)

| Prompt que escribí | Qué respondió Gemini | Qué cambié o descarté | Por qué |
|---|---|---|---|
| «Cambia contarPrimos para que reciba alAvanzar y avise el progreso cada 1 %. El ViewModel expone progresoPrimos y la pantalla muestra la barra y el porcentaje.» | Cambió la interface y TareasRepositoryLocal, agregó progresoPrimos en el ViewModel y la barra con el porcentaje en la tarjeta de primos. | Pedí que avise cada 1 % y no en cada número. Probé que Cancelar siga funcionando a mitad del cálculo. | Si avisara en cada número, actualizaría la pantalla cientos de miles de veces y se pondría lenta. |
| «Reintenta las cargas hasta 3 veces con espera creciente de 500 y 1000 ms. La CancellationException no se reintenta. Agrega el estado Reintentando.» | Creó ejecutarConReintento en el ViewModel, agregó CargaUi.Reintentando y la pantalla muestra "Reintentando (2/3)...". | Revisé que los botones estén deshabilitados mientras reintenta, para que no se lancen 2 cargas a la vez. | Si se reintentara la cancelación, el usuario no podría detener la carga y se rompería viewModelScope. |
| «Agrega una tabla que acumule los tiempos de Una por una y A la vez con sus promedios y un botón Limpiar. El promedio se calcula en el ViewModel.» | Agregó la lista de tiempos, los promedios y limpiarTiempos() en el ViewModel, y la tabla con el botón Limpiar en la pantalla. | Gemini se cortó por el límite de uso justo al terminar. Revisé que compilara y probé la tabla antes de aceptar. | Los promedios van en el ViewModel porque la pantalla solo debe mostrar datos, no calcular. |

## Prompt principal usado

```
Eres mi asistente en Android Studio. Proyecto CentroTareas: Kotlin + Jetpack Compose + Material 3, MVVM + Repository, corrutinas. Paquete com.rosales.centrotareas.

Vamos a hacer 3 mejoras, UNA POR VEZ y en este orden.

REGLAS ESTRICTAS:
1. Haz solo la mejora actual. Al terminar DETENTE y espera a que yo escriba "listo".
2. Prohibido GlobalScope, runBlocking y Thread.sleep. Si atrapas Exception, primero relanza la CancellationException.
3. Respeta MVVM: nada de Context en el ViewModel y nada de lógica en los Composables.
4. No rompas lo que ya funciona. No ejecutes git.

MEJORA 1 · Primos con progreso (Repository + ViewModel + View)
MEJORA 2 · Reintento automático hasta 3 veces (ViewModel + View)
MEJORA 3 · Comparación de tiempos con promedios (ViewModel + View)
```