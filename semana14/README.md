# CentroTareas

## Observación del Paso 1
Al tocar **Tarea larga (MAL)** la pantalla se congela 4 segundos: el botón Tócame no suma y nada responde.
No se ve "Trabajando..." porque `Thread.sleep` bloquea el hilo principal, que es el que dibuja la pantalla; cuando se libera, el mensaje ya cambió a "Terminó".
Si la espera fuera de 10 segundos, Android mostraría el aviso **"La app no responde"** (ANR) para cerrarla o esperar.
