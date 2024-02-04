package com.ldtteam.domumornamentum.datagen.shingle.normal;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ShingleBlock;
import com.ldtteam.domumornamentum.block.types.ShingleShapeType;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.shingles.ShingleHeightType;
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

public class ShinglesBlockStateProvider extends BlockStateProvider
{
    public ShinglesBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        createBlockstateFile(ModBlocks.getInstance().getShingle(ShingleHeightType.DEFAULT), ShingleHeightType.DEFAULT);
        createBlockstateFile(ModBlocks.getInstance().getShingle(ShingleHeightType.FLAT), ShingleHeightType.FLAT);
        createBlockstateFile(ModBlocks.getInstance().getShingle(ShingleHeightType.FLAT_LOWER), ShingleHeightType.FLAT_LOWER);
    }

    private void createBlockstateFile(final ShingleBlock shingle, final ShingleHeightType heightType)
    {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(shingle);

        if (shingle.getRegistryName() == null)
            return;

        final Map<StairsShape, ModelFile> blockModels = new EnumMap<>(StairsShape.class);
        for (Direction facingValue : StairBlock.FACING.getPossibleValues())
        {
            for (StairsShape shapeValue : ShingleBlock.SHAPE.getPossibleValues())
            {
                for (Half halfValue : StairBlock.HALF.getPossibleValues())
                {
                    final ShingleShapeType shingleShapeType = ShingleBlock.getTypeFromShape(shapeValue);
                    builder.part()
                            .modelFile(blockModels.computeIfAbsent(shapeValue, shape -> models().withExistingParent("block/shingle/" + heightType.getId() + shapeValue.name().toLowerCase(), modLoc("block/shingle/" + heightType.getId() + shingleShapeType.name().toLowerCase() + "_spec"))
                                    .customLoader(MateriallyTexturedModelBuilder::new)
                                    .end()))
                            .rotationX(halfValue == Half.TOP ? 180 : 0)
                            .rotationY(getYFromFacing(facingValue) + getYFromShape(shapeValue) + getYFromHalf(halfValue, shapeValue))
                            .addModel()
                            .condition(StairBlock.FACING, facingValue)
                            .condition(StairBlock.SHAPE, shapeValue)
                            .condition(StairBlock.HALF, halfValue)
                            .end();
                }
            }
        }

        final ItemModelBuilder itemModelBuilder = itemModels().getBuilder(shingle.getRegistryName().getPath()).parent(blockModels.get(StairsShape.STRAIGHT));
        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilder);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Shingles BlockStates Provider";
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
}
