package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.BrickBlock;
import com.ldtteam.domumornamentum.block.types.BrickType;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BrickBlockStateProvider extends BlockStateProvider
{
    private final DataGenerator generator;

    public BrickBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, Constants.MOD_ID, existingFileHelper);
        this.generator = generator;
    }

    @Override
    protected void registerStatesAndModels()
    {
        ModBlocks.getInstance().getBricks().forEach(this::registerStatesAndModelsFor);
    }

    private void registerStatesAndModelsFor(final BrickBlock brickBlock) {
        final ModelFile blockModel = models().cubeAll("block/brick/" + brickBlock.getType().getSerializedName() + "_brick", new ResourceLocation(Constants.MOD_ID, "block/brick/" + brickBlock.getType().getSerializedName()));
        simpleBlock(brickBlock, blockModel);
        simpleBlockItem(brickBlock, blockModel);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Brick BlockStates Provider";
    }
}
