package com.ldtteam.domumornamentum.datagen.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import com.ldtteam.domumornamentum.datagen.DatagenContext;
import com.ldtteam.domumornamentum.util.Constants;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class BlockStateProvider implements DataProvider {
    private final PackOutput output;
    private final String modId;
    private final DatagenContext existingFileHelper;
    private final Map<Block, JsonElement> blockStates = new LinkedHashMap<>();
    private final Map<Block, MultiPartBlockStateBuilder> multiParts = new HashMap<>();
    private final Map<Block, VariantBlockStateBuilder> variants = new HashMap<>();
    private final Map<Identifier, ModelFile> models = new LinkedHashMap<>();

    protected BlockStateProvider(PackOutput output, String modId, DatagenContext existingFileHelper) {
        this.output = output;
        this.modId = modId;
        this.existingFileHelper = existingFileHelper;
    }

    protected abstract void registerStatesAndModels();

    public Identifier modLoc(String path) {
        return Identifier.fromNamespaceAndPath(modId, path);
    }

    public Identifier mcLoc(String path) {
        return Identifier.withDefaultNamespace(path);
    }

    public ModelProvider models() {
        return new ModelProvider(false);
    }

    public ModelProvider itemModels() {
        return new ModelProvider(true);
    }

    public MultiPartBlockStateBuilder getMultipartBuilder(Block block) {
        return multiParts.computeIfAbsent(block, key -> {
            MultiPartBlockStateBuilder builder = new MultiPartBlockStateBuilder(this, key);
            blockStates.put(key, new JsonObject());
            return builder;
        });
    }

    public VariantBlockStateBuilder getVariantBuilder(Block block) {
        return variants.computeIfAbsent(block, key -> {
            if (!blockStates.containsKey(key)) {
                blockStates.put(key, new JsonObject());
            }
            return new VariantBlockStateBuilder(key);
        });
    }

    public void simpleBlock(Block block) {
        simpleBlock(block, models().getExistingFile(ModelLocationUtils.getModelLocation(block)));
    }

    public void simpleBlock(Block block, ModelFile model) {
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();
        JsonObject variant = new JsonObject();
        variant.addProperty("model", model.getLocation().toString());
        variants.add("", variant);
        root.add("variants", variants);
        blockStates.put(block, root);
    }

    public void simpleBlockWithItem(Block block, ModelFile model) {
        simpleBlock(block, model);
        itemModels().getBuilder(BuiltInRegistries.ITEM.getKey(block.asItem()).getPath()).parent(model);
    }

    public void simpleBlockItem(Block block, ModelFile model) {
        itemModels().getBuilder(BuiltInRegistries.ITEM.getKey(block.asItem()).getPath()).parent(model);
    }

    void add(MultiPartBlockStateBuilder builder) {
        blockStates.put(builder.block, builder.toJson());
    }

    void add(VariantBlockStateBuilder builder) {
        blockStates.put(builder.block, builder.toJson());
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        registerStatesAndModels();
        multiParts.forEach((block, builder) -> blockStates.put(block, builder.toJson()));
        variants.forEach((block, builder) -> blockStates.put(block, builder.toJson()));
        validateBlocksAndItems();

        PackOutput.PathProvider blockStatesOutput = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        PackOutput.PathProvider modelsOutput = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
        PackOutput.PathProvider itemsOutput = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "items");

        CompletableFuture<?>[] saves = new CompletableFuture[blockStates.size() + models.size() + itemCount()];
        int index = 0;
        for (Map.Entry<Block, JsonElement> entry : blockStates.entrySet()) {
            Identifier id = BuiltInRegistries.BLOCK.getKey(entry.getKey());
            saves[index++] = DataProvider.saveStable(cache, entry.getValue(), blockStatesOutput.json(id));
        }
        for (Map.Entry<Identifier, ModelFile> entry : models.entrySet()) {
            saves[index++] = DataProvider.saveStable(cache, entry.getValue().getRoot(), modelsOutput.json(entry.getKey()));
        }
        for (var item : BuiltInRegistries.ITEM) {
            Identifier id = BuiltInRegistries.ITEM.getKey(item);
            if (!id.getNamespace().equals(modId)) {
                continue;
            }
            JsonObject clientItem = new JsonObject();
            clientItem.add("model", createClientItemModel(id, item));
            saves[index++] = DataProvider.saveStable(cache, clientItem, itemsOutput.json(id));
        }
        return CompletableFuture.allOf(saves);
    }

    /**
     * Builds the 26.2 item-model definition.  The old Domum models used
     * numeric item overrides (for example {@code post_type: 0}), but those
     * overrides are no longer evaluated by the 26.2 item renderer.  The
     * corresponding block-state component is now the supported selector.
     */
    private JsonObject createClientItemModel(final Identifier itemId, final net.minecraft.world.item.Item item) {
        final List<String> values;
        final String modelPrefix;
        switch (itemId.getPath()) {
            case "fancy_door" -> {
                values = List.of("full", "creeper");
                modelPrefix = "item/door/fancy/door_";
            }
            case "vanilla_doors_compat" -> {
                values = List.of("full", "port_manteau", "vertically_striped", "waffle");
                modelPrefix = "item/door/door_";
            }
            case "post" -> {
                values = List.of("plain", "heavy", "turned", "pinched", "double", "quad");
                modelPrefix = "block/post/post_";
            }
            case "panel" -> {
                values = List.of("boss", "coffer", "full", "horizontal_bars", "horizontally_squiggly_striped",
                    "horizontally_striped", "moulding", "port_manteau", "porthole", "roundel", "slot",
                    "vertical_bars", "vertically_squiggly_striped", "vertically_striped", "waffle");
                modelPrefix = "block/panel/panel_";
            }
            case "fancy_trapdoors" -> {
                values = List.of("full", "creeper");
                modelPrefix = "block/trapdoor/fancy/trapdoor_";
            }
            case "vanilla_trapdoors_compat" -> {
                values = List.of("boss", "coffer", "full", "horizontal_bars", "horizontally_squiggly_striped",
                    "horizontally_striped", "moulding", "port_manteau", "porthole", "roundel", "slot",
                    "vertical_bars", "vertically_squiggly_striped", "vertically_striped", "waffle");
                modelPrefix = "block/trapdoor/trapdoor_";
            }
            default -> {
                final Identifier modelLocation = ModelLocationUtils.getModelLocation(item);
                if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof IMateriallyTexturedBlock)
                {
                    return createMateriallyTexturedItemModel(modelLocation);
                }

                final JsonObject model = new JsonObject();
                model.addProperty("type", "minecraft:model");
                model.addProperty("model", modelLocation.toString());
                return model;
            }
        }

        final JsonObject select = new JsonObject();
        select.addProperty("type", "minecraft:select");
        select.addProperty("property", "minecraft:block_state");
        select.addProperty("block_state_property", "type");

        final com.google.gson.JsonArray cases = new com.google.gson.JsonArray();
        for (final String value : values) {
            final JsonObject caseObject = new JsonObject();
            caseObject.addProperty("when", value);
            caseObject.add("model", createMateriallyTexturedItemModel(modLoc(modelPrefix + value + "_spec")));
            cases.add(caseObject);
        }
        select.add("cases", cases);

        select.add("fallback", createMateriallyTexturedItemModel(modLoc(modelPrefix + values.get(0) + "_spec")));
        return select;
    }

    private JsonObject createMateriallyTexturedItemModel(final Identifier modelLocation)
    {
        final JsonObject model = new JsonObject();
        model.addProperty("type", Constants.MOD_ID + ":materially_textured_item");
        model.addProperty("model", modelLocation.toString());
        return model;
    }

    private int itemCount() {
        return (int) BuiltInRegistries.ITEM.stream()
            .filter(item -> BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(modId))
            .count();
    }

    private void validateBlocksAndItems() {
        for (Map.Entry<Block, JsonElement> entry : blockStates.entrySet()) {
            if (entry.getValue().getAsJsonObject().isEmpty()) {
                throw new IllegalStateException("No blockstate generated for " + BuiltInRegistries.BLOCK.getKey(entry.getKey()));
            }
        }
    }

    public final class ModelProvider {
        private final boolean itemModels;

        private ModelProvider(boolean itemModels) {
            this.itemModels = itemModels;
        }

        public ItemModelBuilder getBuilder(String path) {
            return create(modLoc(path));
        }

        public ItemModelBuilder withExistingParent(String path, Identifier parent) {
            return create(modLoc(path)).parent(parent);
        }

        public ItemModelBuilder withExistingParent(String path, String parent) {
            return create(modLoc(path)).parent(Identifier.parse(parent));
        }

        public ModelFile cubeAll(String path, Identifier texture) {
            ItemModelBuilder builder = getBuilder(path);
            builder.getRoot().addProperty("parent", "minecraft:block/cube_all");
            JsonObject textures = new JsonObject();
            textures.addProperty("all", texture.toString());
            builder.getRoot().add("textures", textures);
            return builder;
        }

        public ModelFile getExistingFile(Identifier location) {
            ModelFile model = models.get(location);
            if (model == null) {
                return ModelFile.unmanaged(location);
            }
            return model;
        }

        private ItemModelBuilder create(Identifier location) {
            ModelFile existing = models.get(location);
            if (existing instanceof ItemModelBuilder itemModelBuilder) {
                return itemModelBuilder;
            }
            ItemModelBuilder builder = new ItemModelBuilder(location);
            models.put(location, builder);
            return builder;
        }
    }
}
