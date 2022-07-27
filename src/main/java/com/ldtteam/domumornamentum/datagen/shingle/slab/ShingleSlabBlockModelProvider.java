package com.ldtteam.domumornamentum.datagen.shingle.slab;

import com.ldtteam.datagenerators.models.block.BlockModelJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.TimberFrameBlock;
import com.ldtteam.domumornamentum.block.types.ShingleFaceType;
import com.ldtteam.domumornamentum.block.types.ShingleShapeType;
import com.ldtteam.domumornamentum.block.types.ShingleSlabShapeType;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;

public class ShingleSlabBlockModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public ShingleSlabBlockModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        for (final ShingleSlabShapeType shapeType : ShingleSlabShapeType.values())
        {
            final BlockModelJson modelJson = new BlockModelJson();

            modelJson.setLoader(Constants.MOD_ID + ":" + Constants.MATERIALLY_TEXTURED_MODEL_LOADER.toString());
            modelJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/shingle_slab/shingle_slab_" + shapeType.name().toLowerCase(Locale.ROOT) + "_spec").toString());

            final String name = shapeType.name().toLowerCase(Locale.ROOT) + ".json";
            final Path saveFile = this.generator.getOutputFolder().resolve(DataGeneratorConstants.SHINGLE_SLABS_BLOCK_MODELS_DIR).resolve(name);

            DataProvider.saveStable(cache, DataGeneratorConstants.serialize(modelJson), saveFile);
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Shingle Slabs Block Model Provider";
    }
}
