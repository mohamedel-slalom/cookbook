# Testing Practices

## Testing Goals

Keep tests practical and lightweight, but ensure they cover real behavior.

This repo currently uses:

- JUnit 5
- Mockito for unit tests
- Spring Boot integration tests
- H2 in-memory DB for test runs

## Repo-Specific Testing Pattern

### Unit Tests

Put service unit tests under:

- `src/test/java/com/example/demo/service/`

Current example:

- `RecipeServiceTest`

Use unit tests to cover:

- successful creates/updates/deletes
- validation failures
- not-found behavior
- repository interaction expectations where useful

### Integration Tests

Put API integration tests under:

- `src/test/java/com/example/demo/controller/`

Current example:

- `RecipeControllerIntegrationTest`

Use integration tests to cover:

- basic endpoint reachability
- create/read/update/delete flow
- HTTP status codes
- seed data availability where relevant

## Test Configuration

Test-specific properties live in:

- `src/test/resources/application.properties`

These should keep the DB isolated and repeatable.

## Important Guidance

- Keep tests readable and intentionally scoped.
- Prefer a few meaningful tests over lots of brittle ones.
- Avoid adding heavy test frameworks unless a real need appears.
- If a test-only dependency is not already available, first check whether the same coverage can be achieved with the current stack.
- Make schema initialization idempotent so repeated test contexts do not fail.

## Commands

Run tests:

`mvn test`

Quick compile check:

`mvn clean compile`
