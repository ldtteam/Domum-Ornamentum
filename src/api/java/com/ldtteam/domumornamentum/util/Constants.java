package com.ldtteam.domumornamentum.util;

public class Constants
{

    private Constants()
    {
        throw new IllegalStateException("Can not instantiate an instance of: Constants. This is a utility class");
    }

    public static final String           MOD_ID                           = "domum_ornamentum";
    public static final String MATERIALLY_TEXTURED_MODEL_LOADER = "materially_textured";
    public static final String TRAPDOOR_MODEL_OVERRIDE = MOD_ID + ":trapdoor_type";
    public static final String DOOR_MODEL_OVERRIDE = MOD_ID + ":door_type";
    public static final String POST_MODEL_OVERRIDE = MOD_ID + ":post_type";
    public static final String DEFAULT_LANG = "en_us";

    public static class BlockEntityTypes
    {

        public static final String MATERIALLY_RETEXTURABLE = "materially_retexturable";
    }
}
