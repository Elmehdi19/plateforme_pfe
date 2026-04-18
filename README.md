# Plateforme de Gestion Pédagogique – Projet PFE

## Contexte

Application web de gestion pédagogique destinée aux établissements d’enseignement supérieur.  
Développée dans le cadre de mon projet de fin d’études (PFE), elle permet de gérer les étudiants, professeurs, filières, modules, notes, délibérations et prédictions de risque d’échec.

## Technologies utilisées

- **Backend** : Spring Boot 3.2, Spring Security (JWT), JPA/Hibernate, MySQL
- **Frontend** : Angular 19, Bootstrap, Chart.js
- **Conteneurisation** : Docker, Docker Compose
- **Outils** : Maven, npm, Git, GitHub Actions

## Fonctionnalités principales

### Gestion des utilisateurs
- Authentification sécurisée par JWT (JSON Web Token)
- Trois rôles : **ADMIN**, **PROFESSEUR**, **ETUDIANT**
- Changement de mot de passe après connexion

### Administrateur
- CRUD complet des **étudiants** et **professeurs**
- Import de données via fichiers Excel
- Gestion de la structure pédagogique : **filières**, **départements**, **modules**, **éléments**, **semestres**, **années**
- Tableau de bord avec statistiques et graphiques (Chart.js)
- Paramétrage des seuils de délibération (note minimale, moyenne semestre, note éliminatoire)

### Professeur
- Saisie et modification des notes (par étudiant et par élément)
- Consultation des notes de ses étudiants
- Accès aux résultats de délibération par semestre/filière (statistiques et PV)
- Visualisation des étudiants en risque d’échec
- Génération de PV (procès‑verbaux) imprimables

### Étudiant
- Consultation de ses propres notes
- Affichage de sa moyenne générale et de ses dernières notes
- Modification de son mot de passe

### Délibération (métier avancé)
- Calcul automatique des moyennes :
  - Moyenne d’un élément (CC, TP, Examen)
  - Moyenne d’un module (pondérée par coefficient des éléments)
  - Moyenne d’un semestre (pondérée par coefficient des modules)
  - Moyenne d’une année
- Règles de validation : validation directe, compensation entre modules, rattrapage
- Génération de PV pour module ou semestre
- Identification des étudiants en risque (moyenne < seuil)

### Prédiction (intelligence pédagogique)
- Calcul d’un score de risque pour chaque étudiant (basé sur ses notes)
- Classification : FAIBLE, MOYEN, ÉLEVÉ
- Suivi historique des prédictions

### Tableaux de bord
- **Admin** : cartes de statistiques (étudiants, professeurs, filières, modules), graphiques (moyennes par module, décisions), derniers étudiants
- **Professeur** : total de notes saisies, dernière note, dernières notes
- **Étudiant** : moyenne générale, dernières notes

## Architecture technique

- API REST stateless (Spring Boot)
- Sécurité : JWT, filtres personnalisés, annotations `@PreAuthorize`
- Base de données MySQL (relations JPA)
- Frontend Angular standalone (routage, services, guards)
- Docker : image unique multi‑étapes (Angular compilé + JAR Spring Boot)

## Installation et lancement (pour testeur)

### Prérequis
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) installé

### Étapes

1. **Cloner le dépôt** (ou télécharger l’archive) :
   ```bash
   git clone https://github.com/Elmehdi19/plateforme_pfe.git
   cd plateforme_pfe
Lancer l’application :

bash
docker-compose up -d
La première exécution peut prendre 2‑3 minutes (téléchargement des images, construction, démarrage de MySQL).

Accéder à l’application :
Ouvrez votre navigateur à l’adresse http://localhost:8081

Comptes de démonstration (créés automatiquement au premier démarrage)
Rôle	Email	Mot de passe
Administrateur	admin@test.com	admin123
Professeur	prof.dupont@test.com	default123
Étudiant	etud.dupont@test.com	default123
Ces comptes sont initialisés automatiquement via la classe DataInitializer. Aucune intervention manuelle n’est nécessaire.

Comment tester les fonctionnalités principales
En tant qu’administrateur
Connectez‑vous avec admin@test.com / admin123.

Explorez le menu :

Dashboard : statistiques et graphiques.

Filières, Modules, Éléments, Semestres : créez, modifiez, supprimez.

Étudiants / Professeurs : ajoutez ou importez des données (exemple de fichier Excel disponible dans docs/).

Import : testez l’import de données en masse.

Délibération : choisissez une filière et un semestre, calculez les résultats, imprimez le PV.

Prédictions : générez et consultez les scores de risque.

En tant que professeur
Connectez‑vous avec prof.dupont@test.com / default123.

Dashboard : visualisez les dernières notes.

Notes : ajoutez/modifiez/supprimez des notes.

Délibération : consultez les statistiques et le détail par étudiant.

Prédictions : voyez les risques des étudiants.

En tant qu’étudiant
Connectez‑vous avec etud.dupont@test.com / default123.

Dashboard : consultez votre moyenne et vos dernières notes.

Mes notes : listez toutes vos notes.

Profil : changez votre mot de passe.

Arrêt et nettoyage
bash
docker-compose down          # arrête les conteneurs
docker-compose down -v       # supprime aussi les volumes (réinitialise la base)
Points d’attention pour l’évaluation
Sécurité : JWT, rôles bien protégés (@PreAuthorize).

Calculs métier : moyennes et règles de délibération conformes au cahier des charges.

Import Excel : support des fichiers XLSX.

Graphiques et impressions : Chart.js et window.print() fonctionnels.

Portabilité : application entièrement conteneurisée (Docker).

Dépannage
Port déjà utilisé : modifiez le mapping dans docker-compose.yml (ex: "8082:8080").

Erreur de connexion à la base : vérifiez que MySQL est bien démarré (docker-compose logs db).

Page blanche ou 401 : videz le cache du navigateur ou utilisez une fenêtre de navigation privée.

Conclusion
L’application répond à l’ensemble des besoins d’une plateforme pédagogique moderne : gestion des utilisateurs, des structures, des notes, délibération et prédictions. L’utilisation de Docker simplifie le déploiement sur n’importe quelle machine.

