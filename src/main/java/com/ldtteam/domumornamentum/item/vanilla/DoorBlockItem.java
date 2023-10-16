package com.ldtteam.domumornamentum.item.vanilla;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.types.DoorType;
import com.ldtteam.domumornamentum.block.vanilla.DoorBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.item.DoubleHighBlockItemWithClientBePlacement;
import com.ldtteam.domumornamentum.item.interfaces.IDoItem;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import net.minecraft.network.chat.Component;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromItemStack(stack);

        final IMateriallyTexturedBlockComponent coverComponent = doorBlock.getComponents().get(0);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + ".door.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(
      final @NotNull ItemStack stack, @Nullable final Level worldIn, final @NotNull List<Component> tooltip, final @NotNull TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        DoorType doorType;
        try
        {
            if (stack.getOrCreateTag().contains("type"))
            {
                doorType = DoorType.valueOf(stack.getOrCreateTag().getString("type").toUpperCase());
            }
            else
            {
                doorType = DoorType.FULL;
            }
        }
        catch (Exception ex)
        {
            doorType = DoorType.FULL;
        }

        tooltip.add(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable(
          Constants.MOD_ID + ".door.type.format",
          Component.translatable(
            Constants.MOD_ID + ".door.type.name." + doorType.getTranslationKeySuffix()
          )
        ));

        MaterialTextureData textureData = MaterialTextureData.deserializeFromItemStack(stack);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        final IMateriallyTexturedBlockComponent doorComponent = doorBlock.getComponents().get(0);
        final Block doorBlock = textureData.getTexturedComponents().getOrDefault(doorComponent.getId(), doorComponent.getDefault());
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.onlyone", Component.translatable(Constants.MOD_ID + ".desc.material", BlockUtils.getHoverName(doorBlock))));
    }

    @Override
    public ResourceLocation getGroup()
    {
        return new ResourceLocation(Constants.MOD_ID, "door");
    }
}
