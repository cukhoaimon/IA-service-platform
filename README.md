## Project

- Kotlin
- Micronaut
- PostgresSQL
- Docker Compose

## How to run using Intellij

1. Start docker compose

```shell
docker-compose up -d 
```

2. Build Project

```shell
./gradlew build 
```

3. Run Project

```shell
./gradlew run 
```

4. Run test (optional)

```shell
./gradlew test 
```

## Documentation

Build and run the `service-platform` project should also generate `swagger` spec from Micronaut annotations at:

- Swagger http://localhost:8080/swagger/views/swagger-ui/index.html
- Rapidoc http://localhost:8080/swagger/views/rapidoc/index.html
- Redoc http://localhost:8080/swagger/views/redoc/index.html