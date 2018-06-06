package com.test;

import java.util.HashMap;
import java.util.Map;

import com.maven.config.SystemConstant;
import com.maven.entity.Bank;
import com.maven.util.JSONUnit;

import junit.framework.TestCase;
import redis.clients.jedis.Jedis;

public class TestJedisTest extends TestCase {

	public void testJedis() {
		TestJedis jedis = new TestJedis();
		String value = jedis.jedis("testJedis","test reidis read and wirte");
		System.out.println(value);
	}
	
	public void testJedisDelete(){
		TestJedis jedis = new TestJedis();
		String value = jedis.jedisDelete("testJedis");
		System.out.println(value);
	}
	
	public void testJedisAppend(){
		TestJedis jedis = new TestJedis();
		String value = jedis.jedisAppend("testJedis"," add successful");
		System.out.println(value);
	}
	
	public void testMap(){
		Jedis jedis = new Jedis(SystemConstant.getProperties("cache.server.ip"),Integer.parseInt(SystemConstant.getProperties("cache.server.port")));
		jedis.auth(SystemConstant.getProperties("cache.server.auth"));
		Map<String,String> object = new HashMap<String, String>();
		Bank bank = new Bank();
		bank.setBankcode("B00001");
		bank.setBankname("中山银行");
		bank.setBankurl("E://eee");
		object.put("B00001", JSONUnit.getJSONString(bank));
		jedis.hmset("banks", object);
		String value = jedis.hget("banks", "B00001");
		Bank b = (Bank)JSONUnit.getDTO(value, Bank.class);
		System.out.println(value);
		System.out.println(b.getBankname());
		jedis.close();
	}

}
