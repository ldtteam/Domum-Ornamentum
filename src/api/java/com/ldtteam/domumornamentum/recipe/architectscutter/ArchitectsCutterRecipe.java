package com.ldtteam.domumornamentum.recipe.architectscutter;

import com.google.common.collect.Lists;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockManager;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.recipe.ModRecipeSerializers;
import com.ldtteam.domumornamentum.recipe.ModRecipeTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
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

public class ArchitectsCutterRecipe implements Recipe<ArchitectsCutterRecipeInput>
{
    public static final MapCodec<ArchitectsCutterRecipe> CODEC = RecordCodecBuilder.mapCodec(builder -> builder
        .group(BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("block").forGetter(rec -> rec.getBlock().builtInRegistryHolder()),
            ExtraCodecs.POSITIVE_INT.optionalFieldOf("count", 1).forGetter(ArchitectsCutterRecipe::getCount),
            DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(ArchitectsCutterRecipe::getComponentPatch))
        .apply(builder, ArchitectsCutterRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ArchitectsCutterRecipe> STREAM_CODEC = StreamCodec.composite(ResourceLocation.STREAM_CODEC,
            ArchitectsCutterRecipe::getBlockName,
            ByteBufCodecs.VAR_INT,
            ArchitectsCutterRecipe::getCount,
            DataComponentPatch.STREAM_CODEC,
            ArchitectsCutterRecipe::getComponentPatch,
            ArchitectsCutterRecipe::new);

    private final ResourceLocation blockName;
    private final int count;
    private final DataComponentPatch componentMap;

    public ArchitectsCutterRecipe(final ResourceLocation blockName, final int count, final DataComponentPatch componentMap)
    {
        this.blockName = blockName;
        this.count = count;
        this.componentMap = componentMap;
    }

    public ArchitectsCutterRecipe(final Holder<Block> block, final int count, final DataComponentPatch componentMap)
    {
        this(block.unwrapKey().orElseThrow().location(), count, componentMap);
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
    public boolean matches(final @NotNull ArchitectsCutterRecipeInput inv, final @NotNull Level worldIn)
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
    public @NotNull ItemStack assemble(final @NotNull ArchitectsCutterRecipeInput inv, final HolderLookup.Provider provider)
    {
        final Block generatedBlock = getBlock();

        if (!(generatedBlock instanceof final IMateriallyTexturedBlock materiallyTexturedBlock))
            return ItemStack.EMPTY;

        final List<IMateriallyTexturedBlockComponent> components = Lists.newArrayList(materiallyTexturedBlock.getComponents());

        final MaterialTextureData textureData = new MaterialTextureData();

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

            textureData.setComponent(component.getId(), blockInSlot);
        }

        final ItemStack result = new ItemStack(generatedBlock);
        textureData.writeToItemStack(result);
        result.setCount(Math.max(components.size(), count));

        result.applyComponents(componentMap);

        return result;
    }

    @Override
    public boolean canCraftInDimensions(final int width, final int height)
    {
        return width * height <= IMateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount();
    }

    @Override
    public @NotNull ItemStack getResultItem(final HolderLookup.Provider provider)
    {
        final Block generatedBlock = getBlock();

        if (!(generatedBlock instanceof IMateriallyTexturedBlock))
            return ItemStack.EMPTY;

        final ItemStack result = new ItemStack(generatedBlock);
        result.applyComponents(componentMap);

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

    public @NotNull DataComponentPatch getComponentPatch()
    {
        return componentMap;
    }

    public int getCount()
    {
        return count;
    }
}
