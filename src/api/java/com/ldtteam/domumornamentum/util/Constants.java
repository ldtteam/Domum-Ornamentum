package com.ldtteam.domumornamentum.util;

import net.minecraft.util.ResourceLocation;

public class Constants
{
    private Constants()
    {
        throw new IllegalStateException("Can not instantiate an instance of: Constants. This is a utility class");
    }

    public static final String           MOD_ID                           = "domum_ornamentum";
    public static final String MATERIALLY_TEXTURED_MODEL_LOADER = MOD_ID + ":materially_textured";

    public static class BlockEntityTypes
    {

        public static final String MATERIALLY_RETEXTURABLE = "materially_retexturable";
    }
}
