# BTG Funds Platform

Plataforma full-stack para gestión de fondos FPV/FIC con Spring Boot 4.0.6, MongoDB y Angular standalone.

## Backend

Requisitos:

- Java 21
- Maven
- MongoDB local en `mongodb://localhost:27017`

Variables soportadas:

```bash
MONGO_URI=mongodb://localhost:27017/btg_funds
JWT_SECRET=change-me-with-a-long-secret
JWT_EXPIRATION_MS=86400000
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USERNAME=juan
SMTP_PASSWORD=1234
```

Ejecución:

```bash
mvn spring-boot:run
```

Pruebas unitarias:

```bash
mvn test
```

Swagger:

```text
http://localhost:8080/swagger-ui.html
```

Usuarios semilla:

- Admin: `admin@btg.com` / `Admin123*`
- Cliente: se crea desde `POST /api/auth/register`

Fondos semilla:

- `1` FPV_BTG_PACTUAL_RECAUDADORA
- `2` FPV_BTG_PACTUAL_ECOPETROL
- `3` DEUDAPRIVADA
- `4` FDO-ACCIONES
- `5` FPV_BTG_PACTUAL_DINAMICA

## Frontend

El frontend está en `frontend/` y usa Angular 17+ con Tailwind CSS.

Tu Angular CLI global actual es 14.2.12; instala Angular CLI 17+ antes de compilar:

```bash
npm install -g @angular/cli@17
cd frontend
npm install
npm start
```

URL:

```text
http://localhost:4200
```

## Endpoints principales

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/auth/me`
- `GET /api/fondos`
- `POST /api/suscripciones`
- `GET /api/suscripciones`
- `DELETE /api/suscripciones/{idFondo}`
- `GET /api/transacciones`
- `GET /api/admin/clientes`

Ejemplo de registro:

```json
{
  "email": "cliente@btg.com",
  "password": "Cliente123*",
  "nombre": "Cliente Prueba",
  "telefono": "3001234567"
}
```

Ejemplo de suscripción:

```json
{
  "idFondo": "1",
  "preferenciaNotificacion": "EMAIL"
}
```

Todas las respuestas usan:

```json
{ "success": true, "data": {}, "message": "...", "timestamp": "..." }
```
