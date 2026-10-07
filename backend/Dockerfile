# ETAPA 1: Construcción con Gradle Wrapper y JDK 17
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# 1. Copiamos los archivos de Gradle primero para aprovechar la caché de Docker
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle* .

# Damos permisos de ejecución al wrapper y descargamos dependencias
RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon || true

# 2. Copiamos el código fuente y generamos el bootJar omitiendo tests unitarios
COPY src ./src
RUN ./gradlew bootJar --no-daemon -x test

# ETAPA 2: Runtime liviano para producción
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Creamos un usuario sin privilegios por seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiamos dinámicamente cualquier JAR generado que no sea el de fuentes/plain
COPY --from=build /app/build/libs/*[!plain].jar app.jar

EXPOSE 8080

# Flags optimizados para memoria en contenedores
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]