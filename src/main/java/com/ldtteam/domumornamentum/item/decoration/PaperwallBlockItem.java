package com.ldtteam.domumornamentum.item.decoration;

import com.google.common.collect.ImmutableList;
import java.util.function.Consumer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.TooltipDisplay;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.PaperWallBlock;
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

public class PaperwallBlockItem extends SelfUpgradingBlockItem implements IDoItem
{
    private final PaperWallBlock paperWallBlock;

    public PaperwallBlockItem(final PaperWallBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.paperWallBlock = blockIn;
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public Component getName(final @NonNull ItemStack stack)
    {
        final MaterialTextureData textureData = MaterialTextureData.readFromItemStack(stack);

        final IMateriallyTexturedBlockComponent centerComponent = paperWallBlock.getComponents().get(1);
        final Block centerBlock = textureData.components().getOrDefault(centerComponent.getId(), centerComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + "."+ builtInRegistryHolder().key().identifier().getPath() +".name.format", centerBlockName);
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

        final String id = builtInRegistryHolder().key().identifier().getPath();
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));
    tooltip.accept(Component.literal(""));
    tooltip.accept(Component.translatable(Constants.MOD_ID + "." + id + ".header"));

        final IMateriallyTexturedBlockComponent frameComponent = paperWallBlock.getComponents().get(0);
        final Block frameBlock = textureData.components().getOrDefault(frameComponent.getId(), frameComponent.getDefault());
        final Component frameBlockName = BlockUtils.getHoverName(frameBlock);
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".desc.frame", Component.translatable(Constants.MOD_ID + ".desc.material", frameBlockName)));

        final IMateriallyTexturedBlockComponent centerComponent = paperWallBlock.getComponents().get(1);
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
        return Constants.resLocDO("hpaperwall");
    }
}
