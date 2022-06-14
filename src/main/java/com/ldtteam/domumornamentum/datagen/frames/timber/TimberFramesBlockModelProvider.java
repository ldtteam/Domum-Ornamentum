package com.ldtteam.domumornamentum.datagen.frames.timber;

import com.ldtteam.datagenerators.models.block.BlockModelJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.TimberFrameBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class TimberFramesBlockModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public TimberFramesBlockModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        for (final TimberFrameBlock timberFrame : ModBlocks.getInstance().getTimberFrames())
        {
            final BlockModelJson modelJson = new BlockModelJson();

            modelJson.setLoader(Constants.MATERIALLY_TEXTURED_MODEL_LOADER.toString());
            modelJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/timber_frames/" + Objects.requireNonNull(timberFrame.getRegistryName()).getPath() + "_spec").toString());

            final String name = Objects.requireNonNull(timberFrame.getRegistryName()).getPath() + ".json";
            final Path saveFile = this.generator.getOutputFolder().resolve(DataGeneratorConstants.TIMBER_FRAMES_BLOCK_MODELS_DIR).resolve(name);

            DataProvider.saveStable(cache, DataGeneratorConstants.serialize(modelJson), saveFile);
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Timber Frames Block Model Provider";
    }
}
