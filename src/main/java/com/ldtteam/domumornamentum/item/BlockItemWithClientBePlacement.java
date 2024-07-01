package com.ldtteam.domumornamentum.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import javax.annotation.Nullable;

/**
 * BlockItem which places blockEntity data also on client
 */
public class BlockItemWithClientBePlacement extends SelfUpgradingBlockItem
{
    public BlockItemWithClientBePlacement(final Block block, final Properties properties)
    {
        super(block, properties);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(final BlockPos pos,
        final Level level,
        @Nullable final Player player,
        final ItemStack itemStack,
        final BlockState blockState)
    {
        if (!level.isClientSide)
        {
            return super.updateCustomBlockEntityTag(pos, level, player, itemStack, blockState);
        }

        // INLINE: super call without side checks
        final CustomData beingPlacedTag = itemStack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY);
        final BlockEntity blockentity = level.getBlockEntity(pos);

        if (!beingPlacedTag.isEmpty() && blockentity != null)
        {
            return beingPlacedTag.loadInto(blockentity, level.registryAccess());
        }

        return false;
    }
}
