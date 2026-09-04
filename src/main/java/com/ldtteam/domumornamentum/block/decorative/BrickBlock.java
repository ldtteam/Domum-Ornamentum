package com.ldtteam.domumornamentum.block.decorative;

import com.ldtteam.domumornamentum.block.AbstractBlock;
import com.ldtteam.domumornamentum.block.types.BrickType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

/**
 * Brick blocks.
 */
public class BrickBlock extends AbstractBlock<BrickBlock>
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
    private final BrickType type;

    /**
     * Constructor of the FullBlock.
     */
    public BrickBlock(final BrickType type, final BlockBehaviour.Properties props)
    {
        super(props.mapColor(MapColor.WOOD).sound(SoundType.STONE).strength(BLOCK_HARDNESS, RESISTANCE));
        this.type = type;
    }

    public BrickType getType()
    {
        return type;
    }
}
