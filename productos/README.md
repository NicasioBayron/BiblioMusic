# Productos Microservicio

## POST /producto
Crea un producto nuevo.

### Body JSON
```json
{
  "nombre_producto": "Guitarra eléctrica",
  "tipo_producto": "Instrumento",
  "precio": 1500.0,
  "stock": 10,
  "detalles": [
    {
      "autor": "Marca XYZ",
      "genero": "Rock",
      "descripcion": "Guitarra eléctrica de 6 cuerdas"
    }
  ]
}
```

## PUT /producto
Actualiza un producto existente.

### Body JSON
```json
{
  "id_producto": 1,
  "nombre_producto": "Guitarra eléctrica",
  "tipo_producto": "Instrumento",
  "precio": 1550.0,
  "stock": 12,
  "detalles": [
    {
      "id_detalle": 5,
      "autor": "Marca XYZ",
      "genero": "Rock",
      "descripcion": "Guitarra eléctrica actualizada"
    }
  ]
}
```

## POST /producto/detalle
Crea un detalle de producto nuevo.

### Body JSON
```json
{
  "autor": "Marca XYZ",
  "genero": "Rock",
  "descripcion": "Descripción del producto"
}
```

## PUT /producto/detalle
Actualiza un detalle de producto existente.

### Body JSON
```json
{
  "id_detalle": 1,
  "autor": "Marca XYZ",
  "genero": "Rock",
  "descripcion": "Descripción actualizada"
}
```
