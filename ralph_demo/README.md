# Ralph Demo

A demonstration project showing the "ralph loop" pattern for incremental enum-to-database refactoring.

## Prerequisites

- Java 17+
- Gradle (or use included wrapper)
- [gemini CLI](https://github.com/google/generative-ai-cli) installed and configured

## Setup

```bash
cd ralph_demo
./gradlew build
```

## Running the Demo

1. **Review the starting state:**
   ```bash
   cat prd.json          # See user stories
   cat progress.txt      # See context
   ./gradlew test        # Verify tests pass
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
├── build.gradle       # Gradle build
└── src/
    ├── main/java/com/demo/
    │   ├── DeploymentTarget.java   # Enum to refactor
    │   ├── Feature.java            # Feature class
    │   └── FeatureService.java     # Service layer
    └── test/java/com/demo/
        ├── FeatureTest.java
        └── FeatureServiceTest.java
```

## What to Expect

The agent will:
1. Create a `Target` data class to replace the enum
2. Create a `TargetRepository` interface
3. Add conversion methods between enum and class
4. Update `Feature` and `FeatureService` to use both
5. Mark the enum as deprecated

All while keeping existing tests passing!

## Troubleshooting

**gemini not found:** Install the gemini CLI and ensure it's in your PATH.

**Tests fail:** Check `./gradlew test --info` for details.

**Agent stuck:** Check `prd.json` for the current story and `progress.txt` for context.
