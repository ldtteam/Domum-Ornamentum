package com.ldtteam.domumornamentum.item.vanilla;

import com.google.common.collect.ImmutableList;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.vanilla.StackedSlabBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.item.interfaces.IDoItem;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Item representation for the stacked slab block.
 * <p>
 * The item exposes two slab material inputs to the architect's cutter, formats
 * its name from both selected materials, and groups the recipe with the
 * vanilla block cutter recipes.
 */
public class StackedSlabBlockItem extends BlockItem implements IDoItem
{
    private final StackedSlabBlock stackedSlabBlock;

    /**
     * Creates the item wrapper for the given stacked slab block.
     *
     * @param blockIn the stacked slab block this item places.
     * @param builder the item properties.
     */
    public StackedSlabBlockItem(final StackedSlabBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.stackedSlabBlock = blockIn;
    }

    /**
     * Builds a display name from the selected top and bottom slab materials.
     */
    @Override
    public Component getName(final ItemStack stack)
    {
        final MaterialTextureData textureData = getTextureData(stack);
        final Block topBlock = getBlockForComponent(textureData, 0);
        final Block bottomBlock = getBlockForComponent(textureData, 1);

        return Component.translatable(
          Constants.MOD_ID + ".stacked_slab.name.format",
          BlockUtils.getHoverName(topBlock),
          BlockUtils.getHoverName(bottomBlock));
    }

    /**
     * Shows both selected slab materials in top-to-bottom order so the two-texture item
     * can still be identified clearly in inventories and cutter output slots.
     */
    @Override
    public void appendHoverText(@NotNull final ItemStack stack, @Nullable final Level worldIn, @NotNull final List<Component> tooltip, @NotNull final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));

        final MaterialTextureData textureData = getTextureData(stack);
        final Block topBlock = getBlockForComponent(textureData, 0);
        final Block bottomBlock = getBlockForComponent(textureData, 1);

        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.top", Component.translatable(Constants.MOD_ID + ".desc.material", BlockUtils.getHoverName(topBlock))));
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.bottom", Component.translatable(Constants.MOD_ID + ".desc.material", BlockUtils.getHoverName(bottomBlock))));
    }

    /**
     * Exposes two cutter inputs, one for each half of the stacked slab.
     */
    @Override
    public List<ResourceLocation> getInputIds()
    {
        return ImmutableList.of(new ResourceLocation(Constants.MOD_ID, "top"), new ResourceLocation(Constants.MOD_ID, "bottom"));
    }

    /**
     * Places this two-texture recipe in the vanilla block cutter category.
     */
    @Override
    public ResourceLocation getGroup()
    {
        return new ResourceLocation(Constants.MOD_ID, "avanilla");
    }

    /**
     * Reads texture data from the stack, generating defaults for display stacks
     * that do not already carry material NBT.
     */
    private MaterialTextureData getTextureData(final ItemStack stack)
    {
        final CompoundTag dataNbt = stack.getOrCreateTagElement("textureData");
        MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);
        if (textureData.isEmpty())
        {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        return textureData;
    }

    /**
     * Resolves the selected block material for a component, falling back to that
     * component's default material when the texture data is incomplete.
     */
    private Block getBlockForComponent(final MaterialTextureData textureData, final int componentIndex)
    {
        final IMateriallyTexturedBlockComponent component = stackedSlabBlock.getComponents().get(componentIndex);
        return textureData.getTexturedComponents().getOrDefault(component.getId(), component.getDefault());
    }
}
