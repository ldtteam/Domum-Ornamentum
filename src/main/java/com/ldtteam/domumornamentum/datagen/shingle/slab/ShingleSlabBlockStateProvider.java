package com.ldtteam.domumornamentum.datagen.shingle.slab;

import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ShingleBlock;
import com.ldtteam.domumornamentum.block.decorative.ShingleSlabBlock;
import com.ldtteam.domumornamentum.block.types.ShingleSlabShapeType;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ShingleSlabBlockStateProvider implements IDataProvider
{
    private final DataGenerator generator;

    public ShingleSlabBlockStateProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final DirectoryCache cache) throws IOException
    {
        createBlockstateFile(cache, ModBlocks.getShingleSlabBlock());
    }

    private void createBlockstateFile(final DirectoryCache cache, final ShingleSlabBlock shingleSlab) throws IOException
    {
        if (shingleSlab.getRegistryName() == null)
            return;

        final Map<String, BlockstateVariantJson> variants = new HashMap<>();

        for (Direction facingValue : HorizontalBlock.FACING.getPossibleValues())
        {
            for (ShingleSlabShapeType shapeValue : ShingleSlabShapeType.values())
            {
                final String variantKey = "facing=" + facingValue.getName() + ",shape=" + shapeValue.getSerializedName();

                int y = getYFromFacing(facingValue);

                final String modelLocation = Constants.MOD_ID + ":block/shingle_slab/" + shapeValue.name().toLowerCase(Locale.ROOT);

                final BlockstateModelJson model = new BlockstateModelJson(modelLocation, 0, y);
                final BlockstateVariantJson variant = new BlockstateVariantJson(model);

                variants.put(variantKey, variant);
            }
        }

        final BlockstateJson blockstate = new BlockstateJson(variants);

        final Path blockstateFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.BLOCKSTATE_DIR);
        final Path blockstatePath = blockstateFolder.resolve(shingleSlab.getRegistryName().getPath() + ".json");

        IDataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(blockstate), blockstatePath);

    }

    @NotNull
    @Override
    public String getName()
    {
        return "Shingle Slabs BlockStates Provider";
    }

    private int getYFromFacing(final Direction facing)
    {
        switch (facing)
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
}
