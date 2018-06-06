package com.maven.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectSerialize {
	
	/**
	 * 序列化
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object){
		try {
			if(object==null) return null;
			
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			ObjectOutputStream outStream = new ObjectOutputStream(byteStream);
			outStream.writeObject(object);
			return byteStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 /**
     * 反序列化
     * 
     * @param bytes
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T unserialize(byte[] bytes) {
        try {
        	if(bytes==null) return null;
        	
        	ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ((T)ois.readObject());
        } catch(NullPointerException e){
        	return null;
        }catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }

}
