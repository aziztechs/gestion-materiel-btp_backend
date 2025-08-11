# Gestion Matériel BTP - Backend

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.4-green.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![API Status](https://img.shields.io/badge/API-Ready-brightgreen.svg)](http://localhost:8080/api/swagger-ui.html)

🏗️ **Application backend REST API pour la gestion des demandes matérielles dans une entreprise de BTP.**

> ✅ **Statut** : Application entièrement fonctionnelle avec authentification JWT, APIs REST complètes et gestion d'erreurs centralisée.

## 📋 Table des matières

- [Fonctionnalités](#-fonctionnalités)
- [Technologies](#-technologies)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [API Documentation](#-api-documentation)
- [Endpoints](#-endpoints-disponibles)
- [Exemples d'utilisation](#-exemples-dutilisation)
- [Tests](#-tests)
- [Contribution](#-contribution)
- [Licence](#-licence)

## 🚀 Fonctionnalités

### ✅ **Fonctionnalités implémentées**
- **🔐 Authentification JWT** complète avec gestion des rôles
- **👥 Gestion des utilisateurs** avec 6 rôles métier :
  - `ADMIN_SYSTEME` - Administration complète
  - `CHEF_PROJET` - Création et gestion des besoins
  - `RESPONSABLE_ACHATS` - Gestion des achats
  - `RAF` - Responsable Administratif et Financier
  - `CHEF_COMPTABLE` - Gestion comptable
  - `CA` - Conseil d'Administration
- **📋 Gestion des besoins** :
  - Création, modification, suppression
  - Marquage d'urgence
  - Pagination et filtrage
  - Autorisation par rôles
- **🛡️ Sécurité avancée** :
  - JWT avec expiration configurable
  - CORS configuré
  - Gestion d'erreurs centralisée
  - Validation des données
- **📧 Service email** intégré
- **📚 Documentation Swagger** automatique

### 🔄 **Workflow métier** (à implémenter)
- Demande matériel (avant le 25 du mois)
- Validation des devis (1 ou 3 selon montant)
- Budgétisation mensuelle
- Approbation par le CA
- Gestion des urgences hors cycle normal

## 💻 Technologies

- **Backend** :
  - Java 17
  - Spring Boot 3.5.4
  - Spring Security + JWT
  - Spring Data JPA
  - Hibernate
  - MySQL 8.0
  - Swagger/OpenAPI 3.0

- **Outils** :
  - Maven 3.8+
  - Lombok
  - BCrypt (chiffrement mots de passe)
  - JJWT (gestion JWT)

## 📦 Prérequis

- Java 17 JDK
- MySQL 8.0+
- Maven 3.8+

## 🛠 Installation

### 1. **Cloner le repository**
```bash
git clone https://github.com/aziztechs/gestion-materiel-btp_backend.git
cd gestion-materiel-btp_backend
```

### 2. **Configurer la base de données**
```sql
-- Créer la base de données
CREATE DATABASE gestion_materiel_btp;
CREATE USER 'root'@'localhost' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON gestion_materiel_btp.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
```

### 3. **Configurer l'application**
Modifier `src/main/resources/application.properties` si nécessaire :
```properties
# Base de données
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_materiel_btp
spring.datasource.username=root
spring.datasource.password=root

# Email (optionnel)
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

### 4. **Compiler et lancer**
```bash
# Compiler
mvn clean compile

# Lancer l'application
mvn spring-boot:run
```

### 5. **Vérifier l'installation**
- API : http://localhost:8080/api/auth/test
- Swagger : http://localhost:8080/api/swagger-ui.html

## ⚙️ Configuration

### **Variables d'environnement (Production)**
```bash
# Base de données
DB_URL=jdbc:mysql://localhost:3306/gestion_materiel_btp
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password

# JWT
JWT_SECRET=your_secure_jwt_secret_base64
JWT_EXPIRATION=86400000

# Email
EMAIL_USERNAME=your-email@gmail.com
EMAIL_PASSWORD=your-app-password
```

### **Profils Spring**
- `dev` : Développement (logs détaillés, H2 en mémoire)
- `prod` : Production (logs minimaux, MySQL)

```bash
# Lancer en mode production
mvn spring-boot:run -Dspring.profiles.active=prod
```

## 📚 API Documentation

### **Swagger UI**
Une fois l'application lancée, accédez à la documentation interactive :
- **URL** : http://localhost:8080/api/swagger-ui.html
- **OpenAPI JSON** : http://localhost:8080/api/v3/api-docs

### **Authentification**
L'API utilise JWT Bearer Token :
```http
Authorization: Bearer <your-jwt-token>
```

## 🔗 Endpoints disponibles

### **🔐 Authentification** (`/api/auth`)
| Méthode | Endpoint | Description | Accès |
|---------|----------|-------------|-------|
| `POST` | `/auth/signin` | Connexion utilisateur | Public |
| `POST` | `/auth/signup` | Inscription utilisateur | Public |
| `GET` | `/auth/test` | Test de l'API | Public |

### **📋 Gestion des besoins** (`/api/besoins`)
| Méthode | Endpoint | Description | Rôles requis |
|---------|----------|-------------|--------------|
| `GET` | `/besoins` | Liste tous les besoins | ADMIN, CHEF_PROJET, RESPONSABLE_ACHATS |
| `GET` | `/besoins/{id}` | Détails d'un besoin | ADMIN, CHEF_PROJET, RESPONSABLE_ACHATS |
| `POST` | `/besoins` | Créer un besoin | CHEF_PROJET |
| `PUT` | `/besoins/{id}` | Modifier un besoin | CHEF_PROJET |
| `DELETE` | `/besoins/{id}` | Supprimer un besoin | ADMIN, CHEF_PROJET |
| `GET` | `/besoins/mes-besoins` | Mes besoins | Tous (utilisateur connecté) |
| `PATCH` | `/besoins/{id}/urgence` | Marquer comme urgent | CHEF_PROJET |

## 💡 Exemples d'utilisation

### **1. Inscription d'un utilisateur**
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "email": "chef@btp.com",
    "password": "password123",
    "nom": "Dupont",
    "prenom": "Jean",
    "telephone": "0123456789",
    "role": "ROLE_CHEF_PROJET"
  }'
```

### **2. Connexion**
```bash
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "email": "chef@btp.com",
    "password": "password123"
  }'
```

**Réponse :**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "id": 1,
  "email": "chef@btp.com",
  "nom": "Dupont",
  "prenom": "Jean",
  "role": "ROLE_CHEF_PROJET"
}
```

### **3. Créer un besoin**
```bash
curl -X POST http://localhost:8080/api/besoins \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "description": "Béton pour fondations",
    "unite": "m3",
    "quantite": 50,
    "isUrgence": false
  }'
```

### **4. Lister les besoins avec pagination**
```bash
curl -X GET "http://localhost:8080/api/besoins?page=0&size=10&sort=dateDemande,desc" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 🧪 Tests

### **Tests unitaires**
```bash
# Lancer tous les tests
mvn test

# Tests avec couverture
mvn test jacoco:report
```

### **Tests d'intégration**
```bash
# Tests avec profil de test
mvn test -Dspring.profiles.active=test
```

### **Test manuel avec Postman**
Importez la collection Postman disponible dans `/docs/postman/` pour tester rapidement tous les endpoints.

## 🚀 Déploiement

### **Packaging**
```bash
# Créer le JAR exécutable
mvn clean package -DskipTests

# Le JAR sera dans target/gestion-materiel-btp_backend-1.0.0.jar
```

### **Lancement en production**
```bash
java -jar target/gestion-materiel-btp_backend-1.0.0.jar \
  --spring.profiles.active=prod \
  --server.port=8080
```

## 🤝 Contribution

### **Prochaines fonctionnalités à implémenter**
- [ ] **Gestion des devis** (DevisController, DevisService)
- [ ] **Workflow d'approbation** (Budget, Approbation)
- [ ] **Gestion des utilisateurs** (UtilisateurController)
- [ ] **Notifications email** avancées
- [ ] **Tableau de bord** avec statistiques
- [ ] **Export PDF/Excel** des rapports
- [ ] **Tests unitaires** complets
- [ ] **Documentation technique** détaillée

### **Comment contribuer**
1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/nouvelle-fonctionnalite`)
3. Commit les changements (`git commit -m 'Ajout nouvelle fonctionnalité'`)
4. Push vers la branche (`git push origin feature/nouvelle-fonctionnalite`)
5. Ouvrir une Pull Request

## 📄 Licence

Ce projet est sous licence Apache 2.0. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

---

## 📞 Contact

**Développeur** : Abdou Aziz NDIAYE  
**Email** : abdouaziz0104@gmail.com  
**GitHub** : [@aziztechs](https://github.com/aziztechs)

---

⭐ **N'hésitez pas à mettre une étoile si ce projet vous aide !**
