package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.datagenerators.models.item.ItemModelJson;
import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.BrickBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BrickItemModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public BrickItemModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        final ItemModelJson modelJson = new ItemModelJson();

        for (final Block state : IModBlocks.getInstance().getBricks())
        {
            final String modelLocation = Constants.MOD_ID + ":block/brick/" + ForgeRegistries.BLOCKS.getKey(state).getPath();

            modelJson.setParent(modelLocation);

            DataProvider.saveStable(
              cache,
              DataGeneratorConstants.serialize(modelJson),
              generator.getOutputFolder().resolve(DataGeneratorConstants.ITEM_MODEL_DIR).resolve(ForgeRegistries.BLOCKS.getKey(state).getPath() + ".json"));
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Brick Item Model Provider";
    }
}
