package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.BrickBlock;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class BrickBlockStateProvider extends BlockStateProvider
{
    public BrickBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator.getPackOutput(), Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        ModBlocks.getInstance().getBricks().forEach(this::registerStatesAndModelsFor);
    }

    private void registerStatesAndModelsFor(final BrickBlock brickBlock) {
        final ModelFile blockModel = models().cubeAll("block/brick/" + brickBlock.getType().getSerializedName() + "_brick", Constants.resLocDO("block/brick/" + brickBlock.getType().getSerializedName()));
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
