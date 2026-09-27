package com.ldtteam.domumornamentum.datagen.post;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.PostBlock;
import com.ldtteam.domumornamentum.block.types.PostType;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.ldtteam.domumornamentum.block.AbstractPostBlock.FACING;
import static com.ldtteam.domumornamentum.block.AbstractPostBlock.UPRIGHT;
import static com.ldtteam.domumornamentum.block.decorative.PostBlock.TYPE;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class PostModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new PostModelProvider(event.getGenerator().getPackOutput()));
    }

    @SubscribeEvent
    public static void registerProperty(final RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "post_type"), PostTypeProperty.CODEC);
    }

    public PostModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        final PostBlock postBlock = ModBlocks.getInstance().getPost();

        // Hand-crafted geometry lives at models/block/post/post_<type>_spec (in main resources).
        // Datagen no longer writes thin wrapper files; the blockstate references the specs inline as
        // custom block state models.
        final PostType[] types = PostType.values();
        final Identifier[] specLocs = new Identifier[types.length];
        for (int i = 0; i < types.length; i++) {
            specLocs[i] = blockModelLoc("post/post_" + types[i].getSerializedName() + "_spec");
        }

        // Build multipart blockstate with conditions for each combination
        // FACING (6) x UPRIGHT (2) x TYPE (6) = 72 parts
        final MultiPartGenerator multipart = MultiPartGenerator.multiPart(postBlock);

        for (Direction facingValue : FACING.getPossibleValues()) {
            for (Boolean upright : UPRIGHT.getPossibleValues()) {
                for (int i = 0; i < types.length; i++) {
                    final PostType typeValue = types[i];
                    final MultiVariant baseVariant = MateriallyTexturedBuilder.multiVariantWithParent(specLocs[i]);

                    final VariantMutator mutator = getRotationMutator(facingValue, upright);
                    final MultiVariant variant = mutator == BlockModelGenerators.NOP
                            ? baseVariant
                            : baseVariant.with(mutator);

                    multipart.with(
                            new ConditionBuilder()
                                    .term(FACING, facingValue)
                                    .term(UPRIGHT, upright)
                                    .term(TYPE, typeValue),
                            variant
                    );
                }
            }
        }

        blockModels.blockStateOutput.accept(multipart);

        // Item model with rangeSelect overrides for each type
        final List<RangeSelectItemModel.Entry> entries = new ArrayList<>();
        for (int i = 0; i < types.length; i++) {
            entries.add(ItemModelUtils.override(ItemModelUtils.plainModel(specLocs[i]), (float) i));
        }
        itemModels.itemModelOutput.accept(postBlock.asItem(),
                ItemModelUtils.rangeSelect(PostTypeProperty.INSTANCE, entries)
        );
    }

    private VariantMutator getRotationMutator(Direction facing, boolean upright) {
        final int yRot = getYFromFacing(facing);
        final int xRot = getXFromFacing(facing) + getUpright(upright, facing);

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

    private int getXFromFacing(Direction facing) {
        return switch (facing) {
            case UP -> 180;
            case DOWN -> 0;
            case NORTH -> 0;
            case SOUTH -> 0;
            case WEST -> 0;
            case EAST -> 0;
        };
    }

    private int getUpright(boolean upright, Direction direction) {
        if (!upright && direction != Direction.DOWN && direction != Direction.UP) {
            return 90;
        }
        return 0;
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of(ModBlocks.getInstance().getPost())
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of(ModBlocks.getInstance().getPost().asItem())
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Post Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new PostModelProvider(event.getGenerator().getPackOutput()));
    }

}
