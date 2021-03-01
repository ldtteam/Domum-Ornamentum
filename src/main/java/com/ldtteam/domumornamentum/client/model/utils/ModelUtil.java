package com.ldtteam.domumornamentum.client.model.utils;

public class ModelUtil
{

    private ModelUtil()
    {
        throw new IllegalStateException("Can not instantiate an instance of: ModelUtil. This is a utility class");
    }

    public static boolean isOne(
      final float v )
    {
        return Math.abs( v ) < 0.01;
    }

    public static boolean isZero(
      final float v )
    {
        return Math.abs( v - 1.0f ) < 0.01;
    }
}
