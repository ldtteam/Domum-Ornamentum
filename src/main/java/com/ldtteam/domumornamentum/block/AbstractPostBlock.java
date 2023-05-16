package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import com.ldtteam.domumornamentum.block.types.PostType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

/**
 * The abstract class for structurize-added posts.
 *
 * Singular fence-like block, one material channel, custom collision, placing alignment with getclickedface() direction
 */
public abstract class AbstractPostBlock<B extends AbstractPostBlock<B>> extends HorizontalDirectionalBlock implements IDOBlock<B> {

    public static final EnumProperty<PostType> TYPE = EnumProperty.create("type", PostType.class);
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty    UPRIGHT = BlockStateProperties.CONDITIONAL;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape Y_AXIS_AABB = Block.box(6.0D, 0.0D, 6.0D, 10.D, 16.0D, 10.0D);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0D, 6.0D, 0.0D, 10.D, 10.0D, 16.0D);
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
    protected static VoxelShape X_DOUBLE_AABB = Shapes.join(
            Block.box(0, 6.75, 2.75, 16, 9.25, 5.25),
            Block.box(0, 6.75, 10.75, 16, 9.25, 13.25), BooleanOp.OR);
    protected static VoxelShape Y_DOUBLE_AABB = Shapes.join(
            Block.box(2.75, 0, 6.75, 5.25, 16, 9.25),
            Block.box(10.75, 0, 6.75, 13.25, 16, 9.25), BooleanOp.OR);
    protected static VoxelShape Z_DOUBLE_AABB = Shapes.join(
            Block.box(2.75, 6.75, 0, 5.25, 9.25, 16),
            Block.box(10.75, 6.75, 0, 13.25, 9.25, 16), BooleanOp.OR);
    protected static VoxelShape XY_DOUBLE_AABB = Shapes.join(
            Block.box(6.75, 0, 10.75, 9.25, 16, 13.25),
            Block.box(6.75, 0, 2.75, 9.25, 16, 5.25), BooleanOp.OR);

    protected static VoxelShape X_QUAD_AABB = Shapes.or(
            Block.box(0, 0.75, 0.75, 16, 3.25, 3.25),
            Block.box(0, 12.75, 0.75, 16, 15.25, 3.25),
            Block.box(0, 0.75, 12.75, 16, 3.25, 15.25),
            Block.box(0, 12.75, 12.75, 16, 15.25, 15.25));
    protected static VoxelShape Y_QUAD_AABB = Shapes.or(
            Block.box(0.75, 0, 12.75, 3.25, 16, 15.25),
            Block.box(0.75, 0, 0.75, 3.25, 16, 3.25),
            Block.box(12.75, 0, 12.75, 15.25, 16, 15.25),
            Block.box(12.75, 0, 0.75, 15.25, 16, 3.25));
    protected static VoxelShape Z_QUAD_AABB = Shapes.or(
            Block.box(0.75, 0.75, 0, 3.25, 3.25, 16),
            Block.box(0.75, 12.75, 0, 3.25, 15.25, 16),
            Block.box(12.75, 0.75, 0, 15.25, 3.25, 16),
            Block.box(12.75, 12.75, 0, 15.25, 15.25, 16));



    public AbstractPostBlock(final Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE).setValue(UPRIGHT, true));

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
        if (state.getValue(TYPE) == PostType.DOUBLE){
            switch (state.getValue(FACING)){
                case NORTH:
                    if (state.getValue(UPRIGHT)){
                        return Y_DOUBLE_AABB;
                    }
                    return Z_DOUBLE_AABB;
                case SOUTH:
                    if (state.getValue(UPRIGHT)) {
                        return Y_DOUBLE_AABB;
                    }
                    return Z_DOUBLE_AABB;
                case EAST:
                    if (state.getValue(UPRIGHT)) {
                        return XY_DOUBLE_AABB;
                    }
                    return X_DOUBLE_AABB;
                case WEST:
                    if (state.getValue(UPRIGHT)) {
                        return XY_DOUBLE_AABB;
                    }
                    return X_DOUBLE_AABB;
                case UP:
                    return Y_DOUBLE_AABB;
                case DOWN:
                    return Y_DOUBLE_AABB;
            }
        }
        if (state.getValue(TYPE) == PostType.QUAD){
            switch (state.getValue(FACING)){
                case NORTH:
                    if (state.getValue(UPRIGHT)){
                        return Y_QUAD_AABB;
                    }
                    return Z_QUAD_AABB;
                case SOUTH:
                    if (state.getValue(UPRIGHT)) {
                        return Y_QUAD_AABB;
                    }
                    return Z_QUAD_AABB;
                case EAST:
                    if (state.getValue(UPRIGHT)) {
                        return Y_QUAD_AABB;
                    }
                    return X_QUAD_AABB;
                case WEST:
                    if (state.getValue(UPRIGHT)) {
                        return Y_QUAD_AABB;
                    }
                    return X_QUAD_AABB;
                case UP:
                    return Y_QUAD_AABB;
                case DOWN:
                    return Y_QUAD_AABB;
            }
        }

        switch (state.getValue(FACING)) {
            case NORTH:
                if (state.getValue(UPRIGHT)){
                    return Y_AXIS_AABB;
                }
                return Z_AXIS_AABB;
            case SOUTH:
                if (state.getValue(UPRIGHT)) {
                    return Y_AXIS_AABB;
                }
                return Z_AXIS_AABB;
            case EAST:
                if (state.getValue(UPRIGHT)) {
                    return Y_AXIS_AABB;
                }
                return X_AXIS_AABB;
            case WEST:
                if (state.getValue(UPRIGHT)) {
                    return Y_AXIS_AABB;
                }
                return X_AXIS_AABB;
            case UP:
                return Y_AXIS_AABB;
            case DOWN:
                return Y_AXIS_AABB;
        }
        return null;
    }

    @Override
    public BlockState getStateForPlacement(@NotNull final BlockPlaceContext context)
    {
        BlockState blockstate = this.defaultBlockState();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        Direction direction = context.getClickedFace();

        if( direction.getAxis().isVertical()){
            return blockstate.setValue(UPRIGHT, true)
                .setValue(FACING, context.getNearestLookingDirection().getOpposite())
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        }

        return blockstate.setValue(UPRIGHT, false)
                .setValue(FACING, direction)
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, TYPE, UPRIGHT, WATERLOGGED);
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