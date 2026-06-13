# Library — Spring Boot Basics

Projet d'apprentissage des fondamentaux de **Spring Boot** : une petite API REST de gestion de livres (« library »), construite progressivement pour mettre en pratique les concepts clés du framework.

## Aperçu

L'application expose une API REST permettant de gérer des livres (CRUD) et de les persister en base de données MySQL via JPA/Hibernate. Le code illustre une architecture en couches (controller → service → repository), l'utilisation de DTO, la validation des données et une gestion d'erreurs centralisée.

## Stack technique

| Élément | Détail |
|---|---|
| Langage | Java 21 |
| Framework | Spring Boot 3.4.0 |
| Build | Maven (`war`) |
| Base de données | MySQL (via `mysql-connector-j`) |
| ORM | Spring Data JPA / Hibernate |
| Validation | Spring Boot Starter Validation (Jakarta Bean Validation) |
| Utilitaires | Lombok |
| Serveur | Tomcat embarqué |

## Architecture

```
com.basics.library
├── BasicsApplication              # Point d'entrée Spring Boot
├── GlobalExceptionHandler         # Gestion centralisée des exceptions (@RestControllerAdvice)
└── book
    ├── controllers
    │   └── BookRestController      # Endpoints REST /book
    ├── services
    │   └── BookService            # Logique métier + règles de validation
    ├── persistence
    │   └── BookRepository         # Accès données (JpaRepository)
    ├── models
    │   ├── BookEntity             # Entité JPA mappée sur la table `book`
    │   └── exception
    │       ├── BookValidationException
    │       └── BookNotFoundException
    └── dto
        └── BookDTO                # DTO d'entrée/sortie (PostInput / PostOutput)
```

## API

Base path : `/book`

> **Documentation interactive** — une fois l'application démarrée, la documentation
> complète et navigable est générée automatiquement (OpenAPI 3 / Swagger UI) :
> - Swagger UI : [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)
> - Spécification OpenAPI (JSON) : [`http://localhost:8080/v3/api-docs`](http://localhost:8080/v3/api-docs)

| Méthode | URL | Description | Succès |
|---|---|---|---|
| `GET` | `/book` | Liste tous les livres | `200 OK` |
| `GET` | `/book/{id}` | Récupère un livre par son id | `200 OK` |
| `POST` | `/book` | Crée un livre | `201 Created` |
| `PUT` | `/book/{id}` | Met à jour un livre existant | `200 OK` |
| `DELETE` | `/book/{id}` | Supprime un livre | `204 No Content` |

### `POST /book` — création

**Corps de la requête** (`BookDTO.PostInput`) :
```json
{
  "name": "Le Petit Prince",
  "isbn": "978-2-07-040850-4",
  "pages": 96,
  "year": 1943,
  "description": "Conte poétique et philosophique."
}
```
- `name` : obligatoire, non vide (`@NotNull`, `@NotBlank`)
- `isbn` : obligatoire, non vide (`@NotNull`, `@NotBlank`)
- `pages` : obligatoire (`@NotNull`)
- `year` : obligatoire (`@NotNull`)
- `description` : optionnel

**Réponse** — `201 Created` (`BookDTO.PostOutput`) :
```json
{
  "id": 1,
  "name": "Le Petit Prince",
  "isbn": "978-2-07-040850-4",
  "pages": 96,
  "year": 1943,
  "description": "Conte poétique et philosophique."
}
```

### `PUT /book/{id}` — mise à jour

Réutilise le même corps que la création (`BookDTO.PostInput`), l'id étant fourni dans l'URL. Renvoie `200 OK` avec le livre mis à jour, ou `404 Not Found` si l'id n'existe pas.

### `GET /book` et `GET /book/{id}` — lecture

`GET /book` renvoie la liste complète des livres ; `GET /book/{id}` renvoie un livre précis (`404 Not Found` si absent).

### `DELETE /book/{id}` — suppression

Supprime le livre correspondant et renvoie `204 No Content`, ou `404 Not Found` si l'id n'existe pas.

**Règles métier** (validées dans `BookService` à la création et à la mise à jour) :
- Le nom ne peut être ni nul ni vide.
- L'ISBN ne peut être ni nul ni vide et doit être un ISBN-13 valide (13 chiffres).
- Le nombre de pages doit être supérieur à 0.
- L'année de publication ne peut pas être postérieure à l'année courante.
- L'ISBN doit être unique : il ne peut pas être déjà utilisé par un autre livre.

En cas de violation, une `BookValidationException` est levée et renvoie un `400 Bad Request`.

## Gestion d'erreurs

`GlobalExceptionHandler` centralise le traitement des exceptions et renvoie des réponses au format [`ProblemDetail`](https://datatracker.ietf.org/doc/html/rfc7807) :

| Exception | Code HTTP |
|---|---|
| `BookValidationException` | `400 Bad Request` |
| `BadRequestException` | `400 Bad Request` |
| `BookNotFoundException` | `404 Not Found` |
| `Exception` (fallback) | `500 Internal Server Error` |

## Configuration

La configuration se trouve dans `src/main/resources/application.properties.dist` (modèle). Copiez-le en `application.properties` et renseignez vos identifiants :

```bash
cp src/main/resources/application.properties.dist src/main/resources/application.properties
```

Paramètres principaux :
- `spring.datasource.url` — URL de la base MySQL (port 3306 par défaut)
- `spring.datasource.username` / `spring.datasource.password`
- `spring.jpa.hibernate.ddl-auto=update` — création/mise à jour automatique des tables
- `server.port=8080`

> Pensez à démarrer MySQL au préalable (ex. `net start mysql80`).

## Démarrage

Prérequis : **JDK 21** et une instance **MySQL** accessible.

```bash
# Lancer l'application
./mvnw spring-boot:run

# Construire le livrable (war)
./mvnw clean package
```

Sous Windows :
```powershell
.\mvnw.cmd spring-boot:run
```

L'API est ensuite disponible sur `http://localhost:8080/book`.

## Notions illustrées

Ce projet « basics » sert de support pédagogique et couvre :
- la création d'un `@RestController` avec JPA ;
- la mise en place d'un CRUD complet (création, lecture, mise à jour, suppression) ;
- la séparation en couches (arborescence + service) ;
- l'utilisation de DTO d'entrée/sortie ;
- la gestion d'erreurs centralisée ;
- les codes de retour HTTP et la validation des données.
