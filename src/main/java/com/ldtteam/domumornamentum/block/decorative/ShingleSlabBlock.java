package com.ldtteam.domumornamentum.block.decorative;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.block.AbstractBlockDirectional;
import com.ldtteam.domumornamentum.block.ICachedItemGroupBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.block.types.ShingleSlabShapeType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.recipe.FinishedDORecipe;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.ldtteam.domumornamentum.block.types.ShingleSlabShapeType.*;
import static net.minecraft.core.Direction.*;

/**
 * Decorative block
 */
public class ShingleSlabBlock extends AbstractBlockDirectional<ShingleSlabBlock> implements SimpleWaterloggedBlock, IMateriallyTexturedBlock, ICachedItemGroupBlock, EntityBlock
{
    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
                                                                               .add(new SimpleRetexturableComponent(new ResourceLocation("block/oak_planks"), ModTags.SHINGLES_ROOF, Blocks.OAK_PLANKS))
                                                                               .add(new SimpleRetexturableComponent(new ResourceLocation("block/dark_oak_planks"), ModTags.SHINGLES_SUPPORT, Blocks.DARK_OAK_PLANKS))
                                                                               .build();

    /**
     * The SHAPEs of the shingle slab.
     */
    public static final EnumProperty<ShingleSlabShapeType> SHAPE = EnumProperty.create("shape", ShingleSlabShapeType.class);

    /**
     * Whether the slab contains water
     */
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    /**
     * The hardness this block has.
     */
    private static final float BLOCK_HARDNESS = 3F;

    /**
     * The resistance this block has.
     */
    private static final float RESISTANCE = 1F;

    /**
     * Amount of connections with other shingle slabs.
     */
    private static final int FOUR_CONNECTIONS = 4;
    private static final int THREE_CONNECTIONS = 3;
    private static final int TWO_CONNECTIONS = 2;
    private static final int ONE_CONNECTION = 1;

    private final List<ItemStack> fillItemGroupCache = Lists.newArrayList();

    /**
     * Constructor for the TimberFrame
     */
    public ShingleSlabBlock()
    {
        super(Properties.of().mapColor(MapColor.WOOD).strength(BLOCK_HARDNESS, RESISTANCE));
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
    }

    // Deprecated here just means that you should not use this method when referencing a block, and instead it's blockstate <- Forge's Discord
    @NotNull
    @Override
    public BlockState updateShape(final BlockState stateIn, @NotNull final Direction HORIZONTAL_FACING, @NotNull final BlockState HORIZONTAL_FACINGState, @NotNull final LevelAccessor worldIn, @NotNull final BlockPos currentPos, @NotNull final BlockPos HORIZONTAL_FACINGPos)
    {
        if (stateIn.getValue(WATERLOGGED))
        {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }

        return getSlabShape(stateIn, worldIn, currentPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context)
    {
        @NotNull
        final Direction facing = (context.getPlayer() == null) ? Direction.NORTH : Direction.fromYRot(context.getPlayer().getYRot());
        return getSlabShape(
            this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER),
            context.getLevel(),
            context.getClickedPos());
    }

    /**
     * Check if this slab should be waterlogged, and return a fluid state accordingly
     * @param state the block state
     * @return the fluid state
     */
    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(final BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    /**
     * Make the slab and actual slab shape.
     *
     * @param state   Current block state.
     * @param worldIn The world the block is in.
     * @param pos     The position of the block.
     * @param context The selection context.
     * @return The VoxelShape of the block.
     */
    @NotNull
    @Override
    public VoxelShape getShape(@NotNull final BlockState state, @NotNull final BlockGetter worldIn, @NotNull final BlockPos pos, @NotNull final CollisionContext context)
    {
        return Block.box(0.0D, 0.0D, 0.0D, 15.9D, 7.9D, 15.9D);
    }

    /**
     * Get the step shape of the slab
     *
     * @param state    the state.
     * @param world    the world.
     * @param position the position.Re
     * @return the blockState to use.
     */
    private static BlockState getSlabShape(@NotNull final BlockState state, @NotNull final LevelAccessor world, @NotNull final BlockPos position)
    {
        final boolean north = world.getBlockState(position.north()).getBlock() instanceof ShingleSlabBlock;
        final boolean south = world.getBlockState(position.south()).getBlock() instanceof ShingleSlabBlock;
        final boolean east = world.getBlockState(position.east()).getBlock() instanceof ShingleSlabBlock;
        final boolean west = world.getBlockState(position.west()).getBlock() instanceof ShingleSlabBlock;

        final boolean[] connectors = new boolean[] {north, south, east, west};

        int amount = 0;
        for (final boolean check : connectors)
        {
            if (check)
            {
                amount++;
            }
        }

        BlockState shapeState;
        if (amount == ONE_CONNECTION)
        {
            shapeState = state.setValue(SHAPE, ONE_WAY);
            if (north)
                return shapeState.setValue(FACING, NORTH);
            if (south)
                return shapeState.setValue(FACING, SOUTH);
            if (east)
                return shapeState.setValue(FACING, EAST);
            if (west)
                return shapeState.setValue(FACING, WEST);
        }

        if (amount == TWO_CONNECTIONS)
        {
            if (north && east)
            {
                shapeState = state.setValue(SHAPE, CURVED);
                return shapeState.setValue(FACING, WEST);
            }
            if (north && west)
            {
                shapeState = state.setValue(SHAPE, CURVED);
                return shapeState.setValue(FACING, SOUTH);
            }
            if (south && east)
            {
                shapeState = state.setValue(SHAPE, CURVED);
                return shapeState.setValue(FACING, NORTH);
            }
            if (south && west)
            {
                shapeState = state.setValue(SHAPE, CURVED);
                return shapeState.setValue(FACING, EAST);
            }
            if (north && south)
            {
                shapeState = state.setValue(SHAPE, TWO_WAY);
                return shapeState.setValue(FACING, NORTH);
            }
            if (east && west)
            {
                shapeState = state.setValue(SHAPE, TWO_WAY);
                return shapeState.setValue(FACING, EAST);
            }
        }

        if (amount == THREE_CONNECTIONS)
        {
            shapeState = state.setValue(SHAPE, THREE_WAY);
            if (north && east && west)
            {
                return shapeState.setValue(FACING, NORTH);
            }
            if (south && east && west)
            {
                return shapeState.setValue(FACING, SOUTH);
            }
            if (east && north && south)
            {
                return shapeState.setValue(FACING, EAST);
            }
            if (west && north && south)
            {
                return shapeState.setValue(FACING, WEST);
            }
        }

        if (amount == FOUR_CONNECTIONS)
        {
            shapeState = state.setValue(SHAPE, FOUR_WAY);
            return shapeState;
        }

        return state.setValue(SHAPE, TOP);
    }

    @Override
    public boolean isPathfindable(@NotNull final BlockState state, @NotNull final BlockGetter worldIn, @NotNull final BlockPos pos, @NotNull final PathComputationType type)
    {
        return type == PathComputationType.WATER && worldIn.getFluidState(pos).is(FluidTags.WATER);
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, SHAPE, WATERLOGGED);
    }

    @Override
    public @NotNull List<IMateriallyTexturedBlockComponent> getComponents()
    {
        return COMPONENTS;
    }

    @Override
    public void setPlacedBy(
      @NotNull final Level worldIn, @NotNull final BlockPos pos, @NotNull final BlockState state, @org.jetbrains.annotations.Nullable final LivingEntity placer, @NotNull final ItemStack stack)
    {
        super.setPlacedBy(worldIn, pos, state, placer, stack);

        final CompoundTag textureData = stack.getOrCreateTagElement("textureData");
        final BlockEntity tileEntity = worldIn.getBlockEntity(pos);

        if (tileEntity instanceof MateriallyTexturedBlockEntity)
            ((MateriallyTexturedBlockEntity) tileEntity).updateTextureDataWith(MaterialTextureData.deserializeFromNBT(textureData));
    }


    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(final @NotNull BlockPos blockPos, final @NotNull BlockState blockState)
    {
        return new MateriallyTexturedBlockEntity(blockPos, blockState);
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
                  json.addProperty("count", COMPONENTS.size() * 2);
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
        return getDOExplosionResistance(this, state, level, pos, explosion);
    }

    @Override
    public float getDestroyProgress(@NotNull BlockState state, @NotNull Player player, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return getDODestroyProgress(this, state, player, level, pos);
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @org.jetbrains.annotations.Nullable Entity entity) {
        return getDOSoundType(this, state, level, pos, entity);
    }

    @Override
    public IMateriallyTexturedBlockComponent getMainComponent() {
        return COMPONENTS.get(0);
    }

    @Override
    public void fillItemCategory(final @NotNull NonNullList<ItemStack> items) {
        fillDOItemCategory(this, items, fillItemGroupCache);
    }
}
