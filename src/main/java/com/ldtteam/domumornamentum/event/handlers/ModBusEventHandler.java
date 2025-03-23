package com.ldtteam.domumornamentum.event.handlers;

import com.ldtteam.domumornamentum.Network;
import com.ldtteam.domumornamentum.datagen.allbrick.AllBrickBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.allbrick.AllBrickBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.allbrick.AllBrickStairBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickItemTagProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickRecipeProvider;
import com.ldtteam.domumornamentum.datagen.door.DoorsBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.door.DoorsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.door.DoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.door.fancy.FancyDoorsBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.door.fancy.FancyDoorsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.door.fancy.FancyDoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraItemTagProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraRecipeProvider;
import com.ldtteam.domumornamentum.datagen.fence.FenceBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.fence.FenceCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.fence.FenceComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.fencegate.FenceGateBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.fencegate.FenceGateCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.fencegate.FenceGateComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.FloatingCarpetBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.FloatingCarpetBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.FloatingCarpetRecipeProvider;
import com.ldtteam.domumornamentum.datagen.frames.light.FramedLightBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.frames.light.FramedLightComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.frames.timber.TimberFramesBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.frames.timber.TimberFramesComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalLanguageProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalLootTableProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalRecipeProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalTagProvider;
import com.ldtteam.domumornamentum.datagen.global.MateriallyTexturedBlockRecipeProvider;
import com.ldtteam.domumornamentum.datagen.panel.CornerPanelBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.panel.PanelBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.pillar.PillarBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.pillar.PillarComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.post.PostBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.post.PostComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.shingle.normal.ShinglesBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.shingle.normal.ShinglesComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.shingle.slab.ShingleSlabBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.shingle.slab.ShingleSlabComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.slab.SlabBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.slab.SlabCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.slab.SlabComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.stair.StairsBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.stair.StairsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.stair.StairsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.wall.paper.PaperwallBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.wall.paper.PaperwallComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.WallBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.WallCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.WallComponentTagProvider;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEventHandler
{
    /**
     * Called when mod is being initialized.
     *
     * @param event event
     */
    @SubscribeEvent
    public static void onModInit(final FMLCommonSetupEvent event)
    {
        Network.getNetwork().registerMessages();
    }

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        //Extra blocks
        event.getGenerator().addProvider(true, new ExtraBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new ExtraRecipeProvider(event.getGenerator().getPackOutput()));
        final ExtraBlockTagProvider extraBlockTagProvider = new ExtraBlockTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper());
        event.getGenerator().addProvider(true, extraBlockTagProvider);
        event.getGenerator().addProvider(true, new ExtraItemTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), extraBlockTagProvider.contentsGetter(), event.getExistingFileHelper()));

        //Brick blocks
        event.getGenerator().addProvider(true, new BrickBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new BrickRecipeProvider(event.getGenerator().getPackOutput()));
        final BrickBlockTagProvider brickBlockTagProvider = new BrickBlockTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper());
        event.getGenerator().addProvider(true, brickBlockTagProvider);
        event.getGenerator().addProvider(true, new BrickItemTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), brickBlockTagProvider.contentsGetter(), event.getExistingFileHelper()));

        event.getGenerator().addProvider(true, new GlobalTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        // Timber Frames
        event.getGenerator().addProvider(true, new TimberFramesBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new TimberFramesComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        // Framed Light
        event.getGenerator().addProvider(true, new FramedLightBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FramedLightComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        //Shingles
        event.getGenerator().addProvider(true, new ShinglesBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new ShinglesComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        //ShingleSlab
        event.getGenerator().addProvider(true, new ShingleSlabBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new ShingleSlabComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        //Paper wall
        event.getGenerator().addProvider(true, new PaperwallBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new PaperwallComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        //Fence
        event.getGenerator().addProvider(true, new FenceBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FenceComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FenceCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        //FenceGate
        event.getGenerator().addProvider(true, new FenceGateBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FenceGateComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FenceGateCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        //Slab
        event.getGenerator().addProvider(true, new SlabBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new SlabComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new SlabCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        //Wall
        event.getGenerator().addProvider(true, new WallBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new WallComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new WallCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        //Stair
        event.getGenerator().addProvider(true, new StairsBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new StairsComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new StairsCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        //Trapdoor
        event.getGenerator().addProvider(true, new TrapdoorsBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new TrapdoorsComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new TrapdoorsCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        //Panel
        event.getGenerator().addProvider(true, new PanelBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new CornerPanelBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));

        //Post
        event.getGenerator().addProvider(true, new PostBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new PostComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));


        //Fancy Trapdoor
        event.getGenerator().addProvider(true, new FancyTrapdoorsBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FancyTrapdoorsComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FancyTrapdoorsCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        //Door
        event.getGenerator().addProvider(true, new DoorsBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new DoorsComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        // Commented to temporarily prevent the tag generation issue for doors
        //event.getGenerator().addProvider(true, new DoorsCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        //FancyDoor
        event.getGenerator().addProvider(true, new FancyDoorsBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FancyDoorsComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        //event.getGenerator().addProvider(true, new FancyDoorsCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        //Floating carpets
        event.getGenerator().addProvider(true, new FloatingCarpetBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FloatingCarpetBlockTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new FloatingCarpetRecipeProvider(event.getGenerator().getPackOutput()));

        //Pillars
        event.getGenerator().addProvider(true, new PillarBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new PillarComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        //AllBrick
        event.getGenerator().addProvider(true, new AllBrickBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new AllBrickStairBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));

        event.getGenerator().addProvider(true, new AllBrickBlockTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));

        //Global
        event.getGenerator().addProvider(true, new GlobalRecipeProvider(event.getGenerator().getPackOutput()));
        event.getGenerator().addProvider(true, new GlobalLanguageProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new GlobalLootTableProvider(event.getGenerator().getPackOutput()));
        event.getGenerator().addProvider(true, new MateriallyTexturedBlockRecipeProvider(event.getGenerator().getPackOutput()));
    }
}
