package com.ldtteam.domumornamentum.client.render;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;

/**
 * Custom pipeline snippets for custom depth test modes used by ModRenderTypes.
 */
public enum ModRenderPipelineSnippets
{
    NO_DEPTH_TEST(() -> RenderPipeline.builder()
            .withDepthStencilState(new DepthStencilState(CompareOp.ALWAYS_PASS, false))
            .buildSnippet()),

    LESS_OR_EQUAL_DEPTH_TEST(() -> RenderPipeline.builder()
            .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, false))
            .buildSnippet()),

    GREATER_OR_EQUAL_DEPTH_TEST(() -> RenderPipeline.builder()
            .withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, false))
            .buildSnippet()),

    GREATER_DEPTH_TEST(() -> RenderPipeline.builder()
            .withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN, false))
            .buildSnippet());

    private final Supplier<RenderPipeline.Snippet> getter;

    ModRenderPipelineSnippets(final Supplier<RenderPipeline.Snippet> getter) {
        this.getter = Suppliers.memoize(getter::get);
    }

    public RenderPipeline.Snippet snippet() {
        return getter.get();
    }
}
