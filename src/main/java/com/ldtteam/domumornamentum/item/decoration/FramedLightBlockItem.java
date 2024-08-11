package com.ldtteam.domumornamentum.item.decoration;

import com.google.common.collect.ImmutableList;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.FramedLightBlock;
import com.ldtteam.domumornamentum.block.types.FramedLightType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.component.ModDataComponents;
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
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FramedLightBlockItem extends BlockItemWithClientBePlacement implements IDoItem
{
    private final FramedLightBlock framedLightBlock;

    public FramedLightBlockItem(final FramedLightBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.framedLightBlock = blockIn;
    }

    @Override
    public Component getName(final ItemStack stack)
    {
        final MaterialTextureData textureData = stack.getOrDefault(ModDataComponents.TEXTURE_DATA, MaterialTextureData.EMPTY);

        final IMateriallyTexturedBlockComponent centerComponent = framedLightBlock.getComponents().get(1);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(centerComponent.getId(), centerComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + ".light.frame.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(@NotNull final ItemStack stack, final TooltipContext tooltipContext, @NotNull final List<Component> tooltip, @NotNull final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, tooltipContext, tooltip, flagIn);

        MaterialTextureData textureData = stack.getOrDefault(ModDataComponents.TEXTURE_DATA, MaterialTextureData.EMPTY);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        final FramedLightType type = framedLightBlock.getFramedLightType();
        tooltip.add(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable(Constants.MOD_ID + ".light.frame.header"));
        tooltip.add(Component.translatable(Constants.MOD_ID + ".light.frame.type.format", Component.translatable(Constants.MOD_ID + ".light.frame.type." + type.getName())));

        final IMateriallyTexturedBlockComponent frameComponent = framedLightBlock.getComponents().get(0);
        final Block frameBlock = textureData.getTexturedComponents().getOrDefault(frameComponent.getId(), frameComponent.getDefault());
        final Component frameBlockName = BlockUtils.getHoverName(frameBlock);
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.frame", Component.translatable(Constants.MOD_ID + ".desc.material", frameBlockName)));

        final IMateriallyTexturedBlockComponent centerComponent = framedLightBlock.getComponents().get(1);
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
        return Constants.resLocDO("ilight");
    }
}
