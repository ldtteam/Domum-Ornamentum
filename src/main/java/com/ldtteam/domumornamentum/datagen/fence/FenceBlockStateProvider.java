package com.ldtteam.domumornamentum.datagen.fence;

import com.ldtteam.domumornamentum.block.AbstractBlockFence;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class FenceBlockStateProvider extends BlockStateProvider
{
    public FenceBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
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

        final ItemModelBuilder itemModelBuilder = ModelBuilderUtils.applyDefaultItemTransforms(itemModels().withExistingParent(ModBlocks.getInstance().getFence().getRegistryName().getPath(), modLoc("item/fence/fence_spec")));
        itemModelBuilder.customLoader(MateriallyTexturedModelBuilder::new);
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
