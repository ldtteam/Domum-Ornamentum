package com.ldtteam.domumornamentum.datagen.floatingcarpet;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FloatingCarpetBlock;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class FloatingCarpetBlockStateProvider extends BlockStateProvider
{
    public FloatingCarpetBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.getInstance().getFloatingCarpets().forEach(this::registerStatesAndModelsFor);
    }

    private void registerStatesAndModelsFor(FloatingCarpetBlock floatingCarpetBlock) {
        final ModelFile minecraftCarpetModel = models().getExistingFile(new ResourceLocation("block/" + floatingCarpetBlock.getColor().getName() + "_carpet"));
        simpleBlock(floatingCarpetBlock, minecraftCarpetModel);
        simpleBlockItem(floatingCarpetBlock, minecraftCarpetModel);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Floating Carpet BlockStates Provider";
    }
}
