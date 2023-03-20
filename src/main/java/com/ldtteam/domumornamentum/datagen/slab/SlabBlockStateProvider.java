package com.ldtteam.domumornamentum.datagen.slab;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.vanilla.SlabBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.SlabBlock.TYPE;

public class SlabBlockStateProvider extends BlockStateProvider
{

    public SlabBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        createBlockstateFile(ModBlocks.getInstance().getSlab());
    }

    private void createBlockstateFile(final SlabBlock slabBlock)
    {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(slabBlock);
        for (SlabType value : SlabType.values()) {
            builder.part()
                    .modelFile(models().withExistingParent("block/slab/" + value.getSerializedName(), modLoc("block/slab/slab_" + value.getSerializedName() + "_spec"))
                            .customLoader(MateriallyTexturedModelBuilder::new)
                            .end())
                    .addModel()
                    .condition(TYPE, value)
                    .end();
        }

        final ItemModelBuilder itemModelBuilder = itemModels().withExistingParent(slabBlock.getRegistryName().getPath(), modLoc("item/slab/slab_spec"))
                .customLoader(MateriallyTexturedModelBuilder::new)
                .end();
        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilder);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Slab BlockStates Provider";
    }
}
