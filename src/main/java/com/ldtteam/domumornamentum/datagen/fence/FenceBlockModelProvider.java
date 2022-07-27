package com.ldtteam.domumornamentum.datagen.fence;

import com.ldtteam.datagenerators.models.block.BlockModelJson;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;

public class FenceBlockModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public FenceBlockModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        final BlockModelJson postJson = new BlockModelJson();

        postJson.setLoader(Constants.MOD_ID + ":" + Constants.MATERIALLY_TEXTURED_MODEL_LOADER);
        postJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/fences/fence_post_spec").toString());

        final String postName = "fence_post.json";
        final Path postSavePath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.FENCES_BLOCK_MODELS_DIR).resolve(postName);

        DataProvider.saveStable(cache, DataGeneratorConstants.serialize(postJson), postSavePath);

        final BlockModelJson onSideJson = new BlockModelJson();

        onSideJson.setLoader(Constants.MOD_ID + ":" + Constants.MATERIALLY_TEXTURED_MODEL_LOADER);
        onSideJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/fences/fence_side_spec").toString());

        final String onSideName = "fence_side.json";
        final Path onSideSavePath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.FENCES_BLOCK_MODELS_DIR).resolve(onSideName);

        DataProvider.saveStable(cache, DataGeneratorConstants.serialize(onSideJson), onSideSavePath);
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Fences Block Model Provider";
    }
}
