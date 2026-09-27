package com.ldtteam.domumornamentum.datagen.shingle.normal;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ShingleBlock;
import com.ldtteam.domumornamentum.block.types.ShingleShapeType;
import com.ldtteam.domumornamentum.client.model.loader.MateriallyTexturedBuilder;
import com.ldtteam.domumornamentum.datagen.BaseModelProvider;
import com.ldtteam.domumornamentum.shingles.ShingleHeightType;
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
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.ldtteam.domumornamentum.block.DOStairBlock.SHAPE;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HALF;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ShinglesModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new ShinglesModelProvider(event.getGenerator().getPackOutput()));
    }

    public ShinglesModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        for (final ShingleHeightType heightType : ShingleHeightType.values()) {
            registerStatesAndModelsFor(ModBlocks.getInstance().getShingle(heightType), heightType, blockModels, itemModels);
        }
    }

    private void registerStatesAndModelsFor(ShingleBlock shingle, ShingleHeightType heightType,
                                            BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        if (shingle.getRegistryName() == null) {
            return;
        }

        // Hand-crafted geometry lives at models/block/shingle/<heightId><shapeType>_spec (in main
        // resources), named by ShingleShapeType rather than StairsShape: inner_left/inner_right share
        // the concave spec and outer_left/outer_right share the convex spec. Datagen no longer writes
        // thin wrapper files; the blockstate references the specs inline as custom block state models.
        final Map<StairsShape, Identifier> specLocs = new HashMap<>();
        for (StairsShape shapeValue : StairsShape.values()) {
            final ShingleShapeType shingleShapeType = ShingleBlock.getTypeFromShape(shapeValue);
            final String modelPath = "shingle/" + heightType.getId() + shingleShapeType.name().toLowerCase() + "_spec";
            specLocs.put(shapeValue, blockModelLoc(modelPath));
        }

        // Build multipart blockstate with conditions for each combination
        final MultiPartGenerator multipart = MultiPartGenerator.multiPart(shingle);

        for (Direction facingValue : HORIZONTAL_FACING.getPossibleValues()) {
            for (StairsShape shapeValue : StairsShape.values()) {
                for (Half halfValue : HALF.getPossibleValues()) {
                    final MultiVariant baseVariant = MateriallyTexturedBuilder.multiVariantWithParent(specLocs.get(shapeValue));

                    final VariantMutator mutator = getRotationMutator(halfValue, facingValue, shapeValue);
                    final MultiVariant variant = mutator == BlockModelGenerators.NOP
                            ? baseVariant
                            : baseVariant.with(mutator);

                    multipart.with(
                            new ConditionBuilder()
                                    .term(HORIZONTAL_FACING, facingValue)
                                    .term(SHAPE, shapeValue)
                                    .term(HALF, halfValue),
                            variant
                    );
                }
            }
        }

        blockModels.blockStateOutput.accept(multipart);

        // Item model: references STRAIGHT shape custom loader spec
        itemModels.itemModelOutput.accept(
                shingle.asItem(),
                ItemModelUtils.plainModel(specLocs.get(StairsShape.STRAIGHT))
        );
    }

    private VariantMutator getRotationMutator(Half half, Direction facing, StairsShape shape) {
        final int xRot = half == Half.TOP ? 180 : 0;
        final int yRot = getYFromFacing(facing) + getYFromShape(shape) + getYFromHalf(half, shape);

        if (xRot == 0 && yRot % 360 == 0) {
            return BlockModelGenerators.NOP;
        }
        if (yRot % 360 == 0) {
            return getXRotMutator(xRot);
        }
        if (xRot == 0) {
            return getYRotMutator(yRot);
        }
        return getXRotMutator(xRot).then(getYRotMutator(yRot));
    }

    private VariantMutator getXRotMutator(int degrees) {
        return switch (degrees) {
            case 0 -> BlockModelGenerators.NOP;
            case 90 -> BlockModelGenerators.X_ROT_90;
            case 180 -> BlockModelGenerators.X_ROT_180;
            case -90 -> BlockModelGenerators.X_ROT_270;
            default -> BlockModelGenerators.NOP;
        };
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

    private int getYFromHalf(Half half, StairsShape shape) {
        if (half == Half.TOP) {
            if (shape == StairsShape.STRAIGHT) {
                return 180;
            }
            return 270;
        } else {
            return 180;
        }
    }

    private int getYFromShape(StairsShape shape) {
        return switch (shape) {
            default -> 0;
            case OUTER_LEFT, INNER_LEFT -> -90;
        };
    }

    private int getYFromFacing(Direction facing) {
        return switch (facing) {
            default -> 180;
            case SOUTH -> 270;
            case WEST -> 0;
            case NORTH -> 90;
        };
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of(
                ModBlocks.getInstance().getShingle(ShingleHeightType.DEFAULT),
                ModBlocks.getInstance().getShingle(ShingleHeightType.FLAT),
                ModBlocks.getInstance().getShingle(ShingleHeightType.FLAT_LOWER)
        ).map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of(
                ModBlocks.getInstance().getShingle(ShingleHeightType.DEFAULT).asItem(),
                ModBlocks.getInstance().getShingle(ShingleHeightType.FLAT).asItem(),
                ModBlocks.getInstance().getShingle(ShingleHeightType.FLAT_LOWER).asItem()
        ).map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Shingles Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new ShinglesModelProvider(event.getGenerator().getPackOutput()));
    }

}
