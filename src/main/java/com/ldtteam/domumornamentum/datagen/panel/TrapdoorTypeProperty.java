package com.ldtteam.domumornamentum.datagen.panel;

import com.ldtteam.domumornamentum.block.decorative.PanelBlock;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
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
 * Custom RangeSelectItemModelProperty that returns the ordinal of the TrapdoorType
 * from a PanelBlock's block state stored in an item stack.
 */
public class TrapdoorTypeProperty implements RangeSelectItemModelProperty {

    public static final TrapdoorTypeProperty INSTANCE = new TrapdoorTypeProperty();

    public static final MapCodec<TrapdoorTypeProperty> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        if (itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof PanelBlock) {
            final TrapdoorType type = BlockUtils.getPropertyFromBlockStateTag(itemStack, PanelBlock.TYPE, TrapdoorType.FULL);
            return type.ordinal();
        }
        return 0f;
    }

    @Override
    public @NonNull MapCodec<? extends RangeSelectItemModelProperty> type() {
        return CODEC;
    }
}
