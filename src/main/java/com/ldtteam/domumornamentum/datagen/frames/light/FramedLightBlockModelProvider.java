package com.ldtteam.domumornamentum.datagen.frames.light;

import com.ldtteam.datagenerators.models.block.BlockModelJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FramedLightBlock;
import com.ldtteam.domumornamentum.block.decorative.TimberFrameBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class FramedLightBlockModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public FramedLightBlockModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        for (final FramedLightBlock framedLight : ModBlocks.getInstance().getFramedLights())
        {
            final BlockModelJson modelJson = new BlockModelJson();

            modelJson.setLoader(Constants.MOD_ID + ":" + Constants.MATERIALLY_TEXTURED_MODEL_LOADER.toString());
            modelJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/framed_light/" + Objects.requireNonNull(framedLight.getRegistryName()).getPath() + "_spec").toString());

            final String name = Objects.requireNonNull(framedLight.getRegistryName()).getPath() + ".json";
            final Path saveFile = this.generator.getOutputFolder().resolve(DataGeneratorConstants.FRAMED_LIGHT_BLOCK_MODELS_DIR).resolve(name);

            DataProvider.saveStable(cache, DataGeneratorConstants.serialize(modelJson), saveFile);
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Framed Light Block Model Provider";
    }
}
