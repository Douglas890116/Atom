package com.maven.payment.th.utils;

/**
 * Created by thinkpad on 2015/3/28.
 */
public class StringUtils {
    public static boolean isNullOrEmpty(String str)
    {
        if (str == null || "".equals(str))
            return true;
        else
            return false;
    }
}
