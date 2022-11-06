package com.ldtteam.domumornamentum.event.handlers;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.datagen.bricks.BrickBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickItemTagProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickRecipeProvider;
import com.ldtteam.domumornamentum.datagen.door.DoorsBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.door.DoorsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.door.DoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.door.DoorsLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.door.fancy.FancyDoorsBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.door.fancy.FancyDoorsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.door.fancy.FancyDoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.door.fancy.FancyDoorsLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraBlockModelProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraItemModelProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraItemTagProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraRecipeProvider;
import com.ldtteam.domumornamentum.datagen.fence.FenceBlockModelProvider;
import com.ldtteam.domumornamentum.datagen.fence.FenceBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.fence.FenceCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.fence.FenceComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.fence.FenceItemModelProvider;
import com.ldtteam.domumornamentum.datagen.fence.FenceLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.fencegate.FenceGateBlockModelProvider;
import com.ldtteam.domumornamentum.datagen.fencegate.FenceGateBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.fencegate.FenceGateCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.fencegate.FenceGateComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.fencegate.FenceGateItemModelProvider;
import com.ldtteam.domumornamentum.datagen.fencegate.FenceGateLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.FloatingCarpetBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.FloatingCarpetBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.FloatingCarpetItemModelProvider;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.FloatingCarpetLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.FloatingCarpetRecipeProvider;
import com.ldtteam.domumornamentum.datagen.frames.light.*;
import com.ldtteam.domumornamentum.datagen.frames.timber.TimberFramesBlockModelProvider;
import com.ldtteam.domumornamentum.datagen.frames.timber.TimberFramesBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.frames.timber.TimberFramesComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.frames.timber.TimberFramesItemModelProvider;
import com.ldtteam.domumornamentum.datagen.frames.timber.TimberFramesLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalLootTableProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalRecipeProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalTagProvider;
import com.ldtteam.domumornamentum.datagen.global.MateriallyTexturedBlockRecipeProvider;
import com.ldtteam.domumornamentum.datagen.panel.PanelBlockModelProvider;
import com.ldtteam.domumornamentum.datagen.panel.PanelBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.panel.PanelItemModelProvider;
import com.ldtteam.domumornamentum.datagen.panel.PanelItemModelSpecProvider;
import com.ldtteam.domumornamentum.datagen.panel.PanelLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.pillar.PillarBlockModelProvider;
import com.ldtteam.domumornamentum.datagen.pillar.PillarBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.pillar.PillarComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.pillar.PillarItemModelProvider;
import com.ldtteam.domumornamentum.datagen.pillar.PillarLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.shingle.normal.ShinglesBlockModelProvider;
import com.ldtteam.domumornamentum.datagen.shingle.normal.ShinglesBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.shingle.normal.ShinglesComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.shingle.normal.ShinglesItemModelProvider;
import com.ldtteam.domumornamentum.datagen.shingle.normal.ShinglesLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.shingle.slab.ShingleSlabBlockModelProvider;
import com.ldtteam.domumornamentum.datagen.shingle.slab.ShingleSlabBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.shingle.slab.ShingleSlabComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.shingle.slab.ShingleSlabItemModelProvider;
import com.ldtteam.domumornamentum.datagen.shingle.slab.ShingleSlabLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.slab.SlabBlockModelProvider;
import com.ldtteam.domumornamentum.datagen.slab.SlabBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.slab.SlabCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.slab.SlabComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.slab.SlabItemModelProvider;
import com.ldtteam.domumornamentum.datagen.slab.SlabLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.stair.StairsBlockModelProvider;
import com.ldtteam.domumornamentum.datagen.stair.StairsBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.stair.StairsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.stair.StairsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.stair.StairsItemModelProvider;
import com.ldtteam.domumornamentum.datagen.stair.StairsLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsBlockModelProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsItemModelProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsItemModelSpecProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsBlockModelProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsItemModelProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsItemModelSpecProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.wall.paper.PaperwallBlockModelProvider;
import com.ldtteam.domumornamentum.datagen.wall.paper.PaperwallBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.wall.paper.PaperwallComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.wall.paper.PaperwallItemModelProvider;
import com.ldtteam.domumornamentum.datagen.wall.paper.PaperwallLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.WallBlockModelProvider;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.WallBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.WallCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.WallComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.WallItemModelProvider;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.WallLangEntryProvider;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
        event.getGenerator().addProvider(true, new BrickBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
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

        // Framed Light
        event.getGenerator().addProvider(true, new FramedLightBlockStateProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FramedLightItemModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FramedLightBlockModelProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new FramedLightComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FramedLightLangEntryProvider(event.getGenerator(), langJson));

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
        event.getGenerator().addProvider(true, new DoorsBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new DoorsComponentTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new DoorsCompatibilityTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new DoorsLangEntryProvider(event.getGenerator(), langJson));

        //FancyDoor
        event.getGenerator().addProvider(true, new FancyDoorsBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
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
