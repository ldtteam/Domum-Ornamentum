package com.ldtteam.domumornamentum.event.handlers;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.decorative.DynamicTimberFrameBlock;
import com.ldtteam.domumornamentum.datagen.allbrick.AllBrickLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickItemTagProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickRecipeProvider;
import com.ldtteam.domumornamentum.datagen.door.DoorsLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.door.fancy.FancyDoorsLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraItemTagProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraRecipeProvider;
import com.ldtteam.domumornamentum.datagen.fence.FenceLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.fencegate.FenceGateLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.FloatingCarpetLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.FloatingCarpetRecipeProvider;
import com.ldtteam.domumornamentum.datagen.frames.dynamic.DynamicFramesLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.frames.light.FramedLightLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.frames.timber.TimberFramesLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalLanguageProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalLootTableProvider;
import com.ldtteam.domumornamentum.datagen.global.GlobalRecipeProvider;
import com.ldtteam.domumornamentum.datagen.global.ModBlockTagsProvider;
import com.ldtteam.domumornamentum.datagen.global.MateriallyTexturedBlockRecipeProvider;
import com.ldtteam.domumornamentum.datagen.loot.MaterialLootTableProvider;
import com.ldtteam.domumornamentum.datagen.panel.PanelLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.pillar.PillarLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.post.PostLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.shingle.normal.ShinglesLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.shingle.slab.ShingleSlabLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.slab.SlabLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.stair.StairsLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.wall.paper.PaperwallLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.WallLangEntryProvider;
import com.ldtteam.domumornamentum.entity.block.ModBlockEntityTypes;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class ModBusEventHandler
{
    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent.Server event)
    {
        // Block tags - one shared provider instance holding every tag section (see ModBlockTagsProvider).
        ModBlockTagsProvider.register(event);

        // Item tags
        BrickItemTagProvider.register(event);
        ExtraItemTagProvider.register(event);

        // Recipes
        ExtraRecipeProvider.register(event);
        BrickRecipeProvider.register(event);
        FloatingCarpetRecipeProvider.register(event);
        GlobalRecipeProvider.register(event);
        MateriallyTexturedBlockRecipeProvider.register(event);

        // Loot tables
        GlobalLootTableProvider.register(event);
        MaterialLootTableProvider.register(event);
    }

    @SubscribeEvent
    public static void clientDataGeneratorSetup(final GatherDataEvent.Client event)
    {
        // Language provider (aggregates all lang entry providers).
        // Model providers self-register via their own @SubscribeEvent handlers.
        event.addProvider(new GlobalLanguageProvider(event.getGenerator()));

    }

    @SuppressWarnings("SuspiciousToArrayCall")
    @SubscribeEvent
    public static void onBlockEntityTypeAddBlocksEvent(final BlockEntityTypeAddBlocksEvent event) {
        event.modify(
                ModBlockEntityTypes.MATERIALLY_TEXTURED.get(),
                BuiltInRegistries.BLOCK.stream()
                        .filter(IMateriallyTexturedBlock.class::isInstance)
                        .toArray(Block[]::new)
        );

        event.modify(
                ModBlockEntityTypes.DYNAMIC_TIMBERFRAME.get(),
                BuiltInRegistries.BLOCK.stream()
                        .filter(DynamicTimberFrameBlock.class::isInstance)
                        .toArray(Block[]::new)
        );
    }
}
