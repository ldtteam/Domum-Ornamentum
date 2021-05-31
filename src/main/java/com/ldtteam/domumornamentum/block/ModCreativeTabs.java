package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

/**
 * Class used to handle the creativeTab of structurize.
 */
public final class ModCreativeTabs
{
    public static final ItemGroup TIMBER_FRAMES = new ItemGroup(Constants.MOD_ID + ".timber_frames")
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModBlocks.getTimberFrames().stream().findFirst().orElse(null));
        }

        @Override
        public boolean hasSearchBar()
        {
            return true;
        }
    };

    public static final ItemGroup SHINGLES = new ItemGroup(Constants.MOD_ID + ".shingles")
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModBlocks.getShingleBlock());
        }

        @Override
        public boolean hasSearchBar()
        {
            return true;
        }
    };
}