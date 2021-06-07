package com.ldtteam.domumornamentum.recipe;

import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipe;
import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipeSerializer;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRecipeSerializers
{

    public static IRecipeSerializer<ArchitectsCutterRecipe> ARCHITECTS_CUTTER;

    private ModRecipeSerializers()
    {
        throw new IllegalStateException("Can not instantiate an instance of: ModTags. This is a utility class");
    }

    @SubscribeEvent
    public static void registerSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event)
    {
        final IForgeRegistry<IRecipeSerializer<?>> registry = event.getRegistry();

        ARCHITECTS_CUTTER = new ArchitectsCutterRecipeSerializer();
        registry.register(ARCHITECTS_CUTTER.setRegistryName(new ResourceLocation(Constants.MOD_ID, "architects_cutter")));
    }
}
