package com.ldtteam.domumornamentum.item.decoration;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import java.util.function.Consumer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.TooltipDisplay;
import com.ldtteam.domumornamentum.block.decorative.PillarBlock;
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

public class PillarBlockItem extends SelfUpgradingBlockItem implements IDoItem
{
    private PillarBlock pillarBlock;
    
    public PillarBlockItem(final PillarBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.pillarBlock = blockIn;
    }
    @NotNull
    @Override
    public Component getName(final @NonNull ItemStack stack)
    {
        final MaterialTextureData textureData = MaterialTextureData.readFromItemStack(stack);

        final IMateriallyTexturedBlockComponent columnComponent = pillarBlock.getComponents().get(0);
        final Block columnBlock = textureData.components().getOrDefault(columnComponent.getId(), columnComponent.getDefault());
        final Component columnBlockName = BlockUtils.getHoverName(columnBlock);

        return Component.translatable(Constants.MOD_ID + "." + ((PillarBlock) getBlock()).getRegistryName().getPath() + ".name.format", columnBlockName);
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
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));
    tooltip.accept(Component.literal(""));
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".pillar.header"));

        final IMateriallyTexturedBlockComponent frameComponent = pillarBlock.getComponents().get(0);
        final Block frameBlock = textureData.components().getOrDefault(frameComponent.getId(), frameComponent.getDefault());
        final Component frameBlockName = BlockUtils.getHoverName(frameBlock);
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".desc.onlyone", Component.translatable(Constants.MOD_ID + ".desc.material", frameBlockName)));
    }

    @Override
    public Identifier getGroup()
    {
        return Constants.resLocDO("gpillar");
    }
}
