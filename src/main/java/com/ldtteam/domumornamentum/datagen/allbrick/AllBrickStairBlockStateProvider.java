package com.ldtteam.domumornamentum.datagen.allbrick;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.AllBrickStairBlock;
import com.ldtteam.domumornamentum.block.decorative.ShingleBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public class AllBrickStairBlockStateProvider extends BlockStateProvider
{

    public AllBrickStairBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.getInstance().getAllBrickStairBlocks().forEach(this::registerStatesAndModelsFor);
    }

    private void registerStatesAndModelsFor(final AllBrickStairBlock allBrickStairBlock)
    {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(allBrickStairBlock);

        if (allBrickStairBlock.getRegistryName() == null)
            return;

        final Map<StairsShape, ModelFile> blockModels = new EnumMap<>(StairsShape.class);
        for (Direction facingValue : net.minecraft.world.level.block.StairBlock.FACING.getPossibleValues())
        {
            for (StairsShape shapeValue : ShingleBlock.SHAPE.getPossibleValues())
            {
                for (Half halfValue : net.minecraft.world.level.block.StairBlock.HALF.getPossibleValues())
                {
                    final String shapeType = getTypeFromShape(shapeValue);
                    builder.part()
                      .modelFile(blockModels.computeIfAbsent(shapeValue, shape -> models().withExistingParent("block/allbrick/" + allBrickStairBlock.getRegistryName().getPath() + shapeValue.name().toLowerCase(), modLoc("block/allbrick/" + allBrickStairBlock.getRegistryName().getPath() + shapeType + "_spec"))
                                                                                    .customLoader(MateriallyTexturedModelBuilder::new)
                                                                                    .end()))
                      .rotationX(halfValue == Half.TOP ? 180 : 0)
                      .rotationY(getYFromFacing(facingValue) + getYFromShape(shapeValue) + getYFromHalf(halfValue, shapeValue))
                      .addModel()
                      .condition(net.minecraft.world.level.block.StairBlock.FACING, facingValue)
                      .condition(net.minecraft.world.level.block.StairBlock.SHAPE, shapeValue)
                      .condition(StairBlock.HALF, halfValue)
                      .end();
                }
            }
        }

        final ItemModelBuilder itemModelBuilder = itemModels().getBuilder(allBrickStairBlock.getRegistryName().getPath()).parent(blockModels.get(StairsShape.STRAIGHT));
        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilder);
    }
    @NotNull
    @Override
    public String getName()
    {
        return "All Brick Stair BlockStates Provider";
    }

    private int getYFromHalf(final Half half, final StairsShape shape)
    {
        if (half == Half.TOP)
        {
            if (shape == StairsShape.STRAIGHT)
            {
                return 180;
            }
            return 270;
        }
        else
        {
            return 180;
        }
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
                     default -> 180;
                     case SOUTH -> 270;
                     case WEST -> 0;
                     case NORTH -> 90;
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
                     case INNER_LEFT, INNER_RIGHT -> "_inner";
                     case OUTER_LEFT, OUTER_RIGHT -> "_outer";
                     default -> "";
                 };
    }
}
