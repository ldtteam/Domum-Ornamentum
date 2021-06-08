package com.ldtteam.domumornamentum.datagen.shingle.normal;

import com.ldtteam.datagenerators.models.block.BlockModelJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.TimberFrameBlock;
import com.ldtteam.domumornamentum.block.types.ShingleFaceType;
import com.ldtteam.domumornamentum.block.types.ShingleShapeType;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;

public class ShinglesBlockModelProvider implements IDataProvider
{
    private final DataGenerator generator;

    public ShinglesBlockModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull DirectoryCache cache) throws IOException
    {
        for (final ShingleShapeType shapeType : ShingleShapeType.values())
        {
            final BlockModelJson modelJson = new BlockModelJson();

            modelJson.setLoader(Constants.MATERIALLY_TEXTURED_MODEL_LOADER.toString());
            modelJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/shingles/" + shapeType.name().toLowerCase(Locale.ROOT) + "_spec").toString());

            final String name = shapeType.name().toLowerCase(Locale.ROOT) + ".json";
            final Path saveFile = this.generator.getOutputFolder().resolve(DataGeneratorConstants.SHINGLES_BLOCK_MODELS_DIR).resolve(name);

            IDataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(modelJson), saveFile);
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Shingles Block Model Provider";
    }
}
