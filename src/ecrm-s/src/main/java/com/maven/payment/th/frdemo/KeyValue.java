package com.maven.payment.th.frdemo;

/**
 * Created by WIN on 2015/12/10.
 */
public class KeyValue {
    private String key;
    private String val;

    public KeyValue(String k, String v)
    {
        this.key = k;
        this.val = v;
    }

    public int compare(KeyValue other)
    {
        return key.compareTo(other.key);
    }

    public String getKey() {
        return key;
    }

    public String getVal() {
        return val;
    }
}
