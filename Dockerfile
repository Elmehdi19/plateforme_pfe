# Étape 1 : construction du frontend Angular
FROM node:18-alpine AS angular-builder
WORKDIR /app/frontend

# Copier les fichiers de dépendances
COPY frontend/package*.json ./

# Installer les dépendances (en forçant la résolution des conflits de peer dependencies)
RUN npm install --legacy-peer-deps

# Copier tout le code source du frontend
COPY frontend/ ./

# Construire l'application Angular (les fichiers seront dans /app/frontend/dist)
RUN npm run build -- --output-path=dist --configuration=production

# Étape 2 : construction du backend Spring Boot
FROM maven:3.9.6-eclipse-temurin-17 AS backend-builder
WORKDIR /app/backend

# Copier le fichier pom.xml et télécharger les dépendances
COPY backend/pom.xml ./
RUN mvn dependency:go-offline -B

# Copier le code source du backend
COPY backend/src ./src

# Construire le JAR (sans les tests)
RUN mvn clean package -DskipTests

# Étape 3 : image finale (exécution)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copier le JAR depuis l'étape précédente
COPY --from=backend-builder /app/backend/target/*.jar app.jar

# Copier les fichiers Angular compilés (recherche dans les dossiers possibles)
# Angular 15+ génère les fichiers dans dist/ (par défaut) ou dist/nom-projet
# On tente de copier depuis dist/ ; si cela échoue, on ajustera le chemin
COPY --from=angular-builder /app/frontend/dist /app/static
# Si votre build Angular place les fichiers dans un sous-dossier (ex: dist/nom-projet),
# remplacez la ligne ci-dessus par :
# COPY --from=angular-builder /app/frontend/dist/nom-de-votre-projet /app/static

# Exposer le port utilisé par Spring Boot
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]