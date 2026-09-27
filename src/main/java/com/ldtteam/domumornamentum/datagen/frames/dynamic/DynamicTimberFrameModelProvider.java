package com.ldtteam.domumornamentum.datagen.frames.dynamic;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.DynamicTimberFrameBlock;
import com.ldtteam.domumornamentum.client.model.loader.MateriallyTexturedBuilder;
import com.ldtteam.domumornamentum.datagen.BaseModelProvider;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.NonNull;

import java.util.stream.Stream;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class DynamicTimberFrameModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new DynamicTimberFrameModelProvider(event.getGenerator().getPackOutput()));
    }

    public DynamicTimberFrameModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        registerStatesAndModelsFor(ModBlocks.getInstance().getDynamicTimberFrame(), blockModels, itemModels);
    }

    private void registerStatesAndModelsFor(DynamicTimberFrameBlock timberFrameBlock,
                                            BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        // The hand-crafted geometry lives at models/block/timber_frame/dynamic_timberframe_spec (in main resources).
        // Datagen no longer writes a thin wrapper file there; the blockstate references it inline as
        // a custom block state model instead. All FACING values share the same unrotated geometry, so a
        // single variant entry covers every state.
        final Identifier specLoc = blockModelLoc("timber_frame/dynamic_timberframe_spec");

        // Blockstate: simple variant using the custom builder
        final MultiVariant variant = MateriallyTexturedBuilder.multiVariantWithParent(specLoc);
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(timberFrameBlock, variant)
        );

        // Item model: references the hand-crafted item geometry directly (items have no block entity,
        // so they render with the default textures).
        itemModels.itemModelOutput.accept(
                timberFrameBlock.asItem(),
                ItemModelUtils.plainModel(blockModelLoc("timber_frame/dynamic_timberframe_item_spec"))
        );
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of(ModBlocks.getInstance().getDynamicTimberFrame())
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of(ModBlocks.getInstance().getDynamicTimberFrame().asItem())
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Dynamic Timber Frame Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new DynamicTimberFrameModelProvider(event.getGenerator().getPackOutput()));
    }

}
