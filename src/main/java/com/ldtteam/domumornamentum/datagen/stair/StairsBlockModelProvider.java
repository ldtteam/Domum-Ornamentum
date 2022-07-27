package com.ldtteam.domumornamentum.datagen.stair;

import com.ldtteam.datagenerators.models.block.BlockModelJson;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.StairsShape;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class StairsBlockModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public StairsBlockModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        Set<String> uniqueValues = new HashSet<>();
        for (StairsShape stairsShape : StairsShape.values())
        {
            String shapeName = getTypeFromShape(stairsShape);
            if (uniqueValues.add(shapeName))
            {
                final BlockModelJson modelJson = new BlockModelJson();

                modelJson.setLoader(Constants.MOD_ID + ":" + Constants.MATERIALLY_TEXTURED_MODEL_LOADER.toString());
                modelJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/stairs/" + shapeName + "_spec").toString());

                final String name = shapeName + ".json";
                final Path saveFile = this.generator.getOutputFolder().resolve(DataGeneratorConstants.STAIRS_BLOCK_MODELS_DIR).resolve(name);

                DataProvider.saveStable(cache, DataGeneratorConstants.serialize(modelJson), saveFile);
            }
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Stairs Block Model Provider";
    }

    /**
     * Get the model type from a StairsShape object
     *
     * @param shape the StairsShape object
     * @return the model type for provided StairsShape
     */
    private static String getTypeFromShape(final StairsShape shape)
    {
        return switch (shape)
                 {
                     case INNER_LEFT, INNER_RIGHT -> "stairs_inner";
                     case OUTER_LEFT, OUTER_RIGHT -> "stairs_outer";
                     default -> "stairs";
                 };
    }
}
