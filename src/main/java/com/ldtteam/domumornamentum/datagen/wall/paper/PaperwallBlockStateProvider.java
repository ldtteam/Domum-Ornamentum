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
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
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
                .modelFile(models().withExistingParent("block/paperwall/blockpaperwall_post", modLoc("block/paperwall/blockpaperwall_post_spec"))
                        .customLoader(MateriallyTexturedModelBuilder::new)
                        .end())
                .addModel()
                .end();

        for (final Direction possibleValue : HorizontalDirectionalBlock.FACING.getPossibleValues()) {
            builder.part()
                    .modelFile(models().withExistingParent("block/paperwall/blockpaperwall_side_" + possibleValue.name().toLowerCase(Locale.ROOT), modLoc("block/paperwall/blockpaperwall_side_" + possibleValue.name().toLowerCase(Locale.ROOT) + "_spec"))
                            .customLoader(MateriallyTexturedModelBuilder::new)
                            .end())
                    .addModel()
                    .condition(Objects.requireNonNull(AbstractBlockPane.PROPERTIES.get(possibleValue)), true)
                    .end()
                    .part()
                    .modelFile(models().withExistingParent("block/paperwall/blockpaperwall_side_off_" + possibleValue.name().toLowerCase(Locale.ROOT), modLoc("block/paperwall/blockpaperwall_side_off_" + possibleValue.name().toLowerCase(Locale.ROOT) + "_spec"))
                            .customLoader(MateriallyTexturedModelBuilder::new)
                            .end())
                    .addModel()
                    .condition(Objects.requireNonNull(AbstractBlockPane.PROPERTIES.get(possibleValue)), false)
                    .end();
        }

        final ItemModelBuilder itemModelBuilder = itemModels().withExistingParent(paperWallBlock.getRegistryName().getPath(), modLoc("item/paperwall/blockpaperwall_spec"))
                .customLoader(MateriallyTexturedModelBuilder::new)
                .end();

        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilder);
    }

    @NotNull
    @Override
    public String getName() {
        return "Paperwall BlockStates Provider";
    }
}
