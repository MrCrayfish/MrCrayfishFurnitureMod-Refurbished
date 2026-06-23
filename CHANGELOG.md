# Changelog

## [1.1.2] - Fix invisible electricity overlay (nodes, connections, wrench link line)

The wrench's in-progress link line (and, as a side effect of the same bug, existing connection lines and electricity node markers) never rendered. `ElectricityRenderer#setupFramePass`'s `PreparedRenderType#drawFromBuffer(vertexBuffer, indexBuffer, indexType, ...)` call had its three trailing int params in the wrong order — `(indexCount, 0, 0)` instead of `(baseVertex=0, firstIndex=0, indexCount)` — which silently submitted a draw call requesting **zero indices**, so nothing in that batch (all electricity overlay geometry shares one draw call) ever reached the screen. Confirmed the correct parameter order via `StagedVertexBuffer.ExecuteInfo`'s record field order: `(vertexBuffer, indexBuffer, indexType, baseVertex, firstIndex, indexCount)`. The separate `renderPowerableArea`/`blitToScreen` draw calls (manual `RenderPass#drawIndexed`/`#draw`, a different code path) already had the correct argument order and were unaffected.

## [1.1.1] - Fix LevelRenderer/LevelExtractor mixin crash on launch

Launch crash: `@Shadow field level was not located in the target class net.minecraft.client.renderer.LevelRenderer`. MC 26.2 split LevelRenderer's entire per-frame extraction phase (the `level` field, `extractLevel`, and `extractBlockOutline`) out into a brand-new `net.minecraft.client.renderer.extract.LevelExtractor` class. `LevelRenderer#renderLevel` was also renamed to `render` (and dropped its `ChunkSectionsToRender` parameter). This affected three mixins:

- `LevelRendererMixin` (common): removed the broken `@Shadow private ClientLevel level` entirely; the electricity-node cleanup that used to run there moved to a new `LevelExtractorMixin` (common) targeting `LevelExtractor#extract`, which actually still has a `level` field. The tool-animation submission hook (added in 1.1.0) stays on `LevelRenderer#submitBlockEntities`, reading `Minecraft.getInstance().level` directly instead of a shadowed field.
- `FabricLevelRendererMixin`: retargeted `renderLevel` → `render` (and dropped the now-nonexistent `ChunkSectionsToRender` param from the injector signatures) for the electricity frame-pass setup and blit-to-screen hooks.
- New `FabricLevelExtractorMixin`: holds the electricity camera-extract call (`LevelExtractor#extract` HEAD) and the wrench block-outline-cancel hook (`LevelExtractor#extractBlockOutline`, both moved off the old `LevelRenderer`-targeting mixin where they no longer resolved).

Confirmed via vanilla `LevelExtractor`/`LevelRenderer` bytecode (`javap -p -c`) that all new targets exist with matching signatures. Re-ran `:fabric:runDatagen` after the fix with no mixin errors (a partial sanity check only — datagen doesn't render a world, so the actual per-frame render path is still unverified in-game).

## [1.1.0] - MC 26.2 upgrade

Ported from Minecraft 26.1.2 to 26.2 (Fabric + NeoForge). Both subprojects build successfully and datagen (recipes, tags, loot tables) runs cleanly on both loaders; not yet launch-tested in-game.

- Bumped `minecraft_version` 26.1.2 → 26.2, `neo_form_version` → 26.2-1, `fabric_version` → 0.152.2+26.2 (JEI 30.1.0.10 requires >=0.152.2), `fabric_loader_version` → 0.19.3, `neoforge_version` → 26.2.0.6-beta, `jei_minecraft_version` → 26.2, `jei_version` → 30.1.0.10, version ranges updated accordingly.
- Bumped Fabric Loom plugin 1.16-SNAPSHOT → 1.17.11, Gradle wrapper 9.4.1 → 9.5.1.
- `mavenLocal()` + conditional `signing` block (same as [[Framework]]/[[Backpacked]]) so the locally-published 26.2 Framework fork resolves without GitHub Packages credentials.
- Removed `includeInternal` from the fabric loader-attribute configuration loop (Loom 1.17 no longer creates that configuration).
- `net.minecraft.advancements.Criterion` (top-level class) and `net.minecraft.advancements.criterion.RecipeUnlockedTrigger` moved to `net.minecraft.advancements.triggers.*`.
- `IntrinsicHolderTagsProvider` removed; `CommonBlockTagsProvider`/`CommonItemTagsProvider` now extend plain `TagsProvider`/`VanillaItemTagsProvider` and pass `.builtInRegistryHolder().key()` instead of raw items/blocks (~1000 call sites). Fabric's data generator requires the registered tag-provider instance to be a `FabricTagsProvider`, so the tag-adding logic was extracted into static methods (`addCommonBlockTags`/`addCommonItemTags`, parameterized by a `tag()` function reference) shared between the NeoForge-facing classes and new Fabric-specific `FabricBlockTagsProvider`/`FabricItemTagsProvider`.
- Individual colored item constants (`Items.WHITE_DYE`, `Items.WHITE_WOOL`, etc.) were removed in favor of `Items.DYE`/`Items.WOOL` (`ColorCollection<Item>`, accessed via `.white()`, `.black()`, etc. or `.pick(DyeColor)`).
- `BlockPos.getCenter()`/`getBottomCenter()` removed; replaced with `Vec3.atCenterOf()`/`Vec3.atBottomCenterOf()` across 16 files.
- `Minecraft.screen`/`setScreen()`/`getToastManager()` replaced with `Minecraft.gui` equivalents.
- `Block#updateEntityMovementAfterFallOn(BlockGetter, Entity)` removed entirely (no direct replacement); `TrampolineBlock` already overrides the unified `fallOn()` hook, so the obsolete override was removed.
- `Minecraft#isSingleplayer()` → `hasSingleplayerServer()`.
- Deep GPU pipeline API changes in `ElectricityRenderer`/`ModRenderPipelines` (this mod's custom off-screen electricity-overlay renderer): `MultiBufferSource` removal (rebuilt via `BufferBuilder` + `RenderType#prepare().drawFromBuffer(...)`), `RenderPipeline.Builder#withSampler`/`withVertexFormat` replaced by `BindGroupLayout`/`withVertexBinding`+`withPrimitiveTopology`, `TextureTarget`'s new `GpuFormat` constructor param, `RenderPass#draw`/`drawIndexed` gained Vulkan-style parameters (confirmed via vanilla `GuiRenderer` bytecode), `CommandEncoder#clearColorAndDepthTextures`/`createRenderPass`'s color-clear param changed from `OptionalInt` to `Optional<Vector4fc>`, several renames (`mainRenderTarget()`, `weatherTarget()`, `getModelViewMatrixCopy()`, `RenderPipeline#getVertexFormatBinding(int)`/`getPrimitiveTopology()`). The platform-abstracted `getMatricesProjectionSnippet()` was removed entirely in favor of `BindGroupLayouts.MATRICES_PROJECTION` (plain vanilla, no longer loader-specific).
- `GameRenderer#getFeatureRenderDispatcher()`'s `SubmitNodeStorage` accessor was removed with no public replacement; `ToolAnimationRenderer`'s tool-animation submissions now go through a `SubmitNodeCollector` obtained directly from `LevelRenderer#submitBlockEntities`'s injection point (via mixin) instead of being stashed into a fetched storage object during the earlier `extractLevel` phase.
- Removed three now-impossible manual `MultiBufferSource#endBatch` calls (Fabric mixin + 2x NeoForge events) for the television-screen `RenderType` — the deferred `SubmitNodeCollector` pipeline flushes `RenderType`s automatically, making the old manual flush workaround obsolete.

**Not yet done:** in-game launch test, Forge subproject (excluded from `settings.gradle`, untouched), CraftTweaker integration not re-verified for 26.2 compatibility.
