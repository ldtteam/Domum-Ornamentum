package com.ldtteam.domumornamentum;

import com.ldtteam.domumornamentum.api.DomumOrnamentumAPI;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class DomumOrnamentum
{
    public DomumOrnamentum()
    {
        IDomumOrnamentumApi.Holder.setInstance(DomumOrnamentumAPI.getInstance());
    }
}
