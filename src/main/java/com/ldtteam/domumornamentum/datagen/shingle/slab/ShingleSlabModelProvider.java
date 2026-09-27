package com.ldtteam.domumornamentum.datagen.shingle.slab;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ShingleSlabBlock;
import com.ldtteam.domumornamentum.block.types.ShingleSlabShapeType;
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

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.ldtteam.domumornamentum.block.decorative.ShingleSlabBlock.SHAPE;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ShingleSlabModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new ShingleSlabModelProvider(event.getGenerator().getPackOutput()));
    }

    public ShingleSlabModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        registerStatesAndModelsFor(ModBlocks.getInstance().getShingleSlab(), blockModels, itemModels);
    }

    private void registerStatesAndModelsFor(ShingleSlabBlock shingle,
                                            BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        // Hand-crafted geometry lives at models/block/shingle_slab/shingle_slab_<shape>_spec (in main
        // resources). Datagen no longer writes thin wrapper files; the blockstate references the specs
        // inline as custom block state models.
        final Map<ShingleSlabShapeType, Identifier> specLocs = new HashMap<>();
        for (ShingleSlabShapeType shapeValue : ShingleSlabShapeType.values()) {
            specLocs.put(shapeValue, blockModelLoc("shingle_slab/shingle_slab_" + shapeValue.name().toLowerCase() + "_spec"));
        }

        // Build multipart blockstate with conditions for each combination
        final MultiPartGenerator multipart = MultiPartGenerator.multiPart(shingle);

        for (Direction facingValue : HORIZONTAL_FACING.getPossibleValues()) {
            for (ShingleSlabShapeType shapeValue : ShingleSlabShapeType.values()) {
                final MultiVariant baseVariant = MateriallyTexturedBuilder.multiVariantWithParent(specLocs.get(shapeValue));

                final VariantMutator mutator = getRotationMutator(facingValue);
                final MultiVariant variant = mutator == BlockModelGenerators.NOP
                        ? baseVariant
                        : baseVariant.with(mutator);

                multipart.with(
                        new ConditionBuilder()
                                .term(HORIZONTAL_FACING, facingValue)
                                .term(SHAPE, shapeValue),
                        variant
                );
            }
        }

        blockModels.blockStateOutput.accept(multipart);

        // Item model: references TOP shape custom loader spec
        itemModels.itemModelOutput.accept(
                shingle.asItem(),
                ItemModelUtils.plainModel(specLocs.get(ShingleSlabShapeType.TOP))
        );
    }

    private VariantMutator getRotationMutator(Direction facing) {
        final int yRot = getYFromFacing(facing);
        return getYRotMutator(yRot);
    }

    private VariantMutator getYRotMutator(int degrees) {
        return switch (degrees % 360) {
            case 0 -> BlockModelGenerators.NOP;
            case 90 -> BlockModelGenerators.Y_ROT_90;
            case 180 -> BlockModelGenerators.Y_ROT_180;
            case 270 -> BlockModelGenerators.Y_ROT_270;
            default -> BlockModelGenerators.NOP;
        };
    }

    private int getYFromFacing(Direction facing) {
        return switch (facing) {
            default -> 0;
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
        };
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of(ModBlocks.getInstance().getShingleSlab())
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of(ModBlocks.getInstance().getShingleSlab().asItem())
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Shingle Slab Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new ShingleSlabModelProvider(event.getGenerator().getPackOutput()));
    }

}
