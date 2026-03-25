# Dockerfile para el microservicio Pokemon API REST
# Compila y empaqueta la aplicacion dentro de la imagen para evitar usar un JAR local desactualizado.

FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /workspace

COPY pom.xml .
COPY src ./src

RUN mvn -q clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=build /workspace/target/pokemon-api-client.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
