package com.michaljk.micra.utils;

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

    public static Date addMonthsToDate(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }
    public static Date addMonthsToCurrentDate(int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        System.out.println(getCurrentYear());
        System.out.println(getCurrentMonth());
        System.out.println(addMonthsToCurrentDate(3));
    }
}


