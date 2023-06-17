package com.ldtteam.domumornamentum.block.types;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

/**
 * Creates special blocks for shingles and timberframes.
 */
public enum BrickType implements StringRepresentable
{
    BROWN("brown", Items.TERRACOTTA, Items.BRICK),
    BEIGE("beige", Items.GRAVEL, Items.BRICK),
    CREAM("cream", Items.SANDSTONE, Items.BRICK),
    ROAN("roan", Items.WHITE_CONCRETE_POWDER,  Items.BRICK),
    SAND("sand", Items.SAND, Items.SANDSTONE),

    BROWN_STONE("brown_stone", Items.TERRACOTTA, Items.STONE_BRICKS),
    BEIGE_STONE("beige_stone", Items.GRAVEL, Items.STONE_BRICKS),
    CREAM_STONE("cream_stone", Items.SANDSTONE, Items.STONE_BRICKS),
    SAND_STONE("sand_stone", Items.SAND, Items.STONE_BRICKS),
    ROAN_STONE("roan_stone", Items.BRICK, Items.STONE_BRICKS);
    public static final String SUFFIX = "_bricks";

    private final String name;
    private final Item ingredient;
    private final Item ingredient2;

    BrickType(final String name, final Item ingredient, final Item ingredient2)
    {
        this.name = name;
        this.ingredient = ingredient;
        this.ingredient2 = ingredient2;
    }

    @NotNull
    @Override
    public String getSerializedName()
    {
        return this.name + SUFFIX;
    }

    public Item getIngredient()
    {
        return this.ingredient;
    }

    public Item getIngredient2()
    {
        return this.ingredient2;
    }
}
