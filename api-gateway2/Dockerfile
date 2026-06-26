# 1. Usamos una imagen ligera de Java 21 (o la versión que uses en tu pom.xml)
FROM eclipse-temurin:21-jdk-alpine

# 2. Creamos la carpeta dentro del contenedor donde vivirá la app
WORKDIR /app

# 3. Copiamos el archivo .jar compilado desde nuestra carpeta target hacia el contenedor
COPY target/*.jar app.jar

# 4. Le avisamos a Docker que este contenedor usará el puerto 8090 (el de tus boletas)
EXPOSE 8090

# 5. El comando definitivo para arrancar tu microservicio al encender el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]