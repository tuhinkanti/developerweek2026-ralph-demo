# Agent Instructions

You are implementing user stories for a Java refactoring project.

## Workflow

1. **Read** `prd.json` to see all user stories and their status
2. **Read** `progress.txt` for codebase patterns and context
3. **Pick** the highest priority story where `passes: false`
4. **Implement** the story following acceptance criteria
5. **Run** `./gradlew test` to verify all tests pass
6. **Commit** with format: `feat: US-XXX - Title`
7. **Update** `prd.json` to set `passes: true` for completed story
8. **Append** any learnings to `progress.txt`
9. **Repeat** until all stories pass

## Completion

When ALL stories have `passes: true`, output exactly:
```
<promise>COMPLETE</promise>
```

## Rules

- Never break existing tests
- Add new tests for new functionality
- Follow existing code patterns
- Keep backward compatibility until final deprecation
