package com.ldtteam.domumornamentum.datagen.trapdoor;

import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.block.vanilla.TrapdoorBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.level.block.state.properties.Half;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static net.minecraft.world.level.block.TrapDoorBlock.HALF;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;

public class TrapdoorsBlockStateProvider implements DataProvider
{
    private final DataGenerator generator;

    public TrapdoorsBlockStateProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final HashCache cache) throws IOException
    {
        createBlockstateFile(cache, ModBlocks.getTrapdoor());
    }

    private void createBlockstateFile(final HashCache cache, final TrapdoorBlock shingle) throws IOException
    {
        if (shingle.getRegistryName() == null)
            return;

        final Map<String, BlockstateVariantJson> variants = new HashMap<>();

        for (Direction facingValue : HORIZONTAL_FACING.getPossibleValues())
        {
            for (TrapdoorType typeValue : TrapdoorBlock.TYPE.getPossibleValues())
            {
                for (Half halfValue : HALF.getPossibleValues())
                {
                    for(boolean openValue : OPEN.getPossibleValues()) {
                        final String variantKey = "facing=" + facingValue + ",type=" + typeValue.getSerializedName() + ",half=" + halfValue + ",open=" + openValue;

                        int y = getYFromFacing(facingValue);
                        y = y + getYFromOpenAndHalf(openValue, halfValue);

                        int x = getXFromOpenAndHalf(openValue, halfValue);

                        final String modelLocation = Constants.MOD_ID + ":block/trapdoors/trapdoor_" + typeValue.getSerializedName();

                        final BlockstateModelJson model = new BlockstateModelJson(modelLocation, x, y);
                        final BlockstateVariantJson variant = new BlockstateVariantJson(model);

                        variants.put(variantKey, variant);
                    }
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
        return "Trapdoors BlockStates Provider";
    }

    private int getXFromOpenAndHalf(final boolean open, final Half half)
    {
        return half == Half.TOP && open ? 180 : 0;
    }

    private int getYFromOpenAndHalf(final boolean open, final Half half)
    {
        return half == Half.TOP && open ? 180 : 0;
    }

    private int getYFromFacing(final Direction facing)
    {
        return switch (facing)
                 {
                     default -> 0;
                     case SOUTH -> 180;
                     case WEST -> 270;
                     case EAST -> 90;
                 };
    }
}
