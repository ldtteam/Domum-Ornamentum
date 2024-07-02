package com.ldtteam.domumornamentum.item.vanilla;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.block.vanilla.TrapdoorBlock;
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

public class TrapdoorBlockItem extends BlockItemWithClientBePlacement implements IDoItem
{
    private final TrapdoorBlock trapdoorBlock;

    public TrapdoorBlockItem(final TrapdoorBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.trapdoorBlock = blockIn;
    }

    @Override
    public Component getName(final ItemStack stack)
    {
        final MaterialTextureData textureData = stack.getOrDefault(ModDataComponents.TEXTURE_DATA, MaterialTextureData.EMPTY);

        final IMateriallyTexturedBlockComponent coverComponent = trapdoorBlock.getComponents().get(0);
        final Block centerBlock = textureData.texturedComponents().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + ".trapdoor.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(final ItemStack stack, final TooltipContext tooltipContext, final List<Component> tooltip, final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, tooltipContext, tooltip, flagIn);

        final TrapdoorType trapdoorType = BlockUtils.getPropertyFromBlockStateTag(stack, TrapdoorBlock.TYPE, TrapdoorType.FULL);

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

        final IMateriallyTexturedBlockComponent trapDoorComponent = trapdoorBlock.getComponents().get(0);
        final Block trapDoorBlock = textureData.texturedComponents().getOrDefault(trapDoorComponent.getId(), trapDoorComponent.getDefault());
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.onlyone", Component.translatable(Constants.MOD_ID + ".desc.material", BlockUtils.getHoverName(trapDoorBlock))));
    }

    @Override
    public ResourceLocation getGroup()
    {
        return Constants.resLocDO("etrapdoor");
    }
}
