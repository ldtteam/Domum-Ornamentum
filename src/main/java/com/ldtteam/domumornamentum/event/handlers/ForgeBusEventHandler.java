package com.ldtteam.domumornamentum.event.handlers;

import com.google.common.collect.Maps;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmlserverevents.FMLServerStartedEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeBusEventHandler
{

    private static final boolean ACTIVE = true;
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onServerStarted(final FMLServerStartedEvent event) {
        if (!ACTIVE)
            return;

        LOGGER.info("Server started.");
        LOGGER.info("Determining block data count:");
        final Map<ResourceLocation, Integer> dataCount = Maps.newHashMap();
        for (final Item value : ForgeRegistries.ITEMS.getValues())
        {
            if (Objects.requireNonNull(value.getRegistryName()).getNamespace().equals(Constants.MOD_ID)) {
                final NonNullList<ItemStack> stacks = NonNullList.create();
                value.fillItemCategory(Objects.requireNonNull(value.getItemCategory()), stacks);

                dataCount.put(value.getRegistryName(), dataCount.getOrDefault(value.getRegistryName(), 0) + stacks.size());
            }
        }
        dataCount.forEach((rl, count) -> {
            LOGGER.info("  > " + rl.toString() + " = " + count);
        });
        LOGGER.info("Total block count: " + dataCount.values().stream().mapToInt(i -> i).sum());
    }

}
