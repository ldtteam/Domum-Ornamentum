package com.ldtteam.domumornamentum.datagen.frames.dynamic;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.DynamicTimberFrameBlock;
import com.ldtteam.domumornamentum.block.decorative.TimberFrameBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DynamicTimberFramesBlockStateProvider extends BlockStateProvider {
    public DynamicTimberFramesBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
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
