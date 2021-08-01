package com.ldtteam.domumornamentum.event.handlers;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.datagen.bricks.*;
import com.ldtteam.domumornamentum.datagen.extra.*;
import com.ldtteam.domumornamentum.datagen.fence.*;
import com.ldtteam.domumornamentum.datagen.fencegate.*;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.*;
import com.ldtteam.domumornamentum.datagen.frames.timber.*;
import com.ldtteam.domumornamentum.datagen.global.GlobalLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalLootTableProvider;
import com.ldtteam.domumornamentum.datagen.global.MateriallyTexturedBlockRecipeProvider;
import com.ldtteam.domumornamentum.datagen.shingle.normal.*;
import com.ldtteam.domumornamentum.datagen.shingle.slab.*;
import com.ldtteam.domumornamentum.datagen.slab.*;
import com.ldtteam.domumornamentum.datagen.stair.*;
import com.ldtteam.domumornamentum.datagen.wall.paper.*;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.*;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEventHandler
{

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        final LangJson langJson = new LangJson();

        //Extra blocks
        event.getGenerator().addProvider(new ExtraBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(new ExtraItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new ExtraBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new ExtraRecipeProvider(event.getGenerator()));
        event.getGenerator().addProvider(new ExtraBlockTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new ExtraLangEntryProvider(event.getGenerator(), langJson));

        //Brick blocks
        event.getGenerator().addProvider(new BrickBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(new BrickItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new BrickBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new BrickRecipeProvider(event.getGenerator()));
        event.getGenerator().addProvider(new BrickBlockTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new BrickLangEntryProvider(event.getGenerator(), langJson));

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

        //ShingleSlab
        event.getGenerator().addProvider(new ShingleSlabBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(new ShingleSlabItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new ShingleSlabBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new ShingleSlabComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new ShingleSlabLangEntryProvider(event.getGenerator(), langJson));
        
        //Paper wall
        event.getGenerator().addProvider(new PaperwallBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(new PaperwallItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new PaperwallBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new PaperwallComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new PaperwallLangEntryProvider(event.getGenerator(), langJson));

        //Fence
        event.getGenerator().addProvider(new FenceBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(new FenceItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new FenceBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new FenceComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new FenceCompatibilityTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new FenceLangEntryProvider(event.getGenerator(), langJson));

        //FenceGate
        event.getGenerator().addProvider(new FenceGateBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(new FenceGateItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new FenceGateBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new FenceGateComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new FenceGateCompatibilityTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new FenceGateLangEntryProvider(event.getGenerator(), langJson));

        //Slab
        event.getGenerator().addProvider(new SlabBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(new SlabItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new SlabBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new SlabComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new SlabCompatibilityTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new SlabLangEntryProvider(event.getGenerator(), langJson));

        //Wall
        event.getGenerator().addProvider(new WallBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(new WallItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new WallBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new WallComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new WallCompatibilityTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new WallLangEntryProvider(event.getGenerator(), langJson));

        //Stair
        event.getGenerator().addProvider(new StairsBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(new StairsItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new StairsBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new StairsComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new StairsCompatibilityTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new StairsLangEntryProvider(event.getGenerator(), langJson));

        //Floating carpets
        event.getGenerator().addProvider(new FloatingCarpetBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(new FloatingCarpetBlockTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new FloatingCarpetItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(new FloatingCarpetLangEntryProvider(event.getGenerator(), langJson));
        event.getGenerator().addProvider(new FloatingCarpetRecipeProvider(event.getGenerator()));

        //Global
        //IMPORTANT: Needs to be last since this writes the lang data actually to disk!!!!!
        event.getGenerator().addProvider(new GlobalLangEntryProvider(event.getGenerator(), langJson));
        event.getGenerator().addProvider(new GlobalLootTableProvider(event.getGenerator()));
        event.getGenerator().addProvider(new MateriallyTexturedBlockRecipeProvider(event.getGenerator()));
    }
}
