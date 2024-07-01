package com.ldtteam.domumornamentum.api;

import com.ldtteam.domumornamentum.IDomumOrnamentumApi;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockManager;
import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.block.MateriallyTexturedBlockManager;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.component.ModDataComponents;
import net.minecraft.core.component.DataComponentType;

import java.util.function.Supplier;

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

    @Override
    public IMateriallyTexturedBlockManager getMateriallyTexturedBlockManager()
    {
        return MateriallyTexturedBlockManager.getInstance();
    }

    @Override
    public Supplier<DataComponentType<MaterialTextureData>> getMaterialTextureComponentType()
    {
        return ModDataComponents.TEXTURE_DATA;
    }

    private DomumOrnamentumAPI()
    {
    }
}
