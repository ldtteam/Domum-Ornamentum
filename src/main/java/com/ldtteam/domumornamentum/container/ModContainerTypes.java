package com.ldtteam.domumornamentum.container;

import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainerTypes
{
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Constants.MOD_ID);

    public static RegistryObject<MenuType<ArchitectsCutterContainer>> ARCHITECTS_CUTTER = CONTAINERS.register("architects_cutter", () -> new MenuType<>(ArchitectsCutterContainer::new));

    private ModContainerTypes()
    {
        throw new IllegalStateException("Can not instantiate an instance of: ModContainerTypes. This is a utility class");
    }
}
