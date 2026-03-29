# Coding Standards

## General Style

- Follow standard Java naming conventions.
- Keep classes focused and methods concise.
- Prefer readability over abstraction.
- Match the current repo style before introducing new patterns.

## Repo-Specific Expectations

- Keep controller methods simple and explicit.
- Use `@Autowired` field injection only if staying consistent with the current file pattern; prefer consistency over mixing styles in the same area.
- Use `ResponseEntity` and direct try/catch patterns in controllers, matching the existing `RecipeController` style.
- Keep service validation straightforward with clear `IllegalArgumentException` messages.
- Avoid overengineering with DTOs, exception handler layers, mappers, or feature-specific package hierarchies unless requested.

## Entities

- Keep entities plain and easy to reason about.
- Add only fields the user actually needs.
- Reuse the current converter approach if storing simple list fields in one table.

## Comments and Documentation

- Add Javadoc for public methods when it improves clarity, especially for service methods.
- Avoid noisy comments that restate obvious code.
- Prefer clear naming over explanatory comments.

## Scope Control

- Fix only what the task requires.
- Do not refactor unrelated files just to apply a personal preference.
- Do not introduce large frameworks or patterns when a small change is enough.
