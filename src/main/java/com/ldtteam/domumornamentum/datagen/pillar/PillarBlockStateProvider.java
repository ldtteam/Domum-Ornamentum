package com.ldtteam.domumornamentum.datagen.pillar;

import com.google.common.collect.Lists;
import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.datagenerators.blockstate.multipart.MultipartCaseJson;
import com.ldtteam.datagenerators.blockstate.multipart.MultipartWhenJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.PillarBlock;
import com.ldtteam.domumornamentum.block.decorative.ShingleBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HALF;

public class PillarBlockStateProvider implements DataProvider
{
    private final DataGenerator generator;

    public PillarBlockStateProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final CachedOutput cache) throws IOException
    {
        createBlockstateFile(cache, ModBlocks.getInstance().getPillar());
    }

    private void createBlockstateFile(final CachedOutput cache, final PillarBlock pillar) throws IOException
    {
        if (pillar.getRegistryName() == null)
            return;

        final List<MultipartCaseJson> cases = Lists.newArrayList();
        cases.add(
                new MultipartCaseJson(
                        new BlockstateVariantJson(
                                new BlockstateModelJson(
                                        Constants.MOD_ID + ":block/pillars/pillar_column"
                                )
                        ), new MultipartWhenJson("column","pillar_column")
                )
        );
        cases.add(
                new MultipartCaseJson(
                    new BlockstateVariantJson(
                            new BlockstateModelJson(
                                Constants.MOD_ID + ":block/pillars/pillar_base"
                            )
                    ),
                    new MultipartWhenJson("column", "pillar_base"
                    )
                )
        );
        cases.add(
                new MultipartCaseJson(
                        new BlockstateVariantJson(
                                new BlockstateModelJson(
                                        Constants.MOD_ID + ":block/pillars/pillar_capital"
                                )
                        ),
                        new MultipartWhenJson("column","pillar_capital"
                        )
                )
        );
        cases.add(
                new MultipartCaseJson(
                        new BlockstateVariantJson(
                                new BlockstateModelJson(
                                        Constants.MOD_ID+":block/pillars/full_pillar"
                                )
                        ), new MultipartWhenJson("column","full_pillar")
                )
        );

        final BlockstateJson blockstate = new BlockstateJson(cases);

        final Path blockstateFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.BLOCKSTATE_DIR);
        final Path blockstatePath = blockstateFolder.resolve(pillar.getRegistryName().getPath() + ".json");

        DataProvider.saveStable(cache, DataGeneratorConstants.serialize(blockstate), blockstatePath);

    }


    @NotNull
    @Override
    public String getName()
    {
        return "Shingles BlockStates Provider";
    }
}
