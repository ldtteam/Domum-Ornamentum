package com.ldtteam.domumornamentum.datagen.fence;

import com.google.common.collect.Lists;
import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.datagenerators.blockstate.multipart.MultipartCaseJson;
import com.ldtteam.datagenerators.blockstate.multipart.MultipartWhenJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.vanilla.FenceBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public class FenceBlockStateProvider implements DataProvider
{
    private final DataGenerator generator;

    public FenceBlockStateProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final CachedOutput cache) throws IOException
    {
        createBlockstateFile(cache, ModBlocks.getInstance().getFence());
    }

    private void createBlockstateFile(final CachedOutput cache, final FenceBlock fenceBlock) throws IOException
    {
        if (fenceBlock.getRegistryName() == null)
            return;

        final List<MultipartCaseJson> cases = Lists.newArrayList();
        cases.add(
          new MultipartCaseJson(
            new BlockstateVariantJson(
              new BlockstateModelJson(
                Constants.MOD_ID + ":block/fences/fence_post"
              )
            ),
            null
          )
        );

        for (final Direction possibleValue : HorizontalDirectionalBlock.FACING.getPossibleValues())
        {
            cases.add(
              new MultipartCaseJson(
                new BlockstateVariantJson(
                  new BlockstateModelJson(
                    Constants.MOD_ID + ":block/fences/fence_side",
                    0,
                    getYFromFacing(possibleValue)
                  )
                ),
                new MultipartWhenJson(
                  possibleValue.getName().toLowerCase(Locale.ROOT),
                  "true"
                )
              )
            );
        }

        final BlockstateJson blockstate = new BlockstateJson(cases);

        final Path blockstateFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.BLOCKSTATE_DIR);
        final Path blockstatePath = blockstateFolder.resolve(fenceBlock.getRegistryName().getPath() + ".json");

        DataProvider.saveStable(cache, DataGeneratorConstants.serialize(blockstate), blockstatePath);

    }

    @NotNull
    @Override
    public String getName()
    {
        return "Fence BlockStates Provider";
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
