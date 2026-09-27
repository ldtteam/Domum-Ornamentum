package com.ldtteam.domumornamentum.datagen.wall.vanilla;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.vanilla.WallBlock;
import com.ldtteam.domumornamentum.client.model.loader.MateriallyTexturedBuilder;
import com.ldtteam.domumornamentum.datagen.BaseModelProvider;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.stream.Stream;

import static net.minecraft.world.level.block.WallBlock.UP;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class WallModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new WallModelProvider(event.getGenerator().getPackOutput()));
    }

    public WallModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        final WallBlock wallBlock = ModBlocks.getInstance().getWall();

        // Hand-crafted geometry lives at models/block/wall/*_spec (in main resources).
        // Datagen no longer writes thin wrapper files; the blockstate references the specs
        // inline as custom block state models.
        final Identifier postSpecLoc = blockModelLoc("wall/wall_post_spec");
        final Identifier sideSpecLoc = blockModelLoc("wall/wall_side_spec");
        final Identifier sideTallSpecLoc = blockModelLoc("wall/wall_side_tall_spec");

        final MultiPartGenerator multipart = MultiPartGenerator.multiPart(wallBlock);

        // Post when UP=true (no UV lock: old ground truth had no uvlock anywhere)
        multipart.with(
                new ConditionBuilder().term(UP, true),
                MateriallyTexturedBuilder.multiVariantWithParent(postSpecLoc)
        );

        // For each direction: side_tall when TALL, side when LOW, skipped when NONE
        final Map<Direction, EnumProperty<WallSide>> properties = WallBlock.PROPERTIES;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            final EnumProperty<WallSide> prop = properties.get(direction);
            final int yRot = getYFromFacing(direction);

            // Tall side
            multipart.with(
                    new ConditionBuilder().term(prop, WallSide.TALL),
                    MateriallyTexturedBuilder.multiVariantWithParent(sideTallSpecLoc)
                            .with(getYRotMutator(yRot))
            );

            // Low side
            multipart.with(
                    new ConditionBuilder().term(prop, WallSide.LOW),
                    MateriallyTexturedBuilder.multiVariantWithParent(sideSpecLoc)
                            .with(getYRotMutator(yRot))
            );
        }

        blockModels.blockStateOutput.accept(multipart);

        // Item model: hand-crafted geometry at models/item/wall/wall_spec
        itemModels.itemModelOutput.accept(
                wallBlock.asItem(),
                ItemModelUtils.plainModel(itemModelLoc("wall/wall_spec"))
        );
    }

    private int getYFromFacing(final Direction facing) {
        return switch (facing) {
            default -> 0;
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
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

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of(ModBlocks.getInstance().getWall())
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of(ModBlocks.getInstance().getWall().asItem())
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Wall Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new WallModelProvider(event.getGenerator().getPackOutput()));
    }

}
