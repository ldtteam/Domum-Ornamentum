package com.ldtteam.domumornamentum.entity.block;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.decorative.DynamicTimberFrameBlock;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Class to create the modBlocks.
 * References to the blocks can be made here
 */
public final class ModBlockEntityTypes
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends BlockEntity>> MATERIALLY_TEXTURED =
        BLOCK_ENTITIES.register(
            Constants.BlockEntityTypes.MATERIALLY_RETEXTURABLE.getPath(),
            () -> new BlockEntityType<>(
                MateriallyTexturedBlockEntity::new,
                BuiltInRegistries.BLOCK.stream().filter(IMateriallyTexturedBlock.class::isInstance).toArray(Block[]::new)
            )
        );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends BlockEntity>> DYNAMIC_TIMBERFRAME =
        BLOCK_ENTITIES.register(
            Constants.BlockEntityTypes.DYNAMIC_TIMBERFRAME.getPath(),
            () -> new BlockEntityType<>(
                DynamicTimberFrameBlockEntity::new,
                BuiltInRegistries.BLOCK.stream().filter(DynamicTimberFrameBlock.class::isInstance).toArray(Block[]::new)
            )
        );

    /**
     * Private constructor to hide the implicit public one.
     */
    private ModBlockEntityTypes()
    {
    }
}
