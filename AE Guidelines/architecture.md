# Backend Architecture

## Overview

This repository uses a simple Spring Boot backend architecture.

- Controllers handle HTTP requests and translate service results into HTTP responses.
- Services contain validation and business logic.
- Repositories use Spring Data JPA for persistence.
- Entities map directly to database tables.
- Maven handles build, test, and run workflows.

## Repository-Specific Notes

For this repo, keep the architecture flat and simple.

Use:

- `src/main/java/com/example/demo/controller/`
- `src/main/java/com/example/demo/service/`
- `src/main/java/com/example/demo/repository/`
- `src/main/java/com/example/demo/entity/`
- `src/main/java/com/example/demo/config/`

Avoid creating nested feature packages unless explicitly requested.

## Persistence Model

The app currently uses:

- H2 database
- Spring Data JPA
- SQL initialization via `schema.sql` and `data.sql`
- A file-backed local H2 DB in normal app runs
- An in-memory H2 DB during tests

The current `recipes` schema is intentionally simple: one `recipes` table with list-like values stored as string columns through a converter.

## Runtime Artifacts

These are generated/runtime outputs, not source-of-truth:

- `target/`
- `data/`
- `*.mv.db`
- `surefire-reports`

Source-of-truth for schema/data lives in `src/main/resources`.
