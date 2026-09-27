package com.ldtteam.domumornamentum.datagen.allbrick;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.AllBrickStairBlock;
import com.ldtteam.domumornamentum.client.model.loader.MateriallyTexturedBuilder;
import com.ldtteam.domumornamentum.datagen.BaseModelProvider;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.resources.model.sprite.Material;

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
public class AllBrickStairModelProvider extends BaseModelProvider {

    /** Base texture all retexturable stairs start from (see {@link AllBrickStairBlock}'s SimpleRetexturableComponent). */
    private static final Identifier BASE_TEXTURE = Identifier.withDefaultNamespace("block/oak_planks");

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new AllBrickStairModelProvider(event.getGenerator().getPackOutput()));
    }

    public AllBrickStairModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        ModBlocks.getInstance().getAllBrickStairBlocks().forEach(allBrickStairBlock -> {
            registerStatesAndModelsFor(allBrickStairBlock, blockModels, itemModels);
        });
    }

    private void registerStatesAndModelsFor(final AllBrickStairBlock allBrickStairBlock,
                                            BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        final String blockName = allBrickStairBlock.getRegistryName().getPath();

        // Generate the three stair geometries (straight/inner/outer) at block/allbrick/<name>_*;
        // runtime retexturing is applied by the materially_textured custom loader.
        final Material material = new Material(BASE_TEXTURE);
        final TextureMapping textures = new TextureMapping()
                .put(TextureSlot.BOTTOM, material)
                .put(TextureSlot.TOP, material)
                .put(TextureSlot.SIDE, material);

        final Identifier straightLoc = blockModelLoc("allbrick/" + blockName + "_stairs");
        ModelTemplates.STAIRS_STRAIGHT.create(straightLoc, textures, blockModels.modelOutput);
        final Identifier innerLoc = blockModelLoc("allbrick/" + blockName + "_inner_stairs");
        ModelTemplates.STAIRS_INNER.create(innerLoc, textures, blockModels.modelOutput);
        final Identifier outerLoc = blockModelLoc("allbrick/" + blockName + "_outer_stairs");
        ModelTemplates.STAIRS_OUTER.create(outerLoc, textures, blockModels.modelOutput);

        final Map<StairsShape, Identifier> specLocs = new HashMap<>();
        for (final StairsShape shape : SHAPE.getPossibleValues()) {
            specLocs.put(shape, switch (shape) {
                case INNER_LEFT, INNER_RIGHT -> innerLoc;
                case OUTER_LEFT, OUTER_RIGHT -> outerLoc;
                default -> straightLoc;
            });
        }

        final MultiPartGenerator multipart = MultiPartGenerator.multiPart(allBrickStairBlock);
        for (final Direction facing : FACING.getPossibleValues()) {
            for (final StairsShape shape : SHAPE.getPossibleValues()) {
                for (final Half half : HALF.getPossibleValues()) {
                    final VariantMutator mutator = getRotationMutator(half, facing, shape);
                    multipart.with(
                            new ConditionBuilder()
                                    .term(FACING, facing)
                                    .term(SHAPE, shape)
                                    .term(HALF, half),
                            mutator == BlockModelGenerators.NOP
                                    ? MateriallyTexturedBuilder.multiVariantWithParent(specLocs.get(shape))
                                    : MateriallyTexturedBuilder.multiVariantWithParent(specLocs.get(shape)).with(mutator));
                }
            }
        }
        blockModels.blockStateOutput.accept(multipart);

        createItemModel(itemModels, allBrickStairBlock, straightLoc);
    }

    // Rotation table verified against the old vanilla_stairs_compat ground truth (see StairsModelProvider).
    private VariantMutator getRotationMutator(Half half, Direction facing, StairsShape shape) {
        int yRot = getYFromFacing(facing) + getYFromShape(shape) + getYFromHalf(half, shape);
        yRot = ((yRot % 360) + 360) % 360; // normalize to [0, 360)
        int xRot = half == Half.TOP ? 180 : 0;

        VariantMutator mutator = BlockModelGenerators.NOP;
        if (yRot != 0) {
            mutator = switch (yRot) {
                case 90 -> BlockModelGenerators.Y_ROT_90;
                case 180 -> BlockModelGenerators.Y_ROT_180;
                case 270 -> BlockModelGenerators.Y_ROT_270;
                default -> BlockModelGenerators.NOP;
            };
        }

        if (xRot != 0) {
            mutator = mutator.then(BlockModelGenerators.X_ROT_180);
        }

        return mutator;
    }

    private int getYFromHalf(Half half, StairsShape shape) {
        return switch (shape) {
            case STRAIGHT -> 0;
            default -> half == Half.TOP ? 90 : 0;
        };
    }

    private int getYFromShape(StairsShape shape) {
        return switch (shape) {
            case OUTER_LEFT, INNER_LEFT -> -90;
            default -> 0;
        };
    }

    private int getYFromFacing(Direction facing) {
        return switch (facing) {
            case EAST -> 0;
            default -> 90; // SOUTH is the default
            case WEST -> 180;
            case NORTH -> 270;
        };
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.getInstance().getAllBrickStairBlocks().stream()
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return ModBlocks.getInstance().getAllBrickStairBlocks().stream()
                .map(Block::asItem)
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "All Brick Stair Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new AllBrickStairModelProvider(event.getGenerator().getPackOutput()));
    }

}
