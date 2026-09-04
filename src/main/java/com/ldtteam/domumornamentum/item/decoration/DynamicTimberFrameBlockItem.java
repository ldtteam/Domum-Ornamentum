package com.ldtteam.domumornamentum.item.decoration;

import com.google.common.collect.ImmutableList;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.DynamicTimberFrameBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.item.BlockItemWithClientBePlacement;
import com.ldtteam.domumornamentum.item.interfaces.IDoItem;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Consumer;

public class DynamicTimberFrameBlockItem extends BlockItemWithClientBePlacement implements IDoItem
{
    private final DynamicTimberFrameBlock timberFrameBlock;

    public DynamicTimberFrameBlockItem(final DynamicTimberFrameBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.timberFrameBlock = blockIn;
    }

    @Override
    public Component getName(final ItemStack stack)
    {
        final MaterialTextureData textureData = MaterialTextureData.readFromItemStack(stack);

        final IMateriallyTexturedBlockComponent centerComponent = timberFrameBlock.getComponents().get(1);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(centerComponent.getId(), centerComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + ".dynamic.frame.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(final ItemStack stack, final TooltipContext tooltipContext, final TooltipDisplay display, final Consumer<Component> tooltip, final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, tooltipContext, display, tooltip, flagIn);

        MaterialTextureData textureData = MaterialTextureData.readFromItemStack(stack);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        tooltip.accept(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));
        tooltip.accept(Component.literal(""));
        tooltip.accept(Component.translatable(Constants.MOD_ID + ".timber.frame.header"));

        final IMateriallyTexturedBlockComponent frameComponent = timberFrameBlock.getComponents().get(0);
        final Block frameBlock = textureData.getTexturedComponents().getOrDefault(frameComponent.getId(), frameComponent.getDefault());
        final Component frameBlockName = BlockUtils.getHoverName(frameBlock);
        tooltip.accept(Component.translatable(Constants.MOD_ID + ".desc.frame", Component.translatable(Constants.MOD_ID + ".desc.material", frameBlockName)));

        final IMateriallyTexturedBlockComponent centerComponent = timberFrameBlock.getComponents().get(1);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(centerComponent.getId(), centerComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);
        tooltip.accept(Component.translatable(Constants.MOD_ID + ".desc.center", Component.translatable(Constants.MOD_ID + ".desc.material", centerBlockName)));
    }

    @Override
    public List<Identifier> getInputIds()
    {
        return ImmutableList.of(Constants.resLocDO("frame"), Constants.resLocDO("center"));
    }

    @Override
    public Identifier getGroup()
    {
        return Constants.resLocDO("btimberframe");
    }
}
