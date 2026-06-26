# ETAPA 1: Usamos Maven con Java 21 para compilar el código
FROM maven:3.8.8-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos todo el código fuente del microservicio al contenedor
COPY . .

# Compilamos saltándonos los tests (porque ya sabemos que dan verde impecable)
RUN mvn clean package -DskipTests

# ETAPA 2: Creamos la imagen final de ejecución usando Java 21 Alpine (ultra ligera)
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiamos el archivo .jar generado en la etapa de compilación
COPY --from=build /app/target/*.jar app.jar

# Comando para arrancar el microservicio
ENTRYPOINT ["java", "-jar", "app.jar"]