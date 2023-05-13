package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;



public abstract class AbstractBlockSlab<B extends AbstractBlockSlab<B>> extends SlabBlock implements IDOBlock<B>
{

    public static final DirectionProperty    FACING = BlockStateProperties.FACING;
    protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    protected static final VoxelShape WEST_AABB = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    /**
     * Constructor of abstract class.
     *
     */
    @NotNull
    @Override
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context)
    {
        if (state.getValue(TYPE) != SlabType.DOUBLE)  {

            switch (state.getValue(FACING)) {
                case NORTH:
                        return NORTH_AABB;
                case SOUTH:
                        return SOUTH_AABB;
                case EAST:
                        return EAST_AABB;
                case WEST:
                        return WEST_AABB;
                case UP:
                        return TOP_AABB;
                case DOWN:
                        return BOTTOM_AABB;
            }
        }
        if (state.getValue(TYPE) == SlabType.TOP)   {
                return TOP_AABB;
        }
        if (state.getValue(TYPE) == SlabType.BOTTOM)   {
            return BOTTOM_AABB;
        }
        return Shapes.block();
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }
    public AbstractBlockSlab(final Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN).setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, Boolean.FALSE));

    }
    @Override
    public BlockState getStateForPlacement(@NotNull final BlockPlaceContext context)
    {
        BlockPos blockpos = context.getClickedPos();
        BlockState defaultstate = this.defaultBlockState();
        BlockState blockstate = context.getLevel().getBlockState(blockpos);
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        Direction direction = context.getClickedFace();

        /** stacking */
        if (blockstate.is(this)) {
            return defaultstate.setValue(TYPE, SlabType.DOUBLE).setValue(WATERLOGGED, Boolean.valueOf(false));
        }

        /** if player is holding the shift key while clicking, set slab TYPE (N/E/W/S) based on FACING
         * else use the base class method */
        if (context.isSecondaryUseActive()) {
            if (context.getNearestLookingDirection() == Direction.UP){
                return defaultstate.setValue(FACING, context.getNearestLookingDirection());
            }
            if (context.getNearestLookingDirection() == Direction.DOWN){
                return defaultstate.setValue(FACING, context.getNearestLookingDirection());
            }
           return defaultstate.setValue(FACING, context.getHorizontalDirection().getOpposite());

        }

        /**default placing */
        BlockState blockstate1 = this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(FACING, Direction.DOWN).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
        return direction != Direction.DOWN && (direction == Direction.UP || !(context.getClickLocation().y - (double)blockpos.getY() > 0.5D)) ? blockstate1 : blockstate1.setValue(FACING, Direction.UP);
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        ItemStack itemstack = context.getItemInHand();
        SlabType slabtype = state.getValue(TYPE);
        Direction facing = state.getValue(FACING);
        if (slabtype != SlabType.DOUBLE && itemstack.is(this.asItem())) {
            if (context.replacingClickedOnBlock()) {
                /**did you click in the (front) half of the block*/
                boolean xflag = context.getClickLocation().x - (double)context.getClickedPos().getX() > 0.5D;
                boolean yflag = context.getClickLocation().y - (double)context.getClickedPos().getY() > 0.5D;
                boolean zflag = context.getClickLocation().z - (double)context.getClickedPos().getZ() > 0.5D;
                Direction direction = context.getClickedFace();
                if (facing == Direction.DOWN) {
                    return direction == Direction.UP || yflag && direction.getAxis().isHorizontal();
                } if (facing == Direction.UP) {
                    return direction == Direction.DOWN || !yflag && direction.getAxis().isHorizontal();
                } if (facing == Direction.NORTH) {
                    return direction == Direction.SOUTH || !zflag && direction.getAxis().isVertical();
                } if (facing == Direction.SOUTH) {
                    return direction == Direction.NORTH || zflag && direction.getAxis().isVertical();
                } if (facing == Direction.EAST) {
                    return direction == Direction.WEST || !xflag && direction.getAxis().isVertical();
                } if (facing == Direction.WEST) {
                    return direction == Direction.EAST || xflag && direction.getAxis().isVertical();
                }
            }
            return true;
        } else {
            return false;
        }
    }
    @Override
    public ResourceLocation getRegistryName()
    {
        return getRegistryName(this);
    }
}
