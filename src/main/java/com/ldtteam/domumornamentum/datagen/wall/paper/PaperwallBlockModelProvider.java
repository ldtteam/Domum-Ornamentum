package com.ldtteam.domumornamentum.datagen.wall.paper;

import com.ldtteam.datagenerators.models.block.BlockModelJson;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;

public class PaperwallBlockModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public PaperwallBlockModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        final BlockModelJson postJson = new BlockModelJson();

        postJson.setLoader(Constants.MATERIALLY_TEXTURED_MODEL_LOADER.toString());
        postJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/paperwalls/blockpaperwall_post_spec").toString());

        final String postName = "blockpaperwall_post.json";
        final Path postSavePath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.PAPERWALLS_BLOCK_MODELS_DIR).resolve(postName);

        DataProvider.saveStable(cache, DataGeneratorConstants.serialize(postJson), postSavePath);

        for (final Direction possibleValue : HorizontalDirectionalBlock.FACING.getPossibleValues())
        {
            final BlockModelJson onSideJson = new BlockModelJson();

            onSideJson.setLoader(Constants.MATERIALLY_TEXTURED_MODEL_LOADER.toString());
            onSideJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/paperwalls/blockpaperwall_side_" + possibleValue.name().toLowerCase(Locale.ROOT) + "_spec").toString());

            final String onSideName = "blockpaperwall_" + possibleValue.name().toLowerCase(Locale.ROOT) + "_side.json";
            final Path onSideSavePath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.PAPERWALLS_BLOCK_MODELS_DIR).resolve(onSideName);

            DataProvider.saveStable(cache, DataGeneratorConstants.serialize(onSideJson), onSideSavePath);


            final BlockModelJson offSideJson = new BlockModelJson();

            offSideJson.setLoader(Constants.MATERIALLY_TEXTURED_MODEL_LOADER.toString());
            offSideJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/paperwalls/blockpaperwall_side_off_" + possibleValue.name().toLowerCase(Locale.ROOT) + "_spec").toString());

            final String offSideName = "blockpaperwall_off_" + possibleValue.name().toLowerCase(Locale.ROOT) + "_side.json";
            final Path offSideSavePath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.PAPERWALLS_BLOCK_MODELS_DIR).resolve(offSideName);

            DataProvider.saveStable(cache, DataGeneratorConstants.serialize(offSideJson), offSideSavePath);
        }
        
        
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Paperwalls Block Model Provider";
    }
}
