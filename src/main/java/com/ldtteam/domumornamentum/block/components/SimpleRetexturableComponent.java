package com.ldtteam.domumornamentum.block.components;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import net.minecraft.block.Block;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public class SimpleRetexturableComponent implements IMateriallyTexturedBlockComponent
{

    private final ResourceLocation id;
    private final ITag<Block> validSkins;
    private final Block defaultBlock;

    public SimpleRetexturableComponent(final ResourceLocation id, final ITag<Block> validSkins, final Block defaultBlock) {
        this.id = id;
        this.validSkins = validSkins;
        this.defaultBlock = defaultBlock;
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }

    @Override
    public ITag<Block> getValidSkins()
    {
        return validSkins;
    }

    @Override
    public Block getDefault()
    {
        return defaultBlock;
    }
}
