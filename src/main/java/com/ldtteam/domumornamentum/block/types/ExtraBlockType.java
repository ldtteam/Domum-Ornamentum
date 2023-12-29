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
    BLACK_BRICK(DyeColor.BLACK, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
    BLUE_BRICK(DyeColor.BLUE, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
    BLUE_SLATE(DyeColor.BLUE, Items.COBBLESTONE, SoundType.STONE, ExtraBlockCategory.SLATE),
    BROWN_BRICK(DyeColor.BROWN, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
    BASE_BRICK(null, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
    CYAN_BRICK(DyeColor.CYAN, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
    GRAY_BRICK(DyeColor.GRAY, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
    GREEN_BRICK(DyeColor.GREEN, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
    GREEN_SLATE(DyeColor.GREEN, Items.COBBLESTONE, SoundType.STONE, ExtraBlockCategory.SLATE),
    LIGHT_BLUE_BRICK(DyeColor.LIGHT_BLUE, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
    LIGHT_GRAY_BRICK(DyeColor.LIGHT_GRAY, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
    LIME_BRICK(DyeColor.LIME, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
    MAGENTA_BRICK(DyeColor.MAGENTA, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
    MOSS_SLATE(null, Items.MOSSY_COBBLESTONE, SoundType.STONE, ExtraBlockCategory.SLATE),
    ORANGE_BRICK(DyeColor.ORANGE, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
    PINK_BRICK(DyeColor.PINK, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
    PURPLE_BRICK(DyeColor.PURPLE, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
    PURPLE_SLATE(DyeColor.PURPLE, Items.COBBLESTONE, SoundType.STONE, ExtraBlockCategory.SLATE),
    RED_BRICK(DyeColor.RED, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
    BASE_SLATE(null, Items.COBBLESTONE, SoundType.STONE, ExtraBlockCategory.SLATE),
    BASE_THATCHED(null, Items.WHEAT, SoundType.GRASS, ExtraBlockCategory.THATCHED),
    WHITE_BRICK(DyeColor.WHITE, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
    YELLOW_BRICK(DyeColor.YELLOW, Items.BRICK, SoundType.STONE, ExtraBlockCategory.BRICK),
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
