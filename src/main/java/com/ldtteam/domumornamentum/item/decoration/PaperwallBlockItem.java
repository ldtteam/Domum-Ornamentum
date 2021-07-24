package com.ldtteam.domumornamentum.item.decoration;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.PaperWallBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.block.ICachedItemGroupBlock;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PaperwallBlockItem extends BlockItem
{
    private final PaperWallBlock paperWallBlock;

    public PaperwallBlockItem(final PaperWallBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.paperWallBlock = blockIn;
    }

    @NotNull
    @Override
    public ITextComponent getName(final ItemStack stack)
    {
        final CompoundNBT dataNbt = stack.getOrCreateTagElement("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        final IMateriallyTexturedBlockComponent centerComponent = paperWallBlock.getComponents().get(1);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(centerComponent.getId(), centerComponent.getDefault());
        final TranslationTextComponent centerBlockName = new TranslationTextComponent(centerBlock.getDescriptionId());

        return new TranslationTextComponent(Constants.MOD_ID + ".paperwall.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(
      @NotNull final ItemStack stack, @Nullable final World worldIn, @NotNull final List<ITextComponent> tooltip, @NotNull final ITooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        final CompoundNBT dataNbt = stack.getOrCreateTagElement("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        tooltip.add(new StringTextComponent(""));
        tooltip.add(new TranslationTextComponent(Constants.MOD_ID + ".paperwall.header"));
        final IMateriallyTexturedBlockComponent frameComponent = paperWallBlock.getComponents().get(0);
        final Block frameBlock = textureData.getTexturedComponents().getOrDefault(frameComponent.getId(), frameComponent.getDefault());
        final TranslationTextComponent frameBlockName = new TranslationTextComponent(frameBlock.getDescriptionId());
        tooltip.add(new TranslationTextComponent(Constants.MOD_ID + ".paperwall.frame.format", frameBlockName));
        final IMateriallyTexturedBlockComponent centerComponent = paperWallBlock.getComponents().get(1);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(centerComponent.getId(), centerComponent.getDefault());
        final TranslationTextComponent centerBlockName = new TranslationTextComponent(centerBlock.getDescriptionId());
        tooltip.add(new TranslationTextComponent(Constants.MOD_ID + ".paperwall.center.format", centerBlockName));

    }
}
