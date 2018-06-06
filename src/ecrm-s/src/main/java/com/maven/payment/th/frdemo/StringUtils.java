package com.maven.payment.th.frdemo;

/**
 * Created by WIN on 2015/12/10.
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
