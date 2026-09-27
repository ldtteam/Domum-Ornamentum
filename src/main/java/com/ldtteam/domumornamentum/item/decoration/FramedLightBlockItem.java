package com.ldtteam.domumornamentum.item.decoration;

import com.google.common.collect.ImmutableList;
import java.util.function.Consumer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.TooltipDisplay;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.FramedLightBlock;
import com.ldtteam.domumornamentum.block.types.FramedLightType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.item.SelfUpgradingBlockItem;
import com.ldtteam.domumornamentum.item.interfaces.IDoItem;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class FramedLightBlockItem extends SelfUpgradingBlockItem implements IDoItem
{
    private final FramedLightBlock framedLightBlock;

    public FramedLightBlockItem(final FramedLightBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.framedLightBlock = blockIn;
    }

    @Override
    public @NonNull Component getName(final @NonNull ItemStack stack)
    {
        final MaterialTextureData textureData = MaterialTextureData.readFromItemStack(stack);

        final IMateriallyTexturedBlockComponent centerComponent = framedLightBlock.getComponents().get(1);
        final Block centerBlock = textureData.components().getOrDefault(centerComponent.getId(), centerComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + ".light.frame.name.format", centerBlockName);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(@NotNull final ItemStack stack, final Item.@NonNull TooltipContext tooltipContext, final @NonNull TooltipDisplay display, final @NonNull Consumer<Component> tooltip, @NotNull final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, tooltipContext, display, tooltip, flagIn);

        MaterialTextureData textureData = MaterialTextureData.readFromItemStack(stack);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        final FramedLightType type = framedLightBlock.getFramedLightType();
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));
    tooltip.accept(Component.literal(""));
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".light.frame.header"));
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".light.frame.type.format", Component.translatable(Constants.MOD_ID + ".light.frame.type." + type.getName())));

        final IMateriallyTexturedBlockComponent frameComponent = framedLightBlock.getComponents().get(0);
        final Block frameBlock = textureData.components().getOrDefault(frameComponent.getId(), frameComponent.getDefault());
        final Component frameBlockName = BlockUtils.getHoverName(frameBlock);
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".desc.frame", Component.translatable(Constants.MOD_ID + ".desc.material", frameBlockName)));

        final IMateriallyTexturedBlockComponent centerComponent = framedLightBlock.getComponents().get(1);
        final Block centerBlock = textureData.components().getOrDefault(centerComponent.getId(), centerComponent.getDefault());
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
        return Constants.resLocDO("ilight");
    }
}
