# Presence Service

Spring Boot service that tracks user online/offline status using Redis.

## Prerequisites
- Java 21
- Maven 3.9+
- (Optional) Docker + Docker Compose

## Environment (.env)
Create `presence-service/.env` with the required variables, as shown in .env.example.

## Run locally (Maven)
From `presence-service/`:

```
./load-env.ps1
```

This script builds the project, loads `.env`, and starts the API module. 

The service starts on `http://localhost:8081`.

## Run with Docker
From `presence-service/`:

```
docker network create chat-net
```

(Only needed once; `docker-compose.yml` expects this external network.) Then:

```
docker compose up --build
```

This starts:
- Presence Service (port 8081)
- Redis container (port 6379)

## Useful endpoints
- Health check: `http://localhost:8081/actuator/health`
- OpenAPI: `http://localhost:8081/v3/api-docs`
- Swagger UI: `http://localhost:8081/swagger-ui`
- REST:
  - `GET /presence/{userId}`
  - `PUT /presence/{userId}/online`
  - `PUT /presence/{userId}/offline`
  - `GET /presence/bulk?userIds=...`
