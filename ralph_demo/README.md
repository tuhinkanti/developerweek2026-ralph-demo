# Ralph Demo

This repo is a small, runnable example of the "Ralph" technique coined by Geoffrey Huntley:
https://ghuntley.com/ralph/

Ralph is the practice of running a coding agent in a Bash loop with tight feedback (tests/build) and a lightweight state system (a PRD + progress log). The loop allows the agent to run for as long as needed to finish a task, checking its work as it goes.

In this tech talk, you'll see how this pattern can refactor a legacy codebase -- work that would normally take a developer weeks -- by decomposing the refactor into small, testable stories and letting the agent iterate.

## Purpose

The application code now lives at repository root (`src/main/java` and `src/test/java`).
The `ralph_demo` folder contains only Ralph loop artifacts and talk materials:
`ralph.sh`, `prompt.md`, `prd.json`, `progress.txt`, and slide notes.
Ralph executes from `ralph_demo` but migrates root code story-by-story.

## Takeaways

- **Understand the Ralph loop**: what runs every iteration, and why the loop structure matters.
- **Learn the practical workflow** to run a "Ralph Wiggum" (agent in a loop) against real code.
- **See production-grade guardrails**: how to keep changes incremental, reviewable, and safe.
- **Leave with a reusable repo flow**: a PRD (`prd.json`) + context log (`progress.txt`) + instructions (`prompt.md`) + loop script (`ralph.sh`).

## The Migration Task

The codebase contains a `LegacyTargetCode` enum with 12 values and two constructor shapes:
- Core targets (2-param): value + tenant
- Suite targets (5-param): account + project + value + environmentId + tenant

This enum is used across 14+ files: strategy interfaces, builder, serializer, validation services, webhook handler, toggle adapter, and config loader.

The agent will incrementally migrate from the enum to a string-based `TargetKey` data class while keeping all tests green at every step.

## Prerequisites

- Java 17+
- Gradle (or use included wrapper)
- A coding agent CLI (e.g. gemini, claude, etc.)

## Setup

```bash
cd ralph_demo
../gradlew build
```

All demo commands are executed from `ralph_demo`, and build/test commands target the root project.

## Running the Demo

1. **Review the starting state:**
   ```bash
   cat prd.json          # See user stories (9 stories, all passes: false)
   cat progress.txt      # See codebase context
   ../gradlew test       # Verify tests pass (green baseline)
   ```

2. **Run the ralph loop:**
   ```bash
   chmod +x ralph.sh
   ./ralph.sh            # Default: 10 iterations
   ./ralph.sh 5          # Or specify max iterations
   ```

3. **Watch the agent:**
   - Pick stories from `prd.json`
   - Implement changes
   - Run tests
   - Update progress

## Project Structure

```
ralph_demo/
├── prd.json           # User stories (agent reads/updates)
├── progress.txt       # Context and learnings log
├── prompt.md          # Agent instructions
├── ralph.sh           # Loop script
└── SLIDE_NOTES.md     # Talk notes

src/
├── main/java/com/demo/obfuscated/   # Legacy enum-heavy code to migrate
└── test/java/com/demo/obfuscated/   # Test safety net
```

## What to Expect

The agent will:
1. Create a `TargetKey` data class to replace the enum
2. Create a `TargetRepository` interface + in-memory impl
3. Add conversion methods between enum and class
4. Add string-based getters to the strategy interface
5. Update the builder with TargetKey support
6. Update serialization for backward compatibility
7. Migrate validation, webhook, adapter, and config files
8. Mark the enum as deprecated

All while keeping existing tests passing at every step!

## Troubleshooting

**Agent CLI not found:** Install your agent CLI and ensure it's in your PATH.

**Tests fail:** Check `../gradlew test --info` for details.

**Agent stuck:** Check `prd.json` for the current story and `progress.txt` for context.
