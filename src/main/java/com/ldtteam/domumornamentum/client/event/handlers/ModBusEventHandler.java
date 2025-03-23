package com.ldtteam.domumornamentum.client.event.handlers;

import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.block.types.*;
import com.ldtteam.domumornamentum.client.screens.ArchitectsCutterScreen;
import com.ldtteam.domumornamentum.container.ModContainerTypes;
import com.ldtteam.domumornamentum.shingles.ShingleHeightType;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
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
          (itemStack, clientLevel, livingEntity, i) -> handleDoorTypeOverride(itemStack)));
        event.enqueueWork(() -> ItemProperties.register(IModBlocks.getInstance().getFancyDoor().asItem(), new ResourceLocation(Constants.DOOR_MODEL_OVERRIDE),
          (itemStack, clientLevel, livingEntity, i) -> handleFancyDoorTypeOverride(itemStack)));
        event.enqueueWork(() -> ItemProperties.register(IModBlocks.getInstance().getFancyTrapdoor().asItem(), new ResourceLocation(Constants.TRAPDOOR_MODEL_OVERRIDE),
          (itemStack, clientLevel, livingEntity, i) -> handleFancyTrapdoorTypeOverride(itemStack)));
        event.enqueueWork(() -> ItemProperties.register(IModBlocks.getInstance().getPanel().asItem(), new ResourceLocation(Constants.TRAPDOOR_MODEL_OVERRIDE),
          (itemStack, clientLevel, livingEntity, i) -> handleStaticTrapdoorTypeOverride(itemStack)));
        event.enqueueWork(() -> ItemProperties.register(IModBlocks.getInstance().getPost().asItem(), new ResourceLocation(Constants.POST_MODEL_OVERRIDE),
                (itemStack, clientLevel, livingEntity, i) -> handlePostTypeOverride(itemStack)));

        event.enqueueWork(() -> MenuScreens.register(
          ModContainerTypes.ARCHITECTS_CUTTER.get(),
          ArchitectsCutterScreen::new
        ));

        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getArchitectsCutter(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getStandingBarrel(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getLayingBarrel(), RenderType.cutout());

            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getShingleSlab(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getPaperWall(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getFence(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getFenceGate(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getSlab(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getStair(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getWall(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getFancyDoor(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getFancyTrapdoor(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getTrapdoor(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getDoor(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getPanel(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getPost(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getCornerPanel(), RenderType.translucent());

            for (final ShingleHeightType heightType : ShingleHeightType.values())
            {
                ItemBlockRenderTypes.setRenderLayer(IModBlocks.getInstance().getShingle(heightType), RenderType.translucent());
            }

            IModBlocks.getInstance().getFloatingCarpets().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout()));
            IModBlocks.getInstance().getTimberFrames().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, RenderType.translucent()));
            IModBlocks.getInstance().getAllBrickBlocks().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, RenderType.solid()));
            IModBlocks.getInstance().getExtraTopBlocks().forEach(b -> ItemBlockRenderTypes.setRenderLayer(b, ((ExtraBlock) b).getType().isTranslucent() ?  RenderType.translucent() : RenderType.solid()));
        });
    }

    private static float handleDoorTypeOverride(ItemStack itemStack)
    {
        if (!itemStack.getOrCreateTag().contains("type"))
        {
            return 0f;
        }

        DoorType doorType;
        try
        {
            doorType = DoorType.valueOf(itemStack.getOrCreateTag().getString("type").toUpperCase());
        }
        catch (Exception ex)
        {
            doorType = DoorType.FULL;
        }

        return doorType.ordinal();
    }

    private static float handleFancyDoorTypeOverride(ItemStack itemStack)
    {
        if (!itemStack.getOrCreateTag().contains("type"))
        {
            return 0f;
        }

        FancyDoorType doorType;
        try
        {
            doorType = FancyDoorType.valueOf(itemStack.getOrCreateTag().getString("type").toUpperCase());
        }
        catch (Exception ex)
        {
            doorType = FancyDoorType.FULL;
        }

        return doorType.ordinal();
    }

    private static float handleFancyTrapdoorTypeOverride(ItemStack itemStack)
    {
        if (!itemStack.getOrCreateTag().contains("type"))
        {
            return 0f;
        }

        FancyTrapdoorType doorType;
        try
        {
            doorType = FancyTrapdoorType.valueOf(itemStack.getOrCreateTag().getString("type").toUpperCase());
        }
        catch (Exception ex)
        {
            doorType = FancyTrapdoorType.FULL;
        }

        return doorType.ordinal();
    }

    private static float handleStaticTrapdoorTypeOverride(ItemStack itemStack)
    {
        if (!itemStack.getOrCreateTag().contains("type"))
        {
            return 0f;
        }

        TrapdoorType doorType;
        try
        {
            doorType = TrapdoorType.valueOf(itemStack.getOrCreateTag().getString("type").toUpperCase());
        }
        catch (Exception ex)
        {
            doorType = TrapdoorType.FULL;
        }

        return doorType.ordinal();
    }

    private static float handlePostTypeOverride(ItemStack itemStack)
    {
        if (!itemStack.getOrCreateTag().contains("type"))
        {
            return 0f;
        }

        PostType postType;
        try
        {
            postType = PostType.valueOf(itemStack.getOrCreateTag().getString("type").toUpperCase());
        }
        catch (Exception ex)
        {
            postType = PostType.PLAIN;
        }

        return postType.ordinal();
    }
}