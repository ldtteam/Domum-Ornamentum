package com.ldtteam.domumornamentum.datagen.shingle.slab;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ShingleSlabBlock;
import com.ldtteam.domumornamentum.block.types.ShingleShapeType;
import com.ldtteam.domumornamentum.block.types.ShingleSlabShapeType;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public class ShingleSlabBlockStateProvider extends BlockStateProvider
{

    public ShingleSlabBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        createBlockstateFile(ModBlocks.getInstance().getShingleSlab());
    }


    private void createBlockstateFile(final ShingleSlabBlock shingle) {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(shingle);
        final Map<ShingleSlabShapeType, ModelFile> models = new EnumMap<>(ShingleSlabShapeType.class);
        for (Direction facingValue : StairBlock.FACING.getPossibleValues())
        {
            for (ShingleSlabShapeType shapeValue : ShingleSlabBlock.SHAPE.getPossibleValues())
            {
                builder.part()
                        .modelFile(models.computeIfAbsent(shapeValue, shape -> models().withExistingParent("block/shingle_slab/" + shapeValue.name().toLowerCase(), modLoc("block/shingle_slab/shingle_slab_" + shapeValue.name().toLowerCase() + "_spec"))
                                .customLoader(MateriallyTexturedModelBuilder::new)
                                .end()))
                        .rotationY(getYFromFacing(facingValue))
                        .addModel()
                        .condition(StairBlock.FACING, facingValue)
                        .condition(ShingleSlabBlock.SHAPE, shapeValue)
                        .end();
            }
        }

        final ItemModelBuilder itemModelBuilder = itemModels().getBuilder(shingle.getRegistryName().getPath()).parent(models.get(ShingleSlabShapeType.TOP));
        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilder);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Shingle Slabs BlockStates Provider";
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
