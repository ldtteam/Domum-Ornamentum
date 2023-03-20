package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class ExtraBlockStateProvider extends BlockStateProvider
{

    public ExtraBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.getInstance().getExtraTopBlocks().forEach(this::registerStatesAndModelsFor);
    }

    private void registerStatesAndModelsFor(ExtraBlock extraBlock) {
        final ModelFile cubeAll = models().cubeAll("block/extra/" + extraBlock.getType().getCategory().name().toLowerCase() + "/" + extraBlock.getRegistryName().getPath(), modLoc("block/extra/" + extraBlock.getRegistryName().getPath()));
        simpleBlockWithItem(extraBlock, cubeAll);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Extra BlockStates Provider";
    }
}
