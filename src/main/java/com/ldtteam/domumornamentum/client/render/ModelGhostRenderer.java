package com.ldtteam.domumornamentum.client.render;

import com.ldtteam.domumornamentum.util.ItemStackUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.QuadInstance;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.BlockQuadOutput;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.client.renderer.block.BlockStateModelSet;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ARGB;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.model.data.ModelData;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Arrays;
import java.util.Objects;

public class ModelGhostRenderer
{
    private static final ModelGhostRenderer INSTANCE = new ModelGhostRenderer();

    public static ModelGhostRenderer getInstance()
    {
        return INSTANCE;
    }

    private ModelGhostRenderer()
    {
    }

    public void renderGhost(
            final PoseStack poseStack,
            final MultiBufferSource.BufferSource bufferSource,
            final ItemStack renderStack,
            final Vec3 targetedRenderPos,
            final BlockHitResult blockHitResult,
            final ClientLevel level,
            final boolean ignoreDepth)
    {
        poseStack.pushPose();

        // Offset/scale by an unnoticeable amount to prevent z-fighting
        final Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().position();
        poseStack.translate(
                targetedRenderPos.x - camera.x - 0.000125,
                targetedRenderPos.y - camera.y + 0.000125,
                targetedRenderPos.z - camera.z - 0.000125
        );
        poseStack.scale(1.001F, 1.001F, 1.001F);

        final Vector4f color = new Vector4f(0, 0, 1, 0.5f);

        if (renderStack.getItem() instanceof BlockItem blockItem)
        {
            final BlockPlaceContext context = new BlockPlaceContext(
                    Objects.requireNonNull(Minecraft.getInstance().player),
                    Objects.requireNonNull(ItemStackUtils.getHandWithMateriallyTexturedItemStackFromPlayer(Minecraft.getInstance().player)),
                    renderStack,
                    blockHitResult
            );

            BlockState placementState = blockItem.getBlock().getStateForPlacement(context);
            if (placementState == null)
            {
                poseStack.popPose();
                return;
            }

            placementState = renderStack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY).apply(placementState);

            ModelData modelData = ModelData.EMPTY;
            if (blockItem.getBlock() instanceof EntityBlock entityBlock)
            {
                final BlockEntity blockEntity = entityBlock.newBlockEntity(context.getClickedPos(), placementState);
                if (blockEntity != null)
                {
                    blockEntity.applyComponentsFromItemStack(renderStack);
                    modelData = blockEntity.getModelData();
                }
            }

            renderGhostForBlock(
                    placementState,
                    context.getClickedPos(),
                    poseStack,
                    bufferSource,
                    modelData,
                    color,
                    false,
                    ignoreDepth,
                    level
            );
        }
        // Non-BlockItem rendering skipped for now - requires significant API changes in 26.1

        poseStack.popPose();
    }

    private void renderGhostForBlock(
            final BlockState state,
            final BlockPos pos,
            final PoseStack poseStack,
            final MultiBufferSource.BufferSource bufferSource,
            final ModelData modelData,
            final Vector4f color,
            final boolean renderColoredGhost,
            final boolean ignoreDepth,
            final ClientLevel level)
    {
        final RenderType renderType;
        if (renderColoredGhost)
        {
            renderType = ModRenderTypes.GHOST_BLOCK_COLORED_PREVIEW.get();
        }
        else
        {
            renderType = ignoreDepth
                    ? ModRenderTypes.GHOST_BLOCK_PREVIEW_GREATER.get()
                    : ModRenderTypes.GHOST_BLOCK_PREVIEW.get();
        }

        if (renderColoredGhost)
        {
            renderColoredGhost(poseStack, bufferSource, state, pos, modelData, color, renderType);
        }
        else
        {
            renderTexturedGhost(poseStack, bufferSource, state, pos, modelData, level, renderType);
        }

        bufferSource.endBatch(renderType);
    }

    private void renderTexturedGhost(
            final PoseStack poseStack,
            final MultiBufferSource.BufferSource bufferSource,
            final BlockState state,
            final BlockPos pos,
            final ModelData modelData,
            final ClientLevel level,
            final RenderType renderType)
    {
        // Create a BlockAndTintGetter that returns our placement state
        final MovingBlockRenderState blockAndTintGetter = new MovingBlockRenderState();
        blockAndTintGetter.blockPos = pos;
        blockAndTintGetter.blockState = state;
        blockAndTintGetter.biome = level.getBiome(pos);
        blockAndTintGetter.cardinalLighting = level.cardinalLighting();
        blockAndTintGetter.lightEngine = level.getLightEngine();
        blockAndTintGetter.modelData = modelData;

        // Get the BlockStateModel for this block state
        final ModelManager modelManager = Minecraft.getInstance().getModelManager();
        final BlockStateModelSet blockStateModelSet = modelManager.getBlockStateModelSet();
        final BlockStateModel model = blockStateModelSet.get(state);

        // Create the output that submits to our custom render type
        final VertexConsumer buffer = bufferSource.getBuffer(renderType);
        final QuadInstance quadInstance = new QuadInstance();
        quadInstance.setColor(ARGB.color(255, 255, 255, 255));
        quadInstance.setLightCoords(LightCoordsUtil.FULL_BRIGHT);
        quadInstance.setOverlayCoords(OverlayTexture.NO_OVERLAY);

        final BlockQuadOutput output = (x, y, z, quad, instance) ->
                buffer.putBlockBakedQuad(x, y, z, quad, instance);

        // Create the block renderer and tesselate
        final ModelBlockRenderer blockRenderer = new ModelBlockRenderer(
                Minecraft.getInstance().options.ambientOcclusion().get(),
                false,
                Minecraft.getInstance().getBlockColors());

        blockRenderer.tesselateBlock(
                output,
                0, 0, 0,
                blockAndTintGetter,
                pos,
                state,
                model,
                42L);
    }

    private static final float[] DIRECTIONAL_BRIGHTNESS = {0.5f, 1f, 0.7f, 0.7f, 0.6f, 0.6f};

    private static Vector3f[] getShadedColors(final Vector4f color)
    {
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

    private static Vector3f[] getNormals(final PoseStack.Pose pose)
    {
        return Arrays.stream(Direction.values())
                .map(direction ->
                {
                    final Vec3i faceNormal = direction.getUnitVec3i();
                    final Vector3f normal = new Vector3f(faceNormal.getX(), faceNormal.getY(), faceNormal.getZ());
                    normal.mul(pose.normal());
                    return normal;
                }).toArray(Vector3f[]::new);
    }

    private void renderColoredGhost(
            final PoseStack poseStack,
            final MultiBufferSource.BufferSource bufferSource,
            final BlockState state,
            final BlockPos pos,
            final ModelData modelData,
            final Vector4f color,
            final RenderType renderType)
    {
        final ModelManager modelManager = Minecraft.getInstance().getModelManager();
        final BlockStateModelSet blockStateModelSet = modelManager.getBlockStateModelSet();
        final BlockStateModel model = blockStateModelSet.get(state);

        final RandomSource random = RandomSource.create(42);
        final Vector3f[] normals = getNormals(poseStack.last());
        final Vector3f[] shadedColors = getShadedColors(color);
        final Vector4f posVec = new Vector4f();
        final VertexConsumer buffer = bufferSource.getBuffer(renderType);

        for (final Direction direction : Direction.values())
        {
            random.setSeed(42L);
            renderQuadListForDirection(buffer, poseStack.last().pose(), model, state, pos, random, modelData, direction, normals, shadedColors, posVec);
        }

        random.setSeed(42L);
        renderQuadListForDirection(buffer, poseStack.last().pose(), model, state, pos, random, modelData, null, normals, shadedColors, posVec);
    }

    private void renderQuadListForDirection(
            final VertexConsumer buffer,
            final Matrix4f pose,
            final BlockStateModel model,
            final BlockState state,
            final BlockPos pos,
            final RandomSource random,
            final ModelData modelData,
            final Direction direction,
            final Vector3f[] normals,
            final Vector3f[] shadedColors,
            final Vector4f posVec)
    {
        // Get quads for this direction by using a dummy BlockQuadOutput that collects them
        final java.util.List<BakedQuad> quads = new java.util.ArrayList<>();
        final QuadInstance instance = new QuadInstance();
        instance.setColor(ARGB.color(255, 255, 255, 255));
        instance.setLightCoords(LightCoordsUtil.FULL_BRIGHT);
        instance.setOverlayCoords(OverlayTexture.NO_OVERLAY);

        final BlockAndTintGetter dummyGetter = BlockAndTintGetter.EMPTY;
        final MovingBlockRenderState blockAndTintGetter = new MovingBlockRenderState();
        blockAndTintGetter.blockPos = pos;
        blockAndTintGetter.blockState = state;
        blockAndTintGetter.modelData = modelData;

        final ModelBlockRenderer blockRenderer = new ModelBlockRenderer(
                Minecraft.getInstance().options.ambientOcclusion().get(),
                false,
                Minecraft.getInstance().getBlockColors());

        // Collect quads by using a BlockQuadOutput that stores them
        final BlockQuadOutput collector = (x, y, z, quad, inst) ->
        {
            if (direction == null || quad.direction() == direction)
            {
                quads.add(quad);
            }
        };

        blockRenderer.tesselateBlock(
                collector,
                0, 0, 0,
                blockAndTintGetter,
                pos,
                state,
                model,
                42L);

        // Render collected quads with custom color
        for (final BakedQuad quad : quads)
        {
            putColoredBulkData(buffer, pose, quad, shadedColors[quad.direction().ordinal()], normals[quad.direction().ordinal()], posVec);
        }
    }

    private static void putColoredBulkData(
            final VertexConsumer buffer,
            final Matrix4f pose,
            final BakedQuad bakedQuad,
            final Vector3f color,
            final Vector3f normal,
            final Vector4f posVec)
    {
        for (int v = 0; v < 4; ++v)
        {
            final var vertexPos = bakedQuad.position(v);
            posVec.set(vertexPos.x(), vertexPos.y(), vertexPos.z(), 1f);
            posVec.mul(pose);

            final long packedUv = bakedQuad.packedUV(v);
            final float u = UVPair.unpackU(packedUv);
            final float uvV = UVPair.unpackV(packedUv);

            buffer.addVertex(posVec.x(), posVec.y(), posVec.z())
                    .setColor(color.x(), color.y(), color.z(), 1f)
                    .setUv(u, uvV)
                    .setUv1(Short.MAX_VALUE, Short.MAX_VALUE)
                    .setUv2(LightCoordsUtil.block(LightCoordsUtil.FULL_BRIGHT), LightCoordsUtil.sky(LightCoordsUtil.FULL_SKY))
                    .setNormal(normal.x(), normal.y(), normal.z());
        }
    }
}
