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
    BLACK_CLAY(DyeColor.BLACK, Items.BRICK, SoundType.STONE),
    BLUE_CLAY(DyeColor.BLUE, Items.BRICK, SoundType.STONE),
    BLUE_SLATE(DyeColor.BLUE, Items.COBBLESTONE, SoundType.STONE),
    BROWN_CLAY(DyeColor.BROWN, Items.BRICK, SoundType.STONE),
    BASE_CLAY(null, Items.BRICK, SoundType.STONE),
    CYAN_CLAY(DyeColor.CYAN, Items.BRICK, SoundType.STONE),
    GRAY_CLAY(DyeColor.GRAY, Items.BRICK, SoundType.STONE),
    GREEN_CLAY(DyeColor.GREEN, Items.BRICK, SoundType.STONE),
    GREEN_SLATE(DyeColor.GREEN, Items.COBBLESTONE, SoundType.STONE),
    LIGHT_BLUE_CLAY(DyeColor.LIGHT_BLUE, Items.BRICK, SoundType.STONE),
    LIGHT_GRAY_CLAY(DyeColor.LIGHT_GRAY, Items.BRICK, SoundType.STONE),
    LIME_CLAY(DyeColor.LIME, Items.BRICK, SoundType.STONE),
    MAGENTA_CLAY(DyeColor.MAGENTA, Items.BRICK, SoundType.STONE),
    MOSS_SLATE(null, Items.MOSSY_COBBLESTONE, SoundType.STONE),
    ORANGE_CLAY(DyeColor.ORANGE, Items.BRICK, SoundType.STONE),
    PINK_CLAY(DyeColor.PINK, Items.BRICK, SoundType.STONE),
    PURPLE_CLAY(DyeColor.PURPLE, Items.BRICK, SoundType.STONE),
    PURPLE_SLATE(DyeColor.PURPLE, Items.COBBLESTONE, SoundType.STONE),
    RED_CLAY(DyeColor.RED, Items.BRICK, SoundType.STONE),
    BASE_SLATE(null, Items.COBBLESTONE, SoundType.STONE),
    BASE_THATCHED(null, Items.WHEAT, SoundType.GRASS),
    WHITE_CLAY(DyeColor.WHITE, Items.BRICK, SoundType.STONE),
    YELLOW_CLAY(DyeColor.YELLOW, Items.BRICK, SoundType.STONE),
    BASE_PAPER(null, Items.PAPER, SoundType.WOOL),
    BASE_CACTUS(null, Items.CACTUS, SoundType.WOOD),
    GREEN_CACTUS(DyeColor.GREEN, Items.CACTUS, SoundType.WOOD),
    LIGHT_PAPER(DyeColor.WHITE, Items.PAPER, SoundType.WOOL, true);

    private final DyeColor color;
    private final Item material;
    private final SoundType soundType;
    private final boolean isTranslucent;

     ExtraBlockType(final DyeColor color, final Item material, final SoundType soundType, final boolean isTranslucent)
    {
        this.color = color;
        this.material = material;
        this.soundType = soundType;
        this.isTranslucent = isTranslucent;
    }
    
    ExtraBlockType(final DyeColor color, final Item material, final SoundType soundType)
    {
        this.color = color;
        this.material = material;
        this.soundType = soundType;
        this.isTranslucent = false;
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
        return soundType;
    }
    
    public boolean isTranslucent() {
        return this.isTranslucent;
    }

    public BlockBehaviour.Properties adjustProperties(final BlockBehaviour.Properties properties)
    {
        if (this.isTranslucent)
        {
            properties.noCollission();
        }
        return properties;
    }
}
