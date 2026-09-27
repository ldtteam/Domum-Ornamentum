package com.ldtteam.domumornamentum.datagen;

import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.stream.Stream;

/**
 * Base class for model providers that use the materially_textured custom loader.
 * Provides helper methods for creating custom loader models and blockstates.
 */
public abstract class BaseModelProvider extends ModelProvider {

    // Custom loader ID for materially_textured models
    protected static final Identifier MATERIALLY_TEXTURED_LOADER = Constants.resLocDO("materially_textured");

    public BaseModelProvider(final PackOutput packOutput) {
        super(packOutput, Constants.MOD_ID);
    }

    /**
     * Creates a custom loader model that wraps a parent spec model.
     * @param blockModels The block model generators
     * @param modelLoc The model location (without suffix)
     * @return The model location with _spec suffix for use in blockstates
     */
    protected Identifier createCustomLoaderModel(BlockModelGenerators blockModels, Identifier modelLoc) {
        final Identifier specLoc = modelLoc.withSuffix("_spec");
        
        // Generate the spec model with custom loader
        blockModels.modelOutput.accept(specLoc, new ModelInstance() {
            @Override
            public JsonObject get() {
                JsonObject json = new JsonObject();
                json.addProperty("loader", MATERIALLY_TEXTURED_LOADER.toString());
                json.addProperty("parent", modelLoc.toString());
                return json;
            }
        });
        
        return specLoc;
    }

    /**
     * Creates a custom loader model with a specific parent.
     * @param blockModels The block model generators
     * @param modelLoc The model location (without suffix)
     * @param parentLoc The parent model location
     * @return The model location with _spec suffix for use in blockstates
     */
    protected Identifier createCustomLoaderModelWithParent(BlockModelGenerators blockModels, Identifier modelLoc, Identifier parentLoc) {
        final Identifier specLoc = modelLoc.withSuffix("_spec");
        
        // Generate the spec model with custom loader
        blockModels.modelOutput.accept(specLoc, new ModelInstance() {
            @Override
            public JsonObject get() {
                JsonObject json = new JsonObject();
                json.addProperty("loader", MATERIALLY_TEXTURED_LOADER.toString());
                json.addProperty("parent", parentLoc.toString());
                return json;
            }
        });
        
        return specLoc;
    }

    /**
     * Returns a block model location with the mod namespace.
     */
    protected Identifier blockModelLoc(String path) {
        return Constants.resLocDO("block/" + path);
    }

    /**
     * Returns an item model location with the mod namespace.
     */
    protected Identifier itemModelLoc(String path) {
        return Constants.resLocDO("item/" + path);
    }

    /**
     * Creates a plain variant from a model location (equivalent to vanilla's private plainVariant).
     */
    protected MultiVariant plainVariant(Identifier model) {
        return new MultiVariant(WeightedList.of(new Variant(model)));
    }

    /**
     * Creates a simple dispatch generator for a block with an initial model.
     */
    protected BlockModelDefinitionGenerator dispatch(Block block, MultiVariant initialModel) {
        return MultiVariantGenerator.dispatch(block, initialModel);
    }

    /**
     * Creates an empty dispatch generator for a block (to be filled with property dispatch).
     */
    protected MultiVariantGenerator.Empty dispatchEmpty(Block block) {
        return MultiVariantGenerator.dispatch(block);
    }

    /**
     * Creates a simple variant for a custom loader model.
     */
    protected Variant customLoaderVariant(Identifier modelLoc) {
        return new Variant(modelLoc);
    }

    /**
     * Creates a plain item model that references a block model.
     */
    protected void createItemModel(ItemModelGenerators itemModels, Block block, Identifier modelLoc) {
        itemModels.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(modelLoc));
    }

    /**
     * Creates a plain item model that references a custom model location.
     */
    protected void createItemModel(ItemModelGenerators itemModels, Item item, Identifier modelLoc) {
        itemModels.itemModelOutput.accept(item, ItemModelUtils.plainModel(modelLoc));
    }

    /**
     * Creates a simple item model using the standard block model location.
     */
    protected void createSimpleItemModel(BlockModelGenerators blockModels, Block block) {
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    protected Identifier modLoc(String path) {
        return Constants.resLocDO(path);
    }
}
