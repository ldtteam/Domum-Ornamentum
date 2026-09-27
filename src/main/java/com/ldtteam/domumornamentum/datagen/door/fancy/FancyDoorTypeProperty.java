package com.ldtteam.domumornamentum.datagen.door.fancy;

import com.ldtteam.domumornamentum.block.types.FancyDoorType;
import com.ldtteam.domumornamentum.block.decorative.FancyDoorBlock;
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
 * Custom RangeSelectItemModelProperty that returns the ordinal of the FancyDoorType
 * from a FancyDoorBlock's block state stored in an item stack.
 */
public class FancyDoorTypeProperty implements RangeSelectItemModelProperty {

    public static final FancyDoorTypeProperty INSTANCE = new FancyDoorTypeProperty();

    public static final MapCodec<FancyDoorTypeProperty> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        if (itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof FancyDoorBlock) {
            final FancyDoorType type = BlockUtils.getPropertyFromBlockStateTag(itemStack, FancyDoorBlock.TYPE, FancyDoorType.FULL);
            return type.ordinal();
        }
        return 0f;
    }

    @Override
    public @NonNull MapCodec<? extends RangeSelectItemModelProperty> type() {
        return CODEC;
    }
}
