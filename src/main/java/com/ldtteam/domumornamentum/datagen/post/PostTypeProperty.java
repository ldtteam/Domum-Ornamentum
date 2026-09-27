package com.ldtteam.domumornamentum.datagen.post;

import com.ldtteam.domumornamentum.block.decorative.PostBlock;
import com.ldtteam.domumornamentum.block.types.PostType;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Custom RangeSelectItemModelProperty that returns the ordinal of the PostType
 * from a PostBlock's block state stored in an item stack.
 */
public class PostTypeProperty implements RangeSelectItemModelProperty {

    public static final PostTypeProperty INSTANCE = new PostTypeProperty();

    public static final MapCodec<PostTypeProperty> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        if (itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof PostBlock) {
            final PostType type = BlockUtils.getPropertyFromBlockStateTag(itemStack, PostBlock.TYPE, PostType.PLAIN);
            return type.ordinal();
        }
        return 0f;
    }

    @Override
    public @NonNull MapCodec<? extends RangeSelectItemModelProperty> type() {
        return CODEC;
    }
}
