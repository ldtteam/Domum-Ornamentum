package com.ldtteam.domumornamentum.client.model.loader;

import com.ldtteam.domumornamentum.client.model.block.MateriallyTexturedBlockStateModel;
import com.mojang.math.Quadrant;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.SingleVariant;
import net.minecraft.client.renderer.block.dispatch.ModelState;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.resources.model.SimpleModelWrapper;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import org.jspecify.annotations.NonNull;

/**
 * Unbaked model for {@code "loader": "domum_ornamentum:materially_textured"} blockstate models.
 *
 * <p>Bakes the declared parent into a plain part (exactly like {@link SimpleModelWrapper}, with an optional
 * per-variant rotation) and wraps it in a {@link MateriallyTexturedBlockStateModel}, which retextures those
 * parts at render time according to the block entity's texture mapping. The parent is resolved through the
 * normal model dependency machinery, so standard Blockbench JSONs keep working unchanged.
 *
 * <p>Serialized form (inline in a blockstate file):
 * <pre>{"type": "domum_ornamentum:materially_textured", "parent": "...", "x": 90, "y": 90}</pre>
 */
public final class MateriallyTexturedUnbakedModel implements CustomUnbakedBlockStateModel {

    public static final MapCodec<MateriallyTexturedUnbakedModel> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            Identifier.CODEC.fieldOf("parent").forGetter(m -> m.parentLocation),
            Quadrant.CODEC.optionalFieldOf("x", Quadrant.R0).forGetter(m -> m.x),
            Quadrant.CODEC.optionalFieldOf("y", Quadrant.R0).forGetter(m -> m.y),
            Quadrant.CODEC.optionalFieldOf("z", Quadrant.R0).forGetter(m -> m.z)
        ).apply(instance, MateriallyTexturedUnbakedModel::new)
    );

    private final Identifier parentLocation;
    private final Quadrant x;
    private final Quadrant y;
    private final Quadrant z;

    public MateriallyTexturedUnbakedModel(final Identifier parentLocation) {
        this(parentLocation, Quadrant.R0, Quadrant.R0, Quadrant.R0);
    }

    public MateriallyTexturedUnbakedModel(final Identifier parentLocation, final Quadrant x, final Quadrant y, final Quadrant z) {
        this.parentLocation = parentLocation;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public @NonNull Identifier parentLocation() {
        return parentLocation;
    }

    public @NonNull Quadrant x() {
        return x;
    }

    public @NonNull Quadrant y() {
        return y;
    }

    public @NonNull Quadrant z() {
        return z;
    }

    @Override
    public @NonNull MapCodec<? extends CustomUnbakedBlockStateModel> codec() {
        return CODEC;
    }

    @Override
    public void resolveDependencies(final ResolvableModel.Resolver resolver) {
        resolver.markDependency(parentLocation);
    }

    @Override
    public @NonNull BlockStateModel bake(final @NonNull ModelBaker modelBaker) {
        final ModelState state = new Variant.SimpleModelState(x, y, z, false).asModelState();
        final BlockStateModelPart part = SimpleModelWrapper.bake(modelBaker, parentLocation, state);
        return new MateriallyTexturedBlockStateModel(new SingleVariant(part));
    }
}
