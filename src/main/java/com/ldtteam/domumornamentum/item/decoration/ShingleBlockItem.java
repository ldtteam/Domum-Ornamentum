package com.ldtteam.domumornamentum.item.decoration;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.BlockShingle;
import com.ldtteam.domumornamentum.block.decorative.TimberFrameBlock;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShingleBlockItem extends BlockItem
{
    private final BlockShingle blockShingle;

    public ShingleBlockItem(final BlockShingle blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.blockShingle = blockIn;
    }

    @Override
    public ITextComponent getDisplayName(final ItemStack stack)
    {
        final CompoundNBT dataNbt = stack.getOrCreateChildTag("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        final IMateriallyTexturedBlockComponent coverComponent = blockShingle.getComponents().get(0);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final TranslationTextComponent centerBlockName = new TranslationTextComponent(centerBlock.getTranslationKey());

        return new TranslationTextComponent(Constants.MOD_ID + ".shingle.name.format", centerBlockName);
    }

    @Override
    public void addInformation(
      final ItemStack stack, @Nullable final World worldIn, final List<ITextComponent> tooltip, final ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        final CompoundNBT dataNbt = stack.getOrCreateChildTag("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        final IMateriallyTexturedBlockComponent supportComponent = blockShingle.getComponents().get(1);
        final Block supportBlock = textureData.getTexturedComponents().getOrDefault(supportComponent.getId(), supportComponent.getDefault());
        final TranslationTextComponent supportBlockName = new TranslationTextComponent(supportBlock.getTranslationKey());
        tooltip.add(new TranslationTextComponent(Constants.MOD_ID + ".shingle.support.format", supportBlockName));
    }
}
