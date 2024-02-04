package com.ldtteam.domumornamentum.item.decoration;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.AllBrickStairBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.item.interfaces.IDoItem;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AllBrickStairBlockItem extends BlockItem implements IDoItem
{
    private AllBrickStairBlock allBrickStairBlock;

    public AllBrickStairBlockItem(final AllBrickStairBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.allBrickStairBlock = blockIn;
    }
    @NotNull
    @Override
    public Component getName(final ItemStack stack)
    {
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromItemStack(stack);

        final IMateriallyTexturedBlockComponent columnComponent = allBrickStairBlock.getComponents().get(0);
        final Block columnBlock = textureData.getTexturedComponents().getOrDefault(columnComponent.getId(), columnComponent.getDefault());
        final Component columnBlockName = BlockUtils.getHoverName(columnBlock);

        return Component.translatable(Constants.MOD_ID + "." + ((AllBrickStairBlock) getBlock()).getRegistryName().getPath() + ".name.format", columnBlockName);
    }

    @Override
    public void appendHoverText(@NotNull final ItemStack stack, @Nullable final Level worldIn, @NotNull final List<Component> tooltip, @NotNull final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        MaterialTextureData textureData = MaterialTextureData.deserializeFromItemStack(stack);
        if (textureData.isEmpty())
        {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        tooltip.add(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));

        final IMateriallyTexturedBlockComponent frameComponent = allBrickStairBlock.getComponents().get(0);
        final Block frameBlock = textureData.getTexturedComponents().getOrDefault(frameComponent.getId(), frameComponent.getDefault());
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.onlyone", Component.translatable(Constants.MOD_ID + ".desc.material", BlockUtils.getHoverName(frameBlock))));
    }

    @Override
    public ResourceLocation getGroup()
    {
        return new ResourceLocation(Constants.MOD_ID, "jbrick");
    }
}
