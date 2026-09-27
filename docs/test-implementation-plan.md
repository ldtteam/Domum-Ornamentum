# Testing Plan — Domum Ornamentum (NeoForge 26.1)

Status: **Phase 1 in progress** — P0 codec round-trip unit tests implemented and green under `testJunit` (see §4.1 notes); migration-function test, enum↔registry checks, recipe-namespace check, and all game tests still pending.
Purpose: what to test, how to structure the test infrastructure, and where it plugs into the build.
References researched against: NeoForge docs (`docs.neoforged.net/docs/misc/gametest`, 26.1 revision), the `neoforged/NeoForge` repo branch `26.1.x` (modules `tests/` and `testframework/`).

---

## 1. Two testing tiers

| Tier | Runs in | Boot cost | Guards |
|---|---|---|---|
| **JVM unit tests** (`src/junit`) | plain JVM, JUnit 5 | none (or lightweight FML registry bootstrap) | codecs/serialization, enum↔registry consistency, pure logic, migration functions |
| **Game tests** (`src/gametest`, a separate mod) | dedicated server with real world & ticking | full server launch | block placement/state behavior, machine flow, item-in-world behavior, tag membership at runtime |

Minecraft datagen (already in place: `runClientData` / `runServerData`) stays orthogonal; it is not a test tier here but can be cross-checked by CI.

---

## 2. The 26.1 game-test architecture (vanilla layer)

The vanilla framework was reworked for 26.1. Tests are **datapack registry objects**, not just `@GameTest`-annotated methods:

- **Test instances** — `Registries.TEST_INSTANCE`, JSON at `data/<ns>/test_instance/<name>.json`. Two built-in kinds:
  - `minecraft:function` → runs a registered test *function* (`Consumer<GameTestHelper>`, registered in `BuiltInRegistries.TEST_FUNCTION`).
  - `minecraft:block_based` → redstone signal chain through vanilla `TestBlock`s (START / LOG / FAIL / ACCEPT) placed in the structure.
- **Environments** — `Registries.TEST_ENVIRONMENT`, JSON at `data/<ns>/test_environment/...`. Types: `game_rules`, `clock_time` (WorldClock), `timeline_attributes` (day/moon), `weather`, `function` (setup/teardown mcfunctions), `all_of` composite, plus custom types registered as `MapCodec`s in `TEST_ENVIRONMENT_DEFINITION_TYPE`.
- **Structures** — `.nbt` templates at `data/<ns>/structure/...`.
- **Code-only registration** — mod-bus event `RegisterGameTestsEvent`: `event.registerEnvironment(...)` / `event.registerTest(...)` for fully in-code tests (no JSON).
- **Running**: `/test run <name>`, `runall`, `runclosest`, `runkfailed`; a *game test server* run configuration exits with the number of failed *required* tests → CI-friendly. Other runs can opt in via property `neoforge.enableGameTest=true` (useful for manual `/test` in dev client).
- **Helper API** (`GameTestHelper`): success primitives `succeed`, `succeedIf` (immediate), `succeedWhen` (every tick until timeout), `succeedOnTick`; scheduling `runAtTickTime` / `runAfterDelay` / `onEachTick`; assertions throw `GameTestAssertException`; relative↔absolute position conversion tied to the structure block; `makeMockPlayer(...)`.

### NeoForge testframework (`net.neoforged:testframework`)

A separate mod (modid `testframework`, published from the `net.neoforged` group, version = NeoForge version) that layers an annotation/JUnit-friendly API on top of the vanilla layer:

- Class-level `@ForEachTest(groups = "...")`; method-level `@GameTest(timeoutTicks=..., template=..., required=...)` + `@TestHolder(description=..., enabledByDefault=..., side=...)`.
- Test methods have signature `static void test(DynamicTest test, RegistrationHelper reg)`:
  - **`RegistrationHelper`** — registers *per-test scoped* blocks/items (`reg.blocks().register(...)`) that never pollute the production mod; ideal for disposable scaffolding blocks in a scene.
  - **`test.registerGameTestTemplate(StructureTemplateBuilder...)`** — builds structure templates **programmatically**; no hand-authored `.nbt` files needed.
  - **`test.onGameTest(helper -> helper.startSequence().thenExecute(...).thenWaitUntil(...).thenSucceed())`** — a sequence builder over `ExtendedGameTestHelper`.
- Tests are exposed as JUnit dynamic tests; the framework can dump a Markdown summary (`DefaultMarkdownFileSummaryDumper`) and provides client-side screens/overlay for manual runs.

Example shape (from NeoForge's own debug tests, 26.1.x):

```java
@ForEachTest(groups = "domum_ornamentum.doors")
public class DoorTests {
    @GameTest
    @TestHolder(description = "Doors open when interacted with and auto-close")
    static void doorOpenClose(final DynamicTest test, final RegistrationHelper reg) {
        test.onGameTest(helper -> helper.startSequence()
            .thenExecute(() -> /* place block via template/absolutePos */)
            .thenExecuteAfter(1, () -> helper.useBlock(...))
            .thenSucceedIf(() -> /* assert state */));
    }
}
```

---

## 3. Infrastructure plan for this repo

### 3.1 Source-set layout (mirrors the NeoForge repo)

```
src/main        → production mod (unchanged)
src/junit       → new source set: JVM unit tests (+ its own META-INF/neoforge.mods.toml only if it needs @EventBusSubscriber/mixins — codec round-trips do not)
src/gametest    → new source set: a separate *test mod* (e.g. modId `domum_ornamentum_tests`)
                  with META-INF/neoforge.mods.toml declaring required deps on
                  domum_ornamentum + testframework; never included in the published jar
```

### 3.2 Dependencies (what must be wired — Tableau/NeoGradle side, pending)

`src/junit`:
- `platform('org.junit:junit-bom:<ver>')`, `junit-jupiter`, `junit-jupiter-params`, `runtimeOnly junit-platform-launcher`
- `org.assertj:assertj-core`
- optional: `net.neoforged.fancymodloader:junit-fml:<fancy_mod_loader_version>` — JUnit extension giving registry access without a full game launch (needed only for tests that touch mod registries; pure codec tests don't need it)

`src/gametest`:
- the project itself (`implementation(project())`)
- **`net.neoforged:testframework:<neoforge.version>`** ← the artifact reference you flagged; group is `net.neoforged`, version tracks the NeoForge build (e.g. `26.1.0.x`). It loads as a plain mod into the game-test server.

### 3.3 Tasks & run configurations

- **`junitTest`** task, mirroring `tests/build.gradle`:
  `useJUnitPlatform()`, `classpath = sourceSets.junit.output + sourceSets.junit.runtimeClasspath`, `testClassesDirs = output.classesDirs`.
- **Game-test server run configuration** (NeoDev/MDG-style type `gameTestServer`; Gradle task e.g. `runGameTestServer`): boots a dedicated server with `domum_ornamentum` + the test mod + `testframework` on the classpath; **exit code = number of failed required tests** → CI gate.
- Dev convenience: add `property 'neoforge.enableGameTest', 'true'` to the client run configuration so `/test` is available in dev worlds while iterating (not needed for CI).

### 3.4 What NeoGradle/Tableau must provide (for your implementation)

1. A way to declare the extra source sets and their dependency configurations (`junitImplementation`, `gametestImplementation`) — or at minimum a hook to add them from `build.gradle`.
2. The `runGameTestServer` run configuration type (the vanilla/NeoDev one; it already exists in NeoDev as task type `gameTestServer`).
3. Publication exclusion of the test source sets (test mod must not ship in the LDTTeam publish).

### 3.5 CI (later phase)

- Required checks: `junitTest`, `runGameTestServer`.
- Testframework Markdown summary → PR artifact/comment.
- Static datagen guards (cheap script steps): generated tree contains no files under namespace `domumornamentum` (no underscore); two consecutive datagen runs produce identical output (idempotency).

---

## 4. What to test — inventory

Priority: **P0** = regression guard for the 26.1 port (classes of bugs we just hit), **P1** = core gameplay behavior, **P2** = extended coverage.

### 4.1 JVM unit tests (`src/junit`)

| Test | Type | Priority | Notes |
|---|---|---|---|
| `RangeSelectItemModelProperty` codec round-trips: `domum_ornamentum:panel_type`, `trapdoor_type`, `fancy_trapdoor_type`, `post_type` — encode/decode every enum value through `JsonOps` and `NbtOps` | unit (no boot) | **P0** | Direct guard against the "Element with unknown id" failure class. Pattern: NeoForge `ExtraCodecsTests`. **Implemented** (`junit/datagen/RangeSelectItemModelPropertyTest`). |
| `MaterialTextureData` data-component codec round-trip (`texture_data`, Json + NBT + stream codec) | unit (no boot) | P0 | Guards the custom component used by all materially-textured models. **Implemented** (`junit/component/MaterialTextureDataCodecTest`). |

### 4.1a Implementation notes — Phase 1 (2026-09-24)

Environment facts learned while landing the two suites above (all verified under `./gradlew testJunit`):

- **FML auto-boots in unit tests.** The transitive `junit-fml` dependency ships a JUnit launcher session listener that starts FML as a *dedicated server* (`ServerModLoader.load(false)`). Vanilla + mod registries are fully populated (1266 blocks, incl. `domum_ornamentum:panel`) — no separate bootstrap needed.
- **Client events do not fire** under dedicated boot, so the client-side `RangeSelectItemModelProperty` registry is empty in tests; reference properties via their `INSTANCE`/`CODEC` constants instead.
- **26.1 API gotchas:**
  - `NbtOps` lives at `net.minecraft.nbt.NbtOps` (not `com.mojang.serialization`).
  - `JsonOps.encodeStart(...)` yields `DataResult<JsonElement>` — cast to `JsonObject` after `getOrThrow()`.
  - `CompoundTag.getString(key)` returns `Optional<String>`.
  - Stream-codec round-trips need a `RegistryAccess`: `RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY)`. `RegistryFriendlyByteBuf` wraps a Netty buffer — snapshot encoded bytes via the underlying `io.netty.buffer.ByteBuf`, not through the Friendly wrapper.
  - Item holders' default components are **not bound** in a headless boot: `new ItemStack(item)` throws NPE ("Components not bound yet") from `Holder.Reference#components()`. Fix used in tests: if `!holder.areComponentsBound()` → `holder.bindComponents(DataComponentMap.EMPTY)` before constructing the stack.
- The old one-off `TrapdoorTypePropertyTest` and a bootstrap probe test were removed — their coverage is subsumed by the parameterized suite.
| Legacy item-component migration (`SelfUpgradingBlockItem.upgrade`): old layout (`Type`/`TextureData` under `CustomData`) → new layout (`BLOCK_STATE` properties / block-entity tag) | unit (needs refactoring first — see below) | P0/P1 | **Open porting issue**: `upgrade()` currently requires a live `MinecraftServer` and there is no call-site, since `Item#verifyComponentsAfterLoad` was removed in 26.1 (`TODO(26.1)` marker in the file). Extract a pure `migrate(NbtOps.Dynamic...)` function to make it testable; decide + wire the new hook (component codec migration or first-use) as its own task — tests pin whichever behavior is chosen. |
| Enum↔registry consistency: every `BrickType`, `ExtraBlockType`, `FancyDoorType`, `TrapdoorType`, `PillarShapeType`, `ShingleHeightType` value has a registered block, and the tag input lists (`getBricks()`, `getExtraTopBlocks()`) match the enum sets | unit (needs registries — junit-fml) | P1 | Catches future "added an enum constant but forgot the block/tag" drift. |
| Recipe namespace check: every generated recipe ID is under `domum_ornamentum` | CI script or unit over generated output | P0 | Regression for the hardcoded-namespace bug fixed in this session. |

### 4.2 Game tests (`src/gametest`)

Test *functions* registered via testframework; templates built programmatically with `StructureTemplateBuilder`; disposable scaffolding blocks via `RegistrationHelper`. Suggested groups:

**`domum_ornamentum.doors` (P1)**
- Place door/fancy door → interact with mock player → asserts `OPEN`, `FACING`, `HINGE` states; tick until auto-close.
- Fancy door item carries its type component → placed block state has matching `TYPE`.
- Double-door / hinge-side behavior for fancy doors (both halves, correct orientation).

**`domum_ornamentum.trapdoors_panels` (P1)**
- Trapdoor open/close toggle; wall attachment requirement.
- Panel: item type component → placed panel state matches selected `TrapdoorType`.

**`domum_ornamentum.fences_gates` (P1)**
- Fence gate opens on interaction, stays closed without it; `IN_WALL` property flips when adjacent fence present.
- Rotation coverage: all 4 horizontal facings place correctly — direct regression guard for the `FACING` vs `HORIZONTAL_FACING` port bugs fixed in this session.

**`domum_ornamentum.stairs_shingles_slabs_walls` (P1)**
- Stair/shingle placement adjacency & connectivity rules (mod stairs vs vanilla neighbors).
- Half slab + half slab = full; slab orientation on placement.
- Wall post/cap transition logic at corners/adjacency changes.

**`domum_ornamentum.misc_blocks` (P2)**
- Floating carpet: places with no support block, survives ticking (its defining behavior).
- Barrel standing/laying orientation states; pillar/post facing from item component.

**`domum_ornamentum.machine.architects_cutter` (P1)**
- Place cutter → assert block entity present + correct `MenuType`.
- Feed a known input recipe, tick N times, assert output item produced and container contents on break.
- This is the only machine in the mod — highest value per test-hour after doors.

**`domum_ornamentum.tags` (P0-lite)**
- Runtime tag membership: all brick blocks ∈ `#domum_ornamentum:bricks`; extra blocks ∈ `#...:extra_block`; both ⊆ `#...:default`. Regression guard for the consolidated `ModBlockTagsProvider`.

**`domum_ornamentum.items.self_upgrading` (P2, blocked)**
- In-world migration of old-format item NBT — only after the 26.1 load hook is decided/wired (§4.1 row 3).

### 4.3 Out of scope for automated tests (manual / visual)

- JEI category rendering (`ArchitectsCutterCategory`), creative-tab contents, model/blockstate JSON *rendering* correctness → dev-client walkthrough + datagen review.
- Optional later: a JVM test that parses generated model/blockstate JSON and validates references resolve (cheap, no boot).

---

## 5. Phasing

1. **Phase 1 — infra + P0 unit tests.** New `src/junit` source set, JUnit/AssertJ deps, `junitTest` task (needs the NeoGradle/Tableau hooks from §3.4). Land codec round-trips + migration-function test immediately; these need no game boot at all.
2. **Phase 2 — game-test scaffolding.** `src/gametest` test mod + `testframework` dependency, `runGameTestServer` run config, first groups (`doors`, `trapdoors_panels`, `tags`) using programmatic templates. Establish the CI gate on required tests.
3. **Phase 3 — remaining behavior + machine.** Gates/stairs/walls/misc groups, ArchitectsCutter flow test, Markdown summary in PRs, datagen idempotency check, and unblocking `items.self_upgrading` once the component-migration hook is decided.

---

## 6. Key references

- NeoForge GameTest docs (26.1): https://docs.neoforged.net/docs/misc/gametest
- NeoForge test repo, branch `26.1.x`: https://github.com/neoforged/NeoForge/tree/26.1.x/tests — `tests/build.gradle` (source sets & task wiring), `src/junit/java/net/neoforged/neoforge/unittest/*` (e.g. `ExtraCodecsTests` for the codec round-trip pattern), `src/main/java/net/neoforged/neoforge/debug/block/BlockPropertyTests.java` (annotation-driven game test example).
- Framework module: https://github.com/neoforged/NeoForge/tree/26.1.x/testframework — artifact coordinates **`net.neoforged:testframework:<neoforge.version>`**; modid `testframework`; core APIs `DynamicTest`, `RegistrationHelper`, `StructureTemplateBuilder`, annotations under `net.neoforged.testframework.annotation`.
