package com.ldtteam.domumornamentum.container;

import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipe;
import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipeSerializer;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContainerTypes
{

    public static ContainerType<ArchitectsCutterContainer> ARCHITECTS_CUTTER;

    private ModContainerTypes()
    {
        throw new IllegalStateException("Can not instantiate an instance of: ModContainerTypes. This is a utility class");
    }

    @SubscribeEvent
    public static void registerSerializers(RegistryEvent.Register<ContainerType<?>> event)
    {
        final IForgeRegistry<ContainerType<?>> registry = event.getRegistry();

        ARCHITECTS_CUTTER = new ContainerType<>(ArchitectsCutterContainer::new);
        registry.register(ARCHITECTS_CUTTER.setRegistryName(new ResourceLocation(Constants.MOD_ID, "architects_cutter")));
    }
}
