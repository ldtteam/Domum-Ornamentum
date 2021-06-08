package com.ldtteam.domumornamentum.recipe.architectscutter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.MateriallyTexturedBlockManager;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.recipe.ModRecipeSerializers;
import com.ldtteam.domumornamentum.recipe.ModRecipeTypes;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;

public class ArchitectsCutterRecipe implements IRecipe<IInventory>
{
    private final ResourceLocation blockName;

    public ArchitectsCutterRecipe(final ResourceLocation blockName) {this.blockName = blockName;}

    public ResourceLocation getBlockName()
    {
        return blockName;
    }

    @Override
    public boolean matches(final IInventory inv, final World worldIn)
    {
        if (!ForgeRegistries.BLOCKS.containsKey(getBlockName()))
            return false;

        final Block generatedBlock = ForgeRegistries.BLOCKS.getValue(getBlockName());

        if (!(generatedBlock instanceof IMateriallyTexturedBlock))
            return false;

        final IMateriallyTexturedBlock materiallyTexturedBlock = (IMateriallyTexturedBlock) generatedBlock;
        final List<IMateriallyTexturedBlockComponent> components = Lists.newArrayList(materiallyTexturedBlock.getComponents());
        for (int componentsIndex = 0; componentsIndex < components.size(); componentsIndex++)
        {
            final IMateriallyTexturedBlockComponent component = components.get(componentsIndex);
            final ItemStack itemStackInSlot = inv.getItem(componentsIndex);

            final Item item = itemStackInSlot.getItem();
            if (!(item instanceof BlockItem))
                return false;

            final BlockItem blockItem = (BlockItem) item;
            final Block blockInSlot = blockItem.getBlock();

            if (!component.getValidSkins().contains(blockInSlot))
                return false;
        }

        return true;
    }

    @Override
    public ItemStack assemble(final IInventory inv)
    {
        if (!ForgeRegistries.BLOCKS.containsKey(getBlockName()))
            return ItemStack.EMPTY;

        final Block generatedBlock = ForgeRegistries.BLOCKS.getValue(getBlockName());

        if (!(generatedBlock instanceof IMateriallyTexturedBlock))
            return ItemStack.EMPTY;

        final IMateriallyTexturedBlock materiallyTexturedBlock = (IMateriallyTexturedBlock) generatedBlock;
        final List<IMateriallyTexturedBlockComponent> components = Lists.newArrayList(materiallyTexturedBlock.getComponents());

        final Map<ResourceLocation, Block> textureData = Maps.newHashMap();

        for (int componentsIndex = 0; componentsIndex < components.size(); componentsIndex++)
        {
            final IMateriallyTexturedBlockComponent component = components.get(componentsIndex);
            final ItemStack itemStackInSlot = inv.getItem(componentsIndex);

            final Item item = itemStackInSlot.getItem();
            if (!(item instanceof BlockItem))
                return ItemStack.EMPTY;

            final BlockItem blockItem = (BlockItem) item;
            final Block blockInSlot = blockItem.getBlock();

            if (!component.getValidSkins().contains(blockInSlot))
                return ItemStack.EMPTY;

            textureData.put(component.getId(), blockInSlot);
        }

        final MaterialTextureData materialTextureData = new MaterialTextureData(textureData);

        final CompoundNBT textureNbt = materialTextureData.serializeNBT();

        final ItemStack result = new ItemStack(generatedBlock);
        result.getOrCreateTag().put("textureData", textureNbt);

        return result;
    }

    @Override
    public boolean canCraftInDimensions(final int width, final int height)
    {
        return width * height <= MateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount();
    }

    @Override
    public ItemStack getResultItem()
    {
        if (!ForgeRegistries.BLOCKS.containsKey(getBlockName()))
            return ItemStack.EMPTY;

        final Block generatedBlock = ForgeRegistries.BLOCKS.getValue(getBlockName());

        if (!(generatedBlock instanceof IMateriallyTexturedBlock))
            return ItemStack.EMPTY;

        return new ItemStack(generatedBlock);
    }

    @Override
    public ResourceLocation getId()
    {
        return getBlockName();
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return ModRecipeSerializers.ARCHITECTS_CUTTER;
    }

    @Override
    public IRecipeType<?> getType()
    {
        return ModRecipeTypes.ARCHITECTS_CUTTER;
    }
}
