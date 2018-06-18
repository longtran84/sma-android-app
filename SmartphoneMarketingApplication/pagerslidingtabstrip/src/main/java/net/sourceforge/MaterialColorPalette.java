package net.sourceforge;

import java.util.ArrayList;
import java.util.List;

public class MaterialColorPalette {

    public static final int RED_500 = 0xFFF44336;
    public static final int PINK_500 = 0xFFE91E63;
    public static final int PURPLE_500 = 0xFF9C27B0;
    public static final int DEEP_PURPLE_500 = 0xFF673AB7;
    public static final int INDIGO_500 = 0xFF3F51B5;
    public static final int BLUE_500 = 0xFF2196F3;
    public static final int LIGHT_BLUE_500 = 0xFF03A9F4;
    public static final int CYAN_500 = 0xFF00BCD4;
    public static final int TEAL_500 = 0xFF009688;
    public static final int GREEN_500 = 0xFF4CAF50;
    public static final int LIGHT_GREEN_500 = 0xFF8BC34A;
    public static final int LIME_500 = 0xFFCDDC39;
    public static final int YELLOW_500 = 0xFFFFEB3B;
    public static final int AMBER_500 = 0xFFFFC107;
    public static final int ORANGE_500 = 0xFFFF9800;
    public static final int DEEP_ORANGE_500 = 0xFFFF5722;
    public static final int BROWN_500 = 0xFF795548;
    public static final int GREY_500 = 0xFF9E9E9E;
    public static final int BLUE_GREY_500 = 0xFF607D8B;

    private static final List<Integer> MATERIAL_PALETTES;

    static {
        MATERIAL_PALETTES = new ArrayList<Integer>();
        MATERIAL_PALETTES.add((RED_500));
        MATERIAL_PALETTES.add((PINK_500));
        MATERIAL_PALETTES.add((PURPLE_500));
        MATERIAL_PALETTES.add((DEEP_PURPLE_500));
        MATERIAL_PALETTES.add((INDIGO_500));
        MATERIAL_PALETTES.add((BLUE_500));
        MATERIAL_PALETTES.add((LIGHT_BLUE_500));
        MATERIAL_PALETTES.add((CYAN_500));
        MATERIAL_PALETTES.add((TEAL_500));
        MATERIAL_PALETTES.add((GREEN_500));
        MATERIAL_PALETTES.add((LIGHT_GREEN_500));
        MATERIAL_PALETTES.add((LIME_500));
        MATERIAL_PALETTES.add((YELLOW_500));
        MATERIAL_PALETTES.add((AMBER_500));
        MATERIAL_PALETTES.add((ORANGE_500));
        MATERIAL_PALETTES.add((DEEP_ORANGE_500));
        MATERIAL_PALETTES.add((BROWN_500));
        MATERIAL_PALETTES.add((GREY_500));
        MATERIAL_PALETTES.add((BLUE_GREY_500));
    }

    public static int getRandomColor(int key) {
        return MATERIAL_PALETTES.get(key);
    }


}
