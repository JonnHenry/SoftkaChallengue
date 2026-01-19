# 📌 Sofka Challenge – Banking Microservices

Este proyecto implementa una **arquitectura de microservicios** para la gestión de **clientes, cuentas bancarias y movimientos**, desarrollado como parte del **Sofka Challenge**.

La solución utiliza **Spring Boot**, **Apache Kafka** para comunicación asíncrona, **PostgreSQL** como base de datos y **Docker Compose** para el despliegue completo del entorno.

---

## 🧱 Arquitectura

El sistema está compuesto por los siguientes servicios:

| Servicio                     | Descripción                                   | Puerto Local | Puerto Contenedor |
|------------------------------|-----------------------------------------------|--------------|-------------------|
| **customer-service**         | Gestión de clientes                           | `8001`       | `8001`            |
| **account-service**          | Gestión de cuentas y movimientos              | `8000`       | `8000`            |
| **Kafka**                    | Broker de mensajería                          | `9092 / 29092` | `9092 / 29092`  |
| **Zookeeper**                | Coordinador de Kafka                          | `2181`       | `2181`            |
| **PostgreSQL (Customer)**    | Base de datos de clientes                     | `15430`      | `5432`            |
| **PostgreSQL (Account)**     | Base de datos de cuentas                      | `15431`      | `5432`            |


📌 **Comunicación entre servicios**
- **Sincrónica:** REST (`account-service → customer-service`)
- **Asíncrona:** Kafka (customer-service y account-service)

---

## ⚙️ Tecnologías Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Apache Kafka
- PostgreSQL
- Docker & Docker Compose
- Gradle
- Postman

---

## 🚀 Ejecución del Proyecto

### 1️⃣ Requisitos
- Docker
- Docker Compose

### 2️⃣ Levantar el entorno completo

```bash
docker-compose up --build
```

## 🌐 Endpoints Disponibles

---

## 🧑 Customer Service (`customer-service` – puerto **8001**)

### 🔹 Obtener todos los clientes
```http
GET /api/clientes
```

### 🔹 Obtener cliente por ID
```http
GET /api/clientes/{id}
```

### 🔹 Crear cliente
```http
POST /api/clientes
```

```json
{
  "dni": "0105476052",
  "name": "Jonnathan",
  "password": "12345.Qwwww",
  "gender": "M",
  "age": 21,
  "address": "prueba@prueba.com",
  "phone": "0984655221"
}
```

### 🔹 Actualizar cliente
```http
PUT /api/clientes/{id}
```

### 🔹 Eliminar cliente
```http
DELETE /api/clientes/{id}
```

---

## 🏦 Account Service (`account-service` – puerto **8000**)

### 🔹 Obtener todas las cuentas
```http
GET /api/cuentas
```

### 🔹 Obtener cuenta por ID
```http
GET /api/cuentas/{id}
```

### 🔹 Crear cuenta
```http
POST /api/cuentas
```

```json
{
  "number": "0000000001",
  "accountType": "Ahorro",
  "initialAmount": 100,
  "isActive": true,
  "clientId": 1
}
```

### 🔹 Actualizar cuenta
```http
PUT /api/cuentas/{id}
```

### 🔹 Eliminar cuenta
```http
DELETE /api/cuentas/{id}
```
### 🔹 Crear cuenta con información del clientes
📌 Publica evento en Kafka (`CLIENTS`) para enviar el evento de creación de una cuenta que luego es escuchada en el account-services y es procesada de manera asicrona.

```http
POST /api/cuentas
```

```json
{
   "dni": "0105476060",
   "name": "Jonnathan",
   "password": "12345.$-?Qwwww",
   "gender": "M",
   "age": 21,
   "address": "prueba@prueba.com",
   "phone": "0984655221",
   "numberAccount": "0000000003",
   "accountType": "Ahorro",
   "initialAmount": 100,
   "clientId":1
}
```


---

## 💸 Movimientos

### 🔹 Obtener todos los movimientos
```http
GET /api/movimientos
```

### 🔹 Crear movimiento
```http
POST /api/movimientos
```

```json
{
  "transactionType": "Deposito",
  "amount": 100.20,
  "accountId": 1
}
```

---

## 📊 Reportes

```http
GET /api/movimientos/{clientId}/reportes?from=YYYY-MM-DD&to=YYYY-MM-DD
```
```json
[
    {
        "transactionDate": "2026-01-19T05:19:00.771935",
        "clientId": "1",
        "number": "0000000003",
        "accountType": "Ahorro",
        "initialAmount": 100.0,
        "transactionType": "Deposito",
        "amount": 100.203565,
        "balance": 200.203565,
        "isActive": true
    },
    {
        "transactionDate": "2026-01-19T05:19:02.04665",
        "clientId": "1",
        "number": "0000000003",
        "accountType": "Ahorro",
        "initialAmount": 100.0,
        "transactionType": "Deposito",
        "amount": 100.203565,
        "balance": 300.40713,
        "isActive": true
    }
]
```

👤 Autor

Jonnathan Campoberde
Software Developer