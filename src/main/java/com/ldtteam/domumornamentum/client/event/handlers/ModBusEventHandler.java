package com.ldtteam.domumornamentum.client.event.handlers;

import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.block.types.DoorType;
import com.ldtteam.domumornamentum.block.types.FancyDoorType;
import com.ldtteam.domumornamentum.block.types.FancyTrapdoorType;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.block.types.PostType;
import com.ldtteam.domumornamentum.client.model.block.MateriallyTexturedBlockStateModel;
import com.ldtteam.domumornamentum.client.model.item.MateriallyTexturedItemModel;
import com.ldtteam.domumornamentum.client.screens.ArchitectsCutterScreen;
import com.ldtteam.domumornamentum.container.ModContainerTypes;
import com.ldtteam.domumornamentum.shingles.ShingleHeightType;
import com.ldtteam.domumornamentum.util.Constants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterItemModelsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.ModelEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ModBusEventHandler
{
    @SubscribeEvent
    public static void onMenuScreensRegistry(final RegisterMenuScreensEvent event)
    {
        event.register(ModContainerTypes.ARCHITECTS_CUTTER.get(), ArchitectsCutterScreen::new);
    }

    @SubscribeEvent
    public static void onRegisterItemModels(final RegisterItemModelsEvent event)
    {
        event.register(Constants.resLocDO("materially_textured_item"), MateriallyTexturedItemModel.Unbaked.MAP_CODEC);
    }

    @SubscribeEvent
    public static void onModifyBakingResult(final ModelEvent.ModifyBakingResult event)
    {
        MateriallyTexturedBlockStateModel.install(event);
    }

    @SubscribeEvent
    public static void onFMLClientSetup(final FMLClientSetupEvent event)
    {
    }
}
