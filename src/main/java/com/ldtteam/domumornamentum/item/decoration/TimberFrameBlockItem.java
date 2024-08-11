package com.ldtteam.domumornamentum.item.decoration;

import com.google.common.collect.ImmutableList;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.TimberFrameBlock;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
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

import java.util.List;

public class TimberFrameBlockItem extends BlockItemWithClientBePlacement implements IDoItem
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
        final MaterialTextureData textureData = stack.getOrDefault(ModDataComponents.TEXTURE_DATA, MaterialTextureData.EMPTY);

        final IMateriallyTexturedBlockComponent centerComponent = timberFrameBlock.getComponents().get(1);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(centerComponent.getId(), centerComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + ".timber.frame.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(final ItemStack stack, final TooltipContext tooltipContext, final List<Component> tooltip, final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, tooltipContext, tooltip, flagIn);

        MaterialTextureData textureData = stack.getOrDefault(ModDataComponents.TEXTURE_DATA, MaterialTextureData.EMPTY);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        final TimberFrameType type = timberFrameBlock.getTimberFrameType();
        tooltip.add(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable(Constants.MOD_ID + ".timber.frame.header"));
        tooltip.add(Component.translatable(Constants.MOD_ID + ".timber.frame.type.format", Component.translatable(Constants.MOD_ID + ".timber.frame.type." + type.getName())));

        final IMateriallyTexturedBlockComponent frameComponent = timberFrameBlock.getComponents().get(0);
        final Block frameBlock = textureData.getTexturedComponents().getOrDefault(frameComponent.getId(), frameComponent.getDefault());
        final Component frameBlockName = BlockUtils.getHoverName(frameBlock);
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.frame", Component.translatable(Constants.MOD_ID + ".desc.material", frameBlockName)));

        final IMateriallyTexturedBlockComponent centerComponent = timberFrameBlock.getComponents().get(1);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(centerComponent.getId(), centerComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.center", Component.translatable(Constants.MOD_ID + ".desc.material", centerBlockName)));
    }

    @Override
    public List<ResourceLocation> getInputIds()
    {
        return ImmutableList.of(Constants.resLocDO("frame"), Constants.resLocDO("center"));
    }

    @Override
    public ResourceLocation getGroup()
    {
        return Constants.resLocDO("btimberframe");
    }
}
