package com.ldtteam.domumornamentum.client.render;

import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import java.util.function.Supplier;

public enum ModRenderTypes {
    MEASUREMENT_LINES(() -> InternalType.MEASUREMENT_LINES),
    CHISEL_PREVIEW_INSIDE_BLOCKS(() -> InternalType.CHISEL_PREVIEW_INSIDE_BLOCKS),
    CHISEL_PREVIEW_OUTSIDE_BLOCKS(() -> InternalType.CHISEL_PREVIEW_OUTSIDE_BLOCKS),
    WIREFRAME_LINES(() -> InternalType.WIREFRAME_LINES),
    WIREFRAME_LINES_ALWAYS(() -> InternalType.WIREFRAME_LINES_ALWAYS),
    WIREFRAME_BODY(() -> InternalType.WIREFRAME_BODY),
    GHOST_BLOCK_PREVIEW(() -> InternalType.GHOST_BLOCK_PREVIEW),
    GHOST_BLOCK_PREVIEW_GREATER(() -> InternalType.GHOST_BLOCK_PREVIEW_GREATER),
    GHOST_BLOCK_COLORED_PREVIEW(() -> InternalType.GHOST_BLOCK_COLORED_PREVIEW),
    GHOST_BLOCK_COLORED_PREVIEW_ALWAYS(() -> InternalType.GHOST_BLOCK_COLORED_PREVIEW_ALWAYS);

    private final Supplier<RenderType> typeSupplier;

    ModRenderTypes(final Supplier<RenderType> typeSupplier) {
        this.typeSupplier = typeSupplier;
    }

    public RenderType get() {
        return typeSupplier.get();
    }

    private static RenderType translucentLines(final String name) {
        return RenderType.create(name, RenderSetup.builder(RenderPipelines.LINES_TRANSLUCENT)
            
            .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
            .setOutputTarget(OutputTarget.MAIN_TARGET)
            .createRenderSetup());
    }

    private static RenderType translucentBlock(final String name) {
        return RenderType.create(name, RenderSetup.builder(RenderPipelines.TRANSLUCENT_BLOCK)
            
            .setOutputTarget(OutputTarget.MAIN_TARGET)
            .createRenderSetup());
    }

    private static class InternalType {
        private static final RenderType MEASUREMENT_LINES = translucentLines("domum_ornamentum:measurement_lines");
        private static final RenderType CHISEL_PREVIEW_INSIDE_BLOCKS = translucentLines("domum_ornamentum:chisel_preview_inside_blocks");
        private static final RenderType CHISEL_PREVIEW_OUTSIDE_BLOCKS = translucentLines("domum_ornamentum:chisel_preview_outside_blocks");
        private static final RenderType WIREFRAME_LINES = translucentLines("domum_ornamentum:wireframe_lines");
        private static final RenderType WIREFRAME_LINES_ALWAYS = translucentLines("domum_ornamentum:wireframe_lines_always");
        private static final RenderType WIREFRAME_BODY = translucentLines("domum_ornamentum:wireframe_body");
        private static final RenderType GHOST_BLOCK_PREVIEW = translucentBlock("domum_ornamentum:ghost_block_preview");
        private static final RenderType GHOST_BLOCK_PREVIEW_GREATER = translucentBlock("domum_ornamentum:ghost_block_preview_greater");
        private static final RenderType GHOST_BLOCK_COLORED_PREVIEW = translucentBlock("domum_ornamentum:ghost_block_colored_preview");
        private static final RenderType GHOST_BLOCK_COLORED_PREVIEW_ALWAYS = translucentBlock("domum_ornamentum:ghost_block_colored_preview_always");
    }
}
