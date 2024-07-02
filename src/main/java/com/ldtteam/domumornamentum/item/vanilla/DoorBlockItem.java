package com.ldtteam.domumornamentum.item.vanilla;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.types.DoorType;
import com.ldtteam.domumornamentum.block.vanilla.DoorBlock;
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

public class DoorBlockItem extends DoubleHighBlockItemWithClientBePlacement implements IDoItem
{
    private final DoorBlock doorBlock;

    public DoorBlockItem(final DoorBlock blockIn, final Properties builder)
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

        return Component.translatable(Constants.MOD_ID + ".door.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(@NotNull final ItemStack stack, final TooltipContext tooltipContext, @NotNull final List<Component> tooltip, @NotNull final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, tooltipContext, tooltip, flagIn);

        final DoorType doorType = BlockUtils.getPropertyFromBlockStateTag(stack, DoorBlock.TYPE, DoorType.FULL);

        tooltip.add(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable(
          Constants.MOD_ID + ".door.type.format",
          Component.translatable(
            Constants.MOD_ID + ".door.type.name." + doorType.getTranslationKeySuffix()
          )
        ));

        MaterialTextureData textureData = stack.getOrDefault(ModDataComponents.TEXTURE_DATA, MaterialTextureData.EMPTY);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        final IMateriallyTexturedBlockComponent doorComponent = doorBlock.getComponents().get(0);
        final Block doorBlock = textureData.texturedComponents().getOrDefault(doorComponent.getId(), doorComponent.getDefault());
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.onlyone", Component.translatable(Constants.MOD_ID + ".desc.material", BlockUtils.getHoverName(doorBlock))));
    }

    @Override
    public ResourceLocation getGroup()
    {
        return Constants.resLocDO("ddoor");
    }
}
