# Piatto API

API REST hecha con Spring Boot para gestionar autenticación, usuarios, platos y pedidos de una cafetería universitaria.

## Endpoints

### `POST /api/auth/register`
Registra un usuario nuevo.

```json
{
  "username": "juan123",
  "nombreCompleto": "Juan Perez",
  "correoElectronico": "juan@utec.edu.pe",
  "password": "123456"
}
```

### `POST /api/auth/login`
Inicia sesión.

```json
{
  "username": "juan123",
  "password": "123456"
}
```

### `PUT /api/usuarios/{usuarioId}/perfil`
Actualiza el correo y la contraseña del usuario.

```json
{
  "correoElectronico": "nuevo@utec.edu.pe",
  "password": "nueva123"
}
```

### `GET /api/platos`
Lista todos los platos.

No requiere body.

### `GET /api/platos/menu`
Muestra el menú del día.

No requiere body.

### `GET /api/platos/{platoId}`
Obtiene el detalle de un plato.

No requiere body.

### `POST /api/pedidos`
Crea un pedido.

```json
{
  "usuarioId": 1,
  "items": [
    {
      "platoId": 1,
      "cantidad": 2
    },
    {
      "platoId": 2,
      "cantidad": 1
    }
  ]
}
```

### `GET /api/pedidos/usuario/{usuarioId}`
Lista los pedidos de un usuario.

No requiere body.

### `DELETE /api/pedidos/{pedidoId}`
Cancela un pedido.

No requiere body.
