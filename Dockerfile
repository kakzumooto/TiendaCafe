# Etapa de construcción (Build)
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución (Runtime) - CAMBIADO A ECLIPSE TEMURIN
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
# Agregamos un límite de memoria para que no choque con el plan gratuito de Render
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar"]