package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.IDomumOrnamentumApi;
import com.ldtteam.domumornamentum.shingles.ShingleHeightType;
import net.minecraft.world.level.block.Block;

import java.util.List;

public interface IModBlocks
{
    static IModBlocks getInstance() {
        return IDomumOrnamentumApi.getInstance().getBlocks();
    }

    Block getArchitectsCutter();

    Block getShingle(final ShingleHeightType heightType);

    List<? extends Block> getTimberFrames();

    List<? extends Block> getFramedLights();

    Block getShingleSlab();

    Block getPaperWall();

    List<? extends Block> getExtraTopBlocks();

    List<? extends Block> getFloatingCarpets();

    Block getStandingBarrel();

    Block getLayingBarrel();

    Block getFence();

    Block getFenceGate();

    Block getSlab();

    List<? extends Block> getBricks();

    Block getWall();

    Block getStair();

    Block getTrapdoor();

    Block getPanel();

    Block getCornerPanel();

    Block getPost();

    Block getDoor();

    Block getFancyDoor();

    Block getFancyTrapdoor();

    List<? extends Block> getPillars();

    List<? extends Block> getAllBrickBlocks();

    List<? extends Block> getAllBrickStairBlocks();

}
