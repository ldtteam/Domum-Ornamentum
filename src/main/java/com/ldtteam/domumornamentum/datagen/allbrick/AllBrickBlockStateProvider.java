package com.ldtteam.domumornamentum.datagen.allbrick;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.AllBrickBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AllBrickBlockStateProvider extends BlockStateProvider
{

    public AllBrickBlockStateProvider(final DataGenerator gen, final ExistingFileHelper exFileHelper)
    {
        super(gen, Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.getInstance().getAllBrickBlocks().forEach(this::registerStatesAndModelsFor);
    }

    private void registerStatesAndModelsFor(AllBrickBlock allBrickBlock) {
        final ModelFile blockModel = models().withExistingParent(
                        "block/allbrick/" + allBrickBlock.getRegistryName().getPath(),
                        modLoc("block/allbrick/" + Objects.requireNonNull(allBrickBlock.getRegistryName()).getPath() + "_spec"))
                .customLoader(MateriallyTexturedModelBuilder::new)
                .end();
        simpleBlock(allBrickBlock, blockModel);
        simpleBlockItem(allBrickBlock, blockModel);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "All Brick BlockStates Provider";
    }
}
