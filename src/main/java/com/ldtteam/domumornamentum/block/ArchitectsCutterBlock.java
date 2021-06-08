package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.container.ArchitectsCutterContainer;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;


@SuppressWarnings("deprecation")
public final class ArchitectsCutterBlock extends AbstractBlock<ArchitectsCutterBlock>
{
    private static final   ITextComponent    CONTAINER_NAME = new TranslationTextComponent("donum-ornamentum.architects-cutter");
    public static final    DirectionProperty FACING         = HorizontalBlock.FACING;
    protected static final VoxelShape        SHAPE          = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);

    public ArchitectsCutterBlock(AbstractBlock.Properties propertiesIn) {
        super(propertiesIn);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        this.setRegistryName(new ResourceLocation(Constants.MOD_ID, "architectscutter"));
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @NotNull
    public ActionResultType use(@NotNull BlockState state, World worldIn, @NotNull BlockPos pos, @NotNull PlayerEntity player, @NotNull Hand handIn, @NotNull BlockRayTraceResult hit) {
        if (worldIn.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(worldIn, pos));
            return ActionResultType.CONSUME;
        }
    }

    @Nullable
    public INamedContainerProvider getMenuProvider(@NotNull BlockState state, @NotNull World worldIn, @NotNull BlockPos pos) {
        return new SimpleNamedContainerProvider((id, inventory, player) -> new ArchitectsCutterContainer(id, inventory, IWorldPosCallable.create(worldIn, pos)), CONTAINER_NAME);
    }

    @NotNull
    public VoxelShape getShape(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull ISelectionContext context) {
        return SHAPE;
    }

    public boolean useShapeForLightOcclusion(@NotNull BlockState state) {
        return true;
    }

    @NotNull
    public BlockRenderType getRenderShape(@NotNull BlockState state) {
        return BlockRenderType.MODEL;
    }

    @NotNull
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @NotNull
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public boolean isPathfindable(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull PathType type) {
        return false;
    }
}
