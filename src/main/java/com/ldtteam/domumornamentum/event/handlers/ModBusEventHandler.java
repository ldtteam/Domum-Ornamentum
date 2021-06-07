package com.ldtteam.domumornamentum.event.handlers;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.datagen.frames.timber.*;
import com.ldtteam.domumornamentum.datagen.global.GlobalLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.global.MateriallyTexturedBlockRecipeProvider;
import com.ldtteam.domumornamentum.datagen.shingle.normal.*;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEventHandler
{

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        final LangJson langJson = new LangJson();

        // Timber Frames
        event.getGenerator().addProvider(new TimberFramesBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(new TimberFramesItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new TimberFramesBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new TimberFramesComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new TimberFramesLangEntryProvider(event.getGenerator(), langJson));

        //Shingles
        event.getGenerator().addProvider(new ShinglesBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(new ShinglesItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new ShinglesBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new ShinglesComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new ShinglesLangEntryProvider(event.getGenerator(), langJson));

        //Global
        event.getGenerator().addProvider(new GlobalLangEntryProvider(event.getGenerator(), langJson));
        event.getGenerator().addProvider(new MateriallyTexturedBlockRecipeProvider(event.getGenerator()));
    }
}
