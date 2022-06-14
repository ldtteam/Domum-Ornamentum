package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.datagenerators.models.item.ItemModelJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ExtraItemModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public ExtraItemModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        final ItemModelJson modelJson = new ItemModelJson();

        for (final ExtraBlock state : ModBlocks.getInstance().getExtraTopBlocks())
        {
            final String modelLocation = Constants.MOD_ID + ":block/extra/" + state.getRegistryName().getPath();

            modelJson.setParent(modelLocation);

            DataProvider.saveStable(
              cache,
              DataGeneratorConstants.serialize(modelJson),
              generator.getOutputFolder().resolve(DataGeneratorConstants.ITEM_MODEL_DIR).resolve(state.getRegistryName().getPath() + ".json"));
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Extra Item Model Provider";
    }
}
