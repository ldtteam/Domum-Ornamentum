package com.ldtteam.domumornamentum.item.decoration;

import com.google.common.collect.ImmutableList;
import java.util.function.Consumer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.TooltipDisplay;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.FancyTrapdoorBlock;
import com.ldtteam.domumornamentum.block.types.FancyTrapdoorType;
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
import org.jspecify.annotations.NonNull;

import java.util.List;

public class FancyTrapdoorBlockItem extends SelfUpgradingBlockItem implements IDoItem
{
    private final FancyTrapdoorBlock trapdoorBlock;

    public FancyTrapdoorBlockItem(final FancyTrapdoorBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.trapdoorBlock = blockIn;
    }

    @Override
    public @NonNull Component getName(final @NonNull ItemStack stack)
    {
        final MaterialTextureData textureData = MaterialTextureData.readFromItemStack(stack);

        final IMateriallyTexturedBlockComponent coverComponent = trapdoorBlock.getComponents().get(0);
        final Block centerBlock = textureData.components().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + ".fancytrapdoor.name.format", centerBlockName);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(final @NonNull ItemStack stack, final Item.@NonNull TooltipContext tooltipContext, final @NonNull TooltipDisplay display, final @NonNull Consumer<Component> tooltip, final @NonNull TooltipFlag flagIn)
    {
        super.appendHoverText(stack, tooltipContext, display, tooltip, flagIn);

        final FancyTrapdoorType trapdoorType = BlockUtils.getPropertyFromBlockStateTag(stack, FancyTrapdoorBlock.TYPE, FancyTrapdoorType.FULL);
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));
    tooltip.accept(Component.literal(""));
    tooltip.accept(Component.translatable(
          Constants.MOD_ID + ".fancytrapdoor.type.format",
          Component.translatable(
            Constants.MOD_ID + ".fancytrapdoor.type.name." + trapdoorType.getTranslationKeySuffix()
          )));

        MaterialTextureData textureData = MaterialTextureData.readFromItemStack(stack);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        final IMateriallyTexturedBlockComponent trapDoorComponent = trapdoorBlock.getComponents().get(0);
        final Block trapDoorBlock = textureData.components().getOrDefault(trapDoorComponent.getId(), trapDoorComponent.getDefault());
        final Component trapDoorBlockName = BlockUtils.getHoverName(trapDoorBlock);
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".desc.frame", Component.translatable(Constants.MOD_ID + ".desc.material", trapDoorBlockName)));
    tooltip.accept(Component.literal(""));
        final IMateriallyTexturedBlockComponent trapDoorFrameComponent = trapdoorBlock.getComponents().get(1);
        final Block trapDoorFrameBlock = textureData.components().getOrDefault(trapDoorFrameComponent.getId(), trapDoorFrameComponent.getDefault());
        final Component trapDoorFrameBlockName = BlockUtils.getHoverName(trapDoorFrameBlock);
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".desc.center", Component.translatable(Constants.MOD_ID + ".desc.material", trapDoorFrameBlockName)));
    }

    @Override
    public List<Identifier> getInputIds()
    {
        return ImmutableList.of(Constants.resLocDO("frame"), Constants.resLocDO("center"));
    }

    @Override
    public Identifier getGroup()
    {
        return Constants.resLocDO("etrapdoor");
    }
}

