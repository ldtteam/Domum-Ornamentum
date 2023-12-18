package com.ldtteam.domumornamentum.item.vanilla;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.vanilla.FenceGateBlock;
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

public class FenceGateBlockItem extends BlockItem implements IDoItem
{
    private final FenceGateBlock fenceBlock;

    public FenceGateBlockItem(final FenceGateBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.fenceBlock = blockIn;
    }

    @Override
    public Component getName(final ItemStack stack)
    {
        final CompoundTag dataNbt = stack.getOrCreateTagElement("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        final IMateriallyTexturedBlockComponent coverComponent = fenceBlock.getComponents().get(0);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + ".fence-gate.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(@NotNull final ItemStack stack, @Nullable final Level worldIn, @NotNull final List<Component> tooltip, @NotNull final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));

        final CompoundTag dataNbt = stack.getOrCreateTagElement("textureData");
        MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        final IMateriallyTexturedBlockComponent component = fenceBlock.getComponents().get(0);
        final Block block = textureData.getTexturedComponents().getOrDefault(component.getId(), component.getDefault());
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.onlyone", Component.translatable(Constants.MOD_ID + ".desc.material", BlockUtils.getHoverName(block))));
    }

    @Override
    public ResourceLocation getGroup()
    {
        return new ResourceLocation(Constants.MOD_ID, "avanilla");
    }
}

