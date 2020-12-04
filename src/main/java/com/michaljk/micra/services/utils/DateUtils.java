package com.michaljk.micra.services.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static Integer getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static String getCurrentMonth() {
        return Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
    }

    public static void main(String[] args) {
        System.out.println(getCurrentYear());
        System.out.println(getCurrentMonth());
    }
}


