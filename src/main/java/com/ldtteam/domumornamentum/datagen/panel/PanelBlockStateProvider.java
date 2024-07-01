package com.ldtteam.domumornamentum.datagen.panel;

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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static com.ldtteam.domumornamentum.block.AbstractPanelBlockTrapdoor.HALF;
import static com.ldtteam.domumornamentum.block.AbstractPanelBlockTrapdoor.OPEN;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class PanelBlockStateProvider extends BlockStateProvider {

    public PanelBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(ModBlocks.getInstance().getPanel());
        for (Direction facingValue : HORIZONTAL_FACING.getPossibleValues()) {
            for (TrapdoorType typeValue : TrapdoorBlock.TYPE.getPossibleValues()) {
                for (Half halfValue : HALF.getPossibleValues()) {
                    for (boolean openValue : OPEN.getPossibleValues()) {
                        final var partBuilder = builder.part();

                        partBuilder.modelFile(models()
                                        .withExistingParent("block/panel/panel_" + typeValue.getSerializedName(), modLoc("block/panel/panel_%s_spec".formatted(typeValue.getSerializedName())))
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

        final ItemModelBuilder itemSpecModelBuilder = itemModels()
                .withExistingParent("panel_spec", mcLoc("block/thin_block"));
        TrapdoorType[] values = TrapdoorType.values();
        for (int i = 0; i < values.length; i++) {
            TrapdoorType value = values[i];
            final ItemModelBuilder.OverrideBuilder overrideBuilder = itemSpecModelBuilder.override();
            overrideBuilder.predicate(Constants.TRAPDOOR_MODEL_OVERRIDE, i);
            overrideBuilder.model(itemModels().getExistingFile(modLoc("block/panel/panel_%s_spec".formatted(value.getSerializedName()))));
            overrideBuilder.end();
        }

        final ItemModelBuilder itemModelBuilder = itemModels()
                .getBuilder(ModBlocks.getInstance().getPanel().getRegistryName().getPath()).parent(itemSpecModelBuilder)
                .customLoader(MateriallyTexturedModelBuilder::new)
                .end();
        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilder);
    }

    @Contract(pure = true)
    private int getYFromFacing(final Direction facing) {
        return switch (facing) {
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
    public String getName() {
        return "Panel BlockStates Provider";
    }
}
