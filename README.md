# Cinematec Back

![Java](https://img.shields.io/badge/Java-17-orange?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.2-6DB33F?style=flat&logo=springboot&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-0.12.3-000000?style=flat&logo=jsonwebtokens&logoColor=white)

API backend pour l’application **Cinematec**, destinée à la gestion de **films** et de **séries**.

## Table des matières

- [Résumé du projet](#résumé-du-projet)
- [Technologies utilisées](#technologies-utilisées)
- [Prérequis](#prérequis)
- [Configuration](#configuration)
- [Structure du projet](#structure-du-projet)
- [Lancement](#lancement)

## Résumé du projet

**Cinematec Back** est une API REST développée en Spring Boot permettant de gérer un catalogue de films et de séries.  
Elle offre :

- La gestion des films et séries (ajout, consultation, recherche, derniers ajouts…)
- La gestion des utilisateurs (inscription, authentification, modification de mot de passe)
- Un système de rôles (notamment `ROLE_ADMIN`)
- Une authentification sécurisée par JWT
- Une base de données MongoDB pour le stockage des données

L’API est conçue pour être consommée par un frontend (Angular sur `http://localhost:4200`).

## Technologies utilisées

| Technologie              | Version / Détail      |
|--------------------------|-----------------------|
| **Java**                 | 17                    |
| **Spring Boot**          | 3.3.2                 |
| **Spring Web**           | API REST              |
| **Spring Data MongoDB**  | Persistance NoSQL     |
| **Spring Security**      | Authentification & autorisation |
| **JWT** (jjwt)           | 0.12.3                |
| **Lombok**               | Réduction du boilerplate |
| **Passay**               | 2.0.0 (validation de mots de passe) |
| **Maven**                | Build & dépendances   |
| **MongoDB**              | Base de données       |

## Prérequis

- Java 17+
- Maven 3.8+ (ou utiliser le wrapper `./mvnw`)
- MongoDB (local sur le port `27017` par défaut)
- Variable d’environnement `JWT_SECRET` (clé secrète pour signer les tokens)

## Configuration

Fichier `src/main/resources/application.properties` :

```properties
spring.application.name=cinematec_back
spring.data.mongodb.database=cinematec
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
server.port=8080
jwt.secret=${JWT_SECRET}
```

## Structure du projet

## Structure du projet

```text
cinematec_back/
├── src/
│   └── main/
│       ├── java/
│       │   └── fr/poseidonj/cinematec_back/
│       │       ├── CinematecBackApplication.java
│       │       ├── config/
│       │       │   └── WebConfig.java
│       │       ├── controllers/
│       │       │   ├── BaseController.java
│       │       │   ├── MovieController.java
│       │       │   ├── RoleController.java
│       │       │   └── UserController.java
│       │       ├── exception/
│       │       │   ├── ExpiredTokenException.java
│       │       │   ├── GenericException.java
│       │       │   └── IllegalTokenException.java
│       │       ├── models/
│       │       │   ├── dtos/
│       │       │   │   ├── BaseDTO.java
│       │       │   │   ├── MovieDTO.java
│       │       │   │   ├── RegistrationDTO.java
│       │       │   │   ├── RoleDTO.java
│       │       │   │   ├── UserDTO.java
│       │       │   │   └── special/
│       │       │   │       ├── Contains.java
│       │       │   │       ├── DateDTO.java
│       │       │   │       ├── PagedResponse.java
│       │       │   │       ├── SearchDTO.java
│       │       │   │       ├── SortDTO.java
│       │       │   │       └── SortSearchDTO.java
│       │       │   └── entities/
│       │       │       ├── BaseEntity.java
│       │       │       ├── Movie.java
│       │       │       ├── Role.java
│       │       │       └── User.java
│       │       ├── repositories/
│       │       │   ├── IBaseRepository.java
│       │       │   ├── IMovieRepository.java
│       │       │   ├── IRoleRepository.java
│       │       │   └── IUserRepository.java
│       │       ├── security/
│       │       │   ├── CredentialDTO.java
│       │       │   ├── JwtFilter.java
│       │       │   └── JwtResponse.java
│       │       ├── service/
│       │       │   ├── IAdminService.java
│       │       │   ├── IBaseService.java
│       │       │   ├── IMovieService.java
│       │       │   ├── IPageService.java
│       │       │   ├── IRoleService.java
│       │       │   ├── IUserService.java
│       │       │   └── imp/
│       │       │       ├── BaseService.java
│       │       │       ├── MovieService.java
│       │       │       ├── RoleService.java
│       │       │       └── UserService.java
│       │       └── utilities/
│       │           ├── JwtUtil.java
│       │           ├── SearchUtility.java
│       │           ├── ServiceUtility.java
│       │           └── annotation/
│       └── resources/
│           └── application.properties
├── pom.xml
├── mvnw
├── mvnw.cmd
└── .gitignore
```

## Lancement

```bash
# Cloner le projet
git clone https://github.com/PoseidonjGaming/cinematec_back.git
cd cinematec_back

# Définir la clé JWT (exemple)
export JWT_SECRET=votre_cle_secrete_tres_longue_et_securisee

# Lancer l’application
./mvnw spring-boot:run
```
