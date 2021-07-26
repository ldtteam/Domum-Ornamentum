package com.ldtteam.domumornamentum.entity.block;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
    public static final TileEntityType<MateriallyTexturedBlockEntity> MATERIALLY_TEXTURED_BLOCK_ENTITY_TILE_ENTITY_TYPE = null;

    /**
     * Private constructor to hide the implicit public one.
     */
    private ModBlockEntityTypes()
    {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<TileEntityType<?>> event)
    {
        event.getRegistry().register(
          TileEntityType.Builder.of(() -> new MateriallyTexturedBlockEntity(ModBlockEntityTypes.MATERIALLY_TEXTURED_BLOCK_ENTITY_TILE_ENTITY_TYPE),
            ModBlocks.getTimberFrames().toArray(new Block[0])
            ).build(null).setRegistryName(Constants.MOD_ID, Constants.BlockEntityTypes.MATERIALLY_RETEXTURABLE)
        );
    }
}
