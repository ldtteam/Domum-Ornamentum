package com.ldtteam.domumornamentum.block.decorative;

import com.ldtteam.domumornamentum.block.AbstractBlock;
import com.ldtteam.domumornamentum.block.types.ExtraBlockType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

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
        super(Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(BLOCK_HARDNESS, RESISTANCE).requiresCorrectToolForDrops().sound(type.getSoundType()));
        this.type = type;
    }

    public ExtraBlockType getType()
    {
        return type;
    }
}
