package com.ldtteam.domumornamentum.datagen.frames.timber;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.TimberFrameBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TimberFramesBlockStateProvider extends BlockStateProvider {
    public TimberFramesBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.getInstance().getTimberFrames().forEach(this::registerStatesAndModelsFor);
    }

    private void registerStatesAndModelsFor(TimberFrameBlock timberFrameBlock) {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(timberFrameBlock);

        final ModelFile blockModel = models().withExistingParent(
                        "block/timber_frame/" + Objects.requireNonNull(timberFrameBlock.getRegistryName()).getPath(),
                        modLoc("block/timber_frame/" + Objects.requireNonNull(timberFrameBlock.getRegistryName()).getPath() + "_spec").toString()
                )
                .customLoader(MateriallyTexturedModelBuilder::new)
                .end();

        TimberFrameBlock.FACING.getPossibleValues().forEach(direction -> {
            final ConfiguredModel.Builder<MultiPartBlockStateBuilder.PartBuilder> partBuilder = builder.part().modelFile(blockModel);
            if (timberFrameBlock.getTimberFrameType().isRotatable()) {
                partBuilder.rotationX(getXFromDirection(direction));
                partBuilder.rotationY(getYFromDirection(direction));
            }
            partBuilder.addModel().condition(TimberFrameBlock.FACING, direction);
        });

        ModelBuilderUtils.applyDefaultItemTransforms(itemModels().getBuilder(timberFrameBlock.getRegistryName().getPath()).parent(blockModel));
    }


    private int getXFromDirection(final Direction direction) {
        return switch (direction) {
            case UP -> 0;
            case DOWN -> 180;
            default -> 90;
        };
    }

    private int getYFromDirection(final Direction direction) {
        return switch (direction) {
            default -> 0;
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
        };
    }

    @NotNull
    @Override
    public String getName() {
        return "Timber Frames BlockStates Provider";
    }
}
