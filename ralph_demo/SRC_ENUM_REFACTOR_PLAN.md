# Source-Driven Enum Refactor Plan

## Objective
Refactor `LegacyTargetCode` usage in `src/main/java/com/demo/obfuscated/` to a string-based `TargetKey` model, in incremental stories that can be represented in `prd.json` and executed by the Ralph loop.

## Current Enum Usage (from `src`)
- Core enum definition: `src/main/java/com/demo/obfuscated/LegacyTargetCode.java`
- Strategy API and implementations:
  - `RuleStrategy.java`
  - `BaseRuleStrategy.java`
  - `ThresholdRuleStrategy.java`
  - `ExpressionRuleStrategy.java`
  - `RolloutRuleStrategy.java`
- Builder flow:
  - `RuleBuilder.java`
  - `RuleBuilderImpl.java`
- Serialization flow:
  - `RuleStrategySerializer.java`
  - `RuleStrategyDeserializer.java`
- Business/service integrations:
  - `RuleValidationService.java`
  - `WebhookHandler.java`
  - `FeatureToggleAdapter.java`
  - `TenantConfigLoader.java`
  - `TargetHandler.java`

## Key Constraints Observed
- Many code paths compare enum identity (`==`) and call `target.name()`.
- Existing tests assert enum-based behavior and wire format.
- No dedicated `TargetHandler` test currently exists; migration needs added regression coverage for that class.

## Refactor Stories (ready to map into `prd.json`)

### US-001: Introduce `TargetKey` value object
Files:
- Add `src/main/java/com/demo/obfuscated/TargetKey.java`
- Add `src/test/java/com/demo/obfuscated/TargetKeyTest.java`

Acceptance focus:
- Fields: `value`, `account`, `project`, `environmentId`, `tenant`
- Equality/hash behavior and `getValueWithProject()`
- No production code path changes yet

### US-002: Add target repository abstraction
Files:
- Add `TargetRepository.java`
- Add `InMemoryTargetRepository.java`
- Add `InMemoryTargetRepositoryTest.java`

Acceptance focus:
- Seed from `LegacyTargetCode.values()`
- Deterministic lookups for value and account/project/value shapes
- Unknown handling behavior is defined and tested

### US-003: Canonical conversion contract
Files:
- Update `LegacyTargetCode.java`
- Add `TargetConverter.java`
- Add/extend tests in `LegacyTargetCodeTest.java` and/or `TargetConverterTest.java`

Acceptance focus:
- `toTargetKey()` and reverse mapping support
- Explicit exception behavior for unknown/unmapped conversions
- Full enum round-trip coverage

### US-004: Strategy interface migration seam
Files:
- Update `RuleStrategy.java`
- Update `BaseRuleStrategy.java`
- Update `ThresholdRuleStrategy.java`
- Update `ExpressionRuleStrategy.java`
- Update `RolloutRuleStrategy.java`
- Update related strategy tests

Acceptance focus:
- Add `getTargetKey()` / `getTargetValue()` seam
- Keep existing enum constructors/callers operational
- New `TargetKey` constructor path covered by tests

### US-005: Builder migration seam
Files:
- Update `RuleBuilder.java`
- Update `RuleBuilderImpl.java`
- Add/update builder tests

Acceptance focus:
- Add `withTargetKey(TargetKey)`
- Existing `withTarget(LegacyTargetCode)` behavior unchanged
- Both build paths validated

### US-006: Serializer/deserializer compatibility
Files:
- Update `RuleStrategySerializer.java`
- Update `RuleStrategyDeserializer.java`
- Update `RuleStrategySerializerTest.java`

Acceptance focus:
- Support legacy payload (`target=ENUM_NAME`)
- Support new payload fields (`value`, `tenant`, optional `account/project/environmentId`)
- Deserialize both old and new formats without regression

### US-007: Validation and webhook migration
Files:
- Update `RuleValidationService.java`
- Update `WebhookHandler.java`
- Update `RuleValidationServiceTest.java`
- Update `WebhookHandlerTest.java`

Acceptance focus:
- Replace enum identity dependencies with value-based behavior
- Preserve production safeguards and endpoint routing behavior
- Add overloads for `TargetKey` where needed

### US-008: Adapter, tenant loader, and handler migration
Files:
- Update `FeatureToggleAdapter.java`
- Update `TenantConfigLoader.java`
- Update `TargetHandler.java`
- Update `FeatureToggleAdapterTest.java`
- Update `TenantConfigLoaderTest.java`
- Add `TargetHandlerTest.java`

Acceptance focus:
- `TargetKey`-based lookup/evaluation paths
- Backward compatibility for legacy callers
- New `TargetHandler` regression coverage added

### US-009: Deprecate enum API surface
Files:
- Update `LegacyTargetCode.java`
- Update enum-accepting signatures across affected classes as deprecated

Acceptance focus:
- `@Deprecated` + migration guidance in Javadoc
- No new non-deprecated call sites introduced
- Full suite remains green

## Artifact Mapping for Ralph Demo
- `prd.json`
  - Keep these 9 stories in dependency order.
  - Ensure each story includes at least one test criterion and one compatibility criterion where relevant.
- `progress.txt`
  - After each story: append learnings, edge cases, and any compatibility decisions.
  - Track checkpoint status: Foundation -> API Adoption -> Compatibility -> Deprecation.
- `prompt.md`
  - Keep one-story-per-iteration rule.
  - Keep test gate (`../gradlew test`) before setting `passes: true`.
  - Keep deterministic selection (highest priority, lowest ID tie-breaker).

## Execution Order and Gates
1. Complete US-001..US-003, verify conversion primitives are stable.
2. Complete US-004..US-005, verify API seams without breaking old callers.
3. Complete US-006..US-008, verify system integration and payload compatibility.
4. Complete US-009, finalize deprecation.

Gate for every story:
- local tests pass
- acceptance criteria met
- only then set `passes: true` in `prd.json`
