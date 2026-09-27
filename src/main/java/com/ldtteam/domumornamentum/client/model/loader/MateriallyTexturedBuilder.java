package com.ldtteam.domumornamentum.client.model.loader;

import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import net.neoforged.neoforge.client.model.generators.blockstate.CustomBlockStateModelBuilder;
import net.neoforged.neoforge.client.model.generators.blockstate.UnbakedMutator;
import org.jspecify.annotations.NonNull;

/**
 * Datagen builder for {@link MateriallyTexturedUnbakedModel}.
 * <p>
 * Unlike the vanilla {@code CustomBlockStateModelBuilder.Simple} (which silently drops rotations), this builder
 * feeds {@link net.minecraft.client.renderer.block.dispatch.VariantMutator}s through a base {@link Variant} and
 * bakes the resulting x/y/z rotations into the unbaked model, so providers can keep using
 * {@code MultiVariant.of(...).with(BlockModelGenerators.X_ROT_90)} style chains.
 */
public final class MateriallyTexturedBuilder extends CustomBlockStateModelBuilder {

    private final Identifier parent;
    private final Variant.SimpleModelState state;

    public static MateriallyTexturedBuilder withParent(final @NonNull Identifier parent) {
        return new MateriallyTexturedBuilder(parent);
    }

    /** Convenience: wrap in a {@link MultiVariant} for use in blockstate generators. */
    public static MultiVariant multiVariantWithParent(final @NonNull Identifier parent) {
        return MultiVariant.of(new MateriallyTexturedBuilder(parent));
    }

    private MateriallyTexturedBuilder(final Identifier parent) {
        this.parent = parent;
        this.state = Variant.SimpleModelState.DEFAULT;
    }

    private MateriallyTexturedBuilder(final Identifier parent, final Variant.SimpleModelState state) {
        this.parent = parent;
        this.state = state;
    }

    @Override
    public MateriallyTexturedBuilder with(final net.minecraft.client.renderer.block.dispatch.VariantMutator variantMutator) {
        // Apply the mutator to a variant carrying our current rotation state so chained mutators compose.
        final Variant mutated = new Variant(parent, this.state).with(variantMutator);
        return new MateriallyTexturedBuilder(this.parent, mutated.modelState());
    }

    @Override
    public CustomBlockStateModelBuilder with(final UnbakedMutator variantMutator) {
        final var mutated = (MateriallyTexturedUnbakedModel) variantMutator.apply(toUnbaked());
        return new MateriallyTexturedBuilder(mutated.parentLocation(),
            new Variant.SimpleModelState(mutated.x(), mutated.y(), mutated.z(), false));
    }

    @Override
    public CustomUnbakedBlockStateModel toUnbaked() {
        return new MateriallyTexturedUnbakedModel(parent, state.x(), state.y(), state.z());
    }
}
