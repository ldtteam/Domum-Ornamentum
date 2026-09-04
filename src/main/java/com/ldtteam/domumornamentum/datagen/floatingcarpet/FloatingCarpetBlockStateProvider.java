package com.ldtteam.domumornamentum.datagen.floatingcarpet;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FloatingCarpetBlock;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.Identifier;
import com.ldtteam.domumornamentum.datagen.model.BlockStateProvider;
import com.ldtteam.domumornamentum.datagen.model.ModelFile;
import com.ldtteam.domumornamentum.datagen.DatagenContext;
import org.jetbrains.annotations.NotNull;

public class FloatingCarpetBlockStateProvider extends BlockStateProvider
{
    public FloatingCarpetBlockStateProvider(DataGenerator gen, DatagenContext exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.getInstance().getFloatingCarpets().forEach(this::registerStatesAndModelsFor);
    }

    private void registerStatesAndModelsFor(FloatingCarpetBlock floatingCarpetBlock) {
        final ModelFile minecraftCarpetModel = models().getExistingFile(Identifier.withDefaultNamespace("block/" + floatingCarpetBlock.getColor().getName() + "_carpet"));
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
