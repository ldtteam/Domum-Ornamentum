package com.ldtteam.domumornamentum.datagen.fencegate;

import com.google.common.collect.Lists;
import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.datagenerators.blockstate.multipart.MultipartCaseJson;
import com.ldtteam.datagenerators.blockstate.multipart.MultipartWhenJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.vanilla.FenceGateBlock;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FenceGateBlockStateProvider implements DataProvider
{
    private final DataGenerator generator;

    public FenceGateBlockStateProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final CachedOutput cache) throws IOException
    {
        createBlockstateFile(cache, ModBlocks.getInstance().getFenceGate());
    }

    private void createBlockstateFile(final CachedOutput cache, final FenceGateBlock fenceGateBlock) throws IOException
    {
        if (fenceGateBlock.getRegistryName() == null)
            return;

        final Map<String, BlockstateVariantJson> variants = new HashMap<>();
        for (final Direction direction : HorizontalDirectionalBlock.FACING.getPossibleValues()) {
            for(final boolean wallState : FenceGateBlock.IN_WALL.getPossibleValues()) {
                for(final boolean open : FenceGateBlock.OPEN.getPossibleValues()) {
                    final String key = HorizontalDirectionalBlock.FACING.getName() + "=" + direction.toString() + "," +
                                         FenceGateBlock.IN_WALL.getName() + "=" + wallState + "," +
                                         FenceGateBlock.OPEN.getName() + "=" + open;

                    final int yRot = getYFromFacing(direction);
                    String model = Constants.MOD_ID + ":block/fence_gates/fence_gate_"
                                           + (wallState ? "wall_" : "")
                                           + (open ? "open_" : "");
                    model = model.substring(0, model.length() - 1);

                    variants.put(
                      key,
                      new BlockstateVariantJson(
                        new BlockstateModelJson(
                          model, 0, yRot
                        )
                      )
                    );
                }
            }
        }

        final BlockstateJson blockstate = new BlockstateJson(variants);

        final Path blockstateFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.BLOCKSTATE_DIR);
        final Path blockstatePath = blockstateFolder.resolve(fenceGateBlock.getRegistryName().getPath() + ".json");

        DataProvider.saveStable(cache, DataGeneratorConstants.serialize(blockstate), blockstatePath);

    }

    @NotNull
    @Override
    public String getName()
    {
        return "FenceGate BlockStates Provider";
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
