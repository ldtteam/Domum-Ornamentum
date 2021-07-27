package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.datagenerators.models.block.BlockModelJson;
import com.ldtteam.domumornamentum.block.types.ExtraShingleTopType;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;

public class ExtraBlockModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public ExtraBlockModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        for (final ExtraShingleTopType type : ExtraShingleTopType.values())
        {
            final BlockModelJson modelJson = new BlockModelJson();

            modelJson.setParent("minecraft:block/cube_all");
            final HashMap<String, String> textureMap = new HashMap<>();
            textureMap.put("all", Constants.MOD_ID + ":block/shingle/" + type.getSerializedName());
            modelJson.setTextures(textureMap);

            final String name = type.getSerializedName().toLowerCase(Locale.ROOT) + ".json";
            final Path saveFile = this.generator.getOutputFolder().resolve(DataGeneratorConstants.EXTRA_BLOCK_MODELS_DIR).resolve(name);

            DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(modelJson), saveFile);
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Shingles Block Model Provider";
    }
}
