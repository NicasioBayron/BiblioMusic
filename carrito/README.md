# Carrito Microservicio

## POST /carrito
Crea un carrito nuevo.

### Body JSON
```json
{
  "cantidadProducto": 3,
  "idUsuario": 100,
  "idProducto": 700
}
```

## PUT /carrito/{idCarrito}
Actualiza un carrito existente.

### Body JSON
```json
{
  "cantidadProducto": 4,
  "idUsuario": 100,
  "idProducto": 500
}
```
