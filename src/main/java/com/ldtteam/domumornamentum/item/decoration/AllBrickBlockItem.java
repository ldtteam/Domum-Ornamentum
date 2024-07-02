package com.ldtteam.domumornamentum.item.decoration;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.AllBrickBlock;
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
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AllBrickBlockItem extends BlockItemWithClientBePlacement implements IDoItem
{
    private AllBrickBlock allBrickBlock;

    public AllBrickBlockItem(final AllBrickBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.allBrickBlock = blockIn;
    }
    @NotNull
    @Override
    public Component getName(final ItemStack stack)
    {
        final MaterialTextureData textureData = stack.getOrDefault(ModDataComponents.TEXTURE_DATA, MaterialTextureData.EMPTY);

        final IMateriallyTexturedBlockComponent columnComponent = allBrickBlock.getComponents().get(0);
        final Block columnBlock = textureData.texturedComponents().getOrDefault(columnComponent.getId(), columnComponent.getDefault());
        final Component columnBlockName = BlockUtils.getHoverName(columnBlock);

        return Component.translatable(Constants.MOD_ID + "." + ((AllBrickBlock) getBlock()).getRegistryName().getPath() + ".name.format", columnBlockName);
    }

    @Override
    public void appendHoverText(@NotNull final ItemStack stack, final TooltipContext tooltipContext, @NotNull final List<Component> tooltip, @NotNull final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, tooltipContext, tooltip, flagIn);

        MaterialTextureData textureData = stack.getOrDefault(ModDataComponents.TEXTURE_DATA, MaterialTextureData.EMPTY);
        if (textureData.isEmpty())
        {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        tooltip.add(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));

        final IMateriallyTexturedBlockComponent frameComponent = allBrickBlock.getComponents().get(0);
        final Block frameBlock = textureData.texturedComponents().getOrDefault(frameComponent.getId(), frameComponent.getDefault());
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.onlyone", Component.translatable(Constants.MOD_ID + ".desc.material", BlockUtils.getHoverName(frameBlock))));
    }

    @Override
    public ResourceLocation getGroup()
    {
        return Constants.resLocDO("jbrick");
    }
}
