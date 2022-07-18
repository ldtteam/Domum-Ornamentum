package com.ldtteam.domumornamentum.client.model.baked;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.client.model.properties.ModProperties;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.RenderTypeGroup;
import net.minecraftforge.client.model.data.ModelData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.ldtteam.domumornamentum.util.MaterialTextureDataUtil.generateRandomTextureDataFrom;

public class MateriallyTexturedBakedModel implements BakedModel {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final RandomSource RANDOM = RandomSource.create();

    private final Cache<MaterialTextureData, BakedModel> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(2, TimeUnit.MINUTES)
            .concurrencyLevel(4)
            .maximumSize(10000)
            .build();

    private final Cache<Pair<MaterialTextureData, CompoundTag>, BakedModel> itemCache = CacheBuilder.newBuilder()
            .expireAfterAccess(2, TimeUnit.MINUTES)
            .concurrencyLevel(4)
            .maximumSize(10000)
            .build();

    private final BakedModel innerModel;

    public MateriallyTexturedBakedModel(final BakedModel innerModel) {
        this.innerModel = innerModel;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(
            @Nullable final BlockState state, @Nullable final Direction side, final @NotNull RandomSource rand) {
        return innerModel.getQuads(state, side, rand);
    }

    @Override
    public @NotNull ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
        if (!data.has(ModProperties.MATERIAL_TEXTURE_PROPERTY)) {
            return ChunkRenderTypeSet.none();
        }

        final MaterialTextureData textureData = data.get(ModProperties.MATERIAL_TEXTURE_PROPERTY);
        if (textureData == null) {
            return ChunkRenderTypeSet.none();
        }

        return ChunkRenderTypeSet.union(textureData.getTexturedComponents().values().stream()
                .map(block -> Minecraft.getInstance().getBlockRenderer().getBlockModel(block.defaultBlockState())
                        .getRenderTypes(block.defaultBlockState(), rand, ModelData.EMPTY))
                .toArray(ChunkRenderTypeSet[]::new)
        );
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType) {

        final BakedModel remappedModel = getBakedInnerModelFor(data, state, renderType);
        return remappedModel.getQuads(state, side, rand, data, renderType);
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
    public @NotNull TextureAtlasSprite getParticleIcon() {
        return innerModel.getParticleIcon();
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon(@NotNull final ModelData modelData) {
        if (!modelData.has(ModProperties.MATERIAL_TEXTURE_PROPERTY)) {
            return getParticleIcon();
        }

        final MaterialTextureData textureData = modelData.get(ModProperties.MATERIAL_TEXTURE_PROPERTY);
        if (textureData == null)
            return getParticleIcon();

        final ResourceLocation particleTextureName = getParticleIcon().getName();
        if (!textureData.getTexturedComponents().containsKey(particleTextureName))
            return getParticleIcon();

        return Minecraft.getInstance().getBlockRenderer().getBlockModel(textureData.getTexturedComponents().get(particleTextureName).defaultBlockState())
                .getParticleIcon(modelData);
    }

    @Override
    public @NotNull ItemTransforms getTransforms() {
        return this.innerModel.getTransforms();
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }

    @Override
    public @NotNull List<RenderType> getRenderTypes(ItemStack itemStack, boolean fabulous) {
        if (!(itemStack.getItem() instanceof BlockItem blockItem)) {
            return Collections.emptyList();
        }

        MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(itemStack.getOrCreateTagElement("textureData"));
        if (textureData.isEmpty()) {
            return Collections.emptyList();
        }

        return getRenderTypes(blockItem, textureData);
    }

    private List<RenderType> getRenderTypes(BlockItem blockItem, MaterialTextureData textureData) {
        return getRenderTypes(blockItem.getBlock().defaultBlockState(), RANDOM, ModelData.builder().with(ModProperties.MATERIAL_TEXTURE_PROPERTY, textureData).build()).asList();
    }

    @Override
    public @NotNull List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
        if (!(itemStack.getItem() instanceof BlockItem blockItem)) {
            return Collections.emptyList();
        }

        MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(itemStack.getOrCreateTagElement("textureData"));
        if (textureData.isEmpty()) {
            textureData = generateRandomTextureDataFrom(itemStack);
        }

        List<RenderType> renderTypes = getRenderTypes(blockItem, textureData);
        if (renderTypes.isEmpty()) {
            return Collections.emptyList();
        }

        MaterialTextureData finalTextureData = textureData;
        final List<BakedModel> models = new ArrayList<>();
        renderTypes.stream()
                .map(type -> getRenderType(type, fabulous))
                .distinct()
                .map(type -> new SpecificRenderTypeBakedModelWrapper(
                        type,
                        getBakedInnerModelFor(itemStack, finalTextureData, blockItem.getBlock().defaultBlockState(), type)
                )).forEach(models::add);
        return models;
    }

    private BakedModel getBakedInnerModelFor(final ModelData modelData, final BlockState sourceState, final RenderType renderType) {
        if (!modelData.has(ModProperties.MATERIAL_TEXTURE_PROPERTY)) {
            return getBakedInnerModelFor(MaterialTextureData.EMPTY, sourceState, renderType);
        }

        return getBakedInnerModelFor(modelData.get(ModProperties.MATERIAL_TEXTURE_PROPERTY), sourceState, renderType);
    }

    private BakedModel getBakedInnerModelFor(final MaterialTextureData modelData, final BlockState sourceState, final RenderType renderType) {
        try {
            return cache.get(modelData, () -> {
                final RetexturedBakedModelBuilder builder = RetexturedBakedModelBuilder.createFor(
                        sourceState,
                        renderType,
                        false,
                        this.innerModel
                );

                modelData.getTexturedComponents().forEach(builder::with);

                return builder.build();
            });
        } catch (Exception exception) {
            LOGGER.error(String.format("Failed to build baked materially textured model for: %s", modelData), exception);
            return Minecraft.getInstance().getModelManager().getMissingModel();
        }
    }

    private BakedModel getBakedInnerModelFor(final ItemStack stack,
                                             final MaterialTextureData textureData,
                                             final BlockState blockState,
                                             final RenderType renderType) {
        try {
            return itemCache.get(
                    Pair.of(textureData, stack.serializeNBT())
                    , () -> {
                        final RetexturedBakedModelBuilder builder = RetexturedBakedModelBuilder.createFor(
                                blockState,
                                renderType,
                                true,
                                this.innerModel
                        );

                        textureData.getTexturedComponents().forEach(builder::with);

                        return builder.build();
                    });
        } catch (Exception exception) {
            LOGGER.error(String.format("Failed to build baked materially textured model for: %s for item: %s", textureData, stack), exception);
            return Minecraft.getInstance().getModelManager().getMissingModel();
        }
    }


    private ChunkRenderTypeSet createAdaptedSetForEntity(ChunkRenderTypeSet renderTypes, boolean fabulous) {
        return ChunkRenderTypeSet.of(
                renderTypes.asList().stream()
                        .map(renderType -> getRenderType(renderType, fabulous))
                        .collect(Collectors.toSet())
        );
    }

    public static RenderType getRenderType(RenderType renderType, boolean fabulous) {
        if (renderType == RenderType.translucent()) {
            if (!Minecraft.useShaderTransparency()) {
                return Sheets.translucentCullBlockSheet();
            } else {
                return fabulous ? Sheets.translucentCullBlockSheet() : Sheets.translucentItemSheet();
            }
        } else {
            return Sheets.cutoutBlockSheet();
        }
    }

    public static RenderType getFabulousRenderType(RenderType renderType) {
        if (renderType == RenderType.translucent()) {
            return Sheets.translucentCullBlockSheet();
        } else {
            return Sheets.cutoutBlockSheet();
        }
    }

    public static RenderType getNoneFabulousRenderType(RenderType renderType) {
        if (renderType == RenderType.translucent()) {
            return !Minecraft.useShaderTransparency() ? Sheets.translucentCullBlockSheet() : Sheets.translucentItemSheet();
        } else {
            return Sheets.cutoutBlockSheet();
        }
    }
}
