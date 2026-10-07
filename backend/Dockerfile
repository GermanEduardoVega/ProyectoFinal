# ETAPA 1: Construcción con Gradle
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# 1. Archivos de Gradle para caché
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle* .

RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon || true

# 2. Compilación del JAR omitiendo tests
COPY src ./src
RUN ./gradlew bootJar --no-daemon -x test

# ETAPA 2: Runtime liviano
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Creamos usuario y grupo seguro
RUN addgroup -S spring && adduser -S spring -G spring

# Copiamos el JAR asignándole permisos directamente al usuario spring
COPY --from=build --chown=spring:spring /app/build/libs/*.jar app.jar

# Verificación de integridad en el build
RUN ls -la /app/app.jar

USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]