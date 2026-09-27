package com.ldtteam.domumornamentum.datagen.slab;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.vanilla.SlabBlock;
import com.ldtteam.domumornamentum.client.model.loader.MateriallyTexturedBuilder;
import com.ldtteam.domumornamentum.datagen.BaseModelProvider;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.NonNull;

import java.util.stream.Stream;

import static net.minecraft.world.level.block.SlabBlock.TYPE;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class SlabModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new SlabModelProvider(event.getGenerator().getPackOutput()));
    }

    public SlabModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        final SlabBlock slabBlock = ModBlocks.getInstance().getSlab();

        // Hand-crafted geometry lives at models/block/slab/slab_<type>_spec (in main resources).
        // Datagen no longer writes thin wrapper files; the blockstate references the specs inline
        // as custom block state models.
        final MultiPartGenerator multipart = MultiPartGenerator.multiPart(slabBlock);

        for (SlabType slabType : SlabType.values()) {
            final Identifier specLoc = blockModelLoc("slab/slab_" + slabType.getSerializedName() + "_spec");

            final MultiVariant variant = MateriallyTexturedBuilder.multiVariantWithParent(specLoc);

            multipart.with(
                    new ConditionBuilder().term(TYPE, slabType),
                    variant
            );
        }

        blockModels.blockStateOutput.accept(multipart);

        // Item model references the hand-crafted item geometry (models/item/slab/slab_spec).
        itemModels.itemModelOutput.accept(
                slabBlock.asItem(),
                ItemModelUtils.plainModel(itemModelLoc("slab/slab_spec"))
        );
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of(ModBlocks.getInstance().getSlab())
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of(ModBlocks.getInstance().getSlab().asItem())
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Slab Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new SlabModelProvider(event.getGenerator().getPackOutput()));
    }

}
