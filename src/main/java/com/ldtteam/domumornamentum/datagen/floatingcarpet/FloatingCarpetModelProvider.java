package com.ldtteam.domumornamentum.datagen.floatingcarpet;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FloatingCarpetBlock;
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
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.NonNull;

import java.util.stream.Stream;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class FloatingCarpetModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new FloatingCarpetModelProvider(event.getGenerator().getPackOutput()));
    }

    public FloatingCarpetModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        ModBlocks.getInstance().getFloatingCarpets().forEach(floatingCarpetBlock ->
                registerStatesAndModelsFor(floatingCarpetBlock, blockModels, itemModels)
        );
    }

    private void registerStatesAndModelsFor(FloatingCarpetBlock floatingCarpetBlock,
                                            BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        final DyeColor color = floatingCarpetBlock.getColor();
        final Identifier vanillaCarpetModel = Identifier.withDefaultNamespace("block/" + color.getName() + "_carpet");

        // Blockstate: simple variant using vanilla carpet model
        final MultiVariant variant = plainVariant(vanillaCarpetModel);
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(floatingCarpetBlock, variant)
        );

        // Item model: references vanilla carpet model
        itemModels.itemModelOutput.accept(
                floatingCarpetBlock.asItem(),
                ItemModelUtils.plainModel(vanillaCarpetModel)
        );
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.getInstance().getFloatingCarpets().stream()
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return ModBlocks.getInstance().getFloatingCarpets().stream()
                .map(FloatingCarpetBlock::asItem)
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Floating Carpet Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new FloatingCarpetModelProvider(event.getGenerator().getPackOutput()));
    }
}
