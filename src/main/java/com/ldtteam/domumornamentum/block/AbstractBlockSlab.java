package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.IMateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
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

import java.util.Map;

/**
 * Creates an instance of the abstract slab block.
 *
 * @param <B> the type.
 */
public abstract class AbstractBlockSlab<B extends AbstractBlockSlab<B>> extends SlabBlock implements IDOBlock<B> {
    /**
     * Facing state
     * Collision objects for side facing slabs
     */
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    protected static final VoxelShape WEST_AABB = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);

    /**
     * Constructor of abstract class.
     *
     * @param properties the input properties.
     */
    public AbstractBlockSlab(final Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN).setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, Boolean.FALSE));

    }

    @NotNull
    @Override
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (state.getValue(TYPE) != SlabType.DOUBLE) {

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
        } else if (state.getValue(TYPE) == SlabType.TOP) {
            return TOP_AABB;
        } else if (state.getValue(TYPE) == SlabType.BOTTOM) {
            return BOTTOM_AABB;
        }
        return Shapes.block();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull final BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        BlockState defaultState = this.defaultBlockState();
        BlockState blockState = context.getLevel().getBlockState(blockPos);
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        Direction direction = context.getClickedFace();

        // stacking, if clicked position lands RIGHT in middle on any axis, also if clicked in any other side of side facing slabs
        if (blockState.is(this)) {
            boolean xpos = context.getClickLocation().x - (double) context.getClickedPos().getX() == 0.5D;
            boolean ypos = context.getClickLocation().y - (double) context.getClickedPos().getY() == 0.5D;
            boolean zpos = context.getClickLocation().z - (double) context.getClickedPos().getZ() == 0.5D;
            boolean xflag = context.getClickLocation().x - (double) context.getClickedPos().getX() > 0.5D;
            boolean yflag = context.getClickLocation().y - (double) context.getClickedPos().getY() > 0.5D;
            boolean zflag = context.getClickLocation().z - (double) context.getClickedPos().getZ() > 0.5D;
            if (xpos || ypos || zpos) {
                return blockState.setValue(TYPE, SlabType.DOUBLE).setValue(WATERLOGGED, Boolean.valueOf(false));
            } else if (blockState.getValue(FACING) == Direction.UP && !yflag && direction != Direction.DOWN) {
                return blockState.setValue(TYPE, SlabType.DOUBLE).setValue(WATERLOGGED, Boolean.valueOf(false));
            } else if (blockState.getValue(FACING) == Direction.DOWN && yflag && direction != Direction.UP) {
                return blockState.setValue(TYPE, SlabType.DOUBLE).setValue(WATERLOGGED, Boolean.valueOf(false));
            } else if (blockState.getValue(FACING) == Direction.NORTH && !zflag && direction != Direction.SOUTH) {
                return blockState.setValue(TYPE, SlabType.DOUBLE).setValue(WATERLOGGED, Boolean.valueOf(false));
            } else if (blockState.getValue(FACING) == Direction.SOUTH && zflag && direction != Direction.NORTH) {
                return blockState.setValue(TYPE, SlabType.DOUBLE).setValue(WATERLOGGED, Boolean.valueOf(false));
            } else if (blockState.getValue(FACING) == Direction.EAST && xflag && direction != Direction.WEST) {
                return blockState.setValue(TYPE, SlabType.DOUBLE).setValue(WATERLOGGED, Boolean.valueOf(false));
            } else if (blockState.getValue(FACING) == Direction.WEST && !xflag && direction != Direction.EAST) {
                return blockState.setValue(TYPE, SlabType.DOUBLE).setValue(WATERLOGGED, Boolean.valueOf(false));
            }
        }

        //if holding the shift key while clicking, set slab FACING (N/E/W/S), all facing slabs use BOTTOM TYPE
        //else use the base class default
        if (context.isSecondaryUseActive()) {
            if (context.getNearestLookingDirection() == Direction.UP) {
                return defaultState.setValue(FACING, Direction.UP);
            }
            if (context.getNearestLookingDirection() == Direction.DOWN) {
                return defaultState.setValue(FACING, Direction.DOWN);
            }
            return defaultState.setValue(FACING, context.getHorizontalDirection().getOpposite());
        }

        //default placing from vanilla
        BlockState blockState1 = this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(FACING, Direction.DOWN).setValue(WATERLOGGED, Boolean.valueOf(fluidState.getType() == Fluids.WATER));
        return direction != Direction.DOWN && (direction == Direction.UP || !(context.getClickLocation().y - (double) blockPos.getY() > 0.5D)) ? blockState1 : blockState1.setValue(FACING, Direction.UP);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        ItemStack itemstack = context.getItemInHand();
        SlabType slabtype = state.getValue(TYPE);
        Direction slabfacing = state.getValue(FACING);

        if (slabtype != SlabType.DOUBLE && itemstack.is(this.asItem())) {
            if (context.replacingClickedOnBlock()) {
                BlockEntity blockEntity = context.getLevel().getBlockEntity(context.getClickedPos());
                if (blockEntity instanceof IMateriallyTexturedBlockEntity mtbe) {
                    ItemStack itemStack = context.getItemInHand();
                    MaterialTextureData existing = mtbe.getTextureData();
                    MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(itemStack.getTagElement("textureData"));
                    if (textureData.isEmpty()) {
                        textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(itemStack);
                    }
                    if (!existing.equals(textureData)) {
                        return false;
                    }
                }

                boolean xflag = context.getClickLocation().x - (double) context.getClickedPos().getX() > 0.5D;
                boolean yflag = context.getClickLocation().y - (double) context.getClickedPos().getY() > 0.5D;
                boolean zflag = context.getClickLocation().z - (double) context.getClickedPos().getZ() > 0.5D;
                Direction direction = context.getClickedFace();

                if (slabfacing == Direction.DOWN && direction != Direction.UP) {
                    return direction == Direction.UP || yflag && direction.getAxis().isHorizontal();
                }
                if (slabfacing == Direction.UP && direction != Direction.DOWN) {
                    return direction == Direction.DOWN || !yflag && direction.getAxis().isHorizontal();
                }
                if (slabfacing == Direction.NORTH && direction != Direction.NORTH) {
                    return direction == Direction.NORTH || !zflag;
                }
                if (slabfacing == Direction.SOUTH && direction != Direction.SOUTH) {
                    return direction == Direction.SOUTH || zflag;
                }
                if (slabfacing == Direction.EAST && direction != Direction.EAST) {
                    return direction == Direction.EAST || xflag;
                }
                if (slabfacing == Direction.WEST && direction != Direction.WEST) {
                    return direction == Direction.WEST || !xflag;
                }

            }
            return true;
        } else {
            return false;
        }

    }

    @Override
    public ResourceLocation getRegistryName() {
        return getRegistryName(this);
    }
}
