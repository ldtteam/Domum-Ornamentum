package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.BrickBlock;
import com.ldtteam.domumornamentum.datagen.BaseModelProvider;
import com.ldtteam.domumornamentum.util.Constants;
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
public class BrickModelProvider extends BaseModelProvider {

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new BrickModelProvider(event.getGenerator().getPackOutput()));
    }

    public BrickModelProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        ModBlocks.getInstance().getBricks().forEach(brickBlock -> {
            registerStatesAndModelsFor(brickBlock, blockModels, itemModels);
        });
    }

    private void registerStatesAndModelsFor(BrickBlock brickBlock,
                                            BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        final String blockName = brickBlock.getRegistryName().getPath();

        // Generate a cube_all model at block/brick/<name>; the texture lives flat at textures/block/brick/<name>.png.
        // Bricks are not retexturable, so this is a plain variant dispatch (no custom loader).
        final Identifier modelLoc = blockModelLoc("brick/" + blockName);
        ModelTemplates.CUBE_ALL.create(modelLoc,
                TextureMapping.cube(new Material(Constants.resLocDO("block/brick/" + blockName))),
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(brickBlock, plainVariant(modelLoc)));

        createItemModel(itemModels, brickBlock, modelLoc);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.getInstance().getBricks().stream()
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return ModBlocks.getInstance().getBricks().stream()
                .map(Block::asItem)
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    public @NonNull String getName() {
        return "Brick Models Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new BrickModelProvider(event.getGenerator().getPackOutput()));
    }

}
