package com.ldtteam.domumornamentum.client.render;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import com.ldtteam.domumornamentum.util.Constants;

import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;

/**
 * Custom render types for Domum Ornamentum.
 * Uses the 26.x pipeline/RenderSetup architecture.
 */
public enum ModRenderTypes
{
	MEASUREMENT_LINES(InternalType.MEASUREMENT_LINES),
	CHISEL_PREVIEW_INSIDE_BLOCKS(InternalType.CHISEL_PREVIEW_INSIDE_BLOCKS),
	CHISEL_PREVIEW_OUTSIDE_BLOCKS(InternalType.CHISEL_PREVIEW_OUTSIDE_BLOCKS),
	WIREFRAME_LINES(InternalType.WIREFRAME_LINES),
	WIREFRAME_LINES_ALWAYS(InternalType.WIREFRAME_LINES_ALWAYS),
	WIREFRAME_BODY(InternalType.WIREFRAME_BODY),
	GHOST_BLOCK_PREVIEW(InternalType.GHOST_BLOCK_PREVIEW),
	GHOST_BLOCK_PREVIEW_GREATER(InternalType.GHOST_BLOCK_PREVIEW_GREATER),
	GHOST_BLOCK_COLORED_PREVIEW(InternalType.GHOST_BLOCK_COLORED_PREVIEW),
	GHOST_BLOCK_COLORED_PREVIEW_ALWAYS(InternalType.GHOST_BLOCK_COLORED_PREVIEW_ALWAYS);

	private final Supplier<RenderType> typeSupplier;

	ModRenderTypes(final Supplier<RenderType> typeSupplier) {
		this.typeSupplier = typeSupplier;
	}

	public RenderType get() {
		return typeSupplier.get();
	}

	private static class InternalType
	{
		// === Line-based types (measurement, chisel preview, wireframe) ===

		public static Supplier<RenderType> MEASUREMENT_LINES = Suppliers.memoize(InternalType::measurementLines);

		private static RenderType measurementLines()
		{
			var state = RenderSetup.builder(ModRenderPipelines.MEASUREMENT_LINES.pipeline())
					.setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
					.setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
					.createRenderSetup();
			return RenderType.create(Constants.MOD_ID + ":measurement_lines", state);
		}

		public static Supplier<RenderType> CHISEL_PREVIEW_INSIDE_BLOCKS = Suppliers.memoize(InternalType::chiselPreviewInsideBlocks);

		private static RenderType chiselPreviewInsideBlocks()
		{
			var state = RenderSetup.builder(ModRenderPipelines.CHISEL_PREVIEW_INSIDE_BLOCKS.pipeline())
					.setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
					.setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
					.createRenderSetup();
			return RenderType.create(Constants.MOD_ID + ":chisel_preview_inside_blocks", state);
		}

		public static Supplier<RenderType> CHISEL_PREVIEW_OUTSIDE_BLOCKS = Suppliers.memoize(InternalType::chiselPreviewOutsideBlocks);

		private static RenderType chiselPreviewOutsideBlocks()
		{
			var state = RenderSetup.builder(ModRenderPipelines.CHISEL_PREVIEW_OUTSIDE_BLOCKS.pipeline())
					.setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
					.setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
					.createRenderSetup();
			return RenderType.create(Constants.MOD_ID + ":chisel_preview_outside_blocks", state);
		}

		public static Supplier<RenderType> WIREFRAME_LINES = Suppliers.memoize(InternalType::wireframeLines);

		private static RenderType wireframeLines()
		{
			var state = RenderSetup.builder(ModRenderPipelines.WIREFRAME_LINES.pipeline())
					.setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
					.setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
					.sortOnUpload()
					.createRenderSetup();
			return RenderType.create(Constants.MOD_ID + ":wireframe_lines", state);
		}

		public static Supplier<RenderType> WIREFRAME_LINES_ALWAYS = Suppliers.memoize(InternalType::wireframeLinesAlways);

		private static RenderType wireframeLinesAlways()
		{
			var state = RenderSetup.builder(ModRenderPipelines.WIREFRAME_LINES_ALWAYS.pipeline())
					.setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
					.setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
					.sortOnUpload()
					.createRenderSetup();
			return RenderType.create(Constants.MOD_ID + ":wireframe_lines_always", state);
		}

		// === Wireframe body (opaque solid for wireframe overlay) ===

		public static Supplier<RenderType> WIREFRAME_BODY = Suppliers.memoize(InternalType::wireframeBody);

		private static RenderType wireframeBody()
		{
			var state = RenderSetup.builder(ModRenderPipelines.WIREFRAME_BODY.pipeline())
					.setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
					.setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
					.sortOnUpload()
					.createRenderSetup();
			return RenderType.create(Constants.MOD_ID + ":wireframe_body", state);
		}

		// === Ghost block types (textured block preview) ===

		public static Supplier<RenderType> GHOST_BLOCK_PREVIEW = Suppliers.memoize(InternalType::ghostBlockPreview);

		@SuppressWarnings("deprecation")
		private static RenderType ghostBlockPreview()
		{
			var state = RenderSetup.builder(ModRenderPipelines.GHOST_BLOCK.pipeline())
					.withTexture("Sampler0", TextureAtlas.LOCATION_BLOCKS)
					.useLightmap()
					.useOverlay()
					.setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
					.affectsCrumbling()
					.sortOnUpload()
					.createRenderSetup();
			return RenderType.create(Constants.MOD_ID + ":ghost_block_preview", state);
		}

		public static Supplier<RenderType> GHOST_BLOCK_PREVIEW_GREATER = Suppliers.memoize(InternalType::ghostBlockPreviewGreater);

		@SuppressWarnings("deprecation")
		private static RenderType ghostBlockPreviewGreater()
		{
			var state = RenderSetup.builder(ModRenderPipelines.GHOST_BLOCK_GREATER.pipeline())
					.withTexture("Sampler0", TextureAtlas.LOCATION_BLOCKS)
					.useLightmap()
					.useOverlay()
					.setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
					.affectsCrumbling()
					.sortOnUpload()
					.createRenderSetup();
			return RenderType.create(Constants.MOD_ID + ":ghost_block_preview_greater", state);
		}

		// === Colored ghost types (solid color fill) ===

		public static Supplier<RenderType> GHOST_BLOCK_COLORED_PREVIEW = Suppliers.memoize(InternalType::ghostBlockColoredPreview);

		private static RenderType ghostBlockColoredPreview()
		{
			var state = RenderSetup.builder(ModRenderPipelines.GHOST_BLOCK_COLORED.pipeline())
					.setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
					.affectsCrumbling()
					.sortOnUpload()
					.createRenderSetup();
			return RenderType.create(Constants.MOD_ID + ":ghost_block_colored_preview", state);
		}

		public static Supplier<RenderType> GHOST_BLOCK_COLORED_PREVIEW_ALWAYS = Suppliers.memoize(InternalType::ghostBlockColoredPreviewAlways);

		private static RenderType ghostBlockColoredPreviewAlways()
		{
			var state = RenderSetup.builder(ModRenderPipelines.GHOST_BLOCK_COLORED_ALWAYS.pipeline())
					.setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
					.affectsCrumbling()
					.sortOnUpload()
					.createRenderSetup();
			return RenderType.create(Constants.MOD_ID + ":ghost_block_colored_preview_always", state);
		}
	}
}
