package com.ldtteam.domumornamentum.block;

import com.google.common.collect.Lists;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class MateriallyTexturedBlockManager
{
    private static final MateriallyTexturedBlockManager INSTANCE = new MateriallyTexturedBlockManager();

    public static MateriallyTexturedBlockManager getInstance()
    {
        return INSTANCE;
    }

    private MateriallyTexturedBlockManager()
    {
    }

    private List<IMateriallyTexturedBlock> blocks = Lists.newArrayList();
    private int maxTexturableComponentCount = -1;

    public int getMaxTexturableComponentCount() {
        if (this.maxTexturableComponentCount >= 0)
            return this.maxTexturableComponentCount;

        if (this.blocks.isEmpty()) {
            this.blocks = StreamSupport.stream(ForgeRegistries.BLOCKS.spliterator(), false)
              .filter(IMateriallyTexturedBlock.class::isInstance)
              .map(IMateriallyTexturedBlock.class::cast)
              .collect(Collectors.toList());
        }

        this.maxTexturableComponentCount = this.blocks.stream()
          .map(IMateriallyTexturedBlock::getComponents)
          .mapToInt(Collection::size)
          .max()
          .orElse(0);

        return this.maxTexturableComponentCount;
    }

    public boolean doesItemStackContainsMaterialForSlot(final int slotIndex, final ItemStack stack) {
        if (stack.isEmpty())
            return true;

        if (!(stack.getItem() instanceof final BlockItem blockItem))
            return false;

        final Block block = blockItem.getBlock();

        if (slotIndex >= getMaxTexturableComponentCount())
            return false;

        return this.blocks.stream()
                 .map(IMateriallyTexturedBlock::getComponents)
                 .filter(c -> c.size() > slotIndex)
                 .map(ArrayList::new)
                 .map(l -> l.get(slotIndex))
                 .anyMatch(c -> c.getValidSkins().contains(block));
    }
}
