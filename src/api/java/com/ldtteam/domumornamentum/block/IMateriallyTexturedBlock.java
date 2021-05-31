package com.ldtteam.domumornamentum.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;

public interface IMateriallyTexturedBlock
{
    Collection<IMateriallyTexturedBlockComponent> getComponents();
}
