package com.calicode.gymapp.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {

    public static final String DAY_MONTH_YEAR_WITH_DOTS = "dd.MM.yyyy";
    public static final String YEAR_MONTH_DAY_WITH_DASHES = "yyyy-MM-dd";

    public static String changeDateStringFormat(String dateStr, String oldFormat, String newFormat) {
        String formattedStr = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(oldFormat);
            Date d = simpleDateFormat.parse(dateStr);
            simpleDateFormat.applyPattern(newFormat);
            formattedStr = simpleDateFormat.format(d);
        } catch (ParseException ex) {
            Log.error("Date parsing failed", ex);
        }
        return formattedStr;
    }
}
