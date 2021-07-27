package com.ldtteam.domumornamentum.item.decoration;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.TimberFrameBlock;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class TimberFrameBlockItem extends BlockItem
{
    private final TimberFrameBlock timberFrameBlock;

    public TimberFrameBlockItem(final TimberFrameBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.timberFrameBlock = blockIn;
    }

    @Override
    public Component getName(final ItemStack stack)
    {
        final CompoundTag dataNbt = stack.getOrCreateTagElement("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        final IMateriallyTexturedBlockComponent centerComponent = timberFrameBlock.getComponents().get(1);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(centerComponent.getId(), centerComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return new TranslatableComponent(Constants.MOD_ID + ".timber.frame.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(
      final ItemStack stack, @Nullable final Level worldIn, final List<Component> tooltip, final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        final CompoundTag dataNbt = stack.getOrCreateTagElement("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        final TimberFrameType type = timberFrameBlock.getTimberFrameType();
        tooltip.add(new TextComponent(""));
        tooltip.add(new TranslatableComponent(Constants.MOD_ID + ".timber.frame.header"));
        tooltip.add(new TranslatableComponent(Constants.MOD_ID + ".timber.frame.type.format", new TranslatableComponent(Constants.MOD_ID + ".timber.frame.type." + type.getName())));

        final IMateriallyTexturedBlockComponent frameComponent = timberFrameBlock.getComponents().get(0);
        final Block frameBlock = textureData.getTexturedComponents().getOrDefault(frameComponent.getId(), frameComponent.getDefault());
        final Component frameBlockName = BlockUtils.getHoverName(frameBlock);
        tooltip.add(new TranslatableComponent(Constants.MOD_ID + ".timber.frame.block.format", frameBlockName));
    }
}
