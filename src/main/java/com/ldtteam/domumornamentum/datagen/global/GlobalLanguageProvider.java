package com.ldtteam.domumornamentum.datagen.global;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.datagen.bricks.BrickLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.door.DoorsLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.door.fancy.FancyDoorsLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.fence.FenceLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.fencegate.FenceGateLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.FloatingCarpetLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.frames.light.FramedLightLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.frames.timber.TimberFramesLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.panel.PanelLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.pillar.PillarLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.shingle.normal.ShinglesLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.shingle.slab.ShingleSlabLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.slab.SlabLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.stair.StairsLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.wall.paper.PaperwallLangEntryProvider;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.WallLangEntryProvider;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GlobalLanguageProvider extends LanguageProvider
{
    public GlobalLanguageProvider(DataGenerator gen) {
        super(gen, Constants.MOD_ID, Constants.DEFAULT_LANG, List.of(
                new BrickLangEntryProvider(),
                new DoorsLangEntryProvider(),
                new FancyDoorsLangEntryProvider(),
                new ExtraLangEntryProvider(),
                new FenceLangEntryProvider(),
                new FenceGateLangEntryProvider(),
                new FloatingCarpetLangEntryProvider(),
                new FramedLightLangEntryProvider(),
                new TimberFramesLangEntryProvider(),
                new GlobalLanguageEntries(),
                new PanelLangEntryProvider(),
                new PillarLangEntryProvider(),
                new ShinglesLangEntryProvider(),
                new ShingleSlabLangEntryProvider(),
                new SlabLangEntryProvider(),
                new StairsLangEntryProvider(),
                new TrapdoorsLangEntryProvider(),
                new FancyTrapdoorsLangEntryProvider(),
                new PaperwallLangEntryProvider(),
                new WallLangEntryProvider()
        ));
    }

    private final static class GlobalLanguageEntries implements SubProvider {
        @Override
        public void addTranslations(LanguageAcceptor acceptor) {
            acceptor.add("itemGroup." + Constants.MOD_ID + ".timber_frames", "DO - Framed Blocks");
            acceptor.add("itemGroup." + Constants.MOD_ID + ".shingles", "DO - Shingles");
            acceptor.add("itemGroup." + Constants.MOD_ID + ".general", "Domum Ornamentum (DO)");
            acceptor.add("itemGroup." + Constants.MOD_ID + ".paperwalls", "DO - Thin framed walls");
            acceptor.add("itemGroup." + Constants.MOD_ID + ".shingle_slabs", "DO - Shingle slabs");
            acceptor.add("itemGroup." + Constants.MOD_ID + ".extra-blocks", "DO - Decorative blocks");
            acceptor.add("itemGroup." + Constants.MOD_ID + ".floating-carpets", "DO - Floating Carpets");
            acceptor.add("itemGroup." + Constants.MOD_ID + ".fences", "DO - Fences");
            acceptor.add("itemGroup." + Constants.MOD_ID + ".slabs", "DO - Slabs");
            acceptor.add("itemGroup." + Constants.MOD_ID + ".walls", "DO - Walls");
            acceptor.add("itemGroup." + Constants.MOD_ID + ".stairs", "DO - Stairs");
            acceptor.add("itemGroup." + Constants.MOD_ID + ".doors", "DO - Doors");
            acceptor.add("block." + Constants.MOD_ID + ".architectscutter", "Architects cutter");
            acceptor.add(Constants.MOD_ID + ".architectscutter", "Architects cutter");
            acceptor.add(Constants.MOD_ID + ".origin.tooltip", "Crafted in the Architects cutter");
            acceptor.add(Constants.MOD_ID + ".block.format", "Material: %s");
            acceptor.add(ModBlocks.getInstance().getStandingBarrel().getDescriptionId(), "Standing Barrel");
            acceptor.add(ModBlocks.getInstance().getLayingBarrel().getDescriptionId(), "Laying Barrel");
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Global Lang Provider";
    }
}
