package com.ldtteam.domumornamentum.datagen.global;

import com.ldtteam.datagenerators.loot_table.LootTableJson;
import com.ldtteam.datagenerators.loot_table.LootTableTypeEnum;
import com.ldtteam.datagenerators.loot_table.pool.PoolJson;
import com.ldtteam.datagenerators.loot_table.pool.conditions.survives_explosion.SurvivesExplosionConditionJson;
import com.ldtteam.datagenerators.loot_table.pool.entry.EntryJson;
import com.ldtteam.datagenerators.loot_table.pool.entry.EntryTypeEnum;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.BrickBlock;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;

import static com.ldtteam.domumornamentum.util.DataGeneratorConstants.GSON;
import static com.ldtteam.domumornamentum.util.DataGeneratorConstants.LOOT_TABLES_DIR;

/**
 * This class generates the default loot_table for blocks (if a block is destroyed, it drops its item).
 */
public class GlobalLootTableProvider implements DataProvider
{
    private final DataGenerator generator;

    public GlobalLootTableProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final HashCache cache) throws IOException
    {
        for (final BrickBlock block : ModBlocks.getBricks())
        {
            saveBlock(block, cache);
        }

        for (final ExtraBlock block : ModBlocks.getExtraTopBlocks())
        {
            saveBlock(block, cache);
        }
    }

    private void saveBlock(final Block block, final HashCache cache) throws IOException
    {
        final EntryJson entryJson = new EntryJson();
        entryJson.setType(EntryTypeEnum.ITEM);
        entryJson.setName(block.getRegistryName().toString());

        final PoolJson poolJson = new PoolJson();
        poolJson.setEntries(Collections.singletonList(entryJson));
        poolJson.setRolls(1);
        poolJson.setConditions(Collections.singletonList(new SurvivesExplosionConditionJson()));

        final LootTableJson lootTableJson = new LootTableJson();
        lootTableJson.setType(LootTableTypeEnum.BLOCK);
        lootTableJson.setPools(Collections.singletonList(poolJson));

        final Path savePath = generator.getOutputFolder().resolve(LOOT_TABLES_DIR).resolve(block.getRegistryName().getPath() + ".json");
        DataProvider.save(GSON, cache, lootTableJson.serialize(), savePath);
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Default Block Loot Tables Provider";
    }
}
