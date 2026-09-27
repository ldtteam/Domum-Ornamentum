package com.ldtteam.domumornamentum.item.decoration;
import com.ldtteam.domumornamentum.block.types.PostType;
import java.util.function.Consumer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.TooltipDisplay;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.PostBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.item.SelfUpgradingBlockItem;
import com.ldtteam.domumornamentum.item.interfaces.IDoItem;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

/** Post block item
 * copied other types, renamed vars
 */
public class PostBlockItem extends SelfUpgradingBlockItem implements IDoItem
{
    private final PostBlock postBlock;

    public PostBlockItem(final PostBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.postBlock = blockIn;
    }

    @Override
    public @NonNull Component getName(final @NonNull ItemStack stack)
    {
        final MaterialTextureData textureData = MaterialTextureData.readFromItemStack(stack);

        final IMateriallyTexturedBlockComponent coverComponent = postBlock.getComponents().get(0);
        final Block centerBlock = textureData.components().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return Component.translatable(Constants.MOD_ID + ".post.name.format", centerBlockName);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(final @NonNull ItemStack stack, final Item.@NonNull TooltipContext tooltipContext, final @NonNull TooltipDisplay display, final @NonNull Consumer<Component> tooltip, final @NonNull TooltipFlag flagIn)
    {
        super.appendHoverText(stack, tooltipContext, display, tooltip, flagIn);

        final PostType postType = BlockUtils.getPropertyFromBlockStateTag(stack, PostBlock.TYPE, PostType.PLAIN);
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".origin.tooltip"));
    tooltip.accept(Component.literal(""));
    tooltip.accept(Component.translatable(
          Constants.MOD_ID + ".post.type.format",
          Component.translatable(Constants.MOD_ID + ".post.type.name." + postType.getTranslationKeySuffix())
        ));

        MaterialTextureData textureData = MaterialTextureData.readFromItemStack(stack);
        if (textureData.isEmpty()) {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(stack);
        }

        final IMateriallyTexturedBlockComponent postComponent = postBlock.getComponents().get(0);
        final Block postBlock = textureData.components().getOrDefault(postComponent.getId(), postComponent.getDefault());
        final Component postBlockName = BlockUtils.getHoverName(postBlock);
    tooltip.accept(Component.translatable(Constants.MOD_ID + ".desc.onlyone", Component.translatable(Constants.MOD_ID + ".desc.material", postBlockName)));
    }

    @Override
    public Identifier getGroup()
    {
        return Constants.resLocDO("kpost");
    }

    @Override
    public boolean renderPreview()
    {
        return true;
    }
}

