package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.datagenerators.models.block.BlockModelJson;
import com.ldtteam.domumornamentum.block.types.BrickType;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;

public class BrickBlockModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public BrickBlockModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        for (final BrickType type : BrickType.values())
        {
            final BlockModelJson modelJson = new BlockModelJson();

            modelJson.setParent("minecraft:block/cube_all");
            final HashMap<String, String> textureMap = new HashMap<>();
            textureMap.put("all", Constants.MOD_ID + ":block/brick/" + type.getSerializedName());
            modelJson.setTextures(textureMap);

            final String name = type.getSerializedName().toLowerCase(Locale.ROOT) + ".json";
            final Path saveFile = this.generator.getOutputFolder().resolve(DataGeneratorConstants.BRICK_BLOCK_MODELS_DIR).resolve(name);

            DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(modelJson), saveFile);
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Brick Block Model Provider";
    }
}
