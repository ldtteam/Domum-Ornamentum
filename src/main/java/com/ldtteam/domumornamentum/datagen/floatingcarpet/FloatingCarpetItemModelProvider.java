package com.ldtteam.domumornamentum.datagen.floatingcarpet;

import com.ldtteam.datagenerators.models.item.ItemModelJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FloatingCarpetBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FloatingCarpetItemModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public FloatingCarpetItemModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        final ItemModelJson modelJson = new ItemModelJson();

        for (final FloatingCarpetBlock state : ModBlocks.getInstance().getFloatingCarpets())
        {
            final String modelLocation = "minecraft:block/" + state.getColor().getName() + "_carpet";

            modelJson.setParent(modelLocation);

            DataProvider.save(DataGeneratorConstants.GSON,
              cache,
              DataGeneratorConstants.serialize(modelJson),
              generator.getOutputFolder().resolve(DataGeneratorConstants.ITEM_MODEL_DIR).resolve(state.getRegistryName().getPath() + ".json"));
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Floating Carpet Item Model Provider";
    }
}
