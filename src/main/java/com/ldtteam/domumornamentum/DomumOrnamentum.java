package com.ldtteam.domumornamentum;

import com.ldtteam.domumornamentum.api.DomumOrnamentumAPI;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.ModCreativeTabs;
import com.ldtteam.domumornamentum.container.ModContainerTypes;
import com.ldtteam.domumornamentum.entity.block.ModBlockEntityTypes;
import com.ldtteam.domumornamentum.recipe.ModRecipeSerializers;
import com.ldtteam.domumornamentum.recipe.ModRecipeTypes;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class DomumOrnamentum
{
    public DomumOrnamentum()
    {
        IDomumOrnamentumApi.Holder.setInstance(DomumOrnamentumAPI.getInstance());
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlockEntityTypes.BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModContainerTypes.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModRecipeTypes.RECIPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModRecipeSerializers.SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModCreativeTabs.TAB_REG.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
