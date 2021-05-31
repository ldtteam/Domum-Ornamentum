package com.ldtteam.domumornamentum.util;

import net.minecraft.util.ResourceLocation;

public class Constants
{
    private Constants()
    {
        throw new IllegalStateException("Can not instantiate an instance of: Constants. This is a utility class");
    }

    public static final String           MOD_ID                           = "domum-ornamentum";
    public static final ResourceLocation MATERIALLY_TEXTURED_MODEL_LOADER = new ResourceLocation(MOD_ID, "materially_textured");

    public static class BlockEntityTypes
    {

        public static final String TIMBER = "timber";
    }
}
