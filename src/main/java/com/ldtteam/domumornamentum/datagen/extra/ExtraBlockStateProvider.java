package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ExtraBlockStateProvider implements DataProvider
{
    private final DataGenerator generator;

    public ExtraBlockStateProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final HashCache cache) throws IOException
    {
        for (final ExtraBlock state : ModBlocks.getExtraTopBlocks())
        {
            final Map<String, BlockstateVariantJson> variants = new HashMap<>();
            variants.put("", new BlockstateVariantJson(new BlockstateModelJson(Constants.MOD_ID + ":block/extra/" + state.getRegistryName().getPath())));

            final BlockstateJson blockstate = new BlockstateJson(variants);

            final Path blockstateFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.BLOCKSTATE_DIR);
            final Path blockstatePath = blockstateFolder.resolve(state.getRegistryName().getPath() + ".json");

            DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(blockstate), blockstatePath);
        }
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Extra BlockStates Provider";
    }
}
