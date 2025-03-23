package com.ldtteam.domumornamentum.block.decorative;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.block.AbstractBlockDirectional;
import com.ldtteam.domumornamentum.block.ICachedItemGroupBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.block.types.CornerPanelShapeType;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static net.minecraft.world.level.block.Blocks.OAK_PLANKS;

public class CornerPanelBlock extends AbstractBlockDirectional<CornerPanelBlock> implements IMateriallyTexturedBlock, ICachedItemGroupBlock, SimpleWaterloggedBlock, EntityBlock
{
    public static final EnumProperty<CornerPanelShapeType> SHAPE_TYPE  = EnumProperty.create("shape", CornerPanelShapeType.class);
    public static final BooleanProperty                    WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
                                                                               .add(new SimpleRetexturableComponent(new ResourceLocation("minecraft:block/oak_planks"),
                                                                                 ModTags.TRAPDOORS_MATERIALS,
                                                                                 OAK_PLANKS))
                                                                               .build();

    private static final VoxelShape FULL_UP_AABB    = Block.box(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape FULL_DOWN_AABB  = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
    private static final VoxelShape FULL_NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    private static final VoxelShape FULL_EAST_AABB  = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape FULL_SOUTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape FULL_WEST_AABB  = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);

    private static final Map<CornerPanelShapeType, Map<Direction, VoxelShape>> innerModelShapes = new HashMap<>();
    static
    {
        final Map<Direction, VoxelShape> lowerFullShapes = new HashMap<>();
        lowerFullShapes.put(Direction.NORTH, Shapes.or(FULL_DOWN_AABB, FULL_NORTH_AABB));
        lowerFullShapes.put(Direction.EAST, Shapes.or(FULL_DOWN_AABB, FULL_EAST_AABB));
        lowerFullShapes.put(Direction.SOUTH, Shapes.or(FULL_DOWN_AABB, FULL_SOUTH_AABB));
        lowerFullShapes.put(Direction.WEST, Shapes.or(FULL_DOWN_AABB, FULL_WEST_AABB));
        innerModelShapes.put(CornerPanelShapeType.LOWER, lowerFullShapes);

        final Map<Direction, VoxelShape> centerFullShapes = new HashMap<>();
        centerFullShapes.put(Direction.NORTH, Shapes.or(FULL_NORTH_AABB, FULL_WEST_AABB));
        centerFullShapes.put(Direction.EAST, Shapes.or(FULL_EAST_AABB, FULL_NORTH_AABB));
        centerFullShapes.put(Direction.SOUTH, Shapes.or(FULL_SOUTH_AABB, FULL_EAST_AABB));
        centerFullShapes.put(Direction.WEST, Shapes.or(FULL_WEST_AABB, FULL_SOUTH_AABB));
        innerModelShapes.put(CornerPanelShapeType.CENTER, centerFullShapes);

        final Map<Direction, VoxelShape> upperFullShapes = new HashMap<>();
        upperFullShapes.put(Direction.NORTH, Shapes.or(FULL_UP_AABB, FULL_NORTH_AABB));
        upperFullShapes.put(Direction.EAST, Shapes.or(FULL_UP_AABB, FULL_EAST_AABB));
        upperFullShapes.put(Direction.SOUTH, Shapes.or(FULL_UP_AABB, FULL_SOUTH_AABB));
        upperFullShapes.put(Direction.WEST, Shapes.or(FULL_UP_AABB, FULL_WEST_AABB));
        innerModelShapes.put(CornerPanelShapeType.UPPER, upperFullShapes);
    }

    private final List<ItemStack> fillItemGroupCache = Lists.newArrayList();

    public CornerPanelBlock()
    {
        super(Properties.of().mapColor(MapColor.WOOD).strength(3.0F).noOcclusion().isValidSpawn((state, blockGetter, pos, type) -> false));
        this.registerDefaultState(this.defaultBlockState().setValue(SHAPE_TYPE, CornerPanelShapeType.CENTER).setValue(WATERLOGGED, false));
    }

    @Override
    public BlockState getStateForPlacement(@NotNull final BlockPlaceContext context)
    {
        BlockState blockstate = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        Direction direction = context.getClickedFace();

        if (direction.getAxis().isHorizontal())
        {
            blockstate = blockstate.setValue(SHAPE_TYPE, CornerPanelShapeType.CENTER);
        }
        else if (direction.equals(Direction.UP))
        {
            blockstate = blockstate.setValue(SHAPE_TYPE, CornerPanelShapeType.LOWER);
        }
        else
        {
            blockstate = blockstate.setValue(SHAPE_TYPE, CornerPanelShapeType.UPPER);
        }

        return blockstate.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public void setPlacedBy(
      final @NotNull Level worldIn,
      final @NotNull BlockPos pos,
      final @NotNull BlockState state,
      final @Nullable LivingEntity placer,
      final @NotNull ItemStack stack)
    {
        super.setPlacedBy(worldIn, pos, state, placer, stack);

        worldIn.setBlock(pos, state, Block.UPDATE_ALL);

        final CompoundTag textureData = stack.getOrCreateTagElement("textureData");
        final BlockEntity tileEntity = worldIn.getBlockEntity(pos);

        if (tileEntity instanceof MateriallyTexturedBlockEntity)
        {
            ((MateriallyTexturedBlockEntity) tileEntity).updateTextureDataWith(MaterialTextureData.deserializeFromNBT(textureData));
        }
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.@NotNull Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, SHAPE_TYPE, WATERLOGGED);
    }

    @NotNull
    @Override
    public BlockState updateShape(
      BlockState state,
      @NotNull Direction direction,
      @NotNull BlockState stateOut,
      @NotNull LevelAccessor level,
      @NotNull BlockPos pos,
      @NotNull BlockPos pos2)
    {
        if (state.getValue(WATERLOGGED))
        {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, direction, stateOut, level, pos, pos2);
    }

    @NotNull
    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    @NotNull
    public List<ItemStack> getDrops(final @NotNull BlockState state, final @NotNull LootParams.Builder builder)
    {
        return BlockUtils.getMaterializedItemStack(builder);
    }

    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context)
    {
        return innerModelShapes.get(state.getValue(SHAPE_TYPE)).get(state.getValue(FACING));
    }

    @Override
    public float getDestroyProgress(@NotNull BlockState state, @NotNull Player player, @NotNull BlockGetter level, @NotNull BlockPos pos)
    {
        return getDODestroyProgress(super::getDestroyProgress, state, player, level, pos);
    }

    @Override
    public void fillItemCategory(final @NotNull NonNullList<ItemStack> items)
    {
        fillDOItemCategory(this, items, fillItemGroupCache);
    }

    @Override
    public void resetCache()
    {
        fillItemGroupCache.clear();
    }

    @Override
    @NotNull
    public Block getBlock()
    {
        return this;
    }

    @Override
    @NotNull
    public List<IMateriallyTexturedBlockComponent> getComponents()
    {
        return COMPONENTS;
    }

    @Override
    @NotNull
    public Collection<FinishedRecipe> getValidCutterRecipes()
    {
        final List<FinishedRecipe> recipes = new ArrayList<>();

        recipes.add(new FinishedDORecipe()
        {
            @Override
            public void serializeRecipeData(final @NotNull JsonObject jsonObject)
            {
                jsonObject.addProperty("block", Objects.requireNonNull(getRegistryName(getBlock())).toString());
                jsonObject.addProperty("count", COMPONENTS.size() * 4);
            }

            @Override
            @NotNull
            public ResourceLocation getId()
            {
                return Objects.requireNonNull(getRegistryName(getBlock()));
            }
        });

        return recipes;
    }

    @Override
    public IMateriallyTexturedBlockComponent getMainComponent()
    {
        return COMPONENTS.get(0);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final @NotNull BlockPos blockPos, final @NotNull BlockState blockState)
    {
        return new MateriallyTexturedBlockEntity(blockPos, blockState);
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion)
    {
        return getDOExplosionResistance(super::getExplosionResistance, state, level, pos, explosion);
    }

    @Override
    public ItemStack getCloneItemStack(final BlockState state, final HitResult target, final BlockGetter world, final BlockPos pos, final Player player)
    {
        return BlockUtils.getMaterializedItemStack(player, world, pos);
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity)
    {
        return getDOSoundType(super::getSoundType, state, level, pos, entity);
    }
}
