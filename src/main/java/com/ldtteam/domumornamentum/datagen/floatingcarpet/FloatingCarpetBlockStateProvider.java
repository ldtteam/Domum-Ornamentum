package com.ldtteam.domumornamentum.datagen.floatingcarpet;

import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.block.decorative.FloatingCarpetBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FloatingCarpetBlockStateProvider implements DataProvider
{
    private final DataGenerator generator;

    public FloatingCarpetBlockStateProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final CachedOutput cache) throws IOException
    {
        for (final FloatingCarpetBlock state : ModBlocks.getInstance().getFloatingCarpets())
        {
            final Map<String, BlockstateVariantJson> variants = new HashMap<>();
            variants.put("", new BlockstateVariantJson(new BlockstateModelJson("minecraft:block/" + state.getColor().getName() + "_carpet")));

            final BlockstateJson blockstate = new BlockstateJson(variants);

            final Path blockstateFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.BLOCKSTATE_DIR);
            final Path blockstatePath = blockstateFolder.resolve(Objects.requireNonNull(state.getRegistryName()).getPath() + ".json");

            DataProvider.saveStable(cache, DataGeneratorConstants.serialize(blockstate), blockstatePath);
        }
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Floating Carpet BlockStates Provider";
    }
}
