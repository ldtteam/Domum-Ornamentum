package com.ldtteam.domumornamentum.block.decorative;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.block.AbstractBlock;
import com.ldtteam.domumornamentum.block.ICachedItemGroupBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.DynamicTimberFrameBlockEntity;
import com.ldtteam.domumornamentum.recipe.FinishedDORecipe;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Decorative block
 */
public class DynamicTimberFrameBlock extends AbstractBlock<DynamicTimberFrameBlock> implements IMateriallyTexturedBlock, ICachedItemGroupBlock, EntityBlock
{
    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
        .add(new SimpleRetexturableComponent(new ResourceLocation("block/oak_planks"), ModTags.TIMBERFRAMES_FRAME, Blocks.OAK_PLANKS))
        .add(new SimpleRetexturableComponent(new ResourceLocation("block/dark_oak_planks"), ModTags.TIMBERFRAMES_CENTER, Blocks.WHITE_TERRACOTTA))
        .build();

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    /**
     * The hardness this block has.
     */
    private static final float BLOCK_HARDNESS = 3F;

    /**
     * The resistance this block has.
     */
    private static final float RESISTANCE = 1F;

    /**
     * Item group cache for recipe.
     */
    private final List<ItemStack> fillItemGroupCache = Lists.newArrayList();

    /**
     * Enum defining the different block offsets we care about.
     */
    public enum Offset
    {
        UP(BlockPos::above),
        DOWN(BlockPos::below),
        NORTH(BlockPos::north),
        EAST(BlockPos::east),
        SOUTH(BlockPos::south),
        WEST(BlockPos::west),
        UP_EAST(p -> p.above().east()),
        UP_WEST(p -> p.above().west()),
        UP_NORTH(p -> p.above().north()),
        UP_SOUTH(p -> p.above().south()),
        DOWN_EAST(p -> p.below().east()),
        DOWN_WEST(p -> p.below().west()),
        DOWN_NORTH(p -> p.below().north()),
        DOWN_SOUTH(p -> p.below().south());

        /**
         * Translation function this direction means in relation to a blockpos change.
         */
        private final Function<BlockPos, BlockPos> posTranslationFunction;

        /**
         * Create a new offset.
         * @param changeFunction and its blockpos translation.
         */
        Offset(final Function<BlockPos, BlockPos> changeFunction)
        {
            this.posTranslationFunction = changeFunction;
        }

        /**
         * Inversion of offset.
         * @return the inverted offset.
         */
        public Offset inverted()
        {
            return switch (this)
            {
                case UP -> DOWN;
                case DOWN -> UP;
                case NORTH -> SOUTH;
                case SOUTH -> NORTH;
                case EAST -> WEST;
                case WEST -> EAST;
                case UP_NORTH -> DOWN_SOUTH;
                case UP_EAST -> DOWN_WEST;
                case UP_SOUTH -> DOWN_NORTH;
                case UP_WEST -> DOWN_EAST;
                case DOWN_SOUTH -> UP_NORTH;
                case DOWN_WEST -> UP_EAST;
                case DOWN_NORTH -> UP_SOUTH;
                case DOWN_EAST -> UP_WEST;
            };
        }

        /**
         * Apply translation to blockpos.
         * @param pos input pos.
         * @return changed output pos.
         */
        public BlockPos applyToBlockPos(final BlockPos pos)
        {
            return posTranslationFunction.apply(pos);
        }

        public Offset rotate()
        {
            return switch (this)
            {
                case NORTH -> EAST;
                case SOUTH -> WEST;
                case EAST -> SOUTH;
                case WEST -> NORTH;
                case UP_NORTH -> UP_EAST;
                case UP_EAST -> UP_SOUTH;
                case UP_SOUTH -> UP_WEST;
                case UP_WEST -> UP_NORTH;
                case DOWN_SOUTH -> DOWN_WEST;
                case DOWN_WEST -> DOWN_NORTH;
                case DOWN_NORTH -> DOWN_EAST;
                case DOWN_EAST -> DOWN_SOUTH;
                default -> this;
            };
        }
    }

    /**
     * Constructor for the TimberFrame
     */
    public DynamicTimberFrameBlock()
    {
        super(Properties.of().mapColor(MapColor.WOOD).pushReaction(PushReaction.PUSH_ONLY).strength(BLOCK_HARDNESS, RESISTANCE).noOcclusion());
    }

    @Override
    public boolean shouldDisplayFluidOverlay(final BlockState state, final BlockAndTintGetter level, final BlockPos pos, final FluidState fluidState)
    {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(@NotNull final StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
    }

    @Override
    public @NotNull List<IMateriallyTexturedBlockComponent> getComponents()
    {
        return COMPONENTS;
    }

    @Override
    public void setPlacedBy(final Level worldIn, final BlockPos pos, final BlockState state, @Nullable final LivingEntity placer, final ItemStack stack)
    {
        super.setPlacedBy(worldIn, pos, state, placer, stack);

        final BlockEntity tileEntity = worldIn.getBlockEntity(pos);

        if (tileEntity instanceof DynamicTimberFrameBlockEntity timberFrameBlockEntity)
        {
            MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(stack.getOrCreateTagElement("textureData"));
            if (textureData.isEmpty()) {
                textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(this);
            }

            timberFrameBlockEntity.updateTextureDataWith(textureData);
            timberFrameBlockEntity.refreshTextureCache();

            for (Offset offset: Offset.values())
            {
                updateNeighbor(timberFrameBlockEntity, worldIn.getBlockEntity(offset.applyToBlockPos(pos)), offset, true);
            }
        }
    }

    /**
     * Utility method to update neighbor with new neighborhood info.
     * @param thisEntity the blockentity being changed.
     * @param neighborEntity the blockentity that is being notified.
     * @param offset the offset at which the neighbor is at.
     * @param added if this blockentity was being added or removed.
     */
    private static void updateNeighbor(final DynamicTimberFrameBlockEntity thisEntity, final BlockEntity neighborEntity, final Offset offset, final boolean added)
    {
        if (neighborEntity != null && neighborEntity instanceof DynamicTimberFrameBlockEntity timberFrameBlockEntity)
        {
            timberFrameBlockEntity.onNeighborUpdate(thisEntity, offset.inverted(), added);
            if (thisEntity != null && timberFrameBlockEntity.getFrameBlock() == thisEntity.getFrameBlock() && timberFrameBlockEntity.getCenterBlock() == thisEntity.getCenterBlock())
            {
                thisEntity.onNeighborUpdate(thisEntity, offset, added);
            }
        }
    }

    @Override
    public BlockState updateShape(
        final BlockState stateIn,
        @NotNull final Direction direction,
        @NotNull final BlockState directionState,
        @NotNull final LevelAccessor worldIn,
        @NotNull final BlockPos currentPos,
        @NotNull final BlockPos directionPos)
    {
        final BlockEntity tileEntity = worldIn.getBlockEntity(currentPos);

        if (tileEntity instanceof DynamicTimberFrameBlockEntity timberFrameBlockEntity)
        {
            for (Offset offset : Offset.values())
            {
                updateNeighbor(timberFrameBlockEntity, worldIn.getBlockEntity(offset.applyToBlockPos(currentPos)), offset, true);
            }
        }

        return stateIn;
    }

    @Override
    public void onRemove(final BlockState state, final Level worldIn, final BlockPos pos, final BlockState otherState, final boolean drop)
    {
        super.onRemove(state, worldIn, pos, otherState, drop);

        for (Offset offset: Offset.values())
        {
            updateNeighbor(null, worldIn.getBlockEntity(offset.applyToBlockPos(pos)), offset, false);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final @NotNull BlockPos blockPos, final @NotNull BlockState blockState)
    {
        return new DynamicTimberFrameBlockEntity(blockPos, blockState);
    }

    @Override
    public void resetCache()
    {
        fillItemGroupCache.clear();
    }

    @Override
    public @NotNull List<ItemStack> getDrops(final @NotNull BlockState state, final @NotNull LootParams.Builder builder)
    {
        return BlockUtils.getMaterializedItemStack(builder);
    }

    @Override
    public ItemStack getCloneItemStack(final BlockState state, final HitResult target, final BlockGetter world, final BlockPos pos, final Player player)
    {
        return BlockUtils.getMaterializedItemStack(player, world, pos);
    }

    @Override
    public @NotNull Block getBlock()
    {
        return this;
    }

    @NotNull
    public Collection<FinishedRecipe> getValidCutterRecipes() {
        return Lists.newArrayList(
          new FinishedDORecipe() {
              @Override
              public void serializeRecipeData(final @NotNull JsonObject json)
              {
                  json.addProperty("count", COMPONENTS.size());
              }

              @Override
              public @NotNull ResourceLocation getId()
              {
                  return Objects.requireNonNull(getRegistryName(getBlock()));
              }
          }
        );
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return getDOExplosionResistance(super::getExplosionResistance, state, level, pos, explosion);
    }

    @Override
    public float getDestroyProgress(@NotNull BlockState state, @NotNull Player player, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return getDODestroyProgress(super::getDestroyProgress, state, player, level, pos);
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return getDOSoundType(super::getSoundType, state, level, pos, entity);
    }

    @Override
    public IMateriallyTexturedBlockComponent getMainComponent() {
        return COMPONENTS.get(1);
    }

    @Override
    public void fillItemCategory(final @NotNull NonNullList<ItemStack> items) {
        fillDOItemCategory(this, items, fillItemGroupCache);
    }

    @Override
    public BlockState rotate(final BlockState state, final LevelAccessor level, final BlockPos pos, final Rotation direction)
    {
        if (level.getBlockEntity(pos) instanceof DynamicTimberFrameBlockEntity dynamicTimberFrameBlockEntity) {
            dynamicTimberFrameBlockEntity.rotate(direction.ordinal());
        }
        return super.rotate(state, level, pos, direction);
    }

    @Override
    public BlockState rotate(final BlockState p_60530_, final Rotation p_60531_)
    {
        return super.rotate(p_60530_, p_60531_);
    }
}
