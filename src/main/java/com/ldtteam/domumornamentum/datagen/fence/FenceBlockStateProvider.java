package com.ldtteam.domumornamentum.datagen.fence;

import com.ldtteam.domumornamentum.block.AbstractBlockFence;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import com.ldtteam.domumornamentum.datagen.model.BlockStateProvider;
import com.ldtteam.domumornamentum.datagen.model.ItemModelBuilder;
import com.ldtteam.domumornamentum.datagen.model.MultiPartBlockStateBuilder;
import com.ldtteam.domumornamentum.datagen.DatagenContext;
import org.jetbrains.annotations.NotNull;

public class FenceBlockStateProvider extends BlockStateProvider
{
    public FenceBlockStateProvider(DataGenerator gen, DatagenContext exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(ModBlocks.getInstance().getFence())
                .part()
                .modelFile(models().withExistingParent("block/fence/fence_post", modLoc("block/fence/fence_post_spec"))
                        .customLoader(MateriallyTexturedModelBuilder::new)
                        .end())
                .addModel()
                .end();

        for (Direction possibleValue : HorizontalDirectionalBlock.FACING.getPossibleValues()) {
            builder.part()
                    .modelFile(models().withExistingParent("block/fence/fence_side", modLoc("block/fence/fence_side_spec"))
                            .customLoader(MateriallyTexturedModelBuilder::new)
                            .end())
                    .rotationY(getYFromFacing(possibleValue))
                    .addModel()
                    .condition(AbstractBlockFence.getDirectionalProperties().get(possibleValue), true)
                    .end();
        }

        final ItemModelBuilder itemModelBuilder = itemModels()
                                                    .withExistingParent(ModBlocks.getInstance().getFence().getRegistryName().getPath(), modLoc("item/fence/fence_spec"))
                                                    .customLoader(MateriallyTexturedModelBuilder::new)
                                                    .end();

        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilder);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Fence BlockStates Provider";
    }

    private int getYFromFacing(final Direction facing)
    {
        return switch (facing) {
            default -> 0;
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
        };
    }
}
