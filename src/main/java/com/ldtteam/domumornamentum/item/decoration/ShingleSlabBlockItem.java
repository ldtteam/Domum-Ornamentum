package com.ldtteam.domumornamentum.item.decoration;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.ShingleSlabBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class ShingleSlabBlockItem extends BlockItem
{
    private final ShingleSlabBlock shingleBlock;

    public ShingleSlabBlockItem(final ShingleSlabBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.shingleBlock = blockIn;
    }

    @Override
    public Component getName(final ItemStack stack)
    {
        final CompoundTag dataNbt = stack.getOrCreateTagElement("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        final IMateriallyTexturedBlockComponent coverComponent = shingleBlock.getComponents().get(0);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final TranslatableComponent centerBlockName = new TranslatableComponent(centerBlock.getDescriptionId());

        return new TranslatableComponent(Constants.MOD_ID + ".shingle_slab.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(
      final ItemStack stack, @Nullable final Level worldIn, final List<Component> tooltip, final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        final CompoundTag dataNbt = stack.getOrCreateTagElement("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        final IMateriallyTexturedBlockComponent supportComponent = shingleBlock.getComponents().get(1);
        final Block supportBlock = textureData.getTexturedComponents().getOrDefault(supportComponent.getId(), supportComponent.getDefault());
        final TranslatableComponent supportBlockName = new TranslatableComponent(supportBlock.getDescriptionId());
        tooltip.add(new TranslatableComponent(Constants.MOD_ID + ".shingle_slab.support.format", supportBlockName));

        final IMateriallyTexturedBlockComponent coverComponent = shingleBlock.getComponents().get(2);
        final Block coverBlock = textureData.getTexturedComponents().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final TranslatableComponent coverBlockName = new TranslatableComponent(coverBlock.getDescriptionId());
        tooltip.add(new TranslatableComponent(Constants.MOD_ID + ".shingle_slab.cover.format", coverBlockName));
    }
}
