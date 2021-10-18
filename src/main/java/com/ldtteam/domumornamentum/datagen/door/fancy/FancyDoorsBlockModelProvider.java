package com.ldtteam.domumornamentum.datagen.door.fancy;

import com.ldtteam.datagenerators.models.block.BlockModelJson;
import com.ldtteam.domumornamentum.block.types.DoorType;
import com.ldtteam.domumornamentum.block.types.FancyDoorType;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class FancyDoorsBlockModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public FancyDoorsBlockModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        Set<String> uniqueValues = new HashSet<>();
        for (FancyDoorType doorsShape : FancyDoorType.values())
        {
            String shapeName = doorsShape.getSerializedName();
            if (uniqueValues.add(shapeName))
            {
                final BlockModelJson bottomJson = new BlockModelJson();

                bottomJson.setLoader(Constants.MATERIALLY_TEXTURED_MODEL_LOADER.toString());
                bottomJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/doors/fancy/door_" + shapeName + "_spec").toString());

                final String bottomName = "door_" + shapeName + ".json";
                final Path bottomPath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.FANCY_DOORS_BLOCK_MODELS_DIR).resolve(bottomName);

                DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(bottomJson), bottomPath);

                final BlockModelJson topJson = new BlockModelJson();

                topJson.setLoader(Constants.MATERIALLY_TEXTURED_MODEL_LOADER.toString());
                topJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/doors/fancy/door_top_" + shapeName + "_spec").toString());

                final String topName = "door_top_" + shapeName + ".json";
                final Path topPath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.FANCY_DOORS_BLOCK_MODELS_DIR).resolve(topName);

                DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(topJson), topPath);
            }
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Fancy Doors Block Model Provider";
    }
}
