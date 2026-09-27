package com.ldtteam.domumornamentum.datagen.stair;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.vanilla.StairBlock;
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

import static net.minecraft.world.level.block.StairBlock.FACING;
import static net.minecraft.world.level.block.StairBlock.HALF;
import static net.minecraft.world.level.block.StairBlock.SHAPE;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class StairsModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new StairsModelProvider(event.getGenerator().getPackOutput()));
    }

    public StairsModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        final StairBlock stairBlock = ModBlocks.getInstance().getStair();

        // Hand-crafted geometry lives at models/block/stair/stairs[_inner|_outer]_spec (in main
        // resources). Left/right corner shapes share geometry via per-state rotations. Datagen no
        // longer writes thin wrapper files; the blockstate references the specs inline as custom
        // block state models.
        final Map<StairsShape, Identifier> specLocs = new HashMap<>();
        for (StairsShape shapeValue : StairsShape.values()) {
            specLocs.put(shapeValue, blockModelLoc("stair/" + getTypeFromShape(shapeValue) + "_spec"));
        }

        // Build multipart blockstate with conditions for each combination
        final MultiPartGenerator multipart = MultiPartGenerator.multiPart(stairBlock);

        for (Direction facingValue : FACING.getPossibleValues()) {
            for (StairsShape shapeValue : SHAPE.getPossibleValues()) {
                for (Half halfValue : HALF.getPossibleValues()) {
                    final MultiVariant baseVariant = MateriallyTexturedBuilder.multiVariantWithParent(specLocs.get(shapeValue));

                    final VariantMutator mutator = getRotationMutator(halfValue, facingValue, shapeValue);
                    final MultiVariant variant = mutator == BlockModelGenerators.NOP
                            ? baseVariant
                            : baseVariant.with(mutator);

                    multipart.with(
                            new ConditionBuilder()
                                    .term(FACING, facingValue)
                                    .term(SHAPE, shapeValue)
                                    .term(HALF, halfValue),
                            variant
                    );
                }
            }
        }

        blockModels.blockStateOutput.accept(multipart);

        // Item model: references the straight-stairs custom loader spec (created above)
        itemModels.itemModelOutput.accept(
                stairBlock.asItem(),
                ItemModelUtils.plainModel(specLocs.get(StairsShape.STRAIGHT))
        );
    }

    private VariantMutator getRotationMutator(Half half, Direction facing, StairsShape shape) {
        final int xRot = half == Half.TOP ? 180 : 0;
        // Normalize to [0, 360): Java's % keeps the sign of the dividend (e.g. -90 % 360 == -90),
        // which would slip past the switch cases in getYRotMutator and silently drop rotations.
        final int yRot = ((getYFromFacing(facing) + getYFromShape(shape) + getYFromHalf(half, shape)) % 360 + 360) % 360;

        if (xRot == 0 && yRot == 0) {
            return BlockModelGenerators.NOP;
        }
        if (yRot == 0) {
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
                return 0;
            }
            return 90;
        }
        return 0;
    }

    private int getYFromShape(StairsShape shape) {
        return switch (shape) {
            default -> 0;
            case OUTER_LEFT, INNER_LEFT -> -90;
        };
    }

    private int getYFromFacing(Direction facing) {
        return switch (facing) {
            default -> 90;
            case WEST -> 180;
            case NORTH -> 270;
            case EAST -> 0;
        };
    }

    private static String getTypeFromShape(StairsShape shape) {
        return switch (shape) {
            case INNER_LEFT, INNER_RIGHT -> "stairs_inner";
            case OUTER_LEFT, OUTER_RIGHT -> "stairs_outer";
            default -> "stairs";
        };
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of(ModBlocks.getInstance().getStair())
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of(ModBlocks.getInstance().getStair().asItem())
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Stairs Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new StairsModelProvider(event.getGenerator().getPackOutput()));
    }

}
