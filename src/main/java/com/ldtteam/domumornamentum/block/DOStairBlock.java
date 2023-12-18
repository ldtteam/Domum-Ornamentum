package com.ldtteam.domumornamentum.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.IntStream;

/**
 * This class an almost exact 1:1 copy of the {@link StairBlock}, so we can override stair connecting behaviour
 * for non stair blocks that still need stair-like functionality.
 */
public class DOStairBlock extends Block implements SimpleWaterloggedBlock
{
    public static final    DirectionProperty         FACING         = HorizontalDirectionalBlock.FACING;
    public static final    EnumProperty<Half>        HALF           = BlockStateProperties.HALF;
    public static final    EnumProperty<StairsShape> SHAPE          = BlockStateProperties.STAIRS_SHAPE;
    public static final    BooleanProperty           WATERLOGGED    = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape                TOP_AABB       = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape                BOTTOM_AABB    = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    protected static final VoxelShape                OCTET_NNN      = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
    protected static final VoxelShape                OCTET_NNP      = Block.box(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
    protected static final VoxelShape                OCTET_NPN      = Block.box(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
    protected static final VoxelShape                OCTET_NPP      = Block.box(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
    protected static final VoxelShape                OCTET_PNN      = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
    protected static final VoxelShape                OCTET_PNP      = Block.box(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
    protected static final VoxelShape                OCTET_PPN      = Block.box(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    protected static final VoxelShape                OCTET_PPP      = Block.box(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape[]              TOP_SHAPES     = makeShapes(TOP_AABB, OCTET_NNN, OCTET_PNN, OCTET_NNP, OCTET_PNP);
    protected static final VoxelShape[]              BOTTOM_SHAPES  = makeShapes(BOTTOM_AABB, OCTET_NPN, OCTET_PPN, OCTET_NPP, OCTET_PPP);
    private static final   int[]                     SHAPE_BY_STATE = new int[] {12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
    private final          Block                     base;
    private final          BlockState                baseState;

    // Forge Start
    private final java.util.function.Supplier<BlockState> stateSupplier;

    public DOStairBlock(java.util.function.Supplier<BlockState> state, BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                                    .setValue(FACING, Direction.NORTH)
                                    .setValue(HALF, Half.BOTTOM)
                                    .setValue(SHAPE, StairsShape.STRAIGHT)
                                    .setValue(WATERLOGGED, Boolean.valueOf(false)));
        this.base = Blocks.AIR; // These are unused, fields are redirected
        this.baseState = Blocks.AIR.defaultBlockState();
        this.stateSupplier = state;
    }

    /**
     * Generates the different stair shape models.
     *
     * @param p_56934_ the full shape.
     * @param p_56935_ the first corner.
     * @param p_56936_ the second corner.
     * @param p_56937_ the third corner.
     * @param p_56938_ the fourth corner.
     * @return the array of all possible voxel shapes.
     */
    private static VoxelShape[] makeShapes(VoxelShape p_56934_, VoxelShape p_56935_, VoxelShape p_56936_, VoxelShape p_56937_, VoxelShape p_56938_)
    {
        return IntStream.range(0, 16).mapToObj((p_56945_) -> {
            return makeStairShape(p_56945_, p_56934_, p_56935_, p_56936_, p_56937_, p_56938_);
        }).toArray((p_56949_) -> {
            return new VoxelShape[p_56949_];
        });
    }

    /**
     * Creates a stair shape from the 5 input shapes.
     *
     * @param p_56865_ the index offset.
     * @param p_56866_ the full shape.
     * @param p_56867_ the first corner.
     * @param p_56868_ the second corner.
     * @param p_56869_ the third corner.
     * @param p_56870_ the fourth corner.
     * @return the final shape.
     */
    private static VoxelShape makeStairShape(int p_56865_, VoxelShape p_56866_, VoxelShape p_56867_, VoxelShape p_56868_, VoxelShape p_56869_, VoxelShape p_56870_)
    {
        VoxelShape voxelshape = p_56866_;
        if ((p_56865_ & 1) != 0)
        {
            voxelshape = Shapes.or(p_56866_, p_56867_);
        }

        if ((p_56865_ & 2) != 0)
        {
            voxelshape = Shapes.or(voxelshape, p_56868_);
        }

        if ((p_56865_ & 4) != 0)
        {
            voxelshape = Shapes.or(voxelshape, p_56869_);
        }

        if ((p_56865_ & 8) != 0)
        {
            voxelshape = Shapes.or(voxelshape, p_56870_);
        }

        return voxelshape;
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_56947_)
    {
        return this.base.isRandomlyTicking(p_56947_);
    }

    @Override
    public void animateTick(BlockState p_222518_, Level p_222519_, BlockPos p_222520_, RandomSource p_222521_)
    {
        this.base.animateTick(p_222518_, p_222519_, p_222520_, p_222521_);
    }

    @Override
    public void destroy(LevelAccessor p_56882_, BlockPos p_56883_, BlockState p_56884_)
    {
        this.base.destroy(p_56882_, p_56883_, p_56884_);
    }

    @Override
    public float getExplosionResistance()
    {
        return this.base.getExplosionResistance();
    }

    @Override
    public void wasExploded(Level p_56878_, BlockPos p_56879_, Explosion p_56880_)
    {
        this.base.wasExploded(p_56878_, p_56879_, p_56880_);
    }

    @Override
    public void stepOn(Level p_154720_, BlockPos p_154721_, BlockState p_154722_, Entity p_154723_)
    {
        this.base.stepOn(p_154720_, p_154721_, p_154722_, p_154723_);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_56872_)
    {
        Direction direction = p_56872_.getClickedFace();
        BlockPos blockpos = p_56872_.getClickedPos();
        FluidState fluidstate = p_56872_.getLevel().getFluidState(blockpos);
        BlockState blockstate = this.defaultBlockState()
                                  .setValue(FACING, p_56872_.getHorizontalDirection())
                                  .setValue(HALF,
                                    direction != Direction.DOWN && (direction == Direction.UP || !(p_56872_.getClickLocation().y - (double) blockpos.getY() > 0.5D))
                                      ? Half.BOTTOM
                                      : Half.TOP)
                                  .setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
        return blockstate.setValue(SHAPE, getStairsShape(blockstate, p_56872_.getLevel(), blockpos));
    }

    /**
     * Get the {@link StairsShape} enum value, based on rotation, half and surrounding other connected stair like blocks.
     *
     * @param p_56977_ the block state.
     * @param p_56978_ the block getter.
     * @param p_56979_ the block position.
     * @return the stair shape.
     */
    private StairsShape getStairsShape(BlockState p_56977_, BlockGetter p_56978_, BlockPos p_56979_)
    {
        Direction direction = p_56977_.getValue(FACING);
        BlockState blockstate = p_56978_.getBlockState(p_56979_.relative(direction));
        if (isStairs(blockstate) && p_56977_.getValue(HALF) == blockstate.getValue(HALF))
        {
            Direction direction1 = blockstate.getValue(FACING);
            if (direction1.getAxis() != p_56977_.getValue(FACING).getAxis() && canTakeShape(p_56977_, p_56978_, p_56979_, direction1.getOpposite()))
            {
                if (direction1 == direction.getCounterClockWise())
                {
                    return StairsShape.OUTER_LEFT;
                }

                return StairsShape.OUTER_RIGHT;
            }
        }

        BlockState blockstate1 = p_56978_.getBlockState(p_56979_.relative(direction.getOpposite()));
        if (isStairs(blockstate1) && p_56977_.getValue(HALF) == blockstate1.getValue(HALF))
        {
            Direction direction2 = blockstate1.getValue(FACING);
            if (direction2.getAxis() != p_56977_.getValue(FACING).getAxis() && canTakeShape(p_56977_, p_56978_, p_56979_, direction2))
            {
                if (direction2 == direction.getCounterClockWise())
                {
                    return StairsShape.INNER_LEFT;
                }

                return StairsShape.INNER_RIGHT;
            }
        }

        return StairsShape.STRAIGHT;
    }

    /**
     * Check whether a connected block is considered a stair.
     *
     * @param p_56981_ the block state.
     * @return true if the other block is a stair.
     */
    public boolean isStairs(BlockState p_56981_)
    {
        return p_56981_.getBlock() instanceof DOStairBlock;
    }

    /**
     * Checks whether the stair can apply to a given shape.
     *
     * @param p_56971_ the block state.
     * @param p_56972_ the block getter.
     * @param p_56973_ the block position.
     * @param p_56974_ the direction of the connected block.
     * @return whether the shape can be applied to the block.
     */
    private boolean canTakeShape(BlockState p_56971_, BlockGetter p_56972_, BlockPos p_56973_, Direction p_56974_)
    {
        BlockState blockstate = p_56972_.getBlockState(p_56973_.relative(p_56974_));
        return !isStairs(blockstate) || blockstate.getValue(FACING) != p_56971_.getValue(FACING) || blockstate.getValue(HALF) != p_56971_.getValue(HALF);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_56932_)
    {
        p_56932_.add(FACING, HALF, SHAPE, WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(BlockState p_56891_, BlockGetter p_56892_, BlockPos p_56893_, PathComputationType p_56894_)
    {
        return false;
    }

    @Override
    public BlockState updateShape(BlockState p_56925_, Direction p_56926_, BlockState p_56927_, LevelAccessor p_56928_, BlockPos p_56929_, BlockPos p_56930_)
    {
        if (p_56925_.getValue(WATERLOGGED))
        {
            p_56928_.scheduleTick(p_56929_, Fluids.WATER, Fluids.WATER.getTickDelay(p_56928_));
        }

        return p_56926_.getAxis().isHorizontal()
                 ? p_56925_.setValue(SHAPE, getStairsShape(p_56925_, p_56928_, p_56929_))
                 : super.updateShape(p_56925_, p_56926_, p_56927_, p_56928_, p_56929_, p_56930_);
    }

    @Override
    public void onPlace(BlockState p_56961_, Level p_56962_, BlockPos p_56963_, BlockState p_56964_, boolean p_56965_)
    {
        if (!p_56961_.is(p_56961_.getBlock()))
        {
            p_56962_.neighborChanged(this.baseState, p_56963_, Blocks.AIR, p_56963_, false);
            this.base.onPlace(this.baseState, p_56962_, p_56963_, p_56964_, false);
        }
    }

    @Override
    public void onRemove(BlockState p_56908_, Level p_56909_, BlockPos p_56910_, BlockState p_56911_, boolean p_56912_)
    {
        if (!p_56908_.is(p_56911_.getBlock()))
        {
            this.baseState.onRemove(p_56909_, p_56910_, p_56911_, p_56912_);
        }
    }

    @Override
    public InteractionResult use(BlockState p_56901_, Level p_56902_, BlockPos p_56903_, Player p_56904_, InteractionHand p_56905_, BlockHitResult p_56906_)
    {
        return this.baseState.use(p_56902_, p_56904_, p_56905_, p_56906_);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState p_56967_)
    {
        return true;
    }

    @Override
    public FluidState getFluidState(BlockState p_56969_)
    {
        return p_56969_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_56969_);
    }

    @Override
    public BlockState rotate(BlockState p_56922_, Rotation p_56923_)
    {
        return p_56922_.setValue(FACING, p_56923_.rotate(p_56922_.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState p_56919_, Mirror p_56920_)
    {
        Direction direction = p_56919_.getValue(FACING);
        StairsShape stairsshape = p_56919_.getValue(SHAPE);
        switch (p_56920_)
        {
            case LEFT_RIGHT:
                if (direction.getAxis() == Direction.Axis.Z)
                {
                    switch (stairsshape)
                    {
                        case INNER_LEFT:
                            return p_56919_.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_RIGHT);
                        case INNER_RIGHT:
                            return p_56919_.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_LEFT);
                        case OUTER_LEFT:
                            return p_56919_.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return p_56919_.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_LEFT);
                        default:
                            return p_56919_.rotate(Rotation.CLOCKWISE_180);
                    }
                }
                break;
            case FRONT_BACK:
                if (direction.getAxis() == Direction.Axis.X)
                {
                    switch (stairsshape)
                    {
                        case INNER_LEFT:
                            return p_56919_.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_LEFT);
                        case INNER_RIGHT:
                            return p_56919_.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_RIGHT);
                        case OUTER_LEFT:
                            return p_56919_.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return p_56919_.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_LEFT);
                        case STRAIGHT:
                            return p_56919_.rotate(Rotation.CLOCKWISE_180);
                    }
                }
        }

        return super.mirror(p_56919_, p_56920_);
    }

    @Override
    public VoxelShape getShape(BlockState p_56956_, BlockGetter p_56957_, BlockPos p_56958_, CollisionContext p_56959_)
    {
        return (p_56956_.getValue(HALF) == Half.TOP ? TOP_SHAPES : BOTTOM_SHAPES)[SHAPE_BY_STATE[this.getShapeIndex(p_56956_)]];
    }

    /**
     * Get the index of the stairs shape.
     *
     * @param p_56983_ the block state.
     * @return the index.
     */
    private int getShapeIndex(BlockState p_56983_)
    {
        return p_56983_.getValue(SHAPE).ordinal() * 4 + p_56983_.getValue(FACING).get2DDataValue();
    }

    @Override
    public void randomTick(BlockState p_222523_, ServerLevel p_222524_, BlockPos p_222525_, RandomSource p_222526_)
    {
        this.base.randomTick(p_222523_, p_222524_, p_222525_, p_222526_);
    }

    @Override
    public void tick(BlockState p_222513_, ServerLevel p_222514_, BlockPos p_222515_, RandomSource p_222516_)
    {
        this.base.tick(p_222513_, p_222514_, p_222515_, p_222516_);
    }

    @Override
    public void attack(BlockState p_56896_, Level p_56897_, BlockPos p_56898_, Player p_56899_)
    {
        this.baseState.attack(p_56897_, p_56898_, p_56899_);
    }
}
