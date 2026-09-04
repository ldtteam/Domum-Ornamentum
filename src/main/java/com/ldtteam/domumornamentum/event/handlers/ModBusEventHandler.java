package com.ldtteam.domumornamentum.event.handlers;

import com.ldtteam.domumornamentum.datagen.DatagenContext;
import com.ldtteam.domumornamentum.datagen.allbrick.AllBrickBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.DatagenContext;
import com.ldtteam.domumornamentum.datagen.allbrick.AllBrickBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.DatagenContext;
import com.ldtteam.domumornamentum.datagen.allbrick.AllBrickStairBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickItemTagProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickRecipeProvider;
import com.ldtteam.domumornamentum.datagen.door.DoorsBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.door.DoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.door.fancy.FancyDoorsBlockStateProvider;
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
import com.ldtteam.domumornamentum.datagen.global.DomumRecipeProvider;
import com.ldtteam.domumornamentum.datagen.frames.dynamic.DynamicTimberFramesBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.frames.light.FramedLightBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.frames.light.FramedLightComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.frames.timber.TimberFramesBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.frames.timber.TimberFramesComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalLanguageProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalLootTableProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalRecipeProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalTagProvider;
import com.ldtteam.domumornamentum.datagen.global.MateriallyTexturedBlockRecipeProvider;
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
import com.ldtteam.domumornamentum.network.messages.CreativeSetArchitectCutterSlotMessage;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class ModBusEventHandler
{
    /**
     * Called when mod is being initialized.
     *
     * @param event event
     */
    @SubscribeEvent
    public static void onNetworkRegistry(final RegisterPayloadHandlersEvent event)
    {
        final String modVersion = ModList.get().getModContainerById(Constants.MOD_ID).get().getModInfo().getVersion().toString();
        final PayloadRegistrar registry = event.registrar(Constants.MOD_ID).versioned(modVersion);

        registry.playToServer(CreativeSetArchitectCutterSlotMessage.ID, CreativeSetArchitectCutterSlotMessage.CODEC, CreativeSetArchitectCutterSlotMessage::onExecute);
    }

    @SubscribeEvent
    public static void dataGeneratorSetupClient(final GatherDataEvent.Client event)
    {
        registerDataProviders(event);
    }

    @SubscribeEvent
    public static void dataGeneratorSetupServer(final GatherDataEvent.Server event)
    {
        registerDataProviders(event);
    }

    private static void registerDataProviders(final GatherDataEvent event)
    {
        //Extra blocks
        event.getGenerator().addProvider(true, new ExtraBlockStateProvider(event.getGenerator(), new DatagenContext()));
        event.getGenerator().addProvider(true, new DomumRecipeProvider.Runner<>(event.getGenerator().getPackOutput(), event.getLookupProvider(), "Extra Blocks Recipe Provider", ExtraRecipeProvider::new));
        final ExtraBlockTagProvider extraBlockTagProvider = new ExtraBlockTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), new DatagenContext());
        event.getGenerator().addProvider(true, extraBlockTagProvider);
        event.getGenerator().addProvider(true, new ExtraItemTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), extraBlockTagProvider.contentsGetter(), new DatagenContext()));

        //Brick blocks
        event.getGenerator().addProvider(true, new BrickBlockStateProvider(event.getGenerator(), new DatagenContext()));
        event.getGenerator().addProvider(true, new DomumRecipeProvider.Runner<>(event.getGenerator().getPackOutput(), event.getLookupProvider(), "Brick Blocks Recipe Provider", BrickRecipeProvider::new));
        final BrickBlockTagProvider brickBlockTagProvider = new BrickBlockTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), new DatagenContext());
        event.getGenerator().addProvider(true, brickBlockTagProvider);
        event.getGenerator().addProvider(true, new BrickItemTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), brickBlockTagProvider.contentsGetter(), new DatagenContext()));

        final CompletableFuture<TagsProvider.TagLookup<Block>> globalTagParent = extraBlockTagProvider.contentsGetter().thenCombine(
            brickBlockTagProvider.contentsGetter(),
            (extraTags, brickTags) -> key -> extraTags.apply(key).or(() -> brickTags.apply(key))
        );
        final GlobalTagProvider globalTagProvider = new GlobalTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), globalTagParent, new DatagenContext());
        event.getGenerator().addProvider(true, globalTagProvider);
        final CompletableFuture<TagsProvider.TagLookup<Block>> globalAndBaseTagParent = globalTagProvider.contentsGetter().thenCombine(
            globalTagParent,
            (globalTags, baseTags) -> key -> globalTags.apply(key).or(() -> baseTags.apply(key))
        );

        // Timber Frames
        event.getGenerator().addProvider(true, new TimberFramesBlockStateProvider(event.getGenerator(), new DatagenContext()));
        event.getGenerator().addProvider(true, new TimberFramesComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), globalTagProvider.contentsGetter(), new DatagenContext()));

        //Dynamic Timber Frames
        event.getGenerator().addProvider(true, new DynamicTimberFramesBlockStateProvider(event.getGenerator(), new DatagenContext()));

        // Framed Light
        event.getGenerator().addProvider(true, new FramedLightBlockStateProvider(event.getGenerator(), new DatagenContext()));
        event.getGenerator().addProvider(true, new FramedLightComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), new DatagenContext()));

        //Shingles
        event.getGenerator().addProvider(true, new ShinglesBlockStateProvider(event.getGenerator(), new DatagenContext()));
        final ShinglesComponentTagProvider shinglesComponentTagProvider = new ShinglesComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), globalTagProvider.contentsGetter(), new DatagenContext());
        event.getGenerator().addProvider(true, shinglesComponentTagProvider);

        //ShingleSlab
        event.getGenerator().addProvider(true, new ShingleSlabBlockStateProvider(event.getGenerator(), new DatagenContext()));
        event.getGenerator().addProvider(true, new ShingleSlabComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), shinglesComponentTagProvider.contentsGetter(), new DatagenContext()));

        //Paper wall
        event.getGenerator().addProvider(true, new PaperwallBlockStateProvider(event.getGenerator(), new DatagenContext()));
        event.getGenerator().addProvider(true, new PaperwallComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), globalTagProvider.contentsGetter(), new DatagenContext()));

        //Fence
        event.getGenerator().addProvider(true, new FenceBlockStateProvider(event.getGenerator(), new DatagenContext()));
        final FenceComponentTagProvider fenceComponentTagProvider = new FenceComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), globalTagProvider.contentsGetter(), new DatagenContext());
        event.getGenerator().addProvider(true, fenceComponentTagProvider);
        event.getGenerator().addProvider(true, new FenceCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), new DatagenContext()));

        //FenceGate
        event.getGenerator().addProvider(true, new FenceGateBlockStateProvider(event.getGenerator(), new DatagenContext()));
        event.getGenerator().addProvider(true, new FenceGateComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), fenceComponentTagProvider.contentsGetter(), new DatagenContext()));
        event.getGenerator().addProvider(true, new FenceGateCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), new DatagenContext()));

        //Slab
        event.getGenerator().addProvider(true, new SlabBlockStateProvider(event.getGenerator(), new DatagenContext()));
        event.getGenerator().addProvider(true, new SlabComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), globalAndBaseTagParent, new DatagenContext()));
        event.getGenerator().addProvider(true, new SlabCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), new DatagenContext()));

        //Wall
        event.getGenerator().addProvider(true, new WallBlockStateProvider(event.getGenerator(), new DatagenContext()));
        event.getGenerator().addProvider(true, new WallComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), globalAndBaseTagParent, new DatagenContext()));
        event.getGenerator().addProvider(true, new WallCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), new DatagenContext()));

        //Stair
        event.getGenerator().addProvider(true, new StairsBlockStateProvider(event.getGenerator(), new DatagenContext()));
        event.getGenerator().addProvider(true, new StairsComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), globalAndBaseTagParent, new DatagenContext()));
        event.getGenerator().addProvider(true, new StairsCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), new DatagenContext()));

        //Trapdoor
        event.getGenerator().addProvider(true, new TrapdoorsBlockStateProvider(event.getGenerator(), new DatagenContext()));
        final TrapdoorsComponentTagProvider trapdoorsComponentTagProvider = new TrapdoorsComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), globalTagProvider.contentsGetter(), new DatagenContext());
        event.getGenerator().addProvider(true, trapdoorsComponentTagProvider);
        event.getGenerator().addProvider(true, new TrapdoorsCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), new DatagenContext()));

        event.getGenerator().addProvider(true, new PanelBlockStateProvider(event.getGenerator(), new DatagenContext()));

        //Post
        event.getGenerator().addProvider(true, new PostBlockStateProvider(event.getGenerator(), new DatagenContext()));
        event.getGenerator().addProvider(true, new PostComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), globalTagProvider.contentsGetter(), new DatagenContext()));


        //Fancy Trapdoor
        event.getGenerator().addProvider(true, new FancyTrapdoorsBlockStateProvider(event.getGenerator(), new DatagenContext()));
        event.getGenerator().addProvider(true, new FancyTrapdoorsComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), trapdoorsComponentTagProvider.contentsGetter(), new DatagenContext()));
        event.getGenerator().addProvider(true, new FancyTrapdoorsCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), new DatagenContext()));

        //Door
        event.getGenerator().addProvider(true, new DoorsBlockStateProvider(event.getGenerator(), new DatagenContext()));
        final DoorsComponentTagProvider doorsComponentTagProvider = new DoorsComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), globalTagProvider.contentsGetter(), new DatagenContext());
        event.getGenerator().addProvider(true, doorsComponentTagProvider);
        // Commented to temporarily prevent the tag generation issue for doors
        //event.getGenerator().addProvider(true, new DoorsCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), new DatagenContext()));

        //FancyDoor
        event.getGenerator().addProvider(true, new FancyDoorsBlockStateProvider(event.getGenerator(), new DatagenContext()));
        event.getGenerator().addProvider(true, new FancyDoorsComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), doorsComponentTagProvider.contentsGetter(), new DatagenContext()));
        //event.getGenerator().addProvider(true, new FancyDoorsCompatibilityTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), new DatagenContext()));

        //Floating carpets
        event.getGenerator().addProvider(true, new FloatingCarpetBlockStateProvider(event.getGenerator(), new DatagenContext()));
        event.getGenerator().addProvider(true, new FloatingCarpetBlockTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), new DatagenContext()));
        event.getGenerator().addProvider(true, new DomumRecipeProvider.Runner<>(event.getGenerator().getPackOutput(), event.getLookupProvider(), "Floating Carpet Recipe Provider", FloatingCarpetRecipeProvider::new));

        //Pillars
        event.getGenerator().addProvider(true, new PillarBlockStateProvider(event.getGenerator(), new DatagenContext()));
        event.getGenerator().addProvider(true, new PillarComponentTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), globalTagProvider.contentsGetter(), new DatagenContext()));

        //AllBrick
        event.getGenerator().addProvider(true, new AllBrickBlockStateProvider(event.getGenerator(), new DatagenContext()));
        event.getGenerator().addProvider(true, new AllBrickStairBlockStateProvider(event.getGenerator(), new DatagenContext()));

        event.getGenerator().addProvider(true, new AllBrickBlockTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), globalAndBaseTagParent, new DatagenContext()));

        //Global
        event.getGenerator().addProvider(true, new DomumRecipeProvider.Runner<>(event.getGenerator().getPackOutput(), event.getLookupProvider(), "Global Blocks Recipe Provider", GlobalRecipeProvider::new));
        event.getGenerator().addProvider(true, new GlobalLanguageProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new GlobalLootTableProvider(event.getGenerator().getPackOutput(), event.getLookupProvider()));
        event.getGenerator().addProvider(true, new DomumRecipeProvider.Runner<>(event.getGenerator().getPackOutput(), event.getLookupProvider(), "Materially textured block recipes", MateriallyTexturedBlockRecipeProvider::new));
    }
}
