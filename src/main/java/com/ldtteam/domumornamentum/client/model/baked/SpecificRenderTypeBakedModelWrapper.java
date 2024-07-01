package com.ldtteam.domumornamentum.client.model.baked;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class SpecificRenderTypeBakedModelWrapper implements BakedModel {

    private final RenderType renderType;
    private final BakedModel innerModel;

    public SpecificRenderTypeBakedModelWrapper(RenderType renderType, BakedModel innerModel) {
        this.renderType = renderType;
        this.innerModel = innerModel;
    }

    @Override
    public ItemTransforms getTransforms() {
        return innerModel.getTransforms();
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType) {
        if (renderType != this.renderType) {
            return Collections.emptyList();
        }

        return innerModel.getQuads(state, side, rand, data, renderType);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState p_235039_, @Nullable Direction p_235040_, RandomSource p_235041_) {
        return innerModel.getQuads(p_235039_, p_235040_, p_235041_);
    }

    @Override
    public TriState useAmbientOcclusion(BlockState state, ModelData modelData, RenderType renderType) {
        return innerModel.useAmbientOcclusion(state, modelData, renderType);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return innerModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return innerModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return innerModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return innerModel.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return innerModel.getParticleIcon();
    }

    @Override
    public ItemOverrides getOverrides() {
        return innerModel.getOverrides();
    }

    @Override
    public BakedModel applyTransform(final ItemDisplayContext transformType, final PoseStack poseStack, final boolean applyLeftHandTransform)
    {
        return innerModel.applyTransform(transformType, poseStack, applyLeftHandTransform);
    }

    @Override
    public @NotNull ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
        return innerModel.getModelData(level, pos, state, modelData);
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@NotNull ModelData data) {
        return innerModel.getParticleIcon(data);
    }

    @Override
    public ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
        return ChunkRenderTypeSet.of(this.renderType);
    }

    @Override
    public List<RenderType> getRenderTypes(ItemStack itemStack, boolean fabulous) {
        return ImmutableList.of(this.renderType);
    }
}
