package com.ldtteam.domumornamentum.item.decoration;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.TimberFrameBlock;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item.Properties;

public class TimberFrameBlockItem extends BlockItem
{
    private final TimberFrameBlock timberFrameBlock;

    public TimberFrameBlockItem(final TimberFrameBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.timberFrameBlock = blockIn;
    }

    @Override
    public ITextComponent getName(final ItemStack stack)
    {
        final CompoundNBT dataNbt = stack.getOrCreateTagElement("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        final IMateriallyTexturedBlockComponent centerComponent = timberFrameBlock.getComponents().get(1);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(centerComponent.getId(), centerComponent.getDefault());
        final TranslationTextComponent centerBlockName = new TranslationTextComponent(centerBlock.getDescriptionId());

        return new TranslationTextComponent(Constants.MOD_ID + ".timber.frame.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(
      final ItemStack stack, @Nullable final World worldIn, final List<ITextComponent> tooltip, final ITooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        final CompoundNBT dataNbt = stack.getOrCreateTagElement("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        final TimberFrameType type = timberFrameBlock.getTimberFrameType();
        tooltip.add(new StringTextComponent(""));
        tooltip.add(new TranslationTextComponent(Constants.MOD_ID + ".timber.frame.header"));
        tooltip.add(new TranslationTextComponent(Constants.MOD_ID + ".timber.frame.type.format", new TranslationTextComponent(Constants.MOD_ID + ".timber.frame.type." + type.getName())));

        final IMateriallyTexturedBlockComponent frameComponent = timberFrameBlock.getComponents().get(0);
        final Block frameBlock = textureData.getTexturedComponents().getOrDefault(frameComponent.getId(), frameComponent.getDefault());
        final TranslationTextComponent frameBlockName = new TranslationTextComponent(frameBlock.getDescriptionId());
        tooltip.add(new TranslationTextComponent(Constants.MOD_ID + ".timber.frame.block.format", frameBlockName));
    }
}
