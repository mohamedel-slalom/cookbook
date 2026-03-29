# Development Guide

## Core Principles

- Use Spring Boot conventions.
- Keep the design modular and testable.
- Use dependency injection for services and repositories.
- Prefer incremental, low-risk changes.

## Folder Rules

When implementing new backend features, always use the existing folders:

- Controllers: `src/main/java/com/example/demo/controller/`
- Services: `src/main/java/com/example/demo/service/`
- Repositories: `src/main/java/com/example/demo/repository/`
- Entities: `src/main/java/com/example/demo/entity/`
- Config: `src/main/java/com/example/demo/config/`

Do not create extra folders like:

- `dto/`
- `exception/`
- `mapper/`
- nested `feature/...` structures

unless the user explicitly asks for them.

## Resource Rules

Put database source files here:

- `src/main/resources/schema.sql`
- `src/main/resources/data.sql`

Do not treat copied files in `target/classes` as editable source files.

## Local Database Guidance

Normal app runs use a local file-backed H2 database. That creates files under `data/`.

- These files are disposable runtime data.
- They should not be committed.
- Deleting them is safe if local data persistence is not needed.

## When Adding Features

For a typical new API feature:

1. Add or update the entity.
2. Add or update the repository.
3. Add validation/business logic in the service.
4. Add endpoints in the controller.
5. Update `schema.sql` and `data.sql` if needed.
6. Add unit and integration tests.
7. Run compile and test commands.

## Validation Commands

Compile only:

`mvn clean compile`

Run tests:

`mvn test`
