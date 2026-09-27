package com.ldtteam.domumornamentum.client.model.retexturing;

import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

/**
 * The resolved target of one re-texturable texture slot.
 *
 * <p>Quads retextured for this slot carry a {@code MaterialInfo.tintIndex} that is an index into the
 * ordered list of targets (see {@link com.ldtteam.domumornamentum.client.model.RetexturingHandler#targetsFor}).
 * The dynamic color provider fills exactly those indices with resolved ARGB values, so tinting follows
 * whatever block state the slot currently mimics (biome tints and all).
 */
public record TargetTextureSource(BlockState targetBlockState, int originalTintIndex, @Nullable BakedQuad sourceQuad) {

    /** Sentinel for slots whose target model does not contain a matching texture. The quad is kept as-is and never tinted. */
    public static TargetTextureSource missing(final BlockState targetBlockState)
    {
        return new TargetTextureSource(targetBlockState, -1, null);
    }
}
