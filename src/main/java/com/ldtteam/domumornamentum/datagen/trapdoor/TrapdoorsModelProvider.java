package com.ldtteam.domumornamentum.datagen.trapdoor;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.block.vanilla.TrapdoorBlock;
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
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Half;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.ldtteam.domumornamentum.block.vanilla.TrapdoorBlock.TYPE;
import static net.minecraft.world.level.block.TrapDoorBlock.HALF;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.OPEN;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class TrapdoorsModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new TrapdoorsModelProvider(event.getGenerator().getPackOutput()));
    }

    @SubscribeEvent
    public static void registerProperty(final RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "trapdoor_type"), TrapdoorTypeProperty.CODEC);
    }

    public TrapdoorsModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        final TrapdoorBlock trapdoorBlock = ModBlocks.getInstance().getTrapdoor();

        // Hand-crafted geometry lives at models/block/trapdoor/trapdoor_<type>_spec (in main
        // resources). Datagen no longer writes thin wrapper files; the blockstate references the
        // specs inline as custom block state models.
        final TrapdoorType[] types = TrapdoorType.values();
        final Identifier[] specLocs = new Identifier[types.length];
        for (int i = 0; i < types.length; i++) {
            final String serializedName = types[i].getSerializedName();
            specLocs[i] = blockModelLoc("trapdoor/trapdoor_" + serializedName + "_spec");
        }

        // Build multipart blockstate with conditions for each combination
        final MultiPartGenerator multipart = MultiPartGenerator.multiPart(trapdoorBlock);

        for (Direction facingValue : HORIZONTAL_FACING.getPossibleValues()) {
            for (int i = 0; i < types.length; i++) {
                final TrapdoorType typeValue = types[i];
                final MultiVariant baseVariant = MateriallyTexturedBuilder.multiVariantWithParent(specLocs[i]);
                for (Half halfValue : HALF.getPossibleValues()) {
                    for (boolean openValue : OPEN.getPossibleValues()) {
                        final VariantMutator mutator = getRotationMutator(facingValue, openValue, halfValue);
                        final MultiVariant variant = mutator == BlockModelGenerators.NOP
                                ? baseVariant
                                : baseVariant.with(mutator);

                        multipart.with(
                                new ConditionBuilder()
                                        .term(HORIZONTAL_FACING, facingValue)
                                        .term(TYPE, typeValue)
                                        .term(HALF, halfValue)
                                        .term(OPEN, openValue),
                                variant
                        );
                    }
                }
            }
        }

        blockModels.blockStateOutput.accept(multipart);

        // Item model with rangeSelect overrides for each type
        final List<RangeSelectItemModel.Entry> entries = new ArrayList<>();
        for (int i = 0; i < types.length; i++) {
            entries.add(ItemModelUtils.override(ItemModelUtils.plainModel(specLocs[i]), (float) i));
        }
        itemModels.itemModelOutput.accept(trapdoorBlock.asItem(),
                ItemModelUtils.rangeSelect(TrapdoorTypeProperty.INSTANCE, entries)
        );
    }

    private VariantMutator getRotationMutator(Direction facing, boolean open, Half half) {
        final int yBase = getYFromFacing(facing);
        final int yRot = yBase + getYFromOpenAndHalf(open, half);
        final int xRot = getXFromOpenAndHalf(open, half);

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

    private int getYFromFacing(Direction facing) {
        return switch (facing) {
            default -> 0;
            case SOUTH -> 180;
            case WEST -> 270;
            case EAST -> 90;
        };
    }

    private int getYFromOpenAndHalf(boolean open, Half half) {
        // Only OPEN TOP gets the extra +180 y rotation (verified against old ground truth).
        return open && half == Half.TOP ? 180 : 0;
    }

    private int getXFromOpenAndHalf(boolean open, Half half) {
        if (!open) {
            return half == Half.TOP ? 180 : 0;
        }
        return half == Half.TOP ? -90 : 90;
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of(ModBlocks.getInstance().getTrapdoor())
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of(ModBlocks.getInstance().getTrapdoor().asItem())
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Trapdoors Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new TrapdoorsModelProvider(event.getGenerator().getPackOutput()));
    }

}
