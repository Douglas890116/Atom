package com.maven.payment.th.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by thinkpad on 2015/3/28.
 */
public class DateUtils {
    public static final String NORMAL_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static boolean validateNormalFormat(String strDate)
    {
        SimpleDateFormat format = new SimpleDateFormat(NORMAL_FORMAT);
        try {
            format.parse(strDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static String format(Date date)
    {
        SimpleDateFormat format = new SimpleDateFormat(NORMAL_FORMAT);
        try {
            return format.format(date);
        } catch (Exception e) {
            return null;
        }
    }
}
