package com.ldtteam.domumornamentum.util;

/**
 * Magic numbers for the GUI.
 */
public class GuiConstants
{
    // Main background texture
    public static final int CUTTER_BG_W = 242;
    public static final int CUTTER_BG_H = 202;

    // Input slots
    public static final int CUTTER_INPUT_X = 96;
    public static final int CUTTER_INPUT_Y = 66;
    public static final int CUTTER_INPUT_SPACING = 20;

    // Output slots
    public static final int CUTTER_OUTPUT_X = 183;
    public static final int CUTTER_OUTPUT_Y = 77;

    // Slot texture
    public static final int CUTTER_SLOT_U = 16 * 3;
    public static final int CUTTER_SLOT_V = CUTTER_BG_H;
    public static final int CUTTER_SLOT_W = 18;
    public static final int CUTTER_SLOT_H = 18;

    // Recipe selector buttons
    public static final int CUTTER_RECIPE_X = 8 + 49;
    public static final int CUTTER_RECIPE_Y = 16;
    public static final int CUTTER_RECIPE_SPACING = 23;

    // Recipe selector button textures
    public static final int CUTTER_RECIPE_U_SELECTED = 0;
    public static final int CUTTER_RECIPE_U_HOVERED = 16;
    public static final int CUTTER_RECIPE_U_NORMAL = 32;
    public static final int CUTTER_RECIPE_V = CUTTER_BG_H;
    public static final int CUTTER_RECIPE_W = 16;
    public static final int CUTTER_RECIPE_H = 18;

    // Scroll slider location
    public static final int CUTTER_SLIDER_X = 220;
    public static final int CUTTER_SLIDER_Y = CUTTER_RECIPE_Y + 1;

    // Scroll slider texture
    public static final int CUTTER_SLIDER_U_ENABLED = 0;
    public static final int CUTTER_SLIDER_U_DISABLED = 12;
    public static final int CUTTER_SLIDER_V = 220;
    public static final int CUTTER_SLIDER_W = 12;
    public static final int CUTTER_SLIDER_H = 15;

    private GuiConstants()
    {
        // do not construct
    }
}
