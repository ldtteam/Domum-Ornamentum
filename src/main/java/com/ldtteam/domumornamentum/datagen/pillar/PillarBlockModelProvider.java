package com.ldtteam.domumornamentum.datagen.pillar;

import com.ldtteam.datagenerators.models.block.BlockModelJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.PillarBlock;
import com.ldtteam.domumornamentum.block.decorative.TimberFrameBlock;
import com.ldtteam.domumornamentum.block.types.PillarShapeType;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;

public class PillarBlockModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public PillarBlockModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        for (final PillarShapeType shapeType : PillarShapeType.values())
        {
            final BlockModelJson modelJson = new BlockModelJson();

            modelJson.setLoader(Constants.MATERIALLY_TEXTURED_MODEL_LOADER.toString());
            modelJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/pillars/" + shapeType.name().toLowerCase(Locale.ROOT) + "_spec").toString());

            final String name = shapeType.name().toLowerCase(Locale.ROOT) + ".json";
            final Path saveFile = this.generator.getOutputFolder().resolve(DataGeneratorConstants.PILLAR_BLOCK_MODELS_DIR).resolve(name);

            DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(modelJson), saveFile);
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Pillar Block Model Provider";
    }
}
