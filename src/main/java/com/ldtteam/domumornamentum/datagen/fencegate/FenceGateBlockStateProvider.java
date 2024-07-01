package com.ldtteam.domumornamentum.datagen.fencegate;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.vanilla.FenceGateBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class FenceGateBlockStateProvider extends BlockStateProvider {
    public FenceGateBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(ModBlocks.getInstance().getFenceGate());

        record FenceGateModelData(boolean wallState, boolean open) { }

        Map<FenceGateModelData, ModelFile> models = new HashMap<>();
        for (final boolean wallState : FenceGateBlock.IN_WALL.getPossibleValues()) {
            for (final boolean open : FenceGateBlock.OPEN.getPossibleValues()) {
                models.put(
                        new FenceGateModelData(wallState, open),
                        generateBlockModel(wallState, open)
                );
            }
        }

        for (final Direction direction : HorizontalDirectionalBlock.FACING.getPossibleValues()) {
            for (final boolean wallState : FenceGateBlock.IN_WALL.getPossibleValues()) {
                for (final boolean open : FenceGateBlock.OPEN.getPossibleValues()) {
                    final ModelFile blockModel = models.get(new FenceGateModelData(wallState, open));

                    builder.part()
                            .modelFile(blockModel)
                            .rotationY(getYFromFacing(direction))
                            .addModel()
                            .condition(HorizontalDirectionalBlock.FACING, direction)
                            .condition(FenceGateBlock.IN_WALL, wallState)
                            .condition(FenceGateBlock.OPEN, open);
                }
            }
        }

        final ItemModelBuilder itemModelBuilder = itemModels()
                                                    .withExistingParent(ModBlocks.getInstance().getFenceGate().getRegistryName().getPath(), modLoc("item/fence_gate/fence_gate_spec"))
                                                    .customLoader(MateriallyTexturedModelBuilder::new)
                                                    .end();

        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilder);
    }

    private ModelFile generateBlockModel(boolean wallState, boolean open) {
        final String name = "block/fence_gate/fence_gate_"
                + (wallState ? "wall_" : "")
                + (open ? "open" : "");

        final ResourceLocation specLocation = Constants.resLocDO("block/fence_gate/fence_gate_"
                + (wallState ? "wall_" : "")
                + (open ? "open_" : "") + "spec");

        return models().withExistingParent(name, specLocation)
                .customLoader(MateriallyTexturedModelBuilder::new)
                .end();
    }

    @NotNull
    @Override
    public String getName() {
        return "FenceGate BlockStates Provider";
    }

    private int getYFromFacing(final Direction facing) {
        return switch (facing) {
            default -> 0;
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
        };
    }
}
