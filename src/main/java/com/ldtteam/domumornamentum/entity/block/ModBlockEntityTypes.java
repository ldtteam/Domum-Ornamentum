package com.ldtteam.domumornamentum.entity.block;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Class to create the modBlocks.
 * References to the blocks can be made here
 */
public final class ModBlockEntityTypes
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Constants.MOD_ID);

    @SuppressWarnings({"SuspiciousToArrayCall", "ConstantConditions"}) //Not really true.
    public static RegistryObject<BlockEntityType<BlockEntity>> MATERIALLY_TEXTURED = BLOCK_ENTITIES.register(Constants.BlockEntityTypes.MATERIALLY_RETEXTURABLE,
      () -> BlockEntityType.Builder.of((BlockEntityType.BlockEntitySupplier<BlockEntity>) MateriallyTexturedBlockEntity::new,
        ForgeRegistries.BLOCKS.getValues().stream().filter(IMateriallyTexturedBlock.class::isInstance).toArray(Block[]::new)
      ).build(null)
    );

    /**
     * Private constructor to hide the implicit public one.
     */
    private ModBlockEntityTypes()
    {
    }
}
