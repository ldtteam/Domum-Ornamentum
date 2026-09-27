package com.ldtteam.domumornamentum.client.model.retexturing;

import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * A {@link BlockStateModelPart} holding retextured quads. Mirrors the per-part metadata (AO, particle
 * material, flags) of the parent part it was derived from.
 */
public record RetexturedModelPart(
        QuadCollection quads,
        boolean useAmbientOcclusion,
        Material.Baked particleMaterial,
        @BakedQuad.MaterialFlags int materialFlags
) implements BlockStateModelPart {

    @Override
    public @NonNull List<BakedQuad> getQuads(@Nullable final Direction direction)
    {
        return quads.getQuads(direction);
    }

    @Contract(pure = true)
    @Override
    public boolean useAmbientOcclusion()
    {
        return useAmbientOcclusion;
    }

    @Override
    public Material.@NonNull Baked particleMaterial()
    {
        return particleMaterial;
    }

    @Override
    public int materialFlags()
    {
        return materialFlags;
    }
}
