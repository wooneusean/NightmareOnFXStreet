package com.oodj.vaccspace.utils;

import javafx.scene.paint.Color;

public class ColorHelper {
    public static String toRGBAString(Color color) {
        return String.format(
                "rgba(%f, %f, %f, %f)",
                color.getRed() * 255,
                color.getGreen() * 255,
                color.getBlue() * 255,
                color.getOpacity()
        );
    }

    public static Color hexToRGB(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16) / 255f,
                Integer.valueOf(colorStr.substring(3, 5), 16) / 255f,
                Integer.valueOf(colorStr.substring(5, 7), 16) / 255f,
                1f
        );
    }
}
