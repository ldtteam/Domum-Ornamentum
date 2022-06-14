package com.ldtteam.domumornamentum.datagen.panel;

import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.PanelBlock;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.block.vanilla.TrapdoorBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.core.Direction;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.world.level.block.state.properties.Half;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static com.ldtteam.domumornamentum.block.AbstractPanelBlockTrapdoor.OPEN;
import static com.ldtteam.domumornamentum.block.AbstractPanelBlockTrapdoor.HALF;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class PanelBlockStateProvider implements DataProvider
{
    private final DataGenerator generator;

    public PanelBlockStateProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final CachedOutput cache) throws IOException
    {
        createBlockstateFile(cache, ModBlocks.getInstance().getPanel());
    }

    private void createBlockstateFile(final CachedOutput cache, final PanelBlock shingle) throws IOException
    {
        if (shingle.getRegistryName() == null)
        {
            return;
        }

        final Map<String, BlockstateVariantJson> variants = new HashMap<>();

        for (Direction facingValue : HORIZONTAL_FACING.getPossibleValues())
        {
            for (TrapdoorType typeValue : TrapdoorBlock.TYPE.getPossibleValues())
            {
                for (Half halfValue : HALF.getPossibleValues())
                {
                    for (boolean openValue : OPEN.getPossibleValues())
                    {
                        final String variantKey = "facing=" + facingValue + ",type=" + typeValue.getSerializedName() + ",half=" + halfValue + ",open=" + openValue;

                        int y = getYFromFacing(facingValue);
                        y = y + getYFromOpenAndHalf(openValue, halfValue);

                        int x = getXFromOpenAndHalf(openValue, halfValue);

                        final String modelLocation = Constants.MOD_ID + ":block/panels/panel_" + typeValue.getSerializedName();

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

        DataProvider.saveStable(cache, DataGeneratorConstants.serialize(blockstate), blockstatePath);
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

    private int getYFromOpenAndHalf(final boolean open, final Half half)
    {
        return half == Half.TOP && open ? 180 : 0;
    }

    private int getXFromOpenAndHalf(final boolean open, final Half half)
    {
        if (!open)
        {
            return half == Half.TOP ? 180 : 0;
        }

        return half == Half.TOP ? -90 : 90;
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Panel BlockStates Provider";
    }
}
