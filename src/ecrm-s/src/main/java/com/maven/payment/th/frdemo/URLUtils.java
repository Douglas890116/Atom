package com.maven.payment.th.frdemo;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by WIN on 2015/12/10.
 */
public class URLUtils {

    public static String encode(String str, String charset)
    {
        try {
            if (str != null)
                return URLEncoder.encode(str, charset);
            else
                return null;
        } catch (Exception e) {
            return str;
        }
    }

    public static String decode(String str, String charset)
    {
        try {
            if (str != null)
                return URLDecoder.decode(str, charset);
            else
                return null;
        } catch (Exception e) {
            return str;
        }
    }

    public static void appendParam(StringBuilder sb, String name, String val)
    {
        appendParam(sb, name, val, true);
    }

    public static void appendParam(StringBuilder sb, String name, String val, String charset)
    {
        appendParam(sb, name, val, true, charset);
    }

    public static void appendParam(StringBuilder sb, String name, String val, boolean and)
    {
        appendParam(sb, name, val, and, null);
    }

    public static void appendParam(StringBuilder sb, String name, String val, boolean and, String charset)
    {
        if (and)
            sb.append("&");
        else
            sb.append("?");
        sb.append(name);
        sb.append("=");
        if (val == null)
            val = "";
        if (StringUtils.isNullOrEmpty(charset))
            sb.append(val);
        else
            sb.append(encode(val, charset).replaceAll("\\+", "%20"));
    }
}
