package com.ldtteam.domumornamentum.block;

import com.google.common.collect.Lists;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class MateriallyTexturedBlockManager implements IMateriallyTexturedBlockManager
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

    @Override
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

    @Override
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
                 .anyMatch(c -> block.defaultBlockState().is(c.getValidSkins()));
    }

    @Override
    public boolean doesItemStackContainsMaterialForSlot(final int slotIndex, final ItemStack stack, final ItemStack type) {
        if (stack.isEmpty())
            return true;

        if (!(stack.getItem() instanceof final BlockItem blockItem))
            return false;

        final Block block = blockItem.getBlock();

        if (slotIndex >= getMaxTexturableComponentCount())
            return false;

        if (type.getItem() instanceof BlockItem item && item.getBlock() instanceof IMateriallyTexturedBlock texturedBlock)
        {
            if (texturedBlock.getComponents().size() > slotIndex)
            {
                return block.defaultBlockState().is(((IMateriallyTexturedBlockComponent) texturedBlock.getComponents().toArray()[slotIndex]).getValidSkins());
            }
        }

        return false;
    }
}
