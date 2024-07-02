package com.ldtteam.domumornamentum.item.decoration;

import com.google.common.collect.ImmutableList;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.ShingleBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.component.ModDataComponents;
import com.ldtteam.domumornamentum.item.BlockItemWithClientBePlacement;
import com.ldtteam.domumornamentum.item.interfaces.IDoItem;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShingleBlockItem extends BlockItemWithClientBePlacement implements IDoItem
{
    private final ShingleBlock shingleBlock;

    public ShingleBlockItem(final ShingleBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.shingleBlock = blockIn;
    }

    @Override
    public @NotNull Component getName(final ItemStack stack)
    {
        final MaterialTextureData textureData = stack.getOrDefault(ModDataComponents.TEXTURE_DATA, MaterialTextureData.EMPTY);

        final IMateriallyTexturedBlockComponent coverComponent = shingleBlock.getComponents().get(0);
        final Block centerBlock = textureData.texturedComponents().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + ".shingle.name.format." + stack.getItem().getDescriptionId(), centerBlockName);
    }

    @Override
    public void appendHoverText(final ItemStack stack, final TooltipContext tooltipContext, final List<Component> tooltip, final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, tooltipContext, tooltip, flagIn);
        tooltip.add(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));

        MaterialTextureData textureData = stack.getOrDefault(ModDataComponents.TEXTURE_DATA, MaterialTextureData.EMPTY);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        final IMateriallyTexturedBlockComponent mainComponent = shingleBlock.getComponents().get(0);
        final Block mainBlock = textureData.texturedComponents().getOrDefault(mainComponent.getId(), mainComponent.getDefault());
        final Component mainBlockName = BlockUtils.getHoverName(mainBlock);
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.shingle", Component.translatable(Constants.MOD_ID + ".desc.material", mainBlockName)));

        final IMateriallyTexturedBlockComponent supportComponent = shingleBlock.getComponents().get(1);
        final Block supportBlock = textureData.getTexturedComponents().getOrDefault(supportComponent.getId(), supportComponent.getDefault());
        final Component supportBlockName = BlockUtils.getHoverName(supportBlock);
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.support", Component.translatable(Constants.MOD_ID + ".desc.material", supportBlockName)));
    }

    @Override
    public List<ResourceLocation> getInputIds()
    {
        return ImmutableList.of(Constants.resLocDO("shingle"), Constants.resLocDO("support"));
    }

    @Override
    public ResourceLocation getGroup()
    {
        return Constants.resLocDO("cshingle");
    }
}
