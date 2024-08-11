package com.ldtteam.domumornamentum.item.decoration;

import com.google.common.collect.ImmutableList;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.FancyTrapdoorBlock;
import com.ldtteam.domumornamentum.block.types.FancyTrapdoorType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.item.BlockItemWithClientBePlacement;
import com.ldtteam.domumornamentum.item.interfaces.IDoItem;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import net.minecraft.network.chat.Component;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class FancyTrapdoorBlockItem extends BlockItemWithClientBePlacement implements IDoItem
{
    private final FancyTrapdoorBlock trapdoorBlock;

    public FancyTrapdoorBlockItem(final FancyTrapdoorBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.trapdoorBlock = blockIn;
    }

    @Override
    public Component getName(final ItemStack stack)
    {
        final MaterialTextureData textureData = MaterialTextureData.readFromItemStack(stack);

        final IMateriallyTexturedBlockComponent coverComponent = trapdoorBlock.getComponents().get(0);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + ".fancytrapdoor.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(final ItemStack stack, final TooltipContext tooltipContext, final List<Component> tooltip, final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, tooltipContext, tooltip, flagIn);

        final FancyTrapdoorType trapdoorType = BlockUtils.getPropertyFromBlockStateTag(stack, FancyTrapdoorBlock.TYPE, FancyTrapdoorType.FULL);

        tooltip.add(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable(
          Constants.MOD_ID + ".fancytrapdoor.type.format",
          Component.translatable(
            Constants.MOD_ID + ".fancytrapdoor.type.name." + trapdoorType.getTranslationKeySuffix()
          )));

        MaterialTextureData textureData = MaterialTextureData.readFromItemStack(stack);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        final IMateriallyTexturedBlockComponent trapDoorComponent = trapdoorBlock.getComponents().get(0);
        final Block trapDoorBlock = textureData.getTexturedComponents().getOrDefault(trapDoorComponent.getId(), trapDoorComponent.getDefault());
        final Component trapDoorBlockName = BlockUtils.getHoverName(trapDoorBlock);
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.frame", Component.translatable(Constants.MOD_ID + ".desc.material", trapDoorBlockName)));

        tooltip.add(Component.literal(""));
        final IMateriallyTexturedBlockComponent trapDoorFrameComponent = trapdoorBlock.getComponents().get(1);
        final Block trapDoorFrameBlock = textureData.getTexturedComponents().getOrDefault(trapDoorFrameComponent.getId(), trapDoorFrameComponent.getDefault());
        final Component trapDoorFrameBlockName = BlockUtils.getHoverName(trapDoorFrameBlock);
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.center", Component.translatable(Constants.MOD_ID + ".desc.material", trapDoorFrameBlockName)));
    }

    @Override
    public List<ResourceLocation> getInputIds()
    {
        return ImmutableList.of(Constants.resLocDO("frame"), Constants.resLocDO("center"));
    }

    @Override
    public ResourceLocation getGroup()
    {
        return Constants.resLocDO("etrapdoor");
    }
}

