package com.ldtteam.domumornamentum.item.decoration;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.FancyTrapdoorBlock;
import com.ldtteam.domumornamentum.block.types.FancyTrapdoorType;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.block.vanilla.TrapdoorBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FancyTrapdoorBlockItem extends BlockItem
{
    private final FancyTrapdoorBlock trapdoorBlock;

    public FancyTrapdoorBlockItem(final FancyTrapdoorBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.trapdoorBlock = blockIn;
    }

    @Override
    public Component getName(final ItemStack stack)
    {
        final CompoundTag dataNbt = stack.getOrCreateTagElement("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        final IMateriallyTexturedBlockComponent coverComponent = trapdoorBlock.getComponents().get(0);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return new TranslatableComponent(Constants.MOD_ID + ".trapdoor.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(final ItemStack stack, @Nullable final Level worldIn, final List<Component> tooltip, final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        FancyTrapdoorType trapdoorType;
        try {
            if (stack.getOrCreateTag().contains("type"))
                trapdoorType = FancyTrapdoorType.valueOf(stack.getOrCreateTag().getString("type").toUpperCase());
            else
                trapdoorType = FancyTrapdoorType.FULL;
        } catch (Exception ex) {
            trapdoorType = FancyTrapdoorType.FULL;
        }

        tooltip.add(new TranslatableComponent(Constants.MOD_ID + ".origin.tooltip"));
        tooltip.add(new TextComponent(""));
        tooltip.add(new TranslatableComponent(
          Constants.MOD_ID + ".trapdoor.fancy.type.format",
          new TranslatableComponent(
            Constants.MOD_ID + ".trapdoor.fancy.type.name." + trapdoorType.getTranslationKeySuffix()
          )
      ));
    }
}

