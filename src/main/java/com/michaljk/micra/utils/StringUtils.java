package com.michaljk.micra.utils;

import java.util.Objects;

public class StringUtils {

    public static boolean isEmpty(String stringValue) {
        return Objects.isNull(stringValue) || stringValue.isEmpty();
    }

}


