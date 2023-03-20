package com.ldtteam.domumornamentum.block.types;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Creates special blocks for shingles and timberframes.
 */
public enum ExtraBlockType implements StringRepresentable
{
    BLACK_CLAY(DyeColor.BLACK, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    BLUE_CLAY(DyeColor.BLUE, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    BLUE_SLATE(DyeColor.BLUE, Items.COBBLESTONE, SoundType.STONE, ExtraBlockCategory.SLATE),
    BROWN_CLAY(DyeColor.BROWN, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    BASE_CLAY(null, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    CYAN_CLAY(DyeColor.CYAN, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    GRAY_CLAY(DyeColor.GRAY, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    GREEN_CLAY(DyeColor.GREEN, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    GREEN_SLATE(DyeColor.GREEN, Items.COBBLESTONE, SoundType.STONE, ExtraBlockCategory.SLATE),
    LIGHT_BLUE_CLAY(DyeColor.LIGHT_BLUE, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    LIGHT_GRAY_CLAY(DyeColor.LIGHT_GRAY, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    LIME_CLAY(DyeColor.LIME, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    MAGENTA_CLAY(DyeColor.MAGENTA, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    MOSS_SLATE(null, Items.MOSSY_COBBLESTONE, SoundType.STONE, ExtraBlockCategory.SLATE),
    ORANGE_CLAY(DyeColor.ORANGE, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    PINK_CLAY(DyeColor.PINK, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    PURPLE_CLAY(DyeColor.PURPLE, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    PURPLE_SLATE(DyeColor.PURPLE, Items.COBBLESTONE, SoundType.STONE, ExtraBlockCategory.SLATE),
    RED_CLAY(DyeColor.RED, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    BASE_SLATE(null, Items.COBBLESTONE, SoundType.STONE, ExtraBlockCategory.SLATE),
    BASE_THATCHED(null, Items.WHEAT, SoundType.GRASS, ExtraBlockCategory.THATCHED),
    WHITE_CLAY(DyeColor.WHITE, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    YELLOW_CLAY(DyeColor.YELLOW, Items.BRICK, SoundType.STONE, ExtraBlockCategory.CLAY),
    BASE_PAPER(null, Items.PAPER, SoundType.WOOL, ExtraBlockCategory.PAPER),
    BASE_CACTUS(null, Items.CACTUS, SoundType.WOOD, ExtraBlockCategory.CACTUS),
    GREEN_CACTUS(DyeColor.GREEN, Items.CACTUS, SoundType.WOOD, ExtraBlockCategory.CACTUS),
    LIGHT_PAPER(DyeColor.WHITE, Items.PAPER, SoundType.WOOL, ExtraBlockCategory.PAPER);

    private final DyeColor color;
    private final Item material;
    private final SoundType soundType;
    private final ExtraBlockCategory category;

    ExtraBlockType(final DyeColor color, final Item material, final SoundType soundType, ExtraBlockCategory category)
    {
        this.color = color;
        this.material = material;
        this.soundType = soundType;
        this.category = category;
    }

    @NotNull
    @Override
    public String getSerializedName()
    {
        return (this.color == null ? "" : this.color.getSerializedName() + "_") + ForgeRegistries.ITEMS.getKey(this.material).getPath() + "_extra";
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

    public SoundType getSoundType() {
        return this.soundType;
    }

    public ExtraBlockCategory getCategory() {
        return category;
    }
}
