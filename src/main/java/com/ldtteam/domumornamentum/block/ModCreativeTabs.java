package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

/**
 * Class used to handle the creativeTab of structurize.
 */
public final class ModCreativeTabs
{
    public static final ItemGroup GENERAL = new ItemGroup(Constants.MOD_ID + ".general")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModBlocks.getArchitectsCutterBlock());
        }

        @Override
        public boolean hasSearchBar()
        {
            return false;
        }
    };


    public static final ItemGroup TIMBER_FRAMES = new ItemGroup(Constants.MOD_ID + ".timber_frames")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModBlocks.getTimberFrames().stream().findFirst().orElse(null));
        }

        @Override
        public boolean hasSearchBar()
        {
            return false;
        }
    };

    public static final ItemGroup SHINGLES = new ItemGroup(Constants.MOD_ID + ".shingles")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModBlocks.getShingleBlock());
        }

        @Override
        public boolean hasSearchBar()
        {
            return false;
        }
    };

    public static final ItemGroup SHINGLE_SLABS = new ItemGroup(Constants.MOD_ID + ".shingle_slabs")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModBlocks.getShingleSlabBlock());
        }

        @Override
        public boolean hasSearchBar()
        {
            return false;
        }
    };

    public static final ItemGroup PAPERWALLS = new ItemGroup(Constants.MOD_ID + ".paperwalls")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModBlocks.getPaperwallBlock());
        }

        @Override
        public boolean hasSearchBar()
        {
            return false;
        }
    };
}