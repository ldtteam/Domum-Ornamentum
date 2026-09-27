package com.ldtteam.domumornamentum.datagen.panel;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.PanelBlock;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
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

import static com.ldtteam.domumornamentum.block.AbstractPanelBlockTrapdoor.HALF;
import static com.ldtteam.domumornamentum.block.AbstractPanelBlockTrapdoor.OPEN;
import static com.ldtteam.domumornamentum.block.decorative.PanelBlock.TYPE;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class PanelModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new PanelModelProvider(event.getGenerator().getPackOutput()));
    }

    @SubscribeEvent
    public static void registerProperty(final RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "panel_type"), TrapdoorTypeProperty.CODEC);
    }

    public PanelModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        final PanelBlock panelBlock = ModBlocks.getInstance().getPanel();

        // Hand-crafted geometry lives at models/block/panel/panel_<type>_spec (in main resources).
        // Datagen no longer writes thin wrapper files; blockstates reference the specs inline as
        // custom block state models.
        final TrapdoorType[] types = TrapdoorType.values();
        final Identifier[] specLocs = new Identifier[types.length];
        for (int i = 0; i < types.length; i++) {
            specLocs[i] = blockModelLoc("panel/panel_" + types[i].getSerializedName() + "_spec");
        }

        // Build multipart blockstate with conditions for each combination
        final MultiPartGenerator multipart = MultiPartGenerator.multiPart(panelBlock);

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

        // Item model: range select over the hand-crafted block specs, one entry per TrapdoorType.
        // (No separate item geometry exists for panels.)
        final List<RangeSelectItemModel.Entry> entries = new ArrayList<>();
        for (int i = 0; i < types.length; i++) {
            entries.add(ItemModelUtils.override(ItemModelUtils.plainModel(specLocs[i]), (float) i));
        }
        itemModels.itemModelOutput.accept(panelBlock.asItem(),
                ItemModelUtils.rangeSelect(TrapdoorTypeProperty.INSTANCE, entries)
        );
    }

    private VariantMutator getRotationMutator(Direction facing, boolean open, Half half) {
        final int yBase = getYFromFacing(facing);
        final int yRot = yBase + getYFromOpenAndHalf(open, half);
        final int xRot = getXFromOpenAndHalf(open, half);

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

    private int getYFromFacing(Direction facing) {
        return switch (facing) {
            default -> 0;
            case SOUTH -> 180;
            case WEST -> 270;
            case EAST -> 90;
        };
    }

    private int getYFromOpenAndHalf(boolean open, Half half) {
        // Ground truth (old panel blockstate): only the OPEN TOP half gets an extra y+180.
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
        return Stream.of(ModBlocks.getInstance().getPanel())
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of(ModBlocks.getInstance().getPanel().asItem())
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Panel Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new PanelModelProvider(event.getGenerator().getPackOutput()));
    }

}
