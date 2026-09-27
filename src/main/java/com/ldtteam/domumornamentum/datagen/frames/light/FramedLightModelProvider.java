package com.ldtteam.domumornamentum.datagen.frames.light;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FramedLightBlock;
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
public class FramedLightModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new FramedLightModelProvider(event.getGenerator().getPackOutput()));
    }

    public FramedLightModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        ModBlocks.getInstance().getFramedLights().forEach(framedLightBlock ->
                registerStatesAndModelsFor(framedLightBlock, blockModels, itemModels)
        );
    }

    private void registerStatesAndModelsFor(FramedLightBlock framedLightBlock,
                                            BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        final String blockName = framedLightBlock.getRegistryName().getPath();

        // Hand-crafted geometry at models/block/framed_light/<blockName>_spec (in main resources).
        // Datagen no longer writes thin wrapper files; the blockstate references it inline as a
        // custom block state model. No rotations are needed for framed lights.
        final Identifier specLoc = blockModelLoc("framed_light/" + blockName + "_spec");

        // Blockstate: simple variant using the custom builder
        final MultiVariant variant = MateriallyTexturedBuilder.multiVariantWithParent(specLoc);
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(framedLightBlock, variant)
        );

        // Item model: no hand-crafted item geometry exists for framed lights, so reference the block
        // geometry directly (items have no block entity and render with the default textures).
        itemModels.itemModelOutput.accept(
                framedLightBlock.asItem(),
                ItemModelUtils.plainModel(specLoc)
        );
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.getInstance().getFramedLights().stream()
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return ModBlocks.getInstance().getFramedLights().stream()
                .map(FramedLightBlock::asItem)
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Framed Light Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new FramedLightModelProvider(event.getGenerator().getPackOutput()));
    }

}
