package com.ldtteam.domumornamentum.item.decoration;
import com.ldtteam.domumornamentum.block.types.PostType;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.PostBlock;
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
import org.jetbrains.annotations.Nullable;
import java.util.List;

/** Post block item
 * copied other types, renamed vars
 */
public class PostBlockItem extends BlockItem implements IDoItem
{
    private final PostBlock postBlock;

    public PostBlockItem(final PostBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.postBlock = blockIn;
    }

    @Override
    public Component getName(final ItemStack stack)
    {
        final CompoundTag dataNbt = stack.getOrCreateTagElement("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        final IMateriallyTexturedBlockComponent coverComponent = postBlock.getComponents().get(0);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + ".post.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(final ItemStack stack, @Nullable final Level worldIn, final List<Component> tooltip, final TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        PostType postType;
        try
        {
            if (stack.getOrCreateTag().contains("type"))
            {
                postType = PostType.valueOf(stack.getOrCreateTag().getString("type").toUpperCase());
            }
            else
            {
                postType = PostType.PLAIN;
            }
        }
        catch (Exception ex)
        {
            postType = PostType.PLAIN;
        }

        tooltip.add(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable(
          Constants.MOD_ID + ".post.type.format",
          Component.translatable(Constants.MOD_ID + ".post.type.name." + postType.getTranslationKeySuffix())
        ));

        final CompoundTag dataNbt = stack.getOrCreateTagElement("textureData");
        MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        final IMateriallyTexturedBlockComponent postComponent = postBlock.getComponents().get(0);
        final Block postBlock = textureData.getTexturedComponents().getOrDefault(postComponent.getId(), postComponent.getDefault());
        final Component postBlockName = BlockUtils.getHoverName(postBlock);
        tooltip.add(Component.translatable(Constants.MOD_ID + ".desc.onlyone", Component.translatable(Constants.MOD_ID + ".desc.material", postBlockName)));
    }

    @Override
    public ResourceLocation getGroup()
    {
        return new ResourceLocation(Constants.MOD_ID, "kpost");
    }
}

