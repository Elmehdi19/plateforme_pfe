# Étape 1 : construction du frontend Angular (Node.js 22)
FROM node:22-alpine AS angular-builder
WORKDIR /app/frontend

COPY frontend/package*.json ./
RUN npm cache clean --force && npm install --legacy-peer-deps

COPY frontend/ ./
RUN npm run build -- --output-path=dist --configuration=production

# Étape 2 : construction du backend Spring Boot
FROM maven:3.9.6-eclipse-temurin-17 AS backend-builder
WORKDIR /app/backend

COPY backend/pom.xml ./
RUN mvn dependency:go-offline -B

COPY backend/src ./src
RUN mvn clean package -DskipTests

# Étape 3 : image finale
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=backend-builder /app/backend/target/*.jar app.jar

# Copier le contenu de dist/browser/ (pas le dossier browser) vers /app/static
COPY --from=angular-builder /app/frontend/dist/browser/ /app/static

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
