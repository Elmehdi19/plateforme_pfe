# Étape 1 : construction du frontend Angular (Node.js 20 requis pour Angular 19)
FROM node:20-alpine AS angular-builder
WORKDIR /app/frontend

# Copier les fichiers de dépendances
COPY frontend/package*.json ./

# Installer les dépendances (en forçant la résolution des conflits de peer dependencies)
RUN npm install --legacy-peer-deps

# Copier tout le code source du frontend
COPY frontend/ ./

# Construire l'application Angular
RUN npm run build -- --output-path=dist --configuration=production

# Étape 2 : construction du backend Spring Boot
FROM maven:3.9.6-eclipse-temurin-17 AS backend-builder
WORKDIR /app/backend

COPY backend/pom.xml ./
RUN mvn dependency:go-offline -B

COPY backend/src ./src
RUN mvn clean package -DskipTests

# Étape 3 : image finale (exécution)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=backend-builder /app/backend/target/*.jar app.jar
COPY --from=angular-builder /app/frontend/dist /app/static

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]