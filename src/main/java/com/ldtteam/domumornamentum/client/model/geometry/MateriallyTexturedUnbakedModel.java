package com.ldtteam.domumornamentum.client.model.geometry;

import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.client.renderer.block.dispatch.ModelState;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.geometry.UnbakedGeometry;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.util.context.ContextMap;
import net.minecraft.resources.Identifier;

public class MateriallyTexturedUnbakedModel implements UnbakedModel {
    private final Identifier innerModelLocation;

    public MateriallyTexturedUnbakedModel(final Identifier innerModelLocation) {
        this.innerModelLocation = innerModelLocation;
    }

    public Identifier innerModelLocation() {
        return innerModelLocation;
    }

    @Override
    public UnbakedGeometry geometry() {
        return new UnbakedGeometry() {
            @Override
            public QuadCollection bake(
                final TextureSlots textureSlots,
                final ModelBaker baker,
                final ModelState state,
                final ModelDebugName name
            ) {
                return bakeParentGeometry(baker, state, ContextMap.EMPTY);
            }

            @Override
            public QuadCollection bake(
                final TextureSlots textureSlots,
                final ModelBaker baker,
                final ModelState state,
                final ModelDebugName name,
                final ContextMap properties
            ) {
                return bakeParentGeometry(baker, state, properties);
            }
        };
    }

    private QuadCollection bakeParentGeometry(ModelBaker baker, net.minecraft.client.renderer.block.dispatch.ModelState state,
            net.minecraft.util.context.ContextMap properties) {
        ResolvedModel parent = baker.getModel(innerModelLocation);
        return parent.getTopGeometry().bake(parent.getTopTextureSlots(), baker, state, parent, properties);
    }

    @Override
    public void resolveDependencies(Resolver resolver) {
        resolver.markDependency(innerModelLocation);
    }

    /**
     * Keep the wrapped model in the 26.2 model-discovery parent chain.  The
     * custom loader used to rely on the legacy baked-model override path,
     * which no longer exists for item models.  Exposing the parent here lets
     * vanilla resolve its texture slots, particle material, and geometry in
     * the normal way for block models that still use this loader.
     */
    @Override
    public Identifier parent() {
        return innerModelLocation;
    }
}
