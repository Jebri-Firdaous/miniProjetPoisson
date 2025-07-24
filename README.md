# 🐟 miniProjetPoisson

Application web fullstack (Angular + Spring Boot) pour gérer les espèces de poissons avec image (CRUD complet).

## ⚙️ Technologies

* **Frontend** : Angular 17, TypeScript, Bootstrap
* **Backend** : Spring Boot, Spring Data JPA, REST
* **Base de données** : MySQL
* **Stockage image** : Local (système de fichiers)

## 🚀 Lancer le projet

### 1. Cloner le projet

```bash
https://github.com/Jebri-Firdaous/miniProjetPoisson.git
```

### 2. Lancer le backend

```bash
cd backend
mvn spring-boot:run
```

Accès API : [http://localhost:8081/backend/api/poisson](http://localhost:8081/backend/api/poisson)

> ⚠️ Vérifie que ta base MySQL est configurée dans `application.properties`

### 3. Lancer le frontend

```bash
cd frontend
npm install
ng serve -o
```

Accès interface : [http://localhost:4200](http://localhost:4200)

## 🧩 Fonctions principales

* Lister les poissons
* Ajouter un poisson (avec image)
* Modifier (avec ou sans nouvelle image)
* Supprimer poisson + image

## 📁 Exemple d'API REST

| Méthode | Endpoint                          | Description             |
| ------- | --------------------------------- | ----------------------- |
| GET     | `/list-all-poisson`               | Tous les poissons       |
| GET     | `/get-poisson/{id}`               | Récupérer un poisson par ID|
| POST    | `/add-poisson-with-image`         | Ajouter avec image      |
| PUT     | `/update-poisson-with-image/{id}` | Modifier avec image     |
| DELETE  | `/remove-poisson/{id}`            | Supprimer poisson + img |

## 📸 Images

Les images sont stockées dans un dossier local sur le serveur.

Par défaut :

```
/home/firdaous/Bureau/stageING-ETE2025/miniProjetPoisson/images/poissons/
```
## 👤 Auteur

Firdaous Jebri

Projet réalisé dans le cadre d'une formation en développement web pour un stage professionnel.  
But pédagogique : mise en pratique d'un projet fullstack Angular + Spring Boot avec gestion d’images et base de données.

