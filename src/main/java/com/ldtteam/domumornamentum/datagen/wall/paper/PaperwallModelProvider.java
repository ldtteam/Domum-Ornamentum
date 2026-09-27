package com.ldtteam.domumornamentum.datagen.wall.paper;

import com.ldtteam.domumornamentum.block.AbstractBlockPane;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.PaperWallBlock;
import com.ldtteam.domumornamentum.client.model.loader.MateriallyTexturedBuilder;
import com.ldtteam.domumornamentum.datagen.BaseModelProvider;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.NonNull;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class PaperwallModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new PaperwallModelProvider(event.getGenerator().getPackOutput()));
    }

    public PaperwallModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        createBlockstateFile(blockModels, itemModels, ModBlocks.getInstance().getPaperWall(), "");
        createBlockstateFile(blockModels, itemModels, ModBlocks.getInstance().getTiledPaperWall(), "tiled");
    }

    private void createBlockstateFile(final BlockModelGenerators blockModels, final ItemModelGenerators itemModels,
                                      final PaperWallBlock paperWallBlock, final String type) {
        // Hand-crafted geometry lives at models/block/<tiled>paperwall/blockpaperwall_*_spec (in main
        // resources). Datagen no longer writes thin wrapper files; the blockstate references the
        // specs inline as custom block state models. No rotations, no UV lock (old ground truth).
        final Identifier postSpecLoc = blockModelLoc(type + "paperwall/blockpaperwall_post_spec");

        final MultiPartGenerator multipart = MultiPartGenerator.multiPart(paperWallBlock);

        // Post always present
        multipart.with(
                MateriallyTexturedBuilder.multiVariantWithParent(postSpecLoc)
        );

        // For each direction: side model when neighbor=true, side_off model when neighbor=false
        final Map<Direction, BooleanProperty> properties = AbstractBlockPane.PROPERTIES;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            final String dirName = direction.getName().toLowerCase(Locale.ROOT);
            final Identifier sideSpecLoc = blockModelLoc(type + "paperwall/blockpaperwall_side_" + dirName + "_spec");
            final Identifier sideOffSpecLoc = blockModelLoc(type + "paperwall/blockpaperwall_side_off_" + dirName + "_spec");

            final BooleanProperty prop = properties.get(direction);

            multipart.with(
                    new ConditionBuilder().term(prop, true),
                    MateriallyTexturedBuilder.multiVariantWithParent(sideSpecLoc)
            );
            multipart.with(
                    new ConditionBuilder().term(prop, false),
                    MateriallyTexturedBuilder.multiVariantWithParent(sideOffSpecLoc)
            );
        }

        blockModels.blockStateOutput.accept(multipart);

        // Item model: hand-crafted geometry at models/item/paperwall/block<tiled>paperwall_spec
        itemModels.itemModelOutput.accept(
                paperWallBlock.asItem(),
                ItemModelUtils.plainModel(itemModelLoc("paperwall/block" + type + "paperwall_spec"))
        );
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of(ModBlocks.getInstance().getPaperWall(), ModBlocks.getInstance().getTiledPaperWall())
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of(ModBlocks.getInstance().getPaperWall().asItem(), ModBlocks.getInstance().getTiledPaperWall().asItem())
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Paperwall Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new PaperwallModelProvider(event.getGenerator().getPackOutput()));
    }

}
