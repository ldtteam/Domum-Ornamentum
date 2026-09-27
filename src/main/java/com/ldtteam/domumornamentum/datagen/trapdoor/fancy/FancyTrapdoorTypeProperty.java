package com.ldtteam.domumornamentum.datagen.trapdoor.fancy;

import com.ldtteam.domumornamentum.block.decorative.FancyTrapdoorBlock;
import com.ldtteam.domumornamentum.block.types.FancyTrapdoorType;
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
 * Custom RangeSelectItemModelProperty that returns the ordinal of the FancyTrapdoorType
 * from a FancyTrapdoorBlock's block state stored in an item stack.
 */
public class FancyTrapdoorTypeProperty implements RangeSelectItemModelProperty {

    public static final FancyTrapdoorTypeProperty INSTANCE = new FancyTrapdoorTypeProperty();

    public static final MapCodec<FancyTrapdoorTypeProperty> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        if (itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof FancyTrapdoorBlock) {
            final FancyTrapdoorType type = BlockUtils.getPropertyFromBlockStateTag(itemStack, FancyTrapdoorBlock.TYPE, FancyTrapdoorType.FULL);
            return type.ordinal();
        }
        return 0f;
    }

    @Override
    public @NonNull MapCodec<? extends RangeSelectItemModelProperty> type() {
        return CODEC;
    }
}
