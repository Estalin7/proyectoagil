# Etapa 1: Compilar
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# ← AGREGA ESTA LÍNEA
RUN chmod +x ./mvnw

COPY src src

RUN ./mvnw clean package -DskipTests

# Etapa 2: Ejecutar
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /app/target/proyectoagil-0.0.1-SNAPSHOT.jar app.jar

RUN mkdir -p /tmp/uploads/productos

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
