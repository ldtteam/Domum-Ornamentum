package com.ldtteam.domumornamentum.recipe.architectscutter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockManager;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.recipe.ModRecipeSerializers;
import com.ldtteam.domumornamentum.recipe.ModRecipeTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ArchitectsCutterRecipe implements Recipe<Container>
{
    public static final Codec<ArchitectsCutterRecipe> CODEC = RecordCodecBuilder.create(builder -> builder
        .group(BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("block").forGetter(rec -> rec.getBlock().builtInRegistryHolder()),
            ExtraCodecs.strictOptionalField(ExtraCodecs.POSITIVE_INT, "count", 1).forGetter(ArchitectsCutterRecipe::getCount),
            ExtraCodecs.strictOptionalField(CompoundTag.CODEC, "nbt").forGetter(ArchitectsCutterRecipe::getAdditionalTag))
        .apply(builder, ArchitectsCutterRecipe::new));

    private final ResourceLocation blockName;
    private final int count;
    private final Optional<CompoundTag> additionalTag;

    public ArchitectsCutterRecipe(final ResourceLocation blockName, final int count, final Optional<CompoundTag> additionalTag)
    {
        this.blockName = blockName;
        this.count = count;
        this.additionalTag = additionalTag;
    }

    public ArchitectsCutterRecipe(final Holder<Block> block, final int count, final Optional<CompoundTag> additionalTag)
    {
        this(block.unwrapKey().orElseThrow().location(), count, additionalTag);
    }

    public ResourceLocation getBlockName()
    {
        return blockName;
    }

    public Block getBlock()
    {
        return BuiltInRegistries.BLOCK.get(blockName);
    }

    @Override
    public boolean matches(final @NotNull Container inv, final @NotNull Level worldIn)
    {
        final Block generatedBlock = getBlock();

        if (!(generatedBlock instanceof final IMateriallyTexturedBlock materiallyTexturedBlock))
            return false;

        final List<IMateriallyTexturedBlockComponent> components = Lists.newArrayList(materiallyTexturedBlock.getComponents());
        for (int componentsIndex = 0; componentsIndex < components.size(); componentsIndex++)
        {
            final IMateriallyTexturedBlockComponent component = components.get(componentsIndex);
            final ItemStack itemStackInSlot = inv.getItem(componentsIndex);

            final Item item = itemStackInSlot.getItem();
            if (!(item instanceof final BlockItem blockItem))
                return false;

            final Block blockInSlot = blockItem.getBlock();

            if (!blockInSlot.defaultBlockState().is(component.getValidSkins()))
                return false;
        }

        return true;
    }

    @Override
    public @NotNull ItemStack assemble(final @NotNull Container inv, final RegistryAccess registryAccess)
    {
        final Block generatedBlock = getBlock();

        if (!(generatedBlock instanceof final IMateriallyTexturedBlock materiallyTexturedBlock))
            return ItemStack.EMPTY;

        final List<IMateriallyTexturedBlockComponent> components = Lists.newArrayList(materiallyTexturedBlock.getComponents());

        final Map<ResourceLocation, Block> textureData = Maps.newHashMap();

        for (int componentsIndex = 0; componentsIndex < components.size(); componentsIndex++)
        {
            final IMateriallyTexturedBlockComponent component = components.get(componentsIndex);
            final ItemStack itemStackInSlot = inv.getItem(componentsIndex);

            if (itemStackInSlot.isEmpty() && component.isOptional())
                continue;

            final Item item = itemStackInSlot.getItem();
            if (!(item instanceof final BlockItem blockItem))
                return ItemStack.EMPTY;

            final Block blockInSlot = blockItem.getBlock();

            if (!blockInSlot.defaultBlockState().is(component.getValidSkins()))
                return ItemStack.EMPTY;

            textureData.put(component.getId(), blockInSlot);
        }

        final MaterialTextureData materialTextureData = new MaterialTextureData(textureData);

        final ItemStack result = new ItemStack(generatedBlock);
        materialTextureData.writeToItemStack(result);
        result.setCount(Math.max(components.size(), count));

        additionalTag.ifPresent(tag -> tag.getAllKeys().forEach(key -> result.getOrCreateTag().put(key, Objects.requireNonNull(tag.get(key)).copy())));

        return result;
    }

    @Override
    public boolean canCraftInDimensions(final int width, final int height)
    {
        return width * height <= IMateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount();
    }

    @Override
    public @NotNull ItemStack getResultItem(final RegistryAccess registryAccess)
    {
        final Block generatedBlock = getBlock();

        if (!(generatedBlock instanceof IMateriallyTexturedBlock))
            return ItemStack.EMPTY;

        final ItemStack result = new ItemStack(generatedBlock);
        additionalTag.ifPresent(tag -> tag.getAllKeys().forEach(key -> result.getOrCreateTag().put(key, Objects.requireNonNull(tag.get(key)).copy())));

        return result;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer()
    {
        return ModRecipeSerializers.ARCHITECTS_CUTTER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType()
    {
        return ModRecipeTypes.ARCHITECTS_CUTTER.get();
    }

    public @NotNull Optional<CompoundTag> getAdditionalTag()
    {
        return additionalTag;
    }

    public int getCount()
    {
        return count;
    }
}
