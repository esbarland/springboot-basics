# Library — Spring Boot Basics

Projet d'apprentissage des fondamentaux de **Spring Boot** : une petite API REST de gestion de livres (« library »), construite progressivement pour mettre en pratique les concepts clés du framework.

## Aperçu

L'application expose une API REST permettant de créer des livres et de les persister en base de données MySQL via JPA/Hibernate. Le code illustre une architecture en couches (controller → service → repository), l'utilisation de DTO, la validation des données et une gestion d'erreurs centralisée.

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
    │       └── BookCreationException
    └── dto
        └── BookDTO                # DTO d'entrée/sortie (PostInput / PostOutput)
```

## API

Base path : `/book`

### `POST /book`
Crée un nouveau livre.

**Corps de la requête** (`BookDTO.PostInput`) :
```json
{
  "name": "Le Petit Prince",
  "pages": 96
}
```
- `name` : obligatoire, non vide (`@NotNull`, `@NotBlank`)
- `pages` : obligatoire (`@NotNull`)

**Réponse** — `201 Created` (`BookDTO.PostOutput`) :
```json
{
  "id": 1,
  "name": "Le Petit Prince",
  "pages": 96
}
```

**Règles métier** (validées dans `BookService`) :
- Le nom ne peut être ni nul ni vide.
- Le nombre de pages doit être supérieur à 0.
- Un livre identique (même nom **et** même nombre de pages) ne peut pas être créé en double.

En cas de violation, une `BookCreationException` est levée et renvoie un `400 Bad Request`.

### `GET /book`
Endpoint de démonstration. Attend les paramètres `name` et `pages`, renvoie `200 OK`.

## Gestion d'erreurs

`GlobalExceptionHandler` centralise le traitement des exceptions et renvoie des réponses au format [`ProblemDetail`](https://datatracker.ietf.org/doc/html/rfc7807) :

| Exception | Code HTTP |
|---|---|
| `BookCreationException` | `400 Bad Request` |
| `BadRequestException` | `400 Bad Request` |
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
- la séparation en couches (arborescence + service) ;
- l'utilisation d'un DTO pour l'ajout d'un livre ;
- la gestion d'erreurs centralisée ;
- les codes de retour HTTP et la validation des données.
