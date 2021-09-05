package com.ldtteam.domumornamentum.api;

import com.ldtteam.domumornamentum.IDomumOrnamentumApi;
import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.block.ModBlocks;

public class DomumOrnamentumAPI implements IDomumOrnamentumApi
{
    private static final DomumOrnamentumAPI INSTANCE = new DomumOrnamentumAPI();

    public static DomumOrnamentumAPI getInstance()
    {
        return INSTANCE;
    }

    @Override
    public IModBlocks getBlocks()
    {
        return ModBlocks.getInstance();
    }

    private DomumOrnamentumAPI()
    {
    }
}
