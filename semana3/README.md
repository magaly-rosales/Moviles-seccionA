# Lab03 - Registro de Producto

**Nombre:** Magaly Rosales
**Curso:** Programación en Móviles
**Docente:** Juan José León Suiyon

## Descripción

App desarrollada con Jetpack Compose que permite registrar un producto
(nombre, precio y cantidad). Al presionar "AGREGAR PRODUCTO", se muestra
una Card con el resumen y el importe calculado (precio × cantidad).

## Capturas

### Pantalla inicial
![Pantalla vacía](capturas/nada.png)

### Producto registrado
![Producto registrado](capturas/contenido.png)
## Reflexión: ¿qué pasa sin `remember`?

Al declarar las variables sin remember y al correr la app no se ve ningún cambio al escribir en los campos de texto  en
pantalla, pues esto sucede porque remember guarda el valor a pesar de que se reinicie la app.


## Mejora con IA

| Prompt que usé | Qué generó Gemini | Qué acepté o corregí (y por qué) |
|---|---|---|
| En PantallaRegistro, agrega validación: si nombre, precio o cantidad están vacíos al presionar AGREGAR PRODUCTO, muestra un mensaje de error en rojo en vez de la Card. Además agrega un botón Limpiar que vacíe los tres campos y oculte el resumen. No toques el diseño existente ni los imports que ya funcionan. | Agregó una variable `error`, validación con `isBlank()` en el botón, un `OutlinedButton` de Limpiar, y un `Text` en `Color.Red` con mensaje genérico "Todos los campos son obligatorios". | Acepté la lógica de validación y el botón Limpiar tal cual. Corregí el color de `Color.Red` a `MaterialTheme.colorScheme.error` para mantener consistencia con el tema, y cambié el mensaje genérico por uno dinámico que indica exactamente qué campos faltan (ej. "Falta completar: Precio"), porque es más útil para el usuario. |