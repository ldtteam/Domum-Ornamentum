package com.ldtteam.domumornamentum.datagen.wall.vanilla;

import com.ldtteam.datagenerators.models.block.BlockModelJson;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

public class WallBlockModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public WallBlockModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        final BlockModelJson postJson = new BlockModelJson();

        postJson.setLoader(Constants.MOD_ID + ":" + Constants.MATERIALLY_TEXTURED_MODEL_LOADER);
        postJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/walls/wall_post_spec").toString());

        final String postName = "wall_post.json";
        final Path postSavePath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.WALLS_BLOCK_MODELS_DIR).resolve(postName);

        DataProvider.saveStable(cache, DataGeneratorConstants.serialize(postJson), postSavePath);

        final BlockModelJson onSideJson = new BlockModelJson();

        onSideJson.setLoader(Constants.MOD_ID + ":" + Constants.MATERIALLY_TEXTURED_MODEL_LOADER);
        onSideJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/walls/wall_side_spec").toString());

        final String onSideName = "wall_side.json";
        final Path onSideSavePath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.WALLS_BLOCK_MODELS_DIR).resolve(onSideName);

        DataProvider.saveStable(cache, DataGeneratorConstants.serialize(onSideJson), onSideSavePath);

        final BlockModelJson onSideTallJson = new BlockModelJson();

        onSideTallJson.setLoader(Constants.MOD_ID + ":" + Constants.MATERIALLY_TEXTURED_MODEL_LOADER);
        onSideTallJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/walls/wall_side_tall_spec").toString());

        final String onSideTallName = "wall_side_tall.json";
        final Path onSideTallSavePath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.WALLS_BLOCK_MODELS_DIR).resolve(onSideTallName);

        DataProvider.saveStable(cache, DataGeneratorConstants.serialize(onSideTallJson), onSideTallSavePath);
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Walls Block Model Provider";
    }
}
