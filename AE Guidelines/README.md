# AE Guidelines

Repo-specific guidance for the next AI agent working in this Spring Boot backend.

## Purpose

This project is a simple Java Spring Boot backend for recipe APIs. Future changes should preserve the existing shape of the repo and avoid adding unnecessary layers.

## Current Project Shape

Main source folders under `src/main/java/com/example/demo/`:

- `config/`
- `controller/`
- `entity/`
- `repository/`
- `service/`

Main resource files:

- `src/main/resources/application.properties`
- `src/main/resources/schema.sql`
- `src/main/resources/data.sql`

Test folders:

- `src/test/java/com/example/demo/controller/`
- `src/test/java/com/example/demo/service/`
- `src/test/resources/`

## Important Repo-Specific Rules

- Keep the backend simple and close to the current sample/demo structure.
- Do not add new top-level backend folders like `dto/`, `exception/`, `mapper/`, or nested feature-package trees unless the user explicitly requests them.
- Add controllers, services, repositories, and entities only in the existing folders.
- Prefer direct entity usage over introducing DTOs unless there is a clear requirement.
- Use straightforward `ResponseEntity` handling in controllers, matching the current style.
- Put schema and seed data in `src/main/resources`, not in `data/` and not in `target/`.
- Treat `target/` and `data/` as generated/runtime output.

## Current Recipe API

The current recipe endpoints are:

- `GET /api/recipes`
- `GET /api/recipes/{id}`
- `POST /api/recipes/createRecipe`
- `PATCH /api/recipes/patchRecipe/{id}`
- `DELETE /api/recipes/deleteRecipe/{id}`

## Local Dev Commands

Compile:

`mvn clean compile`

Run app:

`mvn spring-boot:run`

Run tests:

`mvn test`

Wrapper equivalents are also supported with `./mvnw`.

## Files in This Folder

- `architecture.md`
- `coding-standards.md`
- `development-guide.md`
- `testing-practices.md`
