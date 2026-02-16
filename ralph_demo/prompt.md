# Agent Instructions

You are implementing user stories for a Java enum migration project.
The goal is to migrate the `LegacyTargetCode` enum in `com.demo.obfuscated` to a
string-based `TargetKey` data class, incrementally and without breaking existing tests.
This is a single-project demo. Execute from `ralph_demo`, but migrate code at repo root `src/**`.

## Workflow

1. **Setup**: If not already on a feature branch, create one: `git checkout -b ralph-migration-$(date +%Y%m%d%H%M)`
2. **Read** `prd.json` to see all user stories and their status
3. **Read** `progress.txt` for codebase patterns and context
4. **Pick** the highest priority story where `passes: false` (if tied, pick the lowest story ID)
5. **Implement** the story following acceptance criteria
6. **Run** `../gradlew test` to verify all tests pass
7. **Commit** with format: `feat: US-XXX - Title`
8. **Update** `prd.json` to set `passes: true` only after acceptance criteria are met and tests pass
9. **Append** any learnings to `progress.txt`
10. **Repeat** until all stories pass

## Completion

When ALL stories have `passes: true`:
1. **Push** the feature branch to remote.
2. **Output** exactly:
```
<promise>COMPLETE</promise>
```

## Rules

- Never break existing tests
- Add new tests for new functionality
- Follow existing code patterns in `com.demo.obfuscated`
- Keep backward compatibility until final deprecation story (US-009)
- Complete one story at a time; do not mark multiple stories as passed in one iteration
- All new code goes in the `com.demo.obfuscated` package
- Run commands from `ralph_demo` only
- Do not modify files outside:
  - `../src/main/java/com/demo/obfuscated/`
  - `../src/test/java/com/demo/obfuscated/`
  - `prd.json`
  - `progress.txt`
  - `prompt.md`
