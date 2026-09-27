package com.ldtteam.domumornamentum.datagen.door.fancy;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FancyDoorBlock;
import com.ldtteam.domumornamentum.block.types.FancyDoorType;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.ldtteam.domumornamentum.block.decorative.FancyDoorBlock.TYPE;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.DOOR_HINGE;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.OPEN;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class FancyDoorsModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new FancyDoorsModelProvider(event.getGenerator().getPackOutput()));
    }

    @SubscribeEvent
    public static void registerProperty(final RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "fancy_door_type"), FancyDoorTypeProperty.CODEC);
    }

    public FancyDoorsModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        final FancyDoorBlock fancyDoorBlock = ModBlocks.getInstance().getFancyDoor();

        // Hand-crafted geometry lives at models/block/door/fancy/door_<type>_<bottom|top>_<left|right>[_open]_spec
        // (in main resources). Datagen no longer writes thin wrapper files; the blockstate references
        // the specs inline as custom block state models.
        final FancyDoorType[] types = FancyDoorType.values();

        // Build multipart blockstate with conditions for each combination
        final MultiPartGenerator multipart = MultiPartGenerator.multiPart(fancyDoorBlock);

        for (Direction facingValue : HORIZONTAL_FACING.getPossibleValues()) {
            for (int i = 0; i < types.length; i++) {
                final FancyDoorType typeValue = types[i];
                for (DoubleBlockHalf halfValue : BlockStateProperties.DOUBLE_BLOCK_HALF.getPossibleValues()) {
                    for (DoorHingeSide hingeValue : DOOR_HINGE.getPossibleValues()) {
                        for (boolean openValue : OPEN.getPossibleValues()) {
                            final Identifier specLoc = getSpecLoc(typeValue, halfValue, hingeValue, openValue);
                            final MultiVariant baseVariant = MateriallyTexturedBuilder.multiVariantWithParent(specLoc);
                            final VariantMutator mutator = getRotationMutator(facingValue, hingeValue, openValue);
                            final MultiVariant variant = mutator == BlockModelGenerators.NOP
                                    ? baseVariant
                                    : baseVariant.with(mutator);

                            multipart.with(
                                    new ConditionBuilder()
                                            .term(HORIZONTAL_FACING, facingValue)
                                            .term(TYPE, typeValue)
                                            .term(BlockStateProperties.DOUBLE_BLOCK_HALF, halfValue)
                                            .term(DOOR_HINGE, hingeValue)
                                            .term(OPEN, openValue),
                                    variant
                            );
                        }
                    }
                }
            }
        }

        blockModels.blockStateOutput.accept(multipart);

        // Item model with rangeSelect overrides for each type (hand-crafted item geometry at models/item/door/fancy/)
        final List<RangeSelectItemModel.Entry> entries = new ArrayList<>();
        for (int i = 0; i < types.length; i++) {
            entries.add(ItemModelUtils.override(
                    ItemModelUtils.plainModel(itemModelLoc("door/fancy/door_" + types[i].getSerializedName() + "_spec")),
                    (float) i));
        }
        itemModels.itemModelOutput.accept(fancyDoorBlock.asItem(),
                ItemModelUtils.rangeSelect(FancyDoorTypeProperty.INSTANCE, entries)
        );
    }

    private Identifier getSpecLoc(final FancyDoorType type, final DoubleBlockHalf half, final DoorHingeSide hinge, final boolean open) {
        final String halfName = half == DoubleBlockHalf.LOWER ? "bottom" : "top";
        final String hingeName = hinge == DoorHingeSide.LEFT ? "left" : "right";
        return blockModelLoc("door/fancy/door_" + type.getSerializedName() + "_" + halfName + "_" + hingeName + (open ? "_open" : "") + "_spec");
    }

    private VariantMutator getRotationMutator(final Direction facing, final DoorHingeSide hinge, final boolean open) {
        // Verified against old ground truth: y = closedY[facing] + (open ? (+90 if left hinge : -90) : 0); no x rotations.
        final int rawY = getYFromFacing(facing) + (open ? (hinge == DoorHingeSide.LEFT ? 90 : -90) : 0);
        final int yRot = ((rawY % 360) + 360) % 360;
        if (yRot == 0) {
            return BlockModelGenerators.NOP;
        }
        return getYRotMutator(yRot);
    }

    private VariantMutator getYRotMutator(final int degrees) {
        return switch (degrees) {
            case 90 -> BlockModelGenerators.Y_ROT_90;
            case 180 -> BlockModelGenerators.Y_ROT_180;
            case 270 -> BlockModelGenerators.Y_ROT_270;
            default -> BlockModelGenerators.NOP;
        };
    }

    private int getYFromFacing(final Direction facing) {
        return switch (facing) {
            case EAST -> 0;
            case SOUTH -> 90;
            case WEST -> 180;
            default -> 270; // NORTH
        };
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of(ModBlocks.getInstance().getFancyDoor())
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of(ModBlocks.getInstance().getFancyDoor().asItem())
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Fancy Doors Models Provider";
    }

    public static void register(GatherDataEvent.Client event) {
        event.addProvider(new FancyDoorsModelProvider(event.getGenerator().getPackOutput()));
    }
}
