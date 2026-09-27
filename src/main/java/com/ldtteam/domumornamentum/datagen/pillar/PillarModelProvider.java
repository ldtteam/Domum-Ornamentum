package com.ldtteam.domumornamentum.datagen.pillar;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.PillarBlock;
import com.ldtteam.domumornamentum.block.types.PillarShapeType;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.NonNull;

import java.util.stream.Stream;

import static com.ldtteam.domumornamentum.block.decorative.PillarBlock.COLUMN;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class PillarModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new PillarModelProvider(event.getGenerator().getPackOutput()));
    }

    public PillarModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        ModBlocks.getInstance().getPillars().forEach(pillar ->
                registerStatesAndModelsFor(pillar, blockModels, itemModels)
        );
    }

    private void registerStatesAndModelsFor(PillarBlock pillar,
                                            BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        final String blockName = pillar.getRegistryName().getPath();

        // Hand-crafted geometry lives at models/block/pillar/<blockName>_<shape>_spec (in main resources).
        // Datagen no longer writes thin wrapper files; the blockstate references the specs inline as
        // custom block state models. No rotations are needed for pillars.
        final MultiPartGenerator multipart = MultiPartGenerator.multiPart(pillar);

        Identifier fullPillarSpecLoc = null;
        for (final PillarShapeType shapeType : PillarShapeType.values()) {
            final Identifier specLoc = blockModelLoc("pillar/" + blockName + "_" + shapeType.getSerializedName() + "_spec");
            if (shapeType == PillarShapeType.FULL_PILLAR) {
                fullPillarSpecLoc = specLoc;
            }

            final MultiVariant variant = MateriallyTexturedBuilder.multiVariantWithParent(specLoc);

            multipart.with(
                    new ConditionBuilder().term(COLUMN, shapeType),
                    variant
            );
        }

        blockModels.blockStateOutput.accept(multipart);

        // Item model: references FULL_PILLAR custom loader spec (created above)
        itemModels.itemModelOutput.accept(
                pillar.asItem(),
                ItemModelUtils.plainModel(fullPillarSpecLoc)
        );
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.getInstance().getPillars().stream()
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return ModBlocks.getInstance().getPillars().stream()
                .map(PillarBlock::asItem)
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Pillar Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new PillarModelProvider(event.getGenerator().getPackOutput()));
    }

}
