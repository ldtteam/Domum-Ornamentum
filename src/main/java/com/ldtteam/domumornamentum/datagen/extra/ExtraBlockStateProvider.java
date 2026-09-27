package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
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
import net.minecraft.resources.Identifier;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.stream.Stream;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ExtraBlockStateProvider extends BaseModelProvider
{

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Client event) {
        event.addProvider(new ExtraBlockStateProvider(event.getGenerator().getPackOutput()));
    }

    public ExtraBlockStateProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.getInstance().getExtraTopBlocks()
                .stream()
                .map(BuiltInRegistries.BLOCK::wrapAsHolder);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return ModBlocks.getInstance().getExtraTopBlocks()
                .stream()
                .map(Block::asItem)
                .map(BuiltInRegistries.ITEM::wrapAsHolder);
    }

    @Override
    protected void registerModels(final @NonNull BlockModelGenerators blockModels, final @NonNull ItemModelGenerators itemModels) {
        ModBlocks.getInstance().getExtraTopBlocks().forEach(brickBlock -> {
            registerStatesAndModelsFor(brickBlock, blockModels, itemModels);
        });
    }

    private void registerStatesAndModelsFor(
            ExtraBlock extraBlock,
            BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        final String blockName = extraBlock.getRegistryName().getPath();

        // Generate a cube_all model at block/extra/<category>/<name>; textures live flat at
        // textures/block/extra/<name>.png (registry names already end in _extra).
        // Extras are not retexturable -> plain variant dispatch.
        final String category = extraBlock.getType().getCategory().name().toLowerCase();
        final Identifier modelLoc = blockModelLoc("extra/" + category + "/" + blockName);
        ModelTemplates.CUBE_ALL.create(modelLoc,
                TextureMapping.cube(new Material(Constants.resLocDO("block/extra/" + blockName))),
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(extraBlock, plainVariant(modelLoc)));

        createItemModel(itemModels, extraBlock, modelLoc);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Extra BlockStates Provider";
    }

    public static void register(GatherDataEvent.Client event)
    {
        event.addProvider(new ExtraBlockStateProvider(event.getGenerator().getPackOutput()));
    }

}
