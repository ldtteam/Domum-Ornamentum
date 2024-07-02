package com.ldtteam.domumornamentum.item.decoration;

import com.google.common.collect.ImmutableList;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.FancyDoorBlock;
import com.ldtteam.domumornamentum.block.types.FancyDoorType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.component.ModDataComponents;
import com.ldtteam.domumornamentum.item.DoubleHighBlockItemWithClientBePlacement;
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

public class FancyDoorBlockItem extends DoubleHighBlockItemWithClientBePlacement implements IDoItem
{
    private final FancyDoorBlock doorBlock;

    public FancyDoorBlockItem(final FancyDoorBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.doorBlock = blockIn;
    }

    @Override
    public @NotNull Component getName(final ItemStack stack)
    {
        final MaterialTextureData textureData = stack.getOrDefault(ModDataComponents.TEXTURE_DATA, MaterialTextureData.EMPTY);

        final IMateriallyTexturedBlockComponent coverComponent = doorBlock.getComponents().get(0);
        final Block centerBlock = textureData.texturedComponents().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + ".fancydoor.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(@NotNull final ItemStack stack, final TooltipContext tooltipContext, @NotNull final List<Component> tooltip, @NotNull final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, tooltipContext, tooltip, flagIn);

        final FancyDoorType doorType = BlockUtils.getPropertyFromBlockStateTag(stack, FancyDoorBlock.TYPE, FancyDoorType.FULL);

        tooltip.add(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable(
          Constants.MOD_ID + ".fancydoor.type.format",
          Component.translatable(
            Constants.MOD_ID + ".fancydoor.type.name." + doorType.getTranslationKeySuffix()
          )
        ));

        MaterialTextureData textureData = stack.getOrDefault(ModDataComponents.TEXTURE_DATA, MaterialTextureData.EMPTY);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        final IMateriallyTexturedBlockComponent trapDoorComponent = doorBlock.getComponents().get(0);
        final Block trapDoorBlock = textureData.texturedComponents().getOrDefault(trapDoorComponent.getId(), trapDoorComponent.getDefault());
        final Component trapDoorBlockName = BlockUtils.getHoverName(trapDoorBlock);
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.frame", Component.translatable(Constants.MOD_ID + ".desc.material", trapDoorBlockName)));

        final IMateriallyTexturedBlockComponent trapDoorFrameComponent = doorBlock.getComponents().get(1);
        final Block trapDoorFrameBlock = textureData.texturedComponents().getOrDefault(trapDoorFrameComponent.getId(), trapDoorFrameComponent.getDefault());
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
        return Constants.resLocDO("ddoor");
    }
}
