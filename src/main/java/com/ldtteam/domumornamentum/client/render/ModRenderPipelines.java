package com.ldtteam.domumornamentum.client.render;

import static net.minecraft.client.renderer.RenderPipelines.LINES_SNIPPET;
import static net.minecraft.client.renderer.RenderPipelines.DEBUG_FILLED_SNIPPET;
import static net.minecraft.client.renderer.RenderPipelines.ENTITY_SNIPPET;
import static net.minecraft.client.renderer.RenderPipelines.BLOCK_SNIPPET;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;

import com.ldtteam.domumornamentum.util.Constants;

import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

/**
 * Custom render pipelines for Domum Ornamentum's render types.
 */
public enum ModRenderPipelines
{
	// Line-based pipelines (measurement, chisel preview, wireframe)
	MEASUREMENT_LINES(() -> RenderPipeline.builder(
						LINES_SNIPPET,
						ModRenderPipelineSnippets.NO_DEPTH_TEST.snippet()
				).withLocation(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "pipeline/measurement_lines")).build()),

	CHISEL_PREVIEW_INSIDE_BLOCKS(() -> RenderPipeline.builder(
						LINES_SNIPPET,
						ModRenderPipelineSnippets.GREATER_OR_EQUAL_DEPTH_TEST.snippet()
				).withLocation(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "pipeline/chisel_preview_inside_blocks")).build()),

	CHISEL_PREVIEW_OUTSIDE_BLOCKS(() -> RenderPipeline.builder(
						LINES_SNIPPET,
						ModRenderPipelineSnippets.LESS_OR_EQUAL_DEPTH_TEST.snippet()
				).withLocation(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "pipeline/chisel_preview_outside_blocks")).build()),

	WIREFRAME_LINES(() -> RenderPipeline.builder(
						LINES_SNIPPET,
						ModRenderPipelineSnippets.LESS_OR_EQUAL_DEPTH_TEST.snippet()
				).withVertexFormat(DefaultVertexFormat.POSITION_COLOR_LINE_WIDTH, VertexFormat.Mode.LINES)
				.withLocation(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "pipeline/wireframe_lines")).build()),

	WIREFRAME_LINES_ALWAYS(() -> RenderPipeline.builder(
						LINES_SNIPPET,
						ModRenderPipelineSnippets.NO_DEPTH_TEST.snippet()
				).withVertexFormat(DefaultVertexFormat.POSITION_COLOR_LINE_WIDTH, VertexFormat.Mode.LINES)
				.withLocation(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "pipeline/wireframe_lines_always")).build()),

	// Wireframe body: opaque solid block render for wireframe overlay
	WIREFRAME_BODY(() -> RenderPipeline.builder(
						BLOCK_SNIPPET,
						ModRenderPipelineSnippets.NO_DEPTH_TEST.snippet()
				).withCull(false)
				.withLocation(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "pipeline/wireframe_body")).build()),

	// Ghost block pipelines (textured block preview)
	GHOST_BLOCK(() ->
			RenderPipeline.builder(ENTITY_SNIPPET)
			.withShaderDefine("ALPHA_CUTOUT", 0.1F)
			.withShaderDefine("PER_FACE_LIGHTING")
			.withSampler("Sampler1")
			.withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
			.withCull(false)
			.withLocation(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "pipeline/ghost_block")).build()),

	GHOST_BLOCK_GREATER(() ->
			RenderPipeline.builder(ENTITY_SNIPPET, ModRenderPipelineSnippets.GREATER_DEPTH_TEST.snippet())
			.withShaderDefine("ALPHA_CUTOUT", 0.1F)
			.withShaderDefine("PER_FACE_LIGHTING")
			.withSampler("Sampler1")
			.withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
			.withCull(false)
			.withLocation(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "pipeline/ghost_block_greater")).build()),

	// Colored ghost pipelines (solid color fill)
	GHOST_BLOCK_COLORED(() -> RenderPipeline.builder(
						DEBUG_FILLED_SNIPPET,
						ModRenderPipelineSnippets.LESS_OR_EQUAL_DEPTH_TEST.snippet()
				).withVertexFormat(DefaultVertexFormat.POSITION_COLOR_NORMAL, VertexFormat.Mode.QUADS)
				.withLocation(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "pipeline/ghost_block_colored")).build()),

	GHOST_BLOCK_COLORED_ALWAYS(() -> RenderPipeline.builder(
						DEBUG_FILLED_SNIPPET,
						ModRenderPipelineSnippets.NO_DEPTH_TEST.snippet()
				).withVertexFormat(DefaultVertexFormat.POSITION_COLOR_NORMAL, VertexFormat.Mode.QUADS)
				.withLocation(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "pipeline/ghost_block_colored_always")).build());

	private final Supplier<RenderPipeline> getter;

	ModRenderPipelines(final Supplier<RenderPipeline> getter) {
		this.getter = Suppliers.memoize(getter::get);
	}

	public RenderPipeline pipeline() {
		return getter.get();
	}
}
