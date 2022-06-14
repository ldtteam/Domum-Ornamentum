package com.ldtteam.domumornamentum.event.handlers;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.datagen.bricks.*;
import com.ldtteam.domumornamentum.datagen.door.*;
import com.ldtteam.domumornamentum.datagen.door.fancy.*;
import com.ldtteam.domumornamentum.datagen.extra.*;
import com.ldtteam.domumornamentum.datagen.fence.*;
import com.ldtteam.domumornamentum.datagen.fencegate.*;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.*;
import com.ldtteam.domumornamentum.datagen.frames.timber.*;
import com.ldtteam.domumornamentum.datagen.global.GlobalTagProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalLootTableProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalRecipeProvider;
import com.ldtteam.domumornamentum.datagen.global.MateriallyTexturedBlockRecipeProvider;
import com.ldtteam.domumornamentum.datagen.panel.*;
import com.ldtteam.domumornamentum.datagen.pillar.*;
import com.ldtteam.domumornamentum.datagen.shingle.normal.*;
import com.ldtteam.domumornamentum.datagen.shingle.slab.*;
import com.ldtteam.domumornamentum.datagen.slab.*;
import com.ldtteam.domumornamentum.datagen.stair.*;
import com.ldtteam.domumornamentum.datagen.trapdoor.*;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.*;
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
        event.getGenerator().addProvider(true, new ExtraBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new ExtraItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new ExtraBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new ExtraRecipeProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new ExtraLangEntryProvider(event.getGenerator(), langJson));
        final ExtraBlockTagProvider extraBlockTagProvider = new ExtraBlockTagProvider(event.getGenerator(), event.getExistingFileHelper());
        event.getGenerator().addProvider(true, extraBlockTagProvider);
        event.getGenerator().addProvider(true, new ExtraItemTagProvider(event.getGenerator(), extraBlockTagProvider, event.getExistingFileHelper()));

        //Brick blocks
        event.getGenerator().addProvider(true, new BrickBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new BrickItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new BrickBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new BrickRecipeProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new BrickLangEntryProvider(event.getGenerator(), langJson));
        final BrickBlockTagProvider brickBlockTagProvider = new BrickBlockTagProvider(event.getGenerator(), event.getExistingFileHelper());
        event.getGenerator().addProvider(true, brickBlockTagProvider);
        event.getGenerator().addProvider(true, new BrickItemTagProvider(event.getGenerator(), brickBlockTagProvider, event.getExistingFileHelper()));

        event.getGenerator().addProvider(true, new GlobalTagProvider(event.getGenerator(), event.getExistingFileHelper()));

        // Timber Frames
        event.getGenerator().addProvider(true, new TimberFramesBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new TimberFramesItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new TimberFramesBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new TimberFramesComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new TimberFramesLangEntryProvider(event.getGenerator(), langJson));

        //Shingles
        event.getGenerator().addProvider(true, new ShinglesBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new ShinglesItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new ShinglesBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new ShinglesComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new ShinglesLangEntryProvider(event.getGenerator(), langJson));

        //ShingleSlab
        event.getGenerator().addProvider(true, new ShingleSlabBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new ShingleSlabItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new ShingleSlabBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new ShingleSlabComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new ShingleSlabLangEntryProvider(event.getGenerator(), langJson));
        
        //Paper wall
        event.getGenerator().addProvider(true, new PaperwallBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new PaperwallItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new PaperwallBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new PaperwallComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new PaperwallLangEntryProvider(event.getGenerator(), langJson));

        //Fence
        event.getGenerator().addProvider(true, new FenceBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FenceItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FenceBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FenceComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FenceCompatibilityTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FenceLangEntryProvider(event.getGenerator(), langJson));

        //FenceGate
        event.getGenerator().addProvider(true, new FenceGateBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FenceGateItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FenceGateBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FenceGateComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FenceGateCompatibilityTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FenceGateLangEntryProvider(event.getGenerator(), langJson));

        //Slab
        event.getGenerator().addProvider(true, new SlabBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new SlabItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new SlabBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new SlabComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new SlabCompatibilityTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new SlabLangEntryProvider(event.getGenerator(), langJson));

        //Wall
        event.getGenerator().addProvider(true, new WallBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new WallItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new WallBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new WallComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new WallCompatibilityTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new WallLangEntryProvider(event.getGenerator(), langJson));

        //Stair
        event.getGenerator().addProvider(true, new StairsBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new StairsItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new StairsBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new StairsComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new StairsCompatibilityTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new StairsLangEntryProvider(event.getGenerator(), langJson));
        
        //Trapdoor
        event.getGenerator().addProvider(true, new TrapdoorsBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new TrapdoorsItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new TrapdoorsItemModelSpecProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new TrapdoorsBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new TrapdoorsComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new TrapdoorsCompatibilityTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new TrapdoorsLangEntryProvider(event.getGenerator(), langJson));

        event.getGenerator().addProvider(true, new PanelBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new PanelBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new PanelItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new PanelItemModelSpecProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new PanelLangEntryProvider(event.getGenerator(), langJson));

        //Fancy Trapdoor
        event.getGenerator().addProvider(true, new FancyTrapdoorsBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FancyTrapdoorsItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FancyTrapdoorsItemModelSpecProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FancyTrapdoorsBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FancyTrapdoorsComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FancyTrapdoorsCompatibilityTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FancyTrapdoorsLangEntryProvider(event.getGenerator(), langJson));

        //Door
        event.getGenerator().addProvider(true, new DoorsBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new DoorsItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new DoorsItemModelSpecProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new DoorsBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new DoorsComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new DoorsCompatibilityTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new DoorsLangEntryProvider(event.getGenerator(), langJson));

        //FancyDoor
        event.getGenerator().addProvider(true, new FancyDoorsBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FancyDoorsItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FancyDoorsItemModelSpecProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FancyDoorsBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FancyDoorsComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FancyDoorsCompatibilityTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FancyDoorsLangEntryProvider(event.getGenerator(), langJson));

        //Floating carpets
        event.getGenerator().addProvider(true, new FloatingCarpetBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FloatingCarpetBlockTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FloatingCarpetItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FloatingCarpetLangEntryProvider(event.getGenerator(), langJson));
        event.getGenerator().addProvider(true, new FloatingCarpetRecipeProvider(event.getGenerator()));

        //Pillars
        event.getGenerator().addProvider(true, new PillarBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new PillarBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new PillarComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new PillarItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new PillarLangEntryProvider(event.getGenerator(), langJson));


        event.getGenerator().addProvider(true, new GlobalRecipeProvider(event.getGenerator()));

        //Global
        //IMPORTANT: Needs to be last since this writes the lang data actually to disk!!!!!
        event.getGenerator().addProvider(true, new GlobalLangEntryProvider(event.getGenerator(), langJson));
        event.getGenerator().addProvider(true, new GlobalLootTableProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new MateriallyTexturedBlockRecipeProvider(event.getGenerator()));
    }
}
