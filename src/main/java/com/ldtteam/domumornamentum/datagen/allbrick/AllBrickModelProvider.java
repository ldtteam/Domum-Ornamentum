package com.ldtteam.domumornamentum.datagen.allbrick;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.AllBrickBlock;
import com.ldtteam.domumornamentum.datagen.BaseModelProvider;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.client.model.loader.MateriallyTexturedBuilder;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.resources.model.sprite.Material;

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

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class AllBrickModelProvider extends BaseModelProvider {

    /** Base texture all retexturable bricks start from (see {@link AllBrickBlock}'s SimpleRetexturableComponent). */
    private static final Identifier BASE_TEXTURE = Identifier.withDefaultNamespace("block/oak_planks");

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new AllBrickModelProvider(event.getGenerator().getPackOutput()));
    }

    public AllBrickModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        ModBlocks.getInstance().getAllBrickBlocks().forEach(allBrickBlock -> {
            registerStatesAndModelsFor(allBrickBlock, blockModels, itemModels);
        });
    }

    private void registerStatesAndModelsFor(AllBrickBlock allBrickBlock,
                                            BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        final String blockName = allBrickBlock.getRegistryName().getPath();

        // Generate a cube_all model at block/allbrick/<name> textured with the oak_planks base;
        // runtime retexturing is applied by the materially_textured custom loader.
        final Identifier modelLoc = blockModelLoc("allbrick/" + blockName);
        ModelTemplates.CUBE_ALL.create(modelLoc,
                TextureMapping.cube(new Material(BASE_TEXTURE)),
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(allBrickBlock, MateriallyTexturedBuilder.multiVariantWithParent(modelLoc)));

        createItemModel(itemModels, allBrickBlock, modelLoc);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.getInstance().getAllBrickBlocks().stream()
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return ModBlocks.getInstance().getAllBrickBlocks().stream()
                .map(Block::asItem)
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "All Brick Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new AllBrickModelProvider(event.getGenerator().getPackOutput()));
    }

}
