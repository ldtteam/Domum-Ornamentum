package com.ldtteam.domumornamentum.datagen.door;

import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.types.DoorType;
import com.ldtteam.domumornamentum.block.vanilla.DoorBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static net.minecraft.world.level.block.DoorBlock.HALF;
import static net.minecraft.world.level.block.DoorBlock.HINGE;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.OPEN;

public class DoorsBlockStateProvider implements DataProvider
{
    private final DataGenerator generator;

    public DoorsBlockStateProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final HashCache cache) throws IOException
    {
        createBlockstateFile(cache, ModBlocks.getDoor());
    }

    private void createBlockstateFile(final HashCache cache, final DoorBlock shingle) throws IOException
    {
        if (shingle.getRegistryName() == null)
        {
            return;
        }

        final Map<String, BlockstateVariantJson> variants = new HashMap<>();

        for (Direction facingValue : HORIZONTAL_FACING.getPossibleValues())
        {
            for (DoorType typeValue : DoorBlock.TYPE.getPossibleValues())
            {
                for (DoubleBlockHalf halfValue : HALF.getPossibleValues())
                {
                    for (boolean openValue : OPEN.getPossibleValues())
                    {
                        for (final DoorHingeSide hingeValue : HINGE.getPossibleValues())
                        {
                            final String variantKey = "facing=" + facingValue + ",type=" + typeValue.getSerializedName() + ",half=" + halfValue + ",open=" + openValue + ",hinge=" + hingeValue;

                            int y = getYFromFacing(facingValue);
                            y = y + getYFromOpenAndHinge(openValue, hingeValue);
                            final String modelLocation = Constants.MOD_ID + ":block/doors/door_" + (halfValue == DoubleBlockHalf.UPPER ? "top_" : "") + typeValue.getSerializedName();

                            final BlockstateModelJson model = new BlockstateModelJson(modelLocation, 0, y);
                            final BlockstateVariantJson variant = new BlockstateVariantJson(model);

                            variants.put(variantKey, variant);
                        }
                    }
                }
            }
        }

        final BlockstateJson blockstate = new BlockstateJson(variants);

        final Path blockstateFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.BLOCKSTATE_DIR);
        final Path blockstatePath = blockstateFolder.resolve(shingle.getRegistryName().getPath() + ".json");

        DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(blockstate), blockstatePath);
    }

    private int getYFromFacing(final Direction facing)
    {
        return switch (facing)
                 {
                     default -> 0;
                     case WEST -> 180;
                     case NORTH -> 270;
                     case SOUTH -> 90;
                 };
    }

    private int getYFromOpenAndHinge(final boolean open, final DoorHingeSide half)
    {
        if (!open)
            return 0;

        return 90 + (half == DoorHingeSide.RIGHT ? 180 : 0);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Doors BlockStates Provider";
    }
}
