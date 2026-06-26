# Login Microservicio

## POST /api/auth/login
Inicia sesión de un usuario.

### Body JSON
```json
{
  "email": "usuario@example.com",
  "password": "password123"
}
```

## POST /api/auth/register
Registra un nuevo usuario.

### Body JSON
```json
{
  "email": "usuario@example.com",
  "password": "password123",
  "nombre": "Juan",
  "apellido": "Pérez"
}
```

## PUT /api/auth/usuarios/{id}
Actualiza un usuario existente. Requiere token de autorización con rol Admin.

### Body JSON
```json
{
  "email": "usuario@example.com",
  "password": "newpassword123",
  "nombre": "Juan",
  "apellido": "Pérez"
}
```
