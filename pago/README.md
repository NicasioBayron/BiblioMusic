# Pago Microservicio

## POST /pago
Crea un pago nuevo.

### Body JSON
```json
{
  "idCarrito": 12,
  "precioProducto": 1500.0,
  "cantidad": 1,
  "medioPago": "Efectivo",
  "confirmacionPago": "APROBADO",
  "fechaPago": "2026-06-27"
}
```

## PUT /pago/{idPago}
Actualiza un pago existente.

### Body JSON
```json
{
  "idCarrito": 10,
  "total": 5500.0,
  "precioProducto": 1500.0,
  "cantidad": 1,
  "medioPago": "Tarjeta",
  "confirmacionPago": "APROBADO",
  "fechaPago": "2026-06-25"
}
```
