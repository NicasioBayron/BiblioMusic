# Envío Microservicio

## POST /envio
Crea un envío nuevo.

### Body JSON
```json
{
  "id_pago": 30,
  "id_usuario": 100,
  "id_carrito": 3,
  "estado_envio": "PENDIENTE",
  "fecha_envio": "2026-06-27",
  "direccion_envio": "Dirección C"
}
```

## PUT /envio/{id_envio}
Actualiza un envío existente.

### Body JSON
```json
{
  "id_pago": 10,
  "id_usuario": 100,
  "id_carrito": 1,
  "estado_envio": "EN_CAMINO",
  "fecha_envio": "2026-06-25",
  "direccion_envio": "Dirección Modificada"
}
```
