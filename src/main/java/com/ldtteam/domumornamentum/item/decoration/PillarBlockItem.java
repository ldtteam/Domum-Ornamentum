package com.ldtteam.domumornamentum.item.decoration;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.PillarBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PillarBlockItem extends BlockItem
{
    private PillarBlock pillarBlock;
    
    public PillarBlockItem(final PillarBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.pillarBlock = blockIn;
    }
    @NotNull
    @Override
    public Component getName(final ItemStack stack)
    {
        final CompoundTag dataNbt = stack.getOrCreateTagElement("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        final IMateriallyTexturedBlockComponent columnComponent = pillarBlock.getComponents().get(1);
        final Block columnBlock = textureData.getTexturedComponents().getOrDefault(columnComponent.getId(), columnComponent.getDefault());
        final Component columnBlockName = BlockUtils.getHoverName(columnBlock);

        return new TranslatableComponent(Constants.MOD_ID + ".pillar.name.format", columnBlockName);
    }

    @Override
    public void appendHoverText(@NotNull final ItemStack stack, @Nullable final Level worldIn, @NotNull final List<Component> tooltip, @NotNull final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        final CompoundTag dataNbt = stack.getOrCreateTagElement("textureData");
        MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        tooltip.add(new TranslatableComponent(Constants.MOD_ID + ".origin.tooltip"));
        tooltip.add(new TextComponent(""));
        tooltip.add(new TranslatableComponent(Constants.MOD_ID + ".pillar.header"));

        final IMateriallyTexturedBlockComponent frameComponent = pillarBlock.getComponents().get(0);
        final Block frameBlock = textureData.getTexturedComponents().getOrDefault(frameComponent.getId(), frameComponent.getDefault());
        final Component frameBlockName = BlockUtils.getHoverName(frameBlock);
        tooltip.add(new TranslatableComponent(Constants.MOD_ID + ".pillar.capital.format", frameBlockName));

        final IMateriallyTexturedBlockComponent columnComponent = pillarBlock.getComponents().get(1);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(columnComponent.getId(), columnComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);
        tooltip.add(new TranslatableComponent(Constants.MOD_ID + ".pillar.column.format", centerBlockName));

    }
}
