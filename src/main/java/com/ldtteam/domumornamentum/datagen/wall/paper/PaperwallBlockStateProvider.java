package com.ldtteam.domumornamentum.datagen.wall.paper;

import com.ldtteam.domumornamentum.block.AbstractBlockPane;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.PaperWallBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

public class PaperwallBlockStateProvider extends BlockStateProvider {


    public PaperwallBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        createBlockstateFile(ModBlocks.getInstance().getPaperWall());
    }

    private void createBlockstateFile(final PaperWallBlock paperWallBlock) {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(paperWallBlock);
        builder.part()
                .modelFile(models().withExistingParent("block/paperwalls/blockpaperwall_post", modLoc("block/paperwalls/blockpaperwall_post_spec"))
                        .customLoader(MateriallyTexturedModelBuilder::new)
                        .end())
                .addModel()
                .end();

        for (final Direction possibleValue : HorizontalDirectionalBlock.FACING.getPossibleValues()) {
            builder.part()
                    .modelFile(models().withExistingParent("block/paperwalls/blockpaperwall_side_" + possibleValue.name().toLowerCase(Locale.ROOT), modLoc("block/paperwalls/blockpaperwall_side_" + possibleValue.name().toLowerCase(Locale.ROOT) + "_spec"))
                            .customLoader(MateriallyTexturedModelBuilder::new)
                            .end())
                    .addModel()
                    .condition(Objects.requireNonNull(AbstractBlockPane.PROPERTIES.get(possibleValue)), true)
                    .end()
                    .part()
                    .modelFile(models().withExistingParent("block/paperwalls/blockpaperwall_side_off_" + possibleValue.name().toLowerCase(Locale.ROOT), modLoc("block/paperwalls/blockpaperwall_side_" + possibleValue.name().toLowerCase(Locale.ROOT) + "_spec"))
                            .customLoader(MateriallyTexturedModelBuilder::new)
                            .end())
                    .addModel()
                    .condition(Objects.requireNonNull(AbstractBlockPane.PROPERTIES.get(possibleValue)), false)
                    .end();
        }

        final ItemModelBuilder itemModelBuilder = itemModels().withExistingParent(paperWallBlock.getRegistryName().getPath(), modLoc("item/paperwalls/blockpaperwall_spec"))
                .customLoader(MateriallyTexturedModelBuilder::new)
                .end();

        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilder);
    }

    @NotNull
    @Override
    public String getName() {
        return "Paperwall BlockStates Provider";
    }

    private int getYFromFacing(final Direction facing) {
        switch (facing) {
            default:
                return 0;
            case WEST:
                return 270;
            case SOUTH:
                return 180;
            case EAST:
                return 90;
        }
    }
}
