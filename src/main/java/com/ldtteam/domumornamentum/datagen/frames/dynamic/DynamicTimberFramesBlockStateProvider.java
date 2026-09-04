package com.ldtteam.domumornamentum.datagen.frames.dynamic;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.DynamicTimberFrameBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import com.ldtteam.domumornamentum.datagen.model.BlockStateProvider;
import com.ldtteam.domumornamentum.datagen.model.ModelFile;
import com.ldtteam.domumornamentum.datagen.DatagenContext;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DynamicTimberFramesBlockStateProvider extends BlockStateProvider
{
    public DynamicTimberFramesBlockStateProvider(DataGenerator gen, DatagenContext exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.registerStatesAndModelsFor(ModBlocks.getInstance().getDynamicTimberFrame());
    }

    private void registerStatesAndModelsFor(DynamicTimberFrameBlock timberFrameBlock) {
        final ModelFile blockModel = models().withExistingParent(
                        "block/timber_frame/" + Objects.requireNonNull(timberFrameBlock.getRegistryName()).getPath(),
                        modLoc("block/timber_frame/" + Objects.requireNonNull(timberFrameBlock.getRegistryName()).getPath() + "_spec").toString()
                )
                .customLoader(MateriallyTexturedModelBuilder::new)
                .end();

        simpleBlock(timberFrameBlock, blockModel);


        ModelBuilderUtils.applyDefaultItemTransforms(itemModels().getBuilder(timberFrameBlock.getRegistryName().getPath()).parent(blockModel));
    }

    @NotNull
    @Override
    public String getName() {
        return "Dynamic Timber Frames BlockStates Provider";
    }
}
