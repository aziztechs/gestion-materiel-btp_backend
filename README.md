# Gestion Matériel BTP - Backend

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-green.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)

Application backend pour la gestion des demandes matérielles dans une entreprise de BTP.

## 📋 Table des matières

- [Fonctionnalités](#-fonctionnalités)
- [Technologies](#-technologies)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [API Documentation](#-api-documentation)
- [Endpoints](#-endpoints-clés)
- [Tests](#-tests)
- [Déploiement](#-déploiement)
- [Licence](#-licence)

## 🚀 Fonctionnalités

- **Gestion des utilisateurs** avec rôles (Admin, Chef de Projet, RAF, etc.)
- **Workflow complet** :
  - Demande matériel (avant le 25 du mois)
  - Validation des devis (1 ou 3 selon montant)
  - Budgétisation mensuelle
  - Approbation par le CA
- **Gestion des urgences** hors cycle normal
- **Notifications** par email
- **Journalisation** des activités
- **Tableau de bord** administratif

## 💻 Technologies

- **Backend** :
  - Java 17
  - Spring Boot 3.2.0
  - Spring Security + JWT
  - Spring Data JPA
  - Hibernate
  - MySQL 8
  - Swagger/OpenAPI 3.0

- **Outils** :
  - Maven
  - Docker
  - Lombok
  - MapStruct

## 📦 Prérequis

- Java 17 JDK
- MySQL 8.0+
- Maven 3.8+
- Docker (optionnel)

## 🛠 Installation

