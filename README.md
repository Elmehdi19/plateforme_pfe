# Plateforme de Gestion Pédagogique Intelligente – Projet PFE

Application web complète de gestion pédagogique pour les établissements d’enseignement supérieur.  
Développée dans le cadre d’un projet de fin d’études, elle automatise les délibérations, fournit des prédictions de réussite, et intègre un déploiement moderne conteneurisé.

**🟢 Démo publique (si ngrok actif)** : [https://wreckage-crewless-breath.ngrok-free.dev](https://wreckage-crewless-breath.ngrok-free.dev)

---

## 📌 Fonctionnalités principales

- 🔐 **Authentification sécurisée JWT** (3 rôles : Admin, Professeur, Étudiant)
- 👥 **Gestion complète des utilisateurs** (CRUD étudiants / professeurs)
- 📋 **Gestion de la structure pédagogique** (filières, départements, modules, éléments, semestres, années)
- 📊 **Saisie des notes** (par élément, avec coefficients) et gestion des absences
- ⚖️ **Délibération automatique** :
  - Calcul des moyennes (élément, module, semestre, année) selon coefficients
  - Règles de validation : compensation, rattrapage, seuil éliminatoire
  - Génération de PV imprimable (admin)
- 🤖 **Prédiction de risque d’échec** (Machine Learning) :
  - Régression logistique entraînée sur données historiques
  - Score de risque (0-100%) et classification (FAIBLE / MOYEN / ÉLEVÉ)
- 📥 **Import de données Excel** (étudiants, notes) avec validation
- 📈 **Tableaux de bord** avec statistiques et graphiques Chart.js (par rôle)
- 🐳 **Conteneurisation Docker** (image unique multi‑étapes)
- ☁️ **Base de données cloud** (MySQL Aiven) avec connexion SSL
- 🌐 **Exposition publique via ngrok** (démonstration sans hébergement payant)
- 🔄 **CI/CD** avec GitHub Actions (build, test, push image)


text

---

## 🚀 Technologies utilisées

| Catégorie       | Outils / Frameworks |
|-----------------|---------------------|
| Backend         | Spring Boot 3.2, Spring Security, JWT (jjwt), JPA/Hibernate |
| Base de données | MySQL 8.0, Aiven (cloud), MySQL Workbench |
| Frontend        | Angular 19, TypeScript, Chart.js, Bootstrap |
| Machine Learning| Weka / Smile (régression logistique), modèle .ser |
| DevOps          | Docker, Docker Compose, GitHub Actions, ngrok |
| Outils          | Maven, npm, Git, GitHub |

---

## ⚙️ Installation et lancement local (sans base cloud)

### Prérequis
- Docker & Docker Compose
- Git

### Étapes

1. **Cloner le dépôt**
   ```bash
   git clone https://github.com/Elmehdi19/plateforme_pfe.git
   cd plateforme_pfe
Créer le fichier .env (à la racine)

env
DB_PASSWORD=un_mot_de_passe_local
(ce fichier est ignoré par Git)

Lancer les conteneurs

bash
docker-compose up -d
Accéder à l’application
http://localhost:8081

☁️ Utilisation de la base cloud Aiven (optionnel)
Le projet peut se connecter à une base MySQL hébergée sur Aiven.

Crée une base Aiven gratuite (ou utilise un backup).

Récupère les infos : host, port, user, password, ca.pem.

Exporte/importe les données avec MySQL Workbench (ou ligne de commande).

Définis les variables d’environnement suivantes dans ton .env (ou directement dans docker-compose.yml pour du dev) :

env
DB_URL=jdbc:mysql://<host>:<port>/defaultdb?useSSL=true&serverTimezone=UTC
DB_USERNAME=avnadmin
DB_PASSWORD=<mot_de_passe_aiven>
Si tu lances l’application localement sans Docker, place le certificat ca.pem dans src/main/resources/ et configure Spring Boot pour l’utiliser.

Lance docker-compose up -d ; l’application dialoguera directement avec Aiven.

🧪 Comptes de démonstration
Ces comptes sont créés automatiquement au premier démarrage (classe DataInitializer).

Rôle	Email	Mot de passe
Admin	admin@test.com	admin123
Professeur	prof.dupont@test.com	default123
Étudiant	etud.dupont@test.com	default123
🧭 Comment tester les fonctionnalités
En tant qu’Admin
Connectez-vous, explorez le tableau de bord (stats, graphiques).

Créez/modifiez des filières, modules, éléments, étudiants, professeurs.

Importez des étudiants via Excel (fichier exemple dans docs/).

Lancez une délibération : sélectionnez filière + semestre, visualisez les résultats, imprimez le PV.

Générez les prédictions de risque et observez les scores.

En tant que Professeur
Saisissez des notes par module/élément/étudiant.

Marquez les absences.

Consultez les résultats de délibération et les étudiants à risque.

En tant qu’Étudiant
Visualisez vos notes, moyenne, absences.

Consultez votre score de risque (si activé).

📁 Structure du projet (principaux dossiers)
text
plateforme_pfe/
├── backend/                  # Code Spring Boot
│   ├── src/main/java/...
│   └── Dockerfile
├── frontend/                 # Code Angular
├── docker-compose.yml
├── .env.example              # Template pour les variables
├── docs/                     # Documentation, exemples Excel, captures
└── README.md
🧹 Arrêt et nettoyage
bash
docker-compose down        # Arrête les conteneurs
docker-compose down -v     # Supprime aussi les volumes (réinitialise la BDD)
🔧 Dépannage
Problème	Solution
Port 8081 déjà utilisé	Modifiez docker-compose.yml ("8082:8080")
Connexion MySQL refusée	Vérifiez que le service db est bien lancé : docker-compose logs db
Erreur 401 non autorisé	Vérifiez vos identifiants ; videz le cache du navigateur
CORS bloqué	La configuration est centralisée dans SecurityConfig.java ; aucun CorsConfig séparé
Le modèle ML (.ser) ne charge pas	Vérifiez que le fichier est dans src/main/resources/ à la racine ou sous un dossier connu
ngrok ne fonctionne pas	Si vous utilisez ngrok, vérifiez que le tunnel est bien dirigé vers localhost:8080 (port du conteneur)
📈 Machine Learning – Prédiction de risque
Modèle : régression logistique (Weka/Smile), sauvegardé en model.ser.

Entraînement : données synthétiques (notes passées, absences, moyenne générale).

Intégration : chargement au démarrage Spring, accessible via endpoint /api/admin/predictions.

Chaque étudiant obtient un score (0.0 à 1.0) et un niveau visuel (🔴🟠🟢).

✨ Points forts pour l’évaluation
Sécurité : JWT, rôles protégés (@PreAuthorize), mot de passe haché (BCrypt)

Logique métier : formules de délibération clairement documentées, compensation, rattrapage

Portabilité : une seule commande docker-compose up suffit

Cloud-ready : base Aiven, CI/CD, image Docker publiée sur ghcr.io

Démo accessible : lien ngrok pour tester en conditions réelles

🧠 Améliorations futures
Export Excel des PV

Signature électronique des notes

Inscription en ligne des étudiants

Modèle ML plus évolué (Random Forest) sur données réelles

Déploiement permanent sur un cloud (Render, Fly.io) avec nom de domaine


Conclusion
L’application répond à l’ensemble des besoins d’une plateforme pédagogique moderne : gestion des utilisateurs, des structures, des notes, délibération et prédictions. L’utilisation de Docker simplifie le déploiement sur n’importe quelle machine.

