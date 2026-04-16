# Étape 1 : construction du backend Spring Boot
FROM maven:3.9.6-eclipse-temurin-17 AS backend-builder
WORKDIR /app/backend

COPY backend/pom.xml ./
RUN mvn dependency:go-offline -B

COPY backend/src ./src
RUN mvn clean package -DskipTests

# Étape 2 : image finale
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=backend-builder /app/backend/target/*.jar app.jar
# Copier le dossier Angular pré‑construit
COPY frontend/dist/gestion-deliberation-frontend /app/static

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]