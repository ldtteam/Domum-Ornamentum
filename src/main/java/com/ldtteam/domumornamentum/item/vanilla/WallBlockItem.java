package com.ldtteam.domumornamentum.item.vanilla;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import java.util.function.Consumer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.TooltipDisplay;
import com.ldtteam.domumornamentum.block.vanilla.WallBlock;
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

public class WallBlockItem extends SelfUpgradingBlockItem implements IDoItem
{
    private final WallBlock wallBlock;

    public WallBlockItem(final WallBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.wallBlock = blockIn;
    }

    @Override
    public @NonNull Component getName(final @NonNull ItemStack stack)
    {
        final MaterialTextureData textureData = MaterialTextureData.readFromItemStack(stack);

        final IMateriallyTexturedBlockComponent coverComponent = wallBlock.getComponents().get(0);
        final Block centerBlock = textureData.components().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + ".wall.name.format", centerBlockName);
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

        final IMateriallyTexturedBlockComponent component = wallBlock.getComponents().get(0);
        final Block block = textureData.components().getOrDefault(component.getId(), component.getDefault());
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".desc.onlyone", Component.translatable(Constants.MOD_ID + ".desc.material", BlockUtils.getHoverName(block))));
    }

    @Override
    public Identifier getGroup()
    {
        return Constants.resLocDO("avanilla");
    }
}

