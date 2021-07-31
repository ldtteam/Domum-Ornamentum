package com.ldtteam.domumornamentum.datagen.slab;

import com.google.common.collect.Lists;
import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.datagenerators.blockstate.multipart.MultipartCaseJson;
import com.ldtteam.datagenerators.blockstate.multipart.MultipartWhenJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.vanilla.FenceGateBlock;
import com.ldtteam.domumornamentum.block.vanilla.SlabBlock;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static net.minecraft.world.level.block.SlabBlock.TYPE;

public class SlabBlockStateProvider implements DataProvider
{
    private final DataGenerator generator;

    public SlabBlockStateProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final HashCache cache) throws IOException
    {
        createBlockstateFile(cache, ModBlocks.getSlab());
    }

    private void createBlockstateFile(final HashCache cache, final SlabBlock slabBlock) throws IOException
    {
        if (slabBlock.getRegistryName() == null)
            return;

        final Map<String, BlockstateVariantJson> variants = new HashMap<>();
        variants.put(
          TYPE.getName() + "=" + SlabType.BOTTOM.toString().toLowerCase(),
          new BlockstateVariantJson(
            new BlockstateModelJson(
              Constants.MOD_ID + ":block/slabs/slab_lower"
            )
          )
        );
        variants.put(
          TYPE.getName() + "=" + SlabType.DOUBLE.toString().toLowerCase(),
          new BlockstateVariantJson(
            new BlockstateModelJson(
              Constants.MOD_ID + ":block/slabs/slab_full"
            )
          )
        );
        variants.put(
          TYPE.getName() + "=" + SlabType.TOP.toString().toLowerCase(),
          new BlockstateVariantJson(
            new BlockstateModelJson(
              Constants.MOD_ID + ":block/slabs/slab_upper"
            )
          )
        );

        final BlockstateJson blockstate = new BlockstateJson(variants);
        final Path blockstateFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.BLOCKSTATE_DIR);
        final Path blockstatePath = blockstateFolder.resolve(slabBlock.getRegistryName().getPath() + ".json");

        DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(blockstate), blockstatePath);

    }

    @NotNull
    @Override
    public String getName()
    {
        return "Slab BlockStates Provider";
    }
}
