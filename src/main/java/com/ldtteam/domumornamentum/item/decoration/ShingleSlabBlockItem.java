package com.ldtteam.domumornamentum.item.decoration;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.ShingleSlabBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShingleSlabBlockItem extends BlockItem
{
    private final ShingleSlabBlock shingleBlock;

    public ShingleSlabBlockItem(final ShingleSlabBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.shingleBlock = blockIn;
    }

    @Override
    public ITextComponent getName(final ItemStack stack)
    {
        final CompoundNBT dataNbt = stack.getOrCreateTagElement("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        final IMateriallyTexturedBlockComponent coverComponent = shingleBlock.getComponents().get(0);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final TranslationTextComponent centerBlockName = new TranslationTextComponent(centerBlock.getDescriptionId());

        return new TranslationTextComponent(Constants.MOD_ID + ".shingle_slab.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(
      final ItemStack stack, @Nullable final World worldIn, final List<ITextComponent> tooltip, final ITooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        final CompoundNBT dataNbt = stack.getOrCreateTagElement("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        final IMateriallyTexturedBlockComponent supportComponent = shingleBlock.getComponents().get(1);
        final Block supportBlock = textureData.getTexturedComponents().getOrDefault(supportComponent.getId(), supportComponent.getDefault());
        final TranslationTextComponent supportBlockName = new TranslationTextComponent(supportBlock.getDescriptionId());
        tooltip.add(new TranslationTextComponent(Constants.MOD_ID + ".shingle_slab.support.format", supportBlockName));

        final IMateriallyTexturedBlockComponent coverComponent = shingleBlock.getComponents().get(2);
        final Block coverBlock = textureData.getTexturedComponents().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final TranslationTextComponent coverBlockName = new TranslationTextComponent(coverBlock.getDescriptionId());
        tooltip.add(new TranslationTextComponent(Constants.MOD_ID + ".shingle_slab.cover.format", coverBlockName));
    }
}
