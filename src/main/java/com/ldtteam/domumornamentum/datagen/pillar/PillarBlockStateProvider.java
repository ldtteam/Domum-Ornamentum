package com.ldtteam.domumornamentum.datagen.pillar;

import com.google.common.collect.Lists;
import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.datagenerators.blockstate.multipart.MultipartCaseJson;
import com.ldtteam.datagenerators.blockstate.multipart.MultipartWhenJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.PillarBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

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
        for (final PillarBlock pillar : ModBlocks.getInstance().getPillars())
        {
            createBlockStateFile(cache, pillar);
        }
    }

    private void createBlockStateFile(final CachedOutput cache, final PillarBlock pillar) throws IOException
    {
        if (pillar.getRegistryName() == null)
            return;

        final List<MultipartCaseJson> cases = Lists.newArrayList();
        cases.add(
                new MultipartCaseJson(
                        new BlockstateVariantJson(
                                new BlockstateModelJson(
                                        Constants.MOD_ID + ":block/pillars/"+ pillar.getRegistryName().getPath() + "/pillar_column"
                                )
                        ), new MultipartWhenJson("column","pillar_column")
                )
        );
        cases.add(
                new MultipartCaseJson(
                    new BlockstateVariantJson(
                            new BlockstateModelJson(
                                Constants.MOD_ID + ":block/pillars/"+ pillar.getRegistryName().getPath() + "/pillar_base"
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
                                        Constants.MOD_ID + ":block/pillars/"+ pillar.getRegistryName().getPath() + "/pillar_capital"
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
                                        Constants.MOD_ID +":block/pillars/"+ pillar.getRegistryName().getPath() + "/full_pillar"
                                )
                        ), new MultipartWhenJson("column","full_pillar")
                )
        );

        final BlockstateJson blockstate = new BlockstateJson(cases);

        final Path blockStateFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.BLOCKSTATE_DIR);
        final Path blockStatePath = blockStateFolder.resolve(pillar.getRegistryName().getPath() + ".json");

        DataProvider.saveStable(cache, DataGeneratorConstants.serialize(blockstate), blockStatePath);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Shingles BlockStates Provider";
    }
}
