package com.ldtteam.domumornamentum.util;

import net.minecraft.resources.ResourceLocation;

public class Constants
{

    private Constants()
    {
        throw new IllegalStateException("Can not instantiate an instance of: Constants. This is a utility class");
    }

    public static final String           MOD_ID                           = "domum_ornamentum";
    public static final ResourceLocation MATERIALLY_TEXTURED_MODEL_LOADER = resLocDO("materially_textured");
    public static final ResourceLocation TRAPDOOR_MODEL_OVERRIDE = resLocDO("trapdoor_type");
    public static final ResourceLocation DOOR_MODEL_OVERRIDE = resLocDO("door_type");
    public static final ResourceLocation POST_MODEL_OVERRIDE = resLocDO("post_type");
    public static final String DEFAULT_LANG = "en_us";
    public static final String BLOCK_ENTITY_TEXTURE_DATA = "textureData";
    public static final String TYPE_BLOCK_PROPERTY = "type";

    public static ResourceLocation resLocDO(final String path)
    {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static class BlockEntityTypes
    {

        public static final ResourceLocation MATERIALLY_RETEXTURABLE = resLocDO("materially_retexturable");
    }
}
