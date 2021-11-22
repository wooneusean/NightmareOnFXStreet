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
}
