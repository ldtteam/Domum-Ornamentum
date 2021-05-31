package com.ldtteam.domumornamentum.datagen.frames.timber;

import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.TimberFrameBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TimberFramesBlockStateProvider implements IDataProvider
{
    private final DataGenerator generator;

    public TimberFramesBlockStateProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void act(@NotNull final DirectoryCache cache) throws IOException
    {
        for (final TimberFrameBlock timberFrame : ModBlocks.getTimberFrames())
        {
            createBlockstateFile(cache, timberFrame);
        }
    }

    private void createBlockstateFile(final DirectoryCache cache, final TimberFrameBlock timberFrame) throws IOException
    {
        if (timberFrame.getRegistryName() == null)
            return;

        final Map<String, BlockstateVariantJson> variants = new HashMap<>();

        for (final Direction direction : TimberFrameBlock.FACING.getAllowedValues())
        {
            final String modelLocation = new ResourceLocation(Constants.MOD_ID, "block/timber_frames/" + Objects.requireNonNull(timberFrame.getRegistryName()).getPath()).toString();

            int x = 0;
            int y = 0;

            if (timberFrame.getTimberFrameType().isRotatable())
            {
                x = getXfromDirection(direction);
                y = getYfromDirection(direction);
            }

            final BlockstateModelJson model = new BlockstateModelJson(modelLocation, x, y);

            final BlockstateVariantJson variant = new BlockstateVariantJson(model);

            variants.put("facing=" + direction.getString(), variant);
        }

        final BlockstateJson blockstate = new BlockstateJson(variants);

        final Path blockstateFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.BLOCKSTATE_DIR);
        final Path blockstatePath = blockstateFolder.resolve(timberFrame.getRegistryName().getPath() + ".json");

        IDataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(blockstate), blockstatePath);

    }

    private int getXfromDirection(final Direction direction)
    {
        switch (direction)
        {
            case UP:
                return 0;
            case DOWN:
                return 180;
            default:
                return 90;
        }
    }

    private int getYfromDirection(final Direction direction)
    {
        switch (direction)
        {
            default:
                return 0;
            case EAST:
                return 90;
            case SOUTH:
                return 180;
            case WEST:
                return 270;
        }
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Timber Frames BlockStates Provider";
    }
}
