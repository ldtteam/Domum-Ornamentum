package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;
/**
 * The abstract class for structurize-added posts.

 */
public abstract class AbstractPostBlock<B extends AbstractPostBlock<B>> extends RotatedPillarBlock implements IDOBlock<B>
{

    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final BooleanProperty    WATERLOGGED = BlockStateProperties.WATERLOGGED;
    /** collision objects
    * */
    protected static final VoxelShape Y_AXIS_AABB = Block.box(6.0D, 0.0D, 6.0D, 10.D, 16.0D, 10.0D);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0D, 6.0D, 0.0D, 10.D, 10.0D, 16.0D);
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
    public AbstractPostBlock(final Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE));
    }


    @Override
    public ResourceLocation getRegistryName()
    {
        return getRegistryName(this);
    }


    @NotNull
    @Override
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context)
    {

        switch (state.getValue(AXIS)) {
            case X:
            default:
                return X_AXIS_AABB;
            case Z:
                return Z_AXIS_AABB;
            case Y:
                return Y_AXIS_AABB;
        }

    }

    @Override
    public BlockState getStateForPlacement(@NotNull final BlockPlaceContext context)
    {
        BlockState blockstate = this.defaultBlockState();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        /**
            Direction direction = context.getClickedFace();

         This is for open trap doors


        if (direction.getAxis().isHorizontal())
        {
            blockstate = blockstate.setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(HALF, direction == Direction.UP ? Half.BOTTOM : Half.TOP).setValue(
                    OPEN, true);
        }
        else
        {
            blockstate = blockstate.setValue(FACING, context.getPlayer().getDirection()).setValue(HALF, direction == Direction.DOWN ? Half.TOP : Half.BOTTOM).setValue(OPEN, false);
        }
 **/


        return blockstate.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_57561_)
    {
        p_57561_.add(FACING, HALF, WATERLOGGED);
    }

    @NotNull
    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
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
}