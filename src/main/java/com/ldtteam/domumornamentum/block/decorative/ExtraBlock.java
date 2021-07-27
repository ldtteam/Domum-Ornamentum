package com.ldtteam.domumornamentum.block.decorative;

import com.ldtteam.domumornamentum.block.AbstractBlock;
import com.ldtteam.domumornamentum.block.types.ExtraBlockType;
import com.ldtteam.domumornamentum.item.decoration.ExtraBlockItem;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Objects;

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
        super(Properties.of(Material.WOOD).strength(BLOCK_HARDNESS, RESISTANCE));
        setRegistryName(new ResourceLocation(Constants.MOD_ID, type.getSerializedName()));
        this.type = type;
    }

    public ExtraBlockType getType()
    {
        return type;
    }

    @Override
    public void registerItemBlock(final IForgeRegistry<Item> registry, final Item.Properties properties)
    {
        registry.register((new ExtraBlockItem(this, properties)).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }
}
