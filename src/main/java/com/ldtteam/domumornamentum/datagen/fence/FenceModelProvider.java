package com.ldtteam.domumornamentum.datagen.fence;

import com.ldtteam.domumornamentum.block.AbstractBlockFence;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.client.model.loader.MateriallyTexturedBuilder;
import com.ldtteam.domumornamentum.datagen.BaseModelProvider;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
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

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.NORTH;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.EAST;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.SOUTH;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WEST;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class FenceModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new FenceModelProvider(event.getGenerator().getPackOutput()));
    }

    public FenceModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        registerStatesAndModelsFor(ModBlocks.getInstance().getFence(), blockModels, itemModels);
    }

    private void registerStatesAndModelsFor(AbstractBlockFence<?> fenceBlock,
                                            BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        // The hand-crafted geometry lives at models/block/fence/{fence_post_spec,fence_side_spec}
        // (in main resources). Datagen no longer writes a thin wrapper file there; the
        // blockstate references it inline as a custom block state model instead.
        final Identifier postSpecLoc = blockModelLoc("fence/fence_post_spec");
        final Identifier sideSpecLoc = blockModelLoc("fence/fence_side_spec");

        // Build multipart blockstate (post always present, sides added conditionally)
        blockModels.blockStateOutput.accept(
                MultiPartGenerator.multiPart(fenceBlock)
                        .with(MateriallyTexturedBuilder.multiVariantWithParent(postSpecLoc))
                        .with(new ConditionBuilder().term(NORTH, true), MateriallyTexturedBuilder.multiVariantWithParent(sideSpecLoc))
                        .with(new ConditionBuilder().term(EAST, true), MateriallyTexturedBuilder.multiVariantWithParent(sideSpecLoc).with(BlockModelGenerators.Y_ROT_90))
                        .with(new ConditionBuilder().term(SOUTH, true), MateriallyTexturedBuilder.multiVariantWithParent(sideSpecLoc).with(BlockModelGenerators.Y_ROT_180))
                        .with(new ConditionBuilder().term(WEST, true), MateriallyTexturedBuilder.multiVariantWithParent(sideSpecLoc).with(BlockModelGenerators.Y_ROT_270))
        );

        // Item model: references the hand-crafted item geometry directly (items have no block entity,
        // so they render with the default textures).
        itemModels.itemModelOutput.accept(
                fenceBlock.asItem(),
                ItemModelUtils.plainModel(itemModelLoc("fence/fence_spec"))
        );
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of(ModBlocks.getInstance().getFence())
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of(ModBlocks.getInstance().getFence().asItem())
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Fence Models Provider";
    }

}
