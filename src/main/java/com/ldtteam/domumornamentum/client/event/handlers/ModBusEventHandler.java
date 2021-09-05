package com.ldtteam.domumornamentum.client.event.handlers;

import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.block.types.DoorType;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.client.screens.ArchitectsCutterScreen;
import com.ldtteam.domumornamentum.container.ModContainerTypes;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModBusEventHandler
{

    @SubscribeEvent
    public static void onFMLClientSetup(final FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> ItemProperties.register(IModBlocks.getInstance().getTrapdoor().asItem(), new ResourceLocation(Constants.TRAPDOOR_MODEL_OVERRIDE),
          (itemStack, clientLevel, livingEntity, i) -> {
            if (!itemStack.getOrCreateTag().contains("type"))
                return 0f;

              TrapdoorType trapdoorType;
              try {
                  trapdoorType = TrapdoorType.valueOf(itemStack.getOrCreateTag().getString("type").toUpperCase());
              } catch (Exception ex) {
                  trapdoorType = TrapdoorType.FULL;
              }

              return trapdoorType.ordinal();
          }));
        event.enqueueWork(() -> ItemProperties.register(IModBlocks.getInstance().getDoor().asItem(), new ResourceLocation(Constants.DOOR_MODEL_OVERRIDE),
          (itemStack, clientLevel, livingEntity, i) -> {
              if (!itemStack.getOrCreateTag().contains("type"))
                  return 0f;

              DoorType doorType;
              try {
                  doorType = DoorType.valueOf(itemStack.getOrCreateTag().getString("type").toUpperCase());
              } catch (Exception ex) {
                  doorType = DoorType.FULL;
              }

              return doorType.ordinal();
          }));
        event.enqueueWork(() -> MenuScreens.register(
          ModContainerTypes.ARCHITECTS_CUTTER,
          ArchitectsCutterScreen::new
        ));
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getArchitectsCutter(), RenderType.cutout());

            IModBlocks.getInstance().getFloatingCarpets().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout()));
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getStandingBarrel(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getLayingBarrel(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getFence(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getFenceGate(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getSlab(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getStair(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getWall(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getTrapdoor(), RenderType.cutout());

            IModBlocks.getInstance().getTimberFrames().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, RenderType.translucent()));
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getShingle(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getShingleSlab(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getPaperWall(), RenderType.translucent());
        });

    }
}
