package com.ldtteam.domumornamentum.entity.block;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Class to create the modBlocks.
 * References to the blocks can be made here
 * <p>
 * We disabled the following finals since we are neither able to mark the items as final, nor do we want to provide public accessors.
 */
@SuppressWarnings({"squid:ClassVariableVisibilityCheck", "squid:S2444", "squid:S1444", "squid:S1820", "ConstantConditions"})
@ObjectHolder(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModBlockEntityTypes
{

    @ObjectHolder(Constants.BlockEntityTypes.MATERIALLY_RETEXTURABLE)
    @NotNull
    public static final BlockEntityType<MateriallyTexturedBlockEntity> MATERIALLY_TEXTURED = null;

    /**
     * Private constructor to hide the implicit public one.
     */
    private ModBlockEntityTypes()
    {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<BlockEntityType<?>> event)
    {
        event.getRegistry().register(
          BlockEntityType.Builder.of((BlockEntityType.BlockEntitySupplier<BlockEntity>) (pos, state) -> new MateriallyTexturedBlockEntity(MATERIALLY_TEXTURED, pos, state),
            ForgeRegistries.BLOCKS.getValues().stream().filter(IMateriallyTexturedBlock.class::isInstance).toArray(Block[]::new)
          ).build(null).setRegistryName(Constants.MOD_ID, Constants.BlockEntityTypes.MATERIALLY_RETEXTURABLE)
        );
    }
}
