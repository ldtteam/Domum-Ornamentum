package com.ldtteam.domumornamentum.block.types;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Creates special blocks for shingles and timberframes.
 */
public enum ExtraBlockType implements StringRepresentable
{
    BLACK_CLAY(DyeColor.BLACK, Items.BRICK),
    BLUE_CLAY(DyeColor.BLUE, Items.BRICK),
    BLUE_SLATE(DyeColor.BLUE, Items.COBBLESTONE),
    BROWN_CLAY(DyeColor.BROWN, Items.BRICK),
    BASE_CLAY(null, Items.BRICK),
    CYAN_CLAY(DyeColor.CYAN, Items.BRICK),
    GRAY_CLAY(DyeColor.GRAY, Items.BRICK),
    GREEN_CLAY(DyeColor.GREEN, Items.BRICK),
    GREEN_SLATE(DyeColor.GREEN, Items.COBBLESTONE),
    LIGHT_BLUE_CLAY(DyeColor.LIGHT_BLUE, Items.BRICK),
    LIGHT_GRAY_CLAY(DyeColor.LIGHT_GRAY, Items.BRICK),
    LIME_CLAY(DyeColor.LIME, Items.BRICK),
    MAGENTA_CLAY(DyeColor.MAGENTA, Items.BRICK),
    MOSS_SLATE(null, Items.MOSSY_COBBLESTONE),
    ORANGE_CLAY(DyeColor.ORANGE, Items.BRICK),
    PINK_CLAY(DyeColor.PINK, Items.BRICK),
    PURPLE_CLAY(DyeColor.PURPLE, Items.BRICK),
    PURPLE_SLATE(DyeColor.PURPLE, Items.COBBLESTONE),
    RED_CLAY(DyeColor.RED, Items.BRICK),
    BASE_SLATE(null, Items.COBBLESTONE),
    BASE_THATCHED(null, Items.WHEAT),
    WHITE_CLAY(DyeColor.WHITE, Items.BRICK),
    YELLOW_CLAY(DyeColor.YELLOW, Items.BRICK),
    BASE_PAPER(null, Items.PAPER),
    BASE_CACTUS(null, Items.CACTUS);

    private final DyeColor color;
    private final Item material;

    ExtraBlockType(final DyeColor color, final Item material)
    {
        this.color = color;
        this.material = material;
    }

    @NotNull
    @Override
    public String getSerializedName()
    {
        return (this.color == null ? "" : this.color.getSerializedName() + "_") + this.material.getRegistryName().getPath() + "_extra";
    }

    @Nullable
    public DyeColor getColor()
    {
        return this.color;
    }

    public Item getMaterial()
    {
        return this.material;
    }
}
