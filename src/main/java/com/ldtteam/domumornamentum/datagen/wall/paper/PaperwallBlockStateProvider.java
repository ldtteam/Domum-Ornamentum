package com.ldtteam.domumornamentum.datagen.wall.paper;

import com.google.common.collect.Lists;
import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.datagenerators.blockstate.multipart.MultipartCaseJson;
import com.ldtteam.datagenerators.blockstate.multipart.MultipartWhenJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.PaperWallBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.DataProvider;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class PaperwallBlockStateProvider implements DataProvider
{
    private final DataGenerator generator;

    public PaperwallBlockStateProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final HashCache cache) throws IOException
    {
        createBlockstateFile(cache, ModBlocks.getPaperWall());
    }

    private void createBlockstateFile(final HashCache cache, final PaperWallBlock paperWallBlock) throws IOException
    {
        if (paperWallBlock.getRegistryName() == null)
            return;

        final List<MultipartCaseJson> cases = Lists.newArrayList();
        cases.add(
          new MultipartCaseJson(
            new BlockstateVariantJson(
              new BlockstateModelJson(
                Constants.MOD_ID + ":block/paperwalls/blockpaperwall_post"
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
                    Constants.MOD_ID + ":block/paperwalls/blockpaperwall_" + possibleValue.name().toLowerCase(Locale.ROOT) + "_side"
                  )
                ),
                new MultipartWhenJson(
                  possibleValue.getName().toLowerCase(Locale.ROOT),
                  "true"
                )
              )
            );

            cases.add(
              new MultipartCaseJson(
                new BlockstateVariantJson(
                  new BlockstateModelJson(
                    Constants.MOD_ID + ":block/paperwalls/blockpaperwall_off_" + possibleValue.name().toLowerCase(Locale.ROOT) + "_side"
                  )
                ),
                new MultipartWhenJson(
                  possibleValue.getName().toLowerCase(Locale.ROOT),
                  "false"
                )
              )
            );
        }

        final BlockstateJson blockstate = new BlockstateJson(cases);

        final Path blockstateFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.BLOCKSTATE_DIR);
        final Path blockstatePath = blockstateFolder.resolve(paperWallBlock.getRegistryName().getPath() + ".json");

        DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(blockstate), blockstatePath);

    }

    @NotNull
    @Override
    public String getName()
    {
        return "Paperwall BlockStates Provider";
    }

    private int getYFromFacing(final Direction facing)
    {
        switch (facing)
        {
            default:
                return 0;
            case WEST:
                return 270;
            case SOUTH:
                return 180;
            case EAST:
                return 90;
        }
    }
}
