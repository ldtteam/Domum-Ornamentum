package com.ldtteam.domumornamentum.datagen.door;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.types.DoorType;
import com.ldtteam.domumornamentum.block.vanilla.DoorBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class DoorsBlockStateProvider extends BlockStateProvider
{
    public DoorsBlockStateProvider(final DataGenerator gen, final ExistingFileHelper exFileHelper)
    {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        doorBlock(ModBlocks.getInstance().getDoor(), DoorType::getSerializedName);
    }

    private void doorBlock(DoorBlock block, Function<DoorType, String> baseName)
    {
        Function<DoorType, ModelFile> bottomLeft = createModel(baseName, "bottom_left");
        Function<DoorType, ModelFile> bottomLeftOpen = createModel(baseName, "bottom_left_open");
        Function<DoorType, ModelFile> bottomRight = createModel(baseName, "bottom_right");
        Function<DoorType, ModelFile> bottomRightOpen = createModel(baseName, "bottom_right_open");
        Function<DoorType, ModelFile> topLeft = createModel(baseName, "top_left");
        Function<DoorType, ModelFile> topLeftOpen = createModel(baseName, "top_left_open");
        Function<DoorType, ModelFile> topRight = createModel(baseName, "top_right");
        Function<DoorType, ModelFile> topRightOpen = createModel(baseName, "top_right_open");
        doorBlock(block, bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen);
    }

    private Function<DoorType, ModelFile> createModel(Function<DoorType, String> baseName, String stateDescription)
    {
        return type -> models()
                               .withExistingParent("block/door/door_" + baseName.apply(type) + "_" + stateDescription, Constants.resLocDO("block/door/door_" + baseName.apply(type) + "_" + stateDescription + "_spec"))
                               .customLoader(MateriallyTexturedModelBuilder::new)
                               .end();
    }

    public void doorBlock(DoorBlock block, Function<DoorType, ModelFile> bottomLeft, Function<DoorType, ModelFile> bottomLeftOpen, Function<DoorType, ModelFile> bottomRight, Function<DoorType, ModelFile> bottomRightOpen, Function<DoorType, ModelFile> topLeft, Function<DoorType, ModelFile> topLeftOpen, Function<DoorType, ModelFile> topRight, Function<DoorType, ModelFile> topRightOpen)
    {
        //Generate all block models from the variant
        getVariantBuilder(block).forAllStatesExcept(state -> {
            int yRot = ((int) state.getValue(DoorBlock.FACING).toYRot()) + 90;
            boolean right = state.getValue(DoorBlock.HINGE) == DoorHingeSide.RIGHT;
            boolean open = state.getValue(DoorBlock.OPEN);
            boolean lower = state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER;
            DoorType type = state.getValue(DoorBlock.TYPE);
            if (open)
            {
                yRot += 90;
            }
            if (right && open)
            {
                yRot += 180;
            }
            yRot %= 360;

            ModelFile model = null;
            if (lower && right && open)
            {
                model = bottomRightOpen.apply(type);
            } else if (lower && !right && open)
            {
                model = bottomLeftOpen.apply(type);
            }
            if (lower && right && !open)
            {
                model = bottomRight.apply(type);
            } else if (lower && !right && !open)
            {
                model = bottomLeft.apply(type);
            }
            if (!lower && right && open)
            {
                model = topRightOpen.apply(type);
            } else if (!lower && !right && open)
            {
                model = topLeftOpen.apply(type);
            }
            if (!lower && right && !open)
            {
                model = topRight.apply(type);
            } else if (!lower && !right && !open)
            {
                model = topLeft.apply(type);
            }

            return ConfiguredModel.builder().modelFile(model)
                                  .rotationY(yRot)
                                  .build();
        }, DoorBlock.POWERED);


        //Next up generate a single spec model with a variant aware resolver
        final ItemModelBuilder overarchingSpecBuilder = itemModels().getBuilder(ModBlocks.getInstance().getDoor().getRegistryName().getPath() + "_spec");
        ModelBuilderUtils.applyDoorItemTransforms(overarchingSpecBuilder);
        DoorType[] values = DoorType.values();
        for (int i = 0; i < values.length; i++)
        {
            final DoorType value = values[i];
            overarchingSpecBuilder.override()
                                  .predicate(Constants.DOOR_MODEL_OVERRIDE, i)
                                  .model(models().getExistingFile(Constants.resLocDO("item/door/door_" + value.getSerializedName() + "_spec")))
                                  .end();
        }


        //Now generate the single over arching item model which is loaded by the material manager.
        final ItemModelBuilder rootBuilder = itemModels().getBuilder(ModBlocks.getInstance().getDoor().getRegistryName().getPath());
        ModelBuilderUtils.applyDoorItemTransforms(rootBuilder);
        rootBuilder.customLoader(MateriallyTexturedModelBuilder::new);
        rootBuilder.parent(overarchingSpecBuilder);
    }
    
    @NotNull
    @Override
    public String getName()
    {
        return "Doors BlockStates Provider";
    }
}
