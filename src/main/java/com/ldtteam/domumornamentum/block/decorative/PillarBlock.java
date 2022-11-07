package com.ldtteam.domumornamentum.block.decorative;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.block.*;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.block.types.PillarShapeType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.recipe.ModRecipeSerializers;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class PillarBlock extends AbstractBlock<PillarBlock> implements IMateriallyTexturedBlock, ICachedItemGroupBlock, EntityBlock
{

    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
        .add(new SimpleRetexturableComponent(new ResourceLocation("block/oak_planks"), ModTags.PILLAR_MATERIALS, Blocks.OAK_PLANKS))
        .build();

    private final List<ItemStack> fillItemGroupCache = Lists.newArrayList();

    /**
     * the shape of the column section
     */
    private static final VoxelShape PILLAR = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    /**
     * The hardness this block has.
     */
    private static final float                      BLOCK_HARDNESS = 3F;

    /**
     * The resistance this block has.
     */
    private static final float                      RESISTANCE     = 1F;
    /**
     * the types of pillar shapes that can be rendered
     */
    private static final EnumProperty<PillarShapeType> column = EnumProperty.create("column", PillarShapeType.class);

    /**
     * base constructor
     */
    public PillarBlock()
    {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(BLOCK_HARDNESS, RESISTANCE));
        this.registerDefaultState(this.stateDefinition.any().setValue(column,PillarShapeType.FULL_PILLAR));
    }

    /**
     * Returns the internal shape of the pillar column.
     * @param state   The current blockstate.
     * @param worldIn The world the block is in.
     * @param pos The position of the block.
     * @param context The selection context.
     * @return The VoxelShape of the Block
     */
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context)
    {
        return PILLAR;
    }

    /**
     * Adds the BlockState property "column", used to determine the correct shape to render.
     * @param builder the state builder used to create the BlockStateDefinition
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(column);
    }

    /**
     * Finds the correct blockstate on placement by checking the blocks above and below the clicked position from the context. Then calls updateAbove and upDateBelow
     * to correct their blockstates based on their upper/lower neighbor and this block.
     * @param context The world and location data for the block's placement.
     * @return The final blockstate for placement
     */
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockPos blockAbove = blockPos.above();
        BlockPos blockBelow = blockPos.below();
        BlockState stateAbove = level.getBlockState(blockAbove);
        BlockState stateBelow = level.getBlockState(blockBelow);

        Boolean base = this.isMatchingPillar(stateBelow);
        Boolean capital = this.isMatchingPillar(stateAbove);
        if (base){
            updateBelow(level, blockBelow,stateBelow);
        }
        if (capital){
            updateAbove(level,blockAbove, stateAbove);
        }
        return updateShape(this.defaultBlockState(), base, capital);

    }

    /**
     * Called to correct the blockstates of surrounding blocks when blocks are destroyed.
     * @param state The current blockstate.
     * @param world The world the block is in.
     * @param pos The location of the block in the world.
     * @param player The destroying entity, may be null.
     * @param willHarvest True if Block.harvestBlock will be called after this, if the return in true. Can be useful to delay the destruction of tile entities till after harvestBlock
     * @param fluid The fluid to replace the block with if waterlogged == true.
     * @return If true, the block will be destroyed
     */
    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid)
    {
        Comparable<PillarShapeType> column_property = state.getValue(column);
        if (column_property == PillarShapeType.PILLAR_COLUMN)
        {
            if (isMatchingPillar(world.getBlockState(pos.above())) && world.getBlockState(pos.above()).getValue(column)== PillarShapeType.PILLAR_COLUMN)
            {
                world.setBlockAndUpdate(pos.above(),state.setValue(column,PillarShapeType.PILLAR_BASE));
            }
            else if (isMatchingPillar(world.getBlockState(pos.above())))
            {
                world.setBlockAndUpdate(pos.above(),state.setValue(column,PillarShapeType.FULL_PILLAR));
            }

            if (isMatchingPillar(world.getBlockState(pos.below())) && world.getBlockState(pos.below()).getValue(column)== PillarShapeType.PILLAR_COLUMN)
            {
                world.setBlockAndUpdate(pos.below(),state.setValue(column,PillarShapeType.PILLAR_CAPITAL));
            }
            else if (isMatchingPillar(world.getBlockState(pos.below())))
            {
                world.setBlockAndUpdate(pos.below(),state.setValue(column,PillarShapeType.FULL_PILLAR));
            }
        }
        if (isMatchingPillar(world.getBlockState(pos.above())))
        {
            if (column_property == PillarShapeType.PILLAR_BASE)
            {
                if (world.getBlockState(pos.above()).getValue(column) == PillarShapeType.PILLAR_COLUMN)
                {
                    world.setBlockAndUpdate(pos.above(), state.setValue(column, PillarShapeType.PILLAR_BASE));
                } else
                {
                    world.setBlockAndUpdate(pos.above(), state.setValue(column, PillarShapeType.FULL_PILLAR));
                }
            }
        }
        if (isMatchingPillar(world.getBlockState(pos.below())))
        {
            if (column_property == PillarShapeType.PILLAR_CAPITAL)
            {
                if (world.getBlockState(pos.below()).getValue(column) == PillarShapeType.PILLAR_COLUMN)
                {
                    world.setBlockAndUpdate(pos.below(), state.setValue(column, PillarShapeType.PILLAR_CAPITAL));
                } else
                {
                    world.setBlockAndUpdate(pos.below(), state.setValue(column, PillarShapeType.FULL_PILLAR));
                }
            }
        }
        return super.onDestroyedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    /**
     * Called on block placement to update the blockstate of the neighbor below the current block.
     * @param level The world the block is in.
     * @param blockPos The location of the block to be updated.
     * @param state The current blockstate.
     */
    private void updateBelow(Level level, BlockPos blockPos, BlockState state)
    {
        BlockPos checkBelow = blockPos.below();
        if (isMatchingPillar(level.getBlockState(checkBelow)))
        {
            level.setBlockAndUpdate(blockPos, state.setValue(column,PillarShapeType.PILLAR_COLUMN));
        }
        else
        {
            level.setBlockAndUpdate(blockPos,state.setValue(column,PillarShapeType.PILLAR_BASE));
        }
    }
    /**
     * Called on block placement to update the blockstate of the neighbor above the current block.
     * @param level The world the block is in.
     * @param blockPos The location of the block to be updated.
     * @param state The current blockstate.
     */
    private void updateAbove(Level level, BlockPos blockPos,BlockState state)
    {
        BlockPos checkAbove = blockPos.above();
        if (isMatchingPillar(level.getBlockState(checkAbove)))
        {
            level.setBlockAndUpdate(blockPos, state.setValue(column,PillarShapeType.PILLAR_COLUMN));
        }
        else
        {
            level.setBlockAndUpdate(blockPos,state.setValue(column,PillarShapeType.PILLAR_CAPITAL));
        }
    }

    /**
     * Called when block is placed to set the correct blockstate for the current block.
     * @param blockState The current blockstate.
     * @param base If true, there is a PillarBlock below this one.
     * @param capital If true, there is a PillarBlock above this one.
     * @return The correct blockstate for placement
     */
    private BlockState updateShape(BlockState blockState, Boolean base, Boolean capital)
    {
        if (isMatchingPillar(blockState))
        {
            if (base && capital)
            {
                return blockState.setValue(column, PillarShapeType.PILLAR_COLUMN);
            }
            if (!base && capital)
            {
                return blockState.setValue(column, PillarShapeType.PILLAR_BASE);
            }
            if (base && !capital)
            {
                return blockState.setValue(column, PillarShapeType.PILLAR_CAPITAL);
            }
            blockState.setValue(column, PillarShapeType.FULL_PILLAR);
        }
        return blockState;
    }

    /**
     * checks that the block passed in is allowed to connect.
     * @param state The blockstate to be checked.
     * @return If true, allows connection to this block
     */
    private boolean isMatchingPillar(@NotNull final BlockState state)
    {
        return state.getBlock() == this;
    }

    @Override
    public @NotNull List<IMateriallyTexturedBlockComponent> getComponents()
    {
        return COMPONENTS;
    }

    @Override
    public void fillItemCategory(final @NotNull CreativeModeTab group, final @NotNull NonNullList<ItemStack> items)
    {
        if (!fillItemGroupCache.isEmpty()) {
            items.addAll(fillItemGroupCache);
            return;
        }

        try {
            final ItemStack result = new ItemStack(this);

            fillItemGroupCache.add(result);
        } catch (IllegalStateException exception)
        {
            //Ignored. Thrown during start up.
        }

        items.addAll(fillItemGroupCache);
    }

    @Override
    public void setPlacedBy(
            final @NotNull Level worldIn, final @NotNull BlockPos pos, final @NotNull BlockState state, @Nullable final LivingEntity placer, final @NotNull ItemStack stack)
    {
        super.setPlacedBy(worldIn, pos, state, placer, stack);

        final CompoundTag textureData = stack.getOrCreateTagElement("textureData");
        final BlockEntity tileEntity = worldIn.getBlockEntity(pos);

        if (tileEntity instanceof MateriallyTexturedBlockEntity)
            ((MateriallyTexturedBlockEntity) tileEntity).updateTextureDataWith(MaterialTextureData.deserializeFromNBT(textureData));
    }

    @Nullable
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
    public @NotNull List<ItemStack> getDrops(final @NotNull BlockState state, final @NotNull LootContext.Builder builder)
    {
        return BlockUtils.getMaterializedItemStack(builder);
    }

    @Override
    public ItemStack getCloneItemStack(final BlockState state, final HitResult target, final BlockGetter world, final BlockPos pos, final Player player)
    {
        return BlockUtils.getMaterializedItemStack(player, world, pos);
    }

    @Override
    public @NotNull Block getBlock() { return this; }

    @NotNull
    public Collection<FinishedRecipe> getValidCutterRecipes()
    {
        return Lists.newArrayList(
                new FinishedRecipe()
                {
                    @Override
                    public void serializeRecipeData(final @NotNull JsonObject json)
                    {
                        json.addProperty("count", COMPONENTS.size() * 3);
                    }

                    @Override
                    public @NotNull ResourceLocation getId()
                    {
                        return Objects.requireNonNull(getRegistryName(getBlock()));
                    }

                    @Override
                    public @NotNull RecipeSerializer<?> getType()
                    {
                        return ModRecipeSerializers.ARCHITECTS_CUTTER.get();
                    }

                    @Nullable
                    @Override
                    public JsonObject serializeAdvancement()
                    {
                        return null;
                    }

                    @Nullable
                    @Override
                    public ResourceLocation getAdvancementId()
                    {
                        return null;
                    }
                }
        );
    }
}
