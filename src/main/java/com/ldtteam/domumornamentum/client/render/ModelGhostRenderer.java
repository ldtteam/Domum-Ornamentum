package com.ldtteam.domumornamentum.client.render;

import com.ldtteam.domumornamentum.util.ItemStackUtils;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ModelGhostRenderer {

    private static final ModelGhostRenderer INSTANCE = new ModelGhostRenderer();

    private static final ByteBufferBuilder BUFFER_BUILDER = new ByteBufferBuilder(2097152);
    private static BufferBuilderTransparent BUFFER = null;

    public static ModelGhostRenderer getInstance() {
        return INSTANCE;
    }

    private ModelGhostRenderer() {
    }

    public void renderGhost(
            final PoseStack poseStack,
            final ItemStack renderStack,
            final Vec3 targetedRenderPos,
            final BlockHitResult blockHitResult,
            final ClientLevel level,
            final boolean ignoreDepth) {
        poseStack.pushPose();

        // Offset/scale by an unnoticeable amount to prevent z-fighting
        final Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        poseStack.translate(
                targetedRenderPos.x - camera.x - 0.000125,
                targetedRenderPos.y - camera.y + 0.000125,
                targetedRenderPos.z - camera.z - 0.000125
        );
        poseStack.scale(1.001F, 1.001F, 1.001F);

        final Vector4f color = new Vector4f(0, 0, 1, 0.5f);

        final List<ModelToRender> models;
        ModelData modelData = null;
        BlockState placementState;
        final boolean renderItemMode;
        if (renderStack.getItem() instanceof BlockItem blockItem) {
            final BlockPlaceContext context = new BlockPlaceContext(
                    Objects.requireNonNull(Minecraft.getInstance().player),
                    Objects.requireNonNull(ItemStackUtils.getHandWithMateriallyTexturedItemStackFromPlayer(Minecraft.getInstance().player)),
                    renderStack,
                    blockHitResult
            );
            placementState = blockItem.getBlock().getStateForPlacement(context);

            if (placementState == null) {
                poseStack.popPose();
                return;
            }

            placementState = renderStack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY).apply(placementState);

            final BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getBlockModel(placementState);

            if (blockItem.getBlock() instanceof EntityBlock entityBlock) {
                final BlockEntity blockEntity = entityBlock.newBlockEntity(context.getClickedPos(), placementState);
                if (blockEntity != null) {
                    blockEntity.applyComponentsFromItemStack(renderStack);

                    modelData = blockEntity.getModelData();
                    modelData = model.getModelData(Objects.requireNonNull(Minecraft.getInstance().level), context.getClickedPos(), placementState, modelData);
                }
            }

            if (modelData == null) {
                modelData = ModelData.EMPTY;
            }

            final RandomSource randomSource = RandomSource.create();
            randomSource.setSeed(42L);
            final ChunkRenderTypeSet renderTypeSet = model.getRenderTypes(placementState, randomSource, modelData);
            models = renderTypeSet.asList().stream()
                    .map(renderType -> new ModelToRender(model, renderType))
                    .toList();
            renderItemMode = false;
        } else {
            placementState = null;
            final BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(renderStack, null, null, 0);
            final List<BakedModel> renderPasses = model.getRenderPasses(renderStack, true);
            //TODO; Figure this out.
            modelData = ModelData.EMPTY;
            models = renderPasses.stream()
                    .flatMap(pass -> pass.getRenderTypes(renderStack, true).stream().map(type -> new ModelToRender(pass, type)))
                    .toList();
            renderItemMode = true;
        }

        renderGhost(
                placementState,
                blockHitResult.getBlockPos(),
                poseStack,
                models,
                modelData,
                color,
                false,
                renderItemMode
        );

        poseStack.popPose();
    }

    @SuppressWarnings("SameParameterValue")
    private void renderGhost(
            final BlockState state,
            final BlockPos pos,
            final PoseStack poseStack,
            final List<ModelToRender> models,
            final ModelData modelData,
            final Vector4f color,
            final boolean renderColoredGhost,
            final boolean renderItemMode) {
        final RenderType renderType;
        if (renderColoredGhost)
        {
            renderType = ModRenderTypes.GHOST_BLOCK_COLORED_PREVIEW.get();
        }
        else
        {
            renderType = ModRenderTypes.GHOST_BLOCK_PREVIEW.get();
        }
        BUFFER = new BufferBuilderTransparent(BUFFER_BUILDER, renderType.mode(), renderType.format());
        BUFFER.setAlphaPercentage(color.w());
        if (renderColoredGhost)
        {
            for (ModelToRender model : models) {
                renderColoredModelLists(
                        state,
                        model,
                        modelData,
                        poseStack,
                        color
                );
            }
        }
        else
        {
            for (ModelToRender model : models) {
                renderFullModelLists(
                        model,
                        modelData,
                        state,
                        pos,
                        poseStack,
                        renderItemMode
                );
            }
        }
        final MeshData meshData = BUFFER.buildOrThrow();
        // TODO: meshData.sortQuads(null, RenderSystem.getVertexSorting()); move to event bufferSource?
        renderType.draw(meshData);
        BUFFER = null;
    }

    private static final float[] DIRECTIONAL_BRIGHTNESS = { 0.5f, 1f, 0.7f, 0.7f, 0.6f, 0.6f };

    private static Vector3f[] getShadedColors(final Vector4f color) {
        // Directionally shade the color by the amount MC normally does
        return Arrays.stream(Direction.values())
                .map(direction ->
                {
                    final float brightness = DIRECTIONAL_BRIGHTNESS[direction.get3DDataValue()];
                    return new Vector3f(
                            color.x() * brightness,
                            color.y() * brightness,
                            color.z() * brightness);
                }).toArray(Vector3f[]::new);
    }

    private static Vector3f[] getNormals(final PoseStack.Pose pose) {
        // Transform the normal vector of each direction by the pose's normal matrix
        return Arrays.stream(Direction.values())
                .map(direction ->
                {
                    final Vec3i faceNormal = direction.getNormal();
                    final Vector3f normal = new Vector3f(faceNormal.getX(), faceNormal.getY(), faceNormal.getZ());
                    normal.mul(pose.normal());
                    return normal;
                }).toArray(Vector3f[]::new);
    }

    private static void renderFullModelLists(
            ModelToRender pModel,
            ModelData modelData,
            BlockState state,
            BlockPos pos,
            PoseStack pPoseStack,
            boolean renderItemMode) {
        RandomSource randomsource = RandomSource.create();

        for(Direction direction : Direction.values()) {
            randomsource.setSeed(42L);
            if (renderItemMode)
                Minecraft.getInstance().getItemRenderer().renderQuadList(pPoseStack, ModelGhostRenderer.BUFFER, pModel.model().getQuads(state, direction, randomsource, modelData, pModel.renderType()), new ItemStack(state.getBlock()), 15728880, OverlayTexture.NO_OVERLAY);
            else
                renderBlockTintedQuadList(pPoseStack, pModel.model().getQuads(state, direction, randomsource, modelData, pModel.renderType()), state, pos);
        }

        randomsource.setSeed(42L);
        if (renderItemMode)
            Minecraft.getInstance().getItemRenderer().renderQuadList(pPoseStack, ModelGhostRenderer.BUFFER, pModel.model().getQuads(state, null, randomsource, modelData, pModel.renderType()), new ItemStack(state.getBlock()), 15728880, OverlayTexture.NO_OVERLAY);
        else
            renderBlockTintedQuadList(pPoseStack, pModel.model().getQuads(state, null, randomsource, modelData, pModel.renderType()), state, pos);
    }

    private static void renderBlockTintedQuadList(PoseStack pPoseStack, List<BakedQuad> pQuads, BlockState placementState, BlockPos pos) {
        PoseStack.Pose posestack$pose = pPoseStack.last();

        for(BakedQuad bakedquad : pQuads) {
            int i = -1;
            if (bakedquad.isTinted()) {
                i = Minecraft.getInstance().getBlockColors().getColor(placementState, Minecraft.getInstance().level, pos, bakedquad.getTintIndex());
            }

            float f = (float)(i >> 16 & 0xFF) / 255.0F;
            float f1 = (float)(i >> 8 & 0xFF) / 255.0F;
            float f2 = (float)(i & 0xFF) / 255.0F;
            ModelGhostRenderer.BUFFER.putBulkData(posestack$pose, bakedquad, f, f1, f2, 1.0F, 15728880, OverlayTexture.NO_OVERLAY, true);
        }
    }

    /**
     * Optimized version of ItemRenderer#renderModelLists that ignores textures, and renders a model's quads with a single RGBA color shaded by the quads' direction to match MCs similar shading
     */
    private static void renderColoredModelLists(
            final BlockState state,
            final ModelToRender model,
            final ModelData modelData,
            final PoseStack poseStack,
            final Vector4f color) {
        final RandomSource random = RandomSource.create(42);

        // Setup normals and shaded colors for each direction
        final Vector3f[] normals = getNormals(poseStack.last());
        final Vector3f[] shadedColors = getShadedColors(color);

        // Initialize reusable position vector to avoid needless creation of new ones
        final Vector4f pos = new Vector4f();

        for (final Direction direction : Direction.values()) {
            // Render outer directional quads
            random.setSeed(42L);
            renderColoredQuadList(poseStack.last().pose(), model.model().getQuads(state, direction, random, modelData, model.renderType()), normals, shadedColors, pos);
        }

        // Render quads of unspecified direction
        random.setSeed(42L);
        renderColoredQuadList(poseStack.last().pose(), model.model().getQuads(state, null, random, modelData, model.renderType()), normals, shadedColors, pos);
    }

    /**
     * Optimized version of ItemRenderer#renderQuadList
     */
    private static void renderColoredQuadList(
            final Matrix4f pose,
            final List<BakedQuad> quads,
            final Vector3f[] normals,
            final Vector3f[] shadedColors,
            final Vector4f pos) {
        for (final BakedQuad quad : quads) {
            putColoredBulkData(
                    pose,
                    quad,
                    shadedColors[quad.getDirection().ordinal()],
                    normals[quad.getDirection().ordinal()],
                    pos);
        }
    }

    private static void putColoredBulkData(
            final Matrix4f pose,
            final BakedQuad bakedQuad,
            final Vector3f color,
            final Vector3f normal,
            final Vector4f pos) {
        // Get vertex data
        final int[] vertices = bakedQuad.getVertices();
        final int vertexCount = vertices.length / (DefaultVertexFormat.BLOCK.getVertexSize() / 4);

        try (final MemoryStack memorystack = MemoryStack.stackPush()) {
            // Setup buffers
            final ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormat.BLOCK.getVertexSize());
            final IntBuffer intbuffer = bytebuffer.asIntBuffer();

            for (int v = 0; v < vertexCount; ++v) {
                // Add vertex data to the buffer
                ((Buffer) intbuffer).clear();
                intbuffer.put(vertices, v * 8, 8);

                // Extract relative position, then transform it to the position in the world
                pos.set(bytebuffer.getFloat(0),
                        bytebuffer.getFloat(4),
                        bytebuffer.getFloat(8),
                        1f);
                pos.mul(pose);

                ModelGhostRenderer.BUFFER.addVertex(pos.x(), pos.y(), pos.z())
                        .setColor(color.x(), color.y(), color.z(), 1f)
                        .setNormal(normal.x(), normal.y(), normal.z());
            }
        }
    }

    private static class BufferBuilderTransparent extends BufferBuilder {

        private float alphaPercentage;

        public BufferBuilderTransparent(ByteBufferBuilder memory, Mode mode, VertexFormat format)
        {
            super(memory, mode, format);
        }

        public void setAlphaPercentage(final float alphaPercentage) {
            this.alphaPercentage = Mth.clamp(alphaPercentage, 0, 1);
        }

        @Override
        public @NotNull VertexConsumer setColor(int red, int green, int blue, int alpha) {
            return super.setColor(red, green, blue, (int) (alpha * alphaPercentage));
        }

        @Override
        public VertexConsumer setColor(int argb)
        {
            final int newAlpha = (int) ((argb >> 24) * alphaPercentage);
            return super.setColor((newAlpha << 24) | (argb & 0x00ffffff));
        }

        @Override
        public void addVertex(float x, float y, float z, int argb, float texU,
                float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ) {
            final int newAlpha = (int) ((argb >> 24) * alphaPercentage);
            super.addVertex(x, y, z, (newAlpha << 24) | (argb & 0x00ffffff), texU, texV, overlayUV, lightmapUV, normalX, normalY, normalZ);
        }

    }

    private record ModelToRender(BakedModel model, RenderType renderType) {}
}
