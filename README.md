# Library — Spring Boot Basics

Projet d'apprentissage des fondamentaux de **Spring Boot** : une petite API REST de gestion de livres (CRUD + notation + recherche), persistée en MySQL via JPA/Hibernate, structurée en couches (controller → service → repository).

## Stack technique

| Élément | Détail |
|---|---|
| Langage | Java 21 |
| Framework | Spring Boot 3.4.0 |
| Build | Maven (`war`) |
| Base de données | MySQL (via `mysql-connector-j`) |
| ORM | Spring Data JPA / Hibernate |
| Validation | Spring Boot Starter Validation (Jakarta Bean Validation) |
| Doc API | springdoc-openapi (Swagger UI) |
| Utilitaires | Lombok |
| Serveur | Tomcat embarqué |

## Architecture

```
com.basics.library
├── BasicsApplication              # Point d'entrée Spring Boot
├── GlobalExceptionHandler         # Gestion centralisée des exceptions (@RestControllerAdvice)
├── config
│   └── OpenApiConfig              # Configuration OpenAPI / Swagger
└── book
    ├── controllers
    │   └── BookRestController      # Endpoints REST /book
    ├── services
    │   └── BookService            # Logique métier + règles de validation
    ├── persistence
    │   └── BookRepository         # Accès données (JpaRepository)
    ├── models
    │   ├── BookEntity             # Entité JPA mappée sur la table `book`
    │   ├── BookCategory           # Enum des catégories de livre
    │   ├── BookStatus             # Enum du statut de lecture
    │   └── exception
    │       ├── BookValidationException
    │       ├── BookNotFoundException
    │       └── InvalidRatingException
    └── dto
        └── BookDTO                # DTO d'entrée/sortie (PostInput / PostOutput)
```

## API

Base path : `/book`. La documentation complète et interactive de chaque endpoint (corps de requête, validations, codes de retour) est générée automatiquement via Swagger une fois l'application démarrée :

- Swagger UI : [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)
- Spécification OpenAPI (JSON) : [`http://localhost:8080/v3/api-docs`](http://localhost:8080/v3/api-docs)

| Méthode | URL | Succès |
|---|---|---|
| `GET` | `/book` (param optionnel `?search=`) | `200 OK` |
| `GET` | `/book/{id}` | `200 OK` |
| `POST` | `/book` | `201 Created` |
| `PUT` | `/book/{id}` | `200 OK` |
| `PUT` | `/book/{id}/rating` | `200 OK` |
| `DELETE` | `/book/{id}` | `204 No Content` |
