package com.ldtteam.domumornamentum.datagen.slab;

import com.ldtteam.datagenerators.models.block.BlockModelJson;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

public class SlabBlockModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public SlabBlockModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        final BlockModelJson fullJson = new BlockModelJson();

        fullJson.setLoader(Constants.MATERIALLY_TEXTURED_MODEL_LOADER);
        fullJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/slabs/slab_full_spec").toString());

        final String fullName = "slab_full.json";
        final Path fullSavePath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.SLABS_BLOCK_MODELS_DIR).resolve(fullName);

        DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(fullJson), fullSavePath);

        final BlockModelJson lowerJson = new BlockModelJson();

        lowerJson.setLoader(Constants.MATERIALLY_TEXTURED_MODEL_LOADER);
        lowerJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/slabs/slab_lower_spec").toString());

        final String lowerName = "slab_lower.json";
        final Path lowerSavePath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.SLABS_BLOCK_MODELS_DIR).resolve(lowerName);

        DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(lowerJson), lowerSavePath);

        final BlockModelJson upperJson = new BlockModelJson();

        upperJson.setLoader(Constants.MATERIALLY_TEXTURED_MODEL_LOADER);
        upperJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/slabs/slab_upper_spec").toString());

        final String upperName = "slab_upper.json";
        final Path upperSavePath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.SLABS_BLOCK_MODELS_DIR).resolve(upperName);

        DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(upperJson), upperSavePath);
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Slabs Block Model Provider";
    }
}
