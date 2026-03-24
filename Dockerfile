# Dockerfile para el microservicio Pokemon API REST
# Construye usando el JAR de Spring Boot

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copia el JAR generado por Maven
COPY target/pokemon-api-client-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
