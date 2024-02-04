package com.ldtteam.domumornamentum.datagen.wall.vanilla;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.vanilla.WallBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static net.minecraft.world.level.block.WallBlock.UP;

public class WallBlockStateProvider extends BlockStateProvider {

    public WallBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        createBlockstateFile(ModBlocks.getInstance().getWall());
    }

    private void createBlockstateFile(final WallBlock wallBlock) {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(wallBlock);
        builder.part()
                .modelFile(models().withExistingParent("block/wall/wall_post", modLoc("block/wall/wall_post_spec"))
                        .customLoader(MateriallyTexturedModelBuilder::new)
                        .end())
                .addModel()
                .condition(UP, true)
                .end();

        for (final Direction possibleValue : HorizontalDirectionalBlock.FACING.getPossibleValues()) {
            for (final WallSide value : WallSide.values()) {
                if (value == WallSide.NONE)
                    continue;

                builder.part()
                        .modelFile(models().withExistingParent("block/wall/wall_side" + (value == WallSide.TALL ? "_tall" : ""), modLoc("block/wall/wall_side" + (value == WallSide.TALL ? "_tall" : "") + "_spec"))
                                .customLoader(MateriallyTexturedModelBuilder::new)
                                .end())
                        .rotationY(getYFromFacing(possibleValue))
                        .addModel()
                        .condition(Objects.requireNonNull(WallBlock.PROPERTIES.get(possibleValue)), value)
                        .end();
            }
        }

        final ItemModelBuilder itemModelBuilder = itemModels()
                .withExistingParent(wallBlock.getRegistryName().getPath(), modLoc("item/wall/wall_spec"))
                .customLoader(MateriallyTexturedModelBuilder::new)
                .end();

        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilder);
    }

    @NotNull
    @Override
    public String getName() {
        return "Wall BlockStates Provider";
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
