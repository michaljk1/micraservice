package com.michaljk.micra.utils;

public class MathUtils {

    public static double roundDouble(double number){
        return Math.round(number * 100.0) / 100.0;
    }
}
