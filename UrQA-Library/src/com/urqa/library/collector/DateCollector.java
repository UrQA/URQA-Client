package com.urqa.library.collector;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public final class DateCollector {

    public static String getDate() {
        return getDate("yyyy-MM-dd HH:mm:ss");
    }

    private static String getDate(String pattern) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return new SimpleDateFormat(pattern).format(calendar.getTime());
    }
}
