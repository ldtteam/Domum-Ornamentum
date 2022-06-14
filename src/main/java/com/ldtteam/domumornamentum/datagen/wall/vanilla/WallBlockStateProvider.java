package com.ldtteam.domumornamentum.datagen.wall.vanilla;

import com.google.common.collect.Lists;
import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.datagenerators.blockstate.multipart.MultipartCaseJson;
import com.ldtteam.datagenerators.blockstate.multipart.MultipartWhenJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.vanilla.WallBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.WallSide;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

import static net.minecraft.world.level.block.WallBlock.UP;

public class WallBlockStateProvider implements DataProvider
{
    private final DataGenerator generator;

    public WallBlockStateProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final CachedOutput cache) throws IOException
    {
        createBlockstateFile(cache, ModBlocks.getInstance().getWall());
    }

    private void createBlockstateFile(final CachedOutput cache, final WallBlock wallBlock) throws IOException
    {
        if (wallBlock.getRegistryName() == null)
            return;

        final List<MultipartCaseJson> cases = Lists.newArrayList();
        cases.add(
          new MultipartCaseJson(
            new BlockstateVariantJson(
              new BlockstateModelJson(
                Constants.MOD_ID + ":block/walls/wall_post"
              )
            ),
            new MultipartWhenJson(
              UP.getName().toLowerCase(),
              "true"
            )
          )
        );

        for (final Direction possibleValue : HorizontalDirectionalBlock.FACING.getPossibleValues())
        {
            for (final WallSide value : WallSide.values())
            {
                if (value == WallSide.NONE)
                    continue;

                cases.add(
                  new MultipartCaseJson(
                    new BlockstateVariantJson(
                      new BlockstateModelJson(
                        Constants.MOD_ID + ":block/walls/wall_side" + (value == WallSide.TALL ? "_tall" : ""),
                        0,
                        getYFromFacing(possibleValue)
                      )
                    ),
                    new MultipartWhenJson(
                      possibleValue.getName().toLowerCase(Locale.ROOT),
                      value.toString()
                    )
                  )
                );
            }


        }

        final BlockstateJson blockstate = new BlockstateJson(cases);

        final Path blockstateFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.BLOCKSTATE_DIR);
        final Path blockstatePath = blockstateFolder.resolve(wallBlock.getRegistryName().getPath() + ".json");

        DataProvider.saveStable(cache, DataGeneratorConstants.serialize(blockstate), blockstatePath);

    }

    @NotNull
    @Override
    public String getName()
    {
        return "Wall BlockStates Provider";
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
