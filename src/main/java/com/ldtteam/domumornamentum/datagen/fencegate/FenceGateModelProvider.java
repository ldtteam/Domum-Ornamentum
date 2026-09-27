package com.ldtteam.domumornamentum.datagen.fencegate;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.vanilla.FenceGateBlock;
import com.ldtteam.domumornamentum.client.model.loader.MateriallyTexturedBuilder;
import com.ldtteam.domumornamentum.datagen.BaseModelProvider;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.core.Direction;
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

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.IN_WALL;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.OPEN;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class FenceGateModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new FenceGateModelProvider(event.getGenerator().getPackOutput()));
    }

    public FenceGateModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        registerStatesAndModelsFor(ModBlocks.getInstance().getFenceGate(), blockModels, itemModels);
    }

    private void registerStatesAndModelsFor(FenceGateBlock fenceGateBlock,
                                            BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        // The hand-crafted geometry lives at models/block/fence_gate/<state>_spec (in main resources).
        // Datagen no longer writes a thin wrapper file there; the blockstate references it inline as
        // a custom block state model instead.
        final Identifier closedSpecLoc = blockModelLoc("fence_gate/fence_gate_spec");
        final Identifier openSpecLoc = blockModelLoc("fence_gate/fence_gate_open_spec");
        final Identifier wallClosedSpecLoc = blockModelLoc("fence_gate/fence_gate_wall_spec");
        final Identifier wallOpenSpecLoc = blockModelLoc("fence_gate/fence_gate_wall_open_spec");

        // Build multipart blockstate with one part per facing × in_wall × open combination
        final MultiPartGenerator multipart = MultiPartGenerator.multiPart(fenceGateBlock);

        for (Direction facing : HORIZONTAL_FACING.getPossibleValues()) {
            for (boolean inWall : new boolean[] {false, true}) {
                for (boolean open : new boolean[] {false, true}) {
                    final Identifier specLoc = inWall ? (open ? wallOpenSpecLoc : wallClosedSpecLoc)
                                                   : (open ? openSpecLoc : closedSpecLoc);

                    MultiVariant variant = MateriallyTexturedBuilder.multiVariantWithParent(specLoc);
                    final VariantMutator rotation = getYRotationMutator(facing);
                    if (rotation != BlockModelGenerators.NOP) {
                        variant = variant.with(rotation);
                    }

                    multipart.with(
                            new ConditionBuilder()
                                    .term(HORIZONTAL_FACING, facing)
                                    .term(IN_WALL, inWall)
                                    .term(OPEN, open),
                            variant
                    );
                }
            }
        }

        blockModels.blockStateOutput.accept(multipart);

        // Item model: references the hand-crafted item geometry directly (items have no block entity,
        // so they render with the default textures).
        itemModels.itemModelOutput.accept(
                fenceGateBlock.asItem(),
                ItemModelUtils.plainModel(itemModelLoc("fence_gate/fence_gate_spec"))
        );
    }

    private VariantMutator getYRotationMutator(Direction facing) {
        return switch (facing) {
            default -> BlockModelGenerators.NOP;
            case EAST -> BlockModelGenerators.Y_ROT_90;
            case SOUTH -> BlockModelGenerators.Y_ROT_180;
            case WEST -> BlockModelGenerators.Y_ROT_270;
        };
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of(ModBlocks.getInstance().getFenceGate())
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of(ModBlocks.getInstance().getFenceGate().asItem())
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "FenceGate Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new FenceGateModelProvider(event.getGenerator().getPackOutput()));
    }

}
