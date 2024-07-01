package com.ldtteam.domumornamentum;

import com.ldtteam.domumornamentum.api.DomumOrnamentumAPI;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.ModCreativeTabs;
import com.ldtteam.domumornamentum.component.ModDataComponents;
import com.ldtteam.domumornamentum.container.ModContainerTypes;
import com.ldtteam.domumornamentum.entity.block.ModBlockEntityTypes;
import com.ldtteam.domumornamentum.recipe.ModRecipeSerializers;
import com.ldtteam.domumornamentum.recipe.ModRecipeTypes;
import com.ldtteam.domumornamentum.util.Constants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Constants.MOD_ID)
public class DomumOrnamentum
{
    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID);

    public DomumOrnamentum(final FMLModContainer modContainer, final Dist dist)
    {
        final IEventBus modBus = modContainer.getEventBus();

        IDomumOrnamentumApi.Holder.setInstance(DomumOrnamentumAPI.getInstance());
        ModBlocks.BLOCKS.register(modBus);
        ModBlocks.ITEMS.register(modBus);
        ModBlockEntityTypes.BLOCK_ENTITIES.register(modBus);
        ModContainerTypes.CONTAINERS.register(modBus);
        ModRecipeTypes.RECIPES.register(modBus);
        ModRecipeSerializers.SERIALIZERS.register(modBus);
        ModCreativeTabs.TAB_REG.register(modBus);
        ModDataComponents.REGISTRY.register(modBus);
    }
}
