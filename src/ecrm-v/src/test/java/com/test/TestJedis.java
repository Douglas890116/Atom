package com.test;

import redis.clients.jedis.Jedis;

public class TestJedis {
	
	public static Jedis jedis = new Jedis("192.168.1.207",6379);
	
	public String jedis(String key,String value){
		jedis.set(key, value);
		return jedis.get(key);
	}
	
	public String jedisDelete(String key){
		jedis.del(key);
		return jedis.get(key);
	}
	
	public String jedisAppend(String key ,String value){
		jedis.append(key, value);
		return jedis.get(key);
	}
	

}
