package com.ldtteam.domumornamentum.container;

import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContainerTypes
{

    public static MenuType<ArchitectsCutterContainer> ARCHITECTS_CUTTER;

    private ModContainerTypes()
    {
        throw new IllegalStateException("Can not instantiate an instance of: ModContainerTypes. This is a utility class");
    }

    @SubscribeEvent
    public static void registerSerializers(RegistryEvent.Register<MenuType<?>> event)
    {
        final IForgeRegistry<MenuType<?>> registry = event.getRegistry();

        ARCHITECTS_CUTTER = new MenuType<>(ArchitectsCutterContainer::new);
        registry.register(ARCHITECTS_CUTTER.setRegistryName(new ResourceLocation(Constants.MOD_ID, "architects_cutter")));
    }
}
