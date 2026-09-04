package com.ldtteam.domumornamentum.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

/**
 * Helpers for the Minecraft 26 TagsProvider API where add() takes ResourceKey.
 */
public final class TagAppenderHelper
{
    private TagAppenderHelper()
    {
    }

    public static TagAppender<Block> addBlocks(final TagAppender<Block> appender, final Block... blocks)
    {
        for (final Block block : blocks)
        {
            appender.add(ResourceKey.create(Registries.BLOCK, BuiltInRegistries.BLOCK.getKey(block)));
        }
        return appender;
    }

    public static TagAppender<Block> addBlocks(final TagAppender<Block> appender, final Iterable<Block> blocks)
    {
        for (final Block block : blocks)
        {
            appender.add(ResourceKey.create(Registries.BLOCK, BuiltInRegistries.BLOCK.getKey(block)));
        }
        return appender;
    }

    public static TagAppender<Block> addKeys(
        final TagAppender<Block> appender,
        final Iterable<ResourceKey<Block>> keys
    )
    {
        for (final ResourceKey<Block> key : keys)
        {
            appender.add(key);
        }
        return appender;
    }
}
