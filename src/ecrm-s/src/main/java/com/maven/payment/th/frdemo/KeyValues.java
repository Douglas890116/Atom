package com.maven.payment.th.frdemo;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by WIN on 2015/12/10.
 */
public class KeyValues {
    private String keyName = "key";

    private List<KeyValue> keyValues = new LinkedList<KeyValue>();

    public List<KeyValue> items()
    {
        return keyValues;
    }

    public static KeyValues create()
    {
        return new KeyValues();
    }

    public KeyValues add(String k, String v)
    {
        return add(new KeyValue(k, v));
    }

    public KeyValues add(KeyValue kv)
    {
        if (kv != null && !StringUtils.isNullOrEmpty(kv.getVal()))
            keyValues.add(kv);
        return this;
    }

    public KeyValues setKeyName(String kn)
    {
        if (!StringUtils.isNullOrEmpty(kn))
            keyName = kn;
        return this;
    }

    public String sign(String keyVal)
    {
        return sign(keyVal, null);
    }

    public String sign(String keyVal, String inputCharset)
    {
        return sign(null, keyVal, inputCharset);
    }

    public String sign(String kn, String kv, String inputCharset)
    {
        if (StringUtils.isNullOrEmpty(kn))
            kn = keyName;
        StringBuilder sb = new StringBuilder();
        Collections.sort(keyValues, new Comparator<KeyValue>(){
            public int compare(KeyValue l, KeyValue r) {
                return  l.compare(r);
            }
        });
        for (KeyValue _kv : keyValues)
        {
            URLUtils.appendParam(sb, _kv.getKey(), _kv.getVal());
        }
        URLUtils.appendParam(sb, kn, kv);
        String s = sb.toString();
        s = s.substring(1, s.length());
        if (StringUtils.isNullOrEmpty(inputCharset))
        {
            return MD5Encoder.encode(s);
        }
        else
        {
            return MD5Encoder.encode(s, inputCharset);
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("KeyValues:[");
        int i = 1;
        int s = keyValues.size();
        for (KeyValue kv : keyValues)
        {
            sb.append("{").append(kv.getKey()).append(",").append("").append(kv.getVal()).append("}");
            if (i < s)
                sb.append(",");
            i++;
        }
        sb.append("]");
        return sb.toString();
    }
}
