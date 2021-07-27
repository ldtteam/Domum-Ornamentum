package com.ldtteam.domumornamentum.recipe.architectscutter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.MateriallyTexturedBlockManager;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.recipe.ModRecipeSerializers;
import com.ldtteam.domumornamentum.recipe.ModRecipeTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.Container;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class ArchitectsCutterRecipe implements Recipe<Container>
{
    private final ResourceLocation blockName;

    public ArchitectsCutterRecipe(final ResourceLocation blockName) {this.blockName = blockName;}

    public ResourceLocation getBlockName()
    {
        return blockName;
    }

    @Override
    public boolean matches(final @NotNull Container inv, final @NotNull Level worldIn)
    {
        if (!ForgeRegistries.BLOCKS.containsKey(getBlockName()))
            return false;

        final Block generatedBlock = ForgeRegistries.BLOCKS.getValue(getBlockName());

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

            if (!component.getValidSkins().contains(blockInSlot))
                return false;
        }

        return true;
    }

    @Override
    public @NotNull ItemStack assemble(final @NotNull Container inv)
    {
        if (!ForgeRegistries.BLOCKS.containsKey(getBlockName()))
            return ItemStack.EMPTY;

        final Block generatedBlock = ForgeRegistries.BLOCKS.getValue(getBlockName());

        if (!(generatedBlock instanceof final IMateriallyTexturedBlock materiallyTexturedBlock))
            return ItemStack.EMPTY;

        final List<IMateriallyTexturedBlockComponent> components = Lists.newArrayList(materiallyTexturedBlock.getComponents());

        final Map<ResourceLocation, Block> textureData = Maps.newHashMap();

        for (int componentsIndex = 0; componentsIndex < components.size(); componentsIndex++)
        {
            final IMateriallyTexturedBlockComponent component = components.get(componentsIndex);
            final ItemStack itemStackInSlot = inv.getItem(componentsIndex);

            final Item item = itemStackInSlot.getItem();
            if (!(item instanceof final BlockItem blockItem))
                return ItemStack.EMPTY;

            final Block blockInSlot = blockItem.getBlock();

            if (!component.getValidSkins().contains(blockInSlot))
                return ItemStack.EMPTY;

            textureData.put(component.getId(), blockInSlot);
        }

        final MaterialTextureData materialTextureData = new MaterialTextureData(textureData);

        final CompoundTag textureNbt = materialTextureData.serializeNBT();

        final ItemStack result = new ItemStack(generatedBlock);
        result.getOrCreateTag().put("textureData", textureNbt);
        result.setCount(components.size());

        return result;
    }

    @Override
    public boolean canCraftInDimensions(final int width, final int height)
    {
        return width * height <= MateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount();
    }

    @Override
    public @NotNull ItemStack getResultItem()
    {
        if (!ForgeRegistries.BLOCKS.containsKey(getBlockName()))
            return ItemStack.EMPTY;

        final Block generatedBlock = ForgeRegistries.BLOCKS.getValue(getBlockName());

        if (!(generatedBlock instanceof IMateriallyTexturedBlock))
            return ItemStack.EMPTY;

        return new ItemStack(generatedBlock);
    }

    @Override
    public @NotNull ResourceLocation getId()
    {
        return getBlockName();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer()
    {
        return ModRecipeSerializers.ARCHITECTS_CUTTER;
    }

    @Override
    public @NotNull RecipeType<?> getType()
    {
        return ModRecipeTypes.ARCHITECTS_CUTTER;
    }
}
