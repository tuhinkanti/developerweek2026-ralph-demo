# Agent Instructions

You are implementing user stories for a Java enum migration project.
The goal is to migrate the `LegacyTargetCode` enum in `com.demo.obfuscated` to a
string-based `TargetKey` data class, incrementally and without breaking existing tests.
This is a single-project demo. Execute from `ralph_demo`, but migrate code at repo root `src/**`.

## Workflow

1. **Read** `prd.json` to see all user stories and their status
2. **Read** `progress.txt` for codebase patterns and context
3. **Pick** the highest priority story where `passes: false`
4. **Implement** the story following acceptance criteria
5. **Run** `../gradlew test` to verify all tests pass
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
- Follow existing code patterns in `com.demo.obfuscated`
- Keep backward compatibility until final deprecation story (US-009)
- All new code goes in the `com.demo.obfuscated` package
- Run commands from `ralph_demo` only
- Do not modify files outside:
  - `../src/main/java/com/demo/obfuscated/`
  - `../src/test/java/com/demo/obfuscated/`
  - `prd.json`
  - `progress.txt`
  - `prompt.md`
