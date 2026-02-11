# Ralph Tech Talk - Slide Notes

## Slide 1: Title
**Ralph: Running Coding Agents in a Bash Loop**

*Speaker notes:*
- Introduce yourself
- "Today I'll show you how I migrated a legacy enum codebase in hours instead of weeks using a technique called Ralph"
- Credit Geoffrey Huntley: https://ghuntley.com/ralph/
- "This demo uses root `src` as the app codebase and `ralph_demo` as the Ralph control folder"

---

## Slide 2: The Problem

**Long-running refactors are painful**

- Hard to scope --> scope creep
- Hard to review --> massive PRs
- Easy to break --> regressions
- Developer fatigue --> context switching

*Speaker notes:*
- "We've all been there -- a 'simple' enum-to-data-class migration turns into a month-long project"
- "You touch one file, break three others, spend half your time just keeping tests green"

---

## Slide 3: What is Ralph?

**Ralph = Agent + Loop + State + Feedback**

```
while not done:
    read state (PRD + progress)
    pick next failing story
    implement changes
    run tests
    commit + update state
```

*Speaker notes:*
- Named after Ralph Wiggum (The Simpsons) -- the agent just keeps going, checking its work
- Key insight: agents can run indefinitely if they have clear goals and feedback
- The loop is the breakthrough -- not the agent itself

---

## Slide 4: The Four Artifacts

| File | Purpose |
|------|---------|
| `prd.json` | User stories with pass/fail status |
| `progress.txt` | Context log for cross-iteration memory |
| `prompt.md` | Agent instructions + completion contract |
| `ralph.sh` | The loop script |

*Speaker notes:*
- These four files are all you need
- PRD is the "todo list" -- agent knows exactly what to work on
- Progress file prevents the agent from losing context between iterations
- Prompt defines the workflow and exit condition

---

## Slide 5: prd.json Deep Dive

```json
{
  "id": "US-001",
  "title": "Create TargetKey data class",
  "priority": 1,
  "passes": false,
  "acceptance_criteria": [
    "Create TargetKey class with fields: value, account, project, environmentId, tenant",
    "Include equals/hashCode based on all fields",
    "Include getValueWithProject()",
    "Add unit tests"
  ]
}
```

*Speaker notes:*
- Each story is small and testable
- `passes: false` --> agent picks it up
- `passes: true` --> agent moves on
- Acceptance criteria are specific enough for the agent to verify

---

## Slide 6: The Legacy Codebase

**LegacyTargetCode enum:** 12 values, two constructor shapes
**Usage:** 14+ files -- strategies, builder, serializer, validators, adapters

```java
public enum LegacyTargetCode {
    CORE_PROD("prod", "tenant-main"),
    SUITE_ALPHA_PROD("suite-alpha", "default", "prod", "env-100", "tenant-suite-a"),
    // ... 10 more values
}
```

*Speaker notes:*
- This is a scaled-down but structurally identical version of a real legacy codebase
- The enum is used everywhere: comparisons, serialization, routing, validation
- Migrating manually would mean touching every file and keeping tests green throughout

---

## Slide 7: ralph.sh Deep Dive

```bash
for i in $(seq 1 $MAX_ITERATIONS); do
    OUTPUT=$(cat prompt.md | gemini --yolo 2>&1)
    echo "$OUTPUT"

    if echo "$OUTPUT" | grep -q "<promise>COMPLETE</promise>"; then
        echo "All stories complete!"
        exit 0
    fi
    sleep 2
done
```

*Speaker notes:*
- Dead simple: pipe prompt to agent, check for completion signal
- `--yolo` flag lets agent execute without confirmation (use carefully!)
- Max iterations is a safety net
- Sleep prevents rate limiting

---

## Slide 8: Live Demo

**Starting state:**
```bash
cd ralph_demo
../gradlew test       # Green baseline (tests at repo root)
cat prd.json          # 9 stories, all passes: false
```

**Run the loop:**
```bash
./ralph.sh
```

*Speaker notes:*
- Show the terminal
- Point out: agent reads PRD, picks US-001, implements TargetKey, tests, commits
- Open the diff after first iteration
- Show `prd.json` updated to `passes: true`
- Show `progress.txt` with new learnings

---

## Slide 9: What the Agent Produces

**Code changes:**
- `TargetKey.java` - new data class
- `TargetRepository.java` + `InMemoryTargetRepository.java` - new repository
- `LegacyTargetCode.java` - conversion methods added, then deprecated
- `RuleStrategy.java` + impls - dual support for enum and data class
- `RuleBuilder.java` - new TargetKey-accepting methods
- `RuleStrategySerializer/Deserializer` - backward-compatible format
- Validation, webhook, adapter, config files - migrated to string-based

**State updates:**
- `prd.json` - stories flipped to `passes: true`
- `progress.txt` - learnings appended

**Git history:**
- Clean, atomic commits per story

*Speaker notes:*
- Each commit is reviewable
- If something breaks, you know exactly which story caused it
- Progress log captures patterns the agent discovered

---

## Slide 10: Production Tips

### Story Size
- **Too big** --> agent gets lost, tests fail mysteriously
- **Too small** --> overhead, slow progress
- **Just right** --> 1-3 acceptance criteria, completable in one iteration

### Tests as Hard Gate
- Agent MUST run tests before marking done
- If tests fail, agent fixes or moves on (configurable)
- Never merge without green CI

### Preventing Agent Drift
- Explicit state in `prd.json` (not just memory)
- Clear rules in `prompt.md`
- Progress log for context

### When to Intervene
- Agent stuck on same story for 3+ iterations
- Tests keep failing with same error
- Agent starts creating files that don't belong

*Speaker notes:*
- Story size is the #1 thing to get right
- I learned this the hard way -- started with stories that were too big
- The progress log saved me multiple times when the agent needed context from 10 iterations ago

---

## Slide 11: Results

**Before Ralph:**
- Estimated time: 3-4 weeks
- Risk: high (big bang refactor)
- Review burden: massive PR

**After Ralph:**
- Actual time: ~2 hours of agent runtime
- Risk: low (incremental, tested)
- Review burden: 9 small, atomic commits

*Speaker notes:*
- This isn't magic -- I still had to write the stories and review the output
- But the tedious implementation work? Agent handled it
- I spent my time on design and review, not typing

---

## Slide 12: Key Takeaways

1. **Ralph = Loop + State + Feedback**
   - The loop is the breakthrough, not the agent

2. **Decompose into small, testable stories**
   - If you can't test it, the agent can't verify it

3. **State lives in files, not memory**
   - `prd.json` + `progress.txt` = persistent context

4. **Tests are your safety net**
   - Agent self-validates; you review

5. **Start small, iterate**
   - Try it on a contained refactor first

*Speaker notes:*
- You can use this pattern with any agent (Gemini, Claude, GPT, etc.)
- The technique is agent-agnostic
- Start with something low-risk to build confidence

---

## Slide 13: Resources

- **Geoffrey Huntley's Ralph post:** https://ghuntley.com/ralph/
- **This demo repo:** [your repo link]

*Speaker notes:*
- The demo repo is fully runnable -- clone it and try it yourself
- Happy to answer questions

---

## Q&A

*Anticipated questions:*

**Q: What if the agent breaks something?**
A: Tests catch it. If tests pass but behavior is wrong, your tests need improvement.

**Q: How do you handle API rate limits?**
A: Sleep between iterations. Most APIs handle 1 request every 2-5 seconds fine.

**Q: Can this work with other agents?**
A: Yes -- just change the CLI command in `ralph.sh`. The pattern is agent-agnostic.

**Q: What about security? The agent has write access.**
A: Run in a sandboxed environment or use `--dry-run` for demos. Review commits before pushing.

**Q: How do you know when stories are the right size?**
A: If the agent can't complete it in 1-2 iterations, it's too big. Split it.
