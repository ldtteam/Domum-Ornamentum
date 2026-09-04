package com.ldtteam.domumornamentum.client.render;

import com.ldtteam.domumornamentum.util.ItemStackUtils;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.QuadInstance;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
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
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ModelGhostRenderer {
    private static final ModelGhostRenderer INSTANCE = new ModelGhostRenderer();
    private static final ByteBufferBuilder BUFFER_BUILDER = new ByteBufferBuilder(2_097_152);

    private ModelGhostRenderer() {}

    public static ModelGhostRenderer getInstance() {
        return INSTANCE;
    }

    public void renderGhost(
        PoseStack poseStack,
        ItemStack renderStack,
        Vec3 targetedRenderPos,
        BlockHitResult blockHitResult,
        ClientLevel level,
        boolean ignoreDepth
    ) {
        if (!(renderStack.getItem() instanceof BlockItem blockItem)) {
            return;
        }

        var player = Objects.requireNonNull(Minecraft.getInstance().player);
        var hand = Objects.requireNonNull(ItemStackUtils.getHandWithMateriallyTexturedItemStackFromPlayer(player));
        BlockPlaceContext context = new BlockPlaceContext(player, hand, renderStack, blockHitResult);
        BlockState placementState = blockItem.getBlock().getStateForPlacement(context);
        if (placementState == null) {
            return;
        }

        placementState = renderStack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY).apply(placementState);
        if (blockItem.getBlock() instanceof EntityBlock entityBlock) {
            BlockEntity blockEntity = entityBlock.newBlockEntity(context.getClickedPos(), placementState);
            if (blockEntity != null) {
                blockEntity.applyComponentsFromItemStack(renderStack);
            }
        }

        BlockPos previewPos = blockHitResult.getBlockPos();
        BlockStateModel model = Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(placementState);
        List<BlockStateModelPart> parts = new ArrayList<>();
        model.collectParts(level, previewPos, placementState, RandomSource.create(42L), parts);
        if (parts.isEmpty()) {
            return;
        }

        poseStack.pushPose();
        Vec3 camera = Minecraft.getInstance().gameRenderer.mainCamera().position();
        poseStack.translate(
            targetedRenderPos.x - camera.x - 0.000125F,
            targetedRenderPos.y - camera.y + 0.000125F,
            targetedRenderPos.z - camera.z - 0.000125F
        );
        poseStack.scale(1.001F, 1.001F, 1.001F);

        RenderType renderType = ModRenderTypes.GHOST_BLOCK_PREVIEW.get();
        BufferBuilderTransparent buffer = new BufferBuilderTransparent(
            BUFFER_BUILDER, renderType.primitiveTopology(), renderType.format(), 0.5F
        );
        QuadInstance quadInstance = new QuadInstance();
        quadInstance.setColor(ARGB.color(128, 255, 255, 255));
        quadInstance.setLightCoords(15728880);

        PoseStack.Pose pose = poseStack.last();
        for (BlockStateModelPart part : parts) {
            for (Direction direction : Direction.values()) {
                putQuads(buffer, pose, quadInstance, part.getQuads(direction));
            }
            putQuads(buffer, pose, quadInstance, part.getQuads(null));
        }

        buffer.buildOrThrow();
        poseStack.popPose();
    }

    private static void putQuads(
        VertexConsumer consumer,
        PoseStack.Pose pose,
        QuadInstance quadInstance,
        List<net.minecraft.client.resources.model.geometry.BakedQuad> quads
    ) {
        for (net.minecraft.client.resources.model.geometry.BakedQuad quad : quads) {
            consumer.putBakedQuad(pose, quad, quadInstance);
        }
    }

    private static final class BufferBuilderTransparent extends BufferBuilder {
        private final float alphaScale;

        private BufferBuilderTransparent(ByteBufferBuilder buffer, com.mojang.blaze3d.PrimitiveTopology mode, VertexFormat format, float alphaScale) {
            super(buffer, mode, format);
            this.alphaScale = Mth.clamp(alphaScale, 0.0F, 1.0F);
        }

        @Override
        public @NotNull VertexConsumer setColor(int red, int green, int blue, int alpha) {
            return super.setColor(red, green, blue, scaleAlpha(alpha));
        }

        @Override
        public VertexConsumer setColor(int argb) {
            int alpha = ARGB.alpha(argb);
            return super.setColor(ARGB.color(scaleAlpha(alpha), ARGB.red(argb), ARGB.green(argb), ARGB.blue(argb)));
        }

        @Override
        public void addVertex(float x, float y, float z, int color, float u, float v, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ) {
            super.addVertex(x, y, z, ARGB.color(scaleAlpha(ARGB.alpha(color)), ARGB.red(color), ARGB.green(color), ARGB.blue(color)), u, v, overlayUV, lightmapUV, normalX, normalY, normalZ);
        }

        private int scaleAlpha(int alpha) {
            return Math.round(alpha * alphaScale);
        }
    }
}
