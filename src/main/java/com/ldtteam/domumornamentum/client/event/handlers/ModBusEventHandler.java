package com.ldtteam.domumornamentum.client.event.handlers;

import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.block.types.DoorType;
import com.ldtteam.domumornamentum.block.types.FancyDoorType;
import com.ldtteam.domumornamentum.block.types.FancyTrapdoorType;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.block.types.PostType;
import com.ldtteam.domumornamentum.client.screens.ArchitectsCutterScreen;
import com.ldtteam.domumornamentum.container.ModContainerTypes;
import com.ldtteam.domumornamentum.shingles.ShingleHeightType;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModBusEventHandler
{
    @SubscribeEvent
    public static void onMenuScreensRegistry(final RegisterMenuScreensEvent event)
    {
        event.register(ModContainerTypes.ARCHITECTS_CUTTER.get(), ArchitectsCutterScreen::new);
    }

    @SubscribeEvent
    public static void onFMLClientSetup(final FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> ItemProperties.register(IModBlocks.getInstance().getTrapdoor().asItem(), Constants.TRAPDOOR_MODEL_OVERRIDE,
          (itemStack, clientLevel, livingEntity, i) -> getTypeOrdinal(itemStack, TrapdoorType.class, TrapdoorType.FULL)));
        event.enqueueWork(() -> ItemProperties.register(IModBlocks.getInstance().getDoor().asItem(), Constants.DOOR_MODEL_OVERRIDE,
          (itemStack, clientLevel, livingEntity, i) -> getTypeOrdinal(itemStack, DoorType.class, DoorType.FULL)));
        event.enqueueWork(() -> ItemProperties.register(IModBlocks.getInstance().getFancyDoor().asItem(), Constants.DOOR_MODEL_OVERRIDE,
          (itemStack, clientLevel, livingEntity, i) -> getTypeOrdinal(itemStack, FancyDoorType.class, FancyDoorType.FULL)));
        event.enqueueWork(() -> ItemProperties.register(IModBlocks.getInstance().getFancyTrapdoor().asItem(), Constants.TRAPDOOR_MODEL_OVERRIDE,
          (itemStack, clientLevel, livingEntity, i) -> getTypeOrdinal(itemStack, FancyTrapdoorType.class, FancyTrapdoorType.FULL)));
        event.enqueueWork(() -> ItemProperties.register(IModBlocks.getInstance().getPanel().asItem(), Constants.TRAPDOOR_MODEL_OVERRIDE,
          (itemStack, clientLevel, livingEntity, i) -> getTypeOrdinal(itemStack, TrapdoorType.class, TrapdoorType.FULL)));
        event.enqueueWork(() -> ItemProperties.register(IModBlocks.getInstance().getPost().asItem(), Constants.POST_MODEL_OVERRIDE,
          (itemStack, clientLevel, livingEntity, i) -> getTypeOrdinal(itemStack, PostType.class, PostType.PLAIN)));

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

    private static <T extends Enum<T>> float getTypeOrdinal(final ItemStack itemStack, final Class<T> enumClass, final T defaultValue)
    {
        final String type = itemStack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY).properties().get(Constants.TYPE_BLOCK_PROPERTY);
        if (type == null)
        {
            return defaultValue.ordinal();
        }

        try
        {
            return Enum.valueOf(enumClass, type.toUpperCase()).ordinal();
        }
        catch (Exception e)
        {
            return defaultValue.ordinal();
        }
    }
}