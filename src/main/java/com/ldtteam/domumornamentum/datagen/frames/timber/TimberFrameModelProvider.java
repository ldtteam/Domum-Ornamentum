package com.ldtteam.domumornamentum.datagen.frames.timber;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.TimberFrameBlock;
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

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class TimberFrameModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new TimberFrameModelProvider(event.getGenerator().getPackOutput()));
    }

    public TimberFrameModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        ModBlocks.getInstance().getTimberFrames().forEach(timberFrameBlock ->
                registerStatesAndModelsFor(timberFrameBlock, blockModels, itemModels)
        );
    }

    private void registerStatesAndModelsFor(TimberFrameBlock timberFrameBlock,
                                            BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        final String blockName = timberFrameBlock.getRegistryName().getPath();

        // The hand-crafted geometry lives at models/block/timber_frame/<name>_spec (in main resources).
        // Datagen no longer writes a thin wrapper file there; the blockstate references it inline as a
        // custom block state model instead.
        final Identifier specLoc = blockModelLoc("timber_frame/" + blockName + "_spec");

        // Build multipart blockstate with conditions for each FACING direction
        final MultiPartGenerator multipart = MultiPartGenerator.multiPart(timberFrameBlock);

        final boolean rotatable = timberFrameBlock.getTimberFrameType().isRotatable();

        TimberFrameBlock.FACING.getPossibleValues().forEach(direction -> {
            MultiVariant dirVariant = MateriallyTexturedBuilder.multiVariantWithParent(specLoc);
            if (rotatable) {
                dirVariant = dirVariant
                        .with(getXRotationMutator(direction))
                        .with(getYRotationMutator(direction));
            }
            multipart.with(
                    new ConditionBuilder().term(FACING, direction),
                    dirVariant
            );
        });

        blockModels.blockStateOutput.accept(multipart);

        // Item model: references the hand-crafted geometry directly (items have no block entity,
        // so they render with the default textures).
        itemModels.itemModelOutput.accept(
                timberFrameBlock.asItem(),
                ItemModelUtils.plainModel(specLoc)
        );
    }

    private VariantMutator getXRotationMutator(Direction direction) {
        return switch (direction) {
            case UP -> BlockModelGenerators.NOP;
            case DOWN -> BlockModelGenerators.X_ROT_180;
            default -> BlockModelGenerators.X_ROT_90;
        };
    }

    private VariantMutator getYRotationMutator(Direction direction) {
        return switch (direction) {
            default -> BlockModelGenerators.NOP;
            case EAST -> BlockModelGenerators.Y_ROT_90;
            case SOUTH -> BlockModelGenerators.Y_ROT_180;
            case WEST -> BlockModelGenerators.Y_ROT_270;
        };
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.getInstance().getTimberFrames().stream()
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return ModBlocks.getInstance().getTimberFrames().stream()
                .map(TimberFrameBlock::asItem)
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Timber Frame Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new TimberFrameModelProvider(event.getGenerator().getPackOutput()));
    }

}
