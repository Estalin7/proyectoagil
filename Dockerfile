# Etapa 1: Compilar la aplicación
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Copiar código fuente
COPY src src

# Compilar la aplicación
RUN ./mvnw clean package -DskipTests

# Etapa 2: Ejecutar la aplicación
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copiar el JAR compilado desde la etapa anterior
COPY --from=builder /app/target/proyectoagil-0.0.1-SNAPSHOT.jar app.jar

# Crear carpeta para uploads
RUN mkdir -p /tmp/uploads/productos

# Exponer puerto
EXPOSE 8080

# Comando para ejecutar
CMD ["java", "-jar", "app.jar"]
