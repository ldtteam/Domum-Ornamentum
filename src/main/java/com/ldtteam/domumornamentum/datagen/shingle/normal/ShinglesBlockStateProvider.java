package com.ldtteam.domumornamentum.datagen.shingle.normal;

import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ShingleBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.DataProvider;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ShinglesBlockStateProvider implements DataProvider
{
    private final DataGenerator generator;

    public ShinglesBlockStateProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final HashCache cache) throws IOException
    {
        createBlockstateFile(cache, ModBlocks.getShingle());
    }

    private void createBlockstateFile(final HashCache cache, final ShingleBlock shingle) throws IOException
    {
        if (shingle.getRegistryName() == null)
            return;

        final Map<String, BlockstateVariantJson> variants = new HashMap<>();

        for (Direction facingValue : StairBlock.FACING.getPossibleValues())
        {
            for (StairsShape shapeValue : StairBlock.SHAPE.getPossibleValues())
            {
                for (Half halfValue : StairBlock.HALF.getPossibleValues())
                {
                    final String variantKey = "facing=" + facingValue + ",shape=" + shapeValue + ",half=" + halfValue;

                    int y = getYFromFacing(facingValue);
                    y = y + getYFromShape(shapeValue);
                    y = y + getYFromHalf(halfValue, shapeValue);

                    int x = halfValue == Half.TOP ? 180 : 0;

                    final String modelLocation = Constants.MOD_ID + ":block/shingle/" + ShingleBlock.getTypeFromShape(shapeValue).name().toLowerCase(Locale.ROOT);

                    final BlockstateModelJson model = new BlockstateModelJson(modelLocation, x, y);
                    final BlockstateVariantJson variant = new BlockstateVariantJson(model);

                    variants.put(variantKey, variant);
                }
            }
        }

        final BlockstateJson blockstate = new BlockstateJson(variants);

        final Path blockstateFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.BLOCKSTATE_DIR);
        final Path blockstatePath = blockstateFolder.resolve(shingle.getRegistryName().getPath() + ".json");

        DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(blockstate), blockstatePath);

    }

    @NotNull
    @Override
    public String getName()
    {
        return "Shingles BlockStates Provider";
    }

    private int getYFromHalf(final Half half, final StairsShape shape)
    {
        if (half == Half.TOP)
        {
            if (shape == StairsShape.STRAIGHT)
            {
                return 180;
            }
            return 90;
        }
        return 0;
    }

    private int getYFromShape(final StairsShape shape)
    {
        switch (shape)
        {
            default:
                return 0;
            case OUTER_RIGHT:
            case INNER_RIGHT:
                return 90;
        }
    }

    private int getYFromFacing(final Direction facing)
    {
        switch (facing)
        {
            default:
                return 0;
            case WEST:
                return 90;
            case NORTH:
                return 180;
            case EAST:
                return 270;
        }
    }
}
