package com.ldtteam.domumornamentum.util;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.client.event.handlers.ClientTickEventHandler;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class MaterialTextureDataUtil
{

    private MaterialTextureDataUtil()
    {
        throw new IllegalStateException("Can not instantiate an instance of: MaterialTextureDataUtil. This is a utility class");
    }

    public static MaterialTextureData generateRandomTextureDataFrom(final ItemStack stack) {
        final Item item = stack.getItem();
        if (!(item instanceof BlockItem blockItem))
            return MaterialTextureData.EMPTY;

        final Block block = blockItem.getBlock();
        return generateRandomTextureDataFrom(block);
    }

    @NotNull
    public static MaterialTextureData generateRandomTextureDataFrom(final Block block)
    {
        if (!(block instanceof IMateriallyTexturedBlock materiallyTexturedBlock))
            return MaterialTextureData.EMPTY;

        try {
            final MaterialTextureData newData = new MaterialTextureData();

            int localOffset = BuiltInRegistries.BLOCK.getId(block);
            int offsetIndex = 0;
            for (IMateriallyTexturedBlockComponent component : materiallyTexturedBlock.getComponents())
            {
                final List<Block> candidates = new ArrayList<>(
                  StreamSupport
                    .stream(BuiltInRegistries.BLOCK.getTagOrEmpty(component.getValidSkins()).spliterator(), false)
                    .map(Holder::value).toList());
                if (candidates.isEmpty())
                {
                    continue;
                }

                final int index = (int) ((ClientTickEventHandler.getInstance().getNonePausedTicks() / 20 + (offsetIndex += localOffset)) % candidates.size());
                final Block texture = candidates.get(index);
                newData.getTexturedComponents().put(component.getId(), texture);
            }

            return newData;
        }
        catch (Exception e)
        {
            return MaterialTextureData.EMPTY;
        }
    }
}
