# Registro de Prompts - Fase 2 (AvisosCampus)

Nota: Las mejoras desarrolladas en la Fase 2 con asistencia de IA fueron implementadas en la rama mejora-ia-15.

| Prompt que escribí | Qué respondió Gemini | Qué cambié o descarté | Por qué |
|---|---|---|---|
| Vamos a hacer la FASE 2 del laboratorio AvisosCampus en la rama mejora-ia. La Fase 1 ya está terminada... | Se confirmó la recepción de reglas de trabajo, restricciones de arquitectura MVVM y disposición para realizar cada mejora de manera incremental. | Se aceptó la metodología propuesta por el asistente. | Garantizó la entrega paso a paso manteniendo la integridad del proyecto base. |
| MEJORA 1 -> COMMIT: "feat(canales): agrega canal Urgentes con importancia alta para el recordatorio" | Generó la definición del canal CANAL_URGENTE con IMPORTANCE_HIGH, incluyó la propiedad urgente: Boolean en el modelo Aviso y actualizó Notificador, Repository y ViewModel. | Se aceptaron todos los cambios sugeridos sin modificaciones. | Mantiene el patrón MVVM sin meter Context en el ViewModel y cumple la especificación de notificaciones emergentes. |
| MEJORA 2 -> COMMIT: "feat(notificacion): muestra el texto completo con BigTextStyle" | Implementó NotificationCompat.BigTextStyle().bigText(aviso.texto) en Notificador.mostrar y actualizó el texto del recordatorio en el ViewModel. | Se conservaron todas las modificaciones tal como fueron generadas. | Permite que las notificaciones extensas se desplieguen completamente en el panel del sistema al expandirlas. |
| MEJORA 3 -> COMMIT: "feat(receptor): avisa al conectar o desconectar el cargador" | Creó CargadorReceiver para capturar ACTION_POWER_CONNECTED y ACTION_POWER_DISCONNECTED, agregó Origen.CARGADOR con píldora verde, integró el flujo en ViewModel y la UI con DisposableEffect. | Se aceptó la propuesta de implementación de manera íntegra. | Implementa el registro dinámico de BroadcastReceiver liberando recursos en onDispose y respetando las reglas de Compose. |
