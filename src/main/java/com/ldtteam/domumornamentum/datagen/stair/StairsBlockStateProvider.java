package com.ldtteam.domumornamentum.datagen.stair;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.vanilla.StairBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.StairBlock.FACING;
import static net.minecraft.world.level.block.StairBlock.HALF;
import static net.minecraft.world.level.block.StairBlock.SHAPE;

public class StairsBlockStateProvider extends BlockStateProvider
{

    public StairsBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        createBlockstateFile(ModBlocks.getInstance().getStair());
    }

    private void createBlockstateFile(final StairBlock stairBlock) {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(stairBlock);
        for (Direction facingValue : FACING.getPossibleValues())
        {
            for (StairsShape shapeValue : SHAPE.getPossibleValues())
            {
                for (Half halfValue : HALF.getPossibleValues())
                {
                    builder.part()
                            .modelFile(models().withExistingParent("block/stair/" + getTypeFromShape(shapeValue), modLoc("block/stair/" + getTypeFromShape(shapeValue) + "_spec"))
                                    .customLoader(MateriallyTexturedModelBuilder::new)
                                    .end())
                            .uvLock(true)
                            .rotationY(getYFromFacing(facingValue) + getYFromShape(shapeValue) + getYFromHalf(halfValue, shapeValue))
                            .rotationX(halfValue == Half.TOP ? 180 : 0)
                            .addModel()
                            .condition(FACING, facingValue)
                            .condition(SHAPE, shapeValue)
                            .condition(HALF, halfValue)
                            .end();
                }
            }
        }

        final ItemModelBuilder itemModelBuilder = itemModels().withExistingParent(stairBlock.getRegistryName().getPath(), modLoc("block/stair/stairs"))
                .customLoader(MateriallyTexturedModelBuilder::new)
                .end();
        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilder);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Stairs BlockStates Provider";
    }

    private int getYFromHalf(final Half half, final StairsShape shape)
    {
        if (half == Half.TOP)
        {
            if (shape == StairsShape.STRAIGHT)
            {
                return 0;
            }
            return 90;
        }
        return 0;
    }

    private int getYFromShape(final StairsShape shape)
    {
        return switch (shape)
                 {
                     default -> 0;
                     case OUTER_LEFT, INNER_LEFT -> -90;
                 };
    }

    private int getYFromFacing(final Direction facing)
    {
        return switch (facing)
                 {
                     default -> 90;
                     case WEST -> 180;
                     case NORTH -> 270;
                     case EAST -> 0;
                 };
    }


    /**
     * Get the model type from a StairsShape object
     *
     * @param shape the StairsShape object
     * @return the model type for provided StairsShape
     */
    private static String getTypeFromShape(final StairsShape shape)
    {
        return switch (shape)
                 {
                     case INNER_LEFT, INNER_RIGHT -> "stairs_inner";
                     case OUTER_LEFT, OUTER_RIGHT -> "stairs_outer";
                     default -> "stairs";
                 };
    }
}
