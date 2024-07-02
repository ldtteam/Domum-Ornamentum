package com.ldtteam.domumornamentum.item.decoration;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.PanelBlock;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.component.ModDataComponents;
import com.ldtteam.domumornamentum.item.BlockItemWithClientBePlacement;
import com.ldtteam.domumornamentum.item.interfaces.IDoItem;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import net.minecraft.network.chat.Component;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class PanelBlockItem extends BlockItemWithClientBePlacement implements IDoItem
{
    private final PanelBlock panelBlock;

    public PanelBlockItem(final PanelBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.panelBlock = blockIn;
    }

    @Override
    public Component getName(final ItemStack stack)
    {
        final MaterialTextureData textureData = stack.getOrDefault(ModDataComponents.TEXTURE_DATA, MaterialTextureData.EMPTY);

        final IMateriallyTexturedBlockComponent coverComponent = panelBlock.getComponents().get(0);
        final Block centerBlock = textureData.texturedComponents().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + ".panel.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(final ItemStack stack, final TooltipContext tooltipContext, final List<Component> tooltip, final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, tooltipContext, tooltip, flagIn);

        final TrapdoorType trapdoorType = BlockUtils.getPropertyFromBlockStateTag(stack, PanelBlock.TYPE, TrapdoorType.FULL);

        tooltip.add(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable(
          Constants.MOD_ID + ".trapdoor.type.format",
          Component.translatable(Constants.MOD_ID + ".trapdoor.type.name." + trapdoorType.getTranslationKeySuffix())
        ));

        MaterialTextureData textureData = stack.getOrDefault(ModDataComponents.TEXTURE_DATA, MaterialTextureData.EMPTY);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        final IMateriallyTexturedBlockComponent trapDoorComponent = panelBlock.getComponents().get(0);
        final Block trapDoorBlock = textureData.texturedComponents().getOrDefault(trapDoorComponent.getId(), trapDoorComponent.getDefault());
        final Component trapDoorBlockName = BlockUtils.getHoverName(trapDoorBlock);
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.onlyone", Component.translatable(Constants.MOD_ID + ".desc.material", trapDoorBlockName)));
    }

    @Override
    public ResourceLocation getGroup()
    {
        return Constants.resLocDO("fpanel");
    }

    @Override
    public boolean renderPreview()
    {
        return true;
    }
}

