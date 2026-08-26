# Lab02 - Carrito de Compras en Kotlin (POO) - Rama con-IA

**Alumna:** Magaly Rosales

**Curso:** Programación en Móviles - 4to Ciclo

**Docente:** Juan León Suiyon

## Descripción

Este programa simula un carrito de compras por consola aplicando los 4 pilares
de la Programación Orientada a Objetos en Kotlin. Permite agregar productos
de distintos tipos, calcular subtotal, IGV (18%) y total, mostrar un detalle
con formato alineado, identificar el producto más caro, aplicar descuentos
según el monto total, y buscar/eliminar productos del carrito.

## Aplicación de los 4 pilares POO

- **Abstracción:** `Producto` es una clase abstracta que define el
  comportamiento común (`calcularImporte`, `mostrarInfo`) sin implementarlo.
- **Encapsulamiento:** el precio base (`precioBase`) es privado dentro de
  `Producto`; solo se accede mediante el método `getPrecio()`.
- **Herencia:** `ProductoElectronico` y `ProductoMobiliario` heredan de
  `Producto` y reutilizan sus propiedades (`nombre`, `cantidad`).
- **Polimorfismo:** cada subclase sobrescribe `calcularImporte()` y
  `mostrarInfo()`; el `Carrito` los recorre como una lista de `Producto`
  sin necesitar saber el tipo exacto de cada uno.

## Funciones implementadas

- `agregarProducto()` - agrega productos al carrito (`mutableListOf`)
- `calcularSubtotal()`, `calcularIGV()`, `calcularTotal()`
- `mostrarDetalle()` - reporte con columnas alineadas (`String.format`)
- `productoMasCaro()` - usa `maxByOrNull`
- `calcularDescuento()` - usa `when` (10% > S/5000, 5% > S/3000)
- `buscarProducto()` - usa `find`
- `eliminarProducto()` - usa `removeIf`

## Prompt utilizado con la IA (Claude)

> "Hola mira me quede en una parte de mi proyecto el objetivo es escribir
> codigo sobre un carrito orientado a POO, con 8 commits, todo con IA...
> dio hacer con los 4 pilares de la programacion POO (herencia,
> polimorfismo, etc), ademas tienes que usar mutableListOf, y con 8 commits...
> a partir del codigo que hice sin IA, ahoho te paso el codigo que hice, quiero que sea orientado a objetos,
> ademas tiene que tener lista mutable, y ademas dame ideas para generar
> 8 commits."


## Evidencia

![Captura de pantalla 2026-08-26 121257](imagenes/Captura%20de%20pantalla%202026-08-26%20121257.png)

![Captura de pantalla 2026-08-26 121306](imagenes/Captura%20de%20pantalla%202026-08-26%20121306.png)



