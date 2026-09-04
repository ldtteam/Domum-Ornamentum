package com.ldtteam.domumornamentum.datagen.allbrick;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.AllBrickBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import com.ldtteam.domumornamentum.datagen.model.BlockStateProvider;
import com.ldtteam.domumornamentum.datagen.model.ModelFile;
import com.ldtteam.domumornamentum.datagen.DatagenContext;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AllBrickBlockStateProvider extends BlockStateProvider
{

    public AllBrickBlockStateProvider(DataGenerator gen, DatagenContext exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
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

        ModelBuilderUtils.applyDefaultItemTransforms(itemModels().getBuilder(allBrickBlock.getRegistryName().getPath())
                .parent(blockModel));
    }

    @NotNull
    @Override
    public String getName()
    {
        return "All Brick BlockStates Provider";
    }
}
