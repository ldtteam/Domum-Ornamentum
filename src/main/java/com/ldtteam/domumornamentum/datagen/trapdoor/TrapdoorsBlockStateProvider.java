package com.ldtteam.domumornamentum.datagen.trapdoor;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.block.vanilla.TrapdoorBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.properties.Half;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.TrapDoorBlock.HALF;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.OPEN;

public class TrapdoorsBlockStateProvider extends BlockStateProvider
{
    public TrapdoorsBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        createBlockstateFile(ModBlocks.getInstance().getTrapdoor());
    }

    private void createBlockstateFile(final TrapdoorBlock trapdoor)
    {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(trapdoor);
        for (Direction facingValue : HORIZONTAL_FACING.getPossibleValues())
        {
            for (TrapdoorType typeValue : TrapdoorBlock.TYPE.getPossibleValues())
            {
                for (Half halfValue : HALF.getPossibleValues())
                {
                    for (boolean openValue : OPEN.getPossibleValues())
                    {
                        builder.part()
                                .modelFile(models().withExistingParent("block/trapdoor/trapdoor_" + typeValue.getSerializedName(), modLoc("block/trapdoor/trapdoor_" + typeValue.getSerializedName() + "_spec"))
                                        .customLoader(MateriallyTexturedModelBuilder::new)
                                        .end())
                                .uvLock(true)
                                .rotationY(getYFromFacing(facingValue) + getYFromOpenAndHalf(openValue, halfValue))
                                .rotationX(getXFromOpenAndHalf(openValue, halfValue))
                                .addModel()
                                .condition(HORIZONTAL_FACING, facingValue)
                                .condition(TrapdoorBlock.TYPE, typeValue)
                                .condition(HALF, halfValue)
                                .condition(OPEN, openValue)
                                .end();
                    }
                }
            }
        }

        final ItemModelBuilder itemModelBuilderSpec = itemModels().withExistingParent(ModBlocks.getInstance().getTrapdoor().getRegistryName().getPath() + "_spec", mcLoc("block/thin_block"));
        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilderSpec);

        for (int i = 0; i < TrapdoorType.values().length; i++) {
            final TrapdoorType type = TrapdoorType.values()[i];
            itemModelBuilderSpec.override()
                    .model(itemModels().getExistingFile(modLoc("block/trapdoor/trapdoor_" + type.getSerializedName() + "_spec")))
                    .predicate(Constants.TRAPDOOR_MODEL_OVERRIDE, i)
                    .end();
        }

        final ItemModelBuilder itemModelBuilder = itemModels().getBuilder(ModBlocks.getInstance().getTrapdoor().getRegistryName().getPath())
                .parent(itemModelBuilderSpec)
                .customLoader(MateriallyTexturedModelBuilder::new)
                .end();
        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilder);
    }

    private int getYFromFacing(final Direction facing)
    {
        return switch (facing)
                 {
                     default -> 0;
                     case SOUTH -> 180;
                     case WEST -> 270;
                     case EAST -> 90;
                 };
    }

    private int getYFromOpenAndHalf(final boolean open, final Half half) {
        if (!open) {
            return half == Half.TOP ? 0 : 180;
        }

        return half == Half.TOP ? 180 : 0;
    }

    private int getXFromOpenAndHalf(final boolean open, final Half half) {
        if (!open) {
            return half == Half.TOP ? 180 : 0;
        }

        return half == Half.TOP ? -90 : 90;
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Trapdoors BlockStates Provider";
    }
}
