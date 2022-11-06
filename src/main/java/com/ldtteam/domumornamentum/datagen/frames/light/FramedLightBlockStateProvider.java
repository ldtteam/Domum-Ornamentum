package com.ldtteam.domumornamentum.datagen.frames.light;

import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FramedLightBlock;
import com.ldtteam.domumornamentum.block.decorative.FramedLightBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.core.Direction;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FramedLightBlockStateProvider implements DataProvider
{
    private final DataGenerator generator;

    public FramedLightBlockStateProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final CachedOutput cache) throws IOException
    {
        for (final FramedLightBlock framedLight : ModBlocks.getInstance().getFramedLights())
        {
            createBlockstateFile(cache, framedLight);
        }
    }

    private void createBlockstateFile(final CachedOutput cache, final FramedLightBlock timberFrame) throws IOException
    {
        if (timberFrame.getRegistryName() == null)
            return;

        final Map<String, BlockstateVariantJson> variants = new HashMap<>();
        variants.put("", new BlockstateVariantJson(new BlockstateModelJson(Constants.MOD_ID + ":block/framed_light/" + timberFrame.getRegistryName().getPath())));

        final BlockstateJson blockstate = new BlockstateJson(variants);

        final Path blockstateFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.BLOCKSTATE_DIR);
        final Path blockstatePath = blockstateFolder.resolve(timberFrame.getRegistryName().getPath() + ".json");

        DataProvider.saveStable(cache, DataGeneratorConstants.serialize(blockstate), blockstatePath);

    }

    @NotNull
    @Override
    public String getName()
    {
        return "Framed Light BlockStates Provider";
    }
}
