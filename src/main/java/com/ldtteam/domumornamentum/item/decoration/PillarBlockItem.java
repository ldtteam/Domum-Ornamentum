package com.ldtteam.domumornamentum.item.decoration;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.PillarBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import net.minecraft.network.chat.Component;

import com.ldtteam.domumornamentum.item.BlockItemWithClientBePlacement;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PillarBlockItem extends BlockItemWithClientBePlacement
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
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromItemStack(stack);

        final IMateriallyTexturedBlockComponent columnComponent = pillarBlock.getComponents().get(0);
        final Block columnBlock = textureData.getTexturedComponents().getOrDefault(columnComponent.getId(), columnComponent.getDefault());
        final Component columnBlockName = BlockUtils.getHoverName(columnBlock);

        return Component.translatable(Constants.MOD_ID + "." + ((PillarBlock) getBlock()).getRegistryName().getPath() + ".name.format", columnBlockName);
    }

    @Override
    public void appendHoverText(@NotNull final ItemStack stack, @Nullable final Level worldIn, @NotNull final List<Component> tooltip, @NotNull final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        MaterialTextureData textureData = MaterialTextureData.deserializeFromItemStack(stack);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        tooltip.add(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable(Constants.MOD_ID + ".pillar.header"));

        final IMateriallyTexturedBlockComponent frameComponent = pillarBlock.getComponents().get(0);
        final Block frameBlock = textureData.getTexturedComponents().getOrDefault(frameComponent.getId(), frameComponent.getDefault());
        final Component frameBlockName = BlockUtils.getHoverName(frameBlock);
        tooltip.add(Component.translatable(Constants.MOD_ID + ".pillar.column.format", frameBlockName));


    }
}
