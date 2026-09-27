package com.ldtteam.domumornamentum.item.decoration;

import com.google.common.collect.ImmutableList;
import java.util.function.Consumer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.TooltipDisplay;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.ShingleSlabBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.item.SelfUpgradingBlockItem;
import com.ldtteam.domumornamentum.item.interfaces.IDoItem;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class ShingleSlabBlockItem extends SelfUpgradingBlockItem implements IDoItem
{
    private final ShingleSlabBlock shingleBlock;

    public ShingleSlabBlockItem(final ShingleSlabBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.shingleBlock = blockIn;
    }

    @Override
    public @NotNull Component getName(final @NonNull ItemStack stack)
    {
        final MaterialTextureData textureData = MaterialTextureData.readFromItemStack(stack);

        final IMateriallyTexturedBlockComponent coverComponent = shingleBlock.getComponents().get(0);
        final Block centerBlock = textureData.components().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + ".shingle_slab.name.format", centerBlockName);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(@NotNull final ItemStack stack, final Item.@NonNull TooltipContext tooltipContext, final @NonNull TooltipDisplay display, final @NonNull Consumer<Component> tooltip, @NotNull final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, tooltipContext, display, tooltip, flagIn);
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));

        MaterialTextureData textureData = MaterialTextureData.readFromItemStack(stack);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        final IMateriallyTexturedBlockComponent mainComponent = shingleBlock.getComponents().get(0);
        final Block mainBlock = textureData.components().getOrDefault(mainComponent.getId(), mainComponent.getDefault());
        final Component mainBlockName = BlockUtils.getHoverName(mainBlock);
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".desc.shingle", Component.translatable(Constants.MOD_ID + ".desc.material", mainBlockName)));
        
        final IMateriallyTexturedBlockComponent supportComponent = shingleBlock.getComponents().get(1);
        final Block supportBlock = textureData.components().getOrDefault(supportComponent.getId(), supportComponent.getDefault());
        final Component supportBlockName = BlockUtils.getHoverName(supportBlock);
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".desc.support", Component.translatable(Constants.MOD_ID + ".desc.material", supportBlockName)));
    }

    @Override
    public List<Identifier> getInputIds()
    {
        return ImmutableList.of(Constants.resLocDO("shingle"), Constants.resLocDO("support"));
    }

    @Override
    public Identifier getGroup()
    {
        return Constants.resLocDO("cshingle");
    }
}
