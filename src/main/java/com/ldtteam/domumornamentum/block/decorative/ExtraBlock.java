package com.ldtteam.domumornamentum.block.decorative;

import com.ldtteam.domumornamentum.block.AbstractBlock;
import com.ldtteam.domumornamentum.block.types.ExtraBlockType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class ExtraBlock extends AbstractBlock<ExtraBlock>
{
    /**
     * The hardness this block has.
     */
    private static final float BLOCK_HARDNESS = 3F;

    /**
     * The resistance this block has.
     */
    private static final float RESISTANCE = 1F;

    /**
     * The top type.
     */
    private final ExtraBlockType type;

    /**
     * Constructor of the FullBlock.
     */
    public ExtraBlock(final ExtraBlockType type)
    {
        super(type.adjustProperties(Properties.of(Material.WOOD).sound(type.getSoundType()).strength(BLOCK_HARDNESS, RESISTANCE).requiresCorrectToolForDrops()));
        this.type = type;
    }

    public ExtraBlockType getType()
    {
        return type;
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext collisionContext)
    {
        return state.getShape(blockGetter, pos);
    }
}
