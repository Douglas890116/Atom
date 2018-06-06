package com.maven.cache;

import java.util.Map;

import com.maven.config.RedisProperties;
import com.maven.logger.TLogger;
import com.maven.util.BeanToMapUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

public class RemoteCache {
	private static JedisPool jedispool;

	static {
		try {
			Map<String, Object> properties = RedisProperties.getJedisPoolProperties();
			TLogger.getLogger().Error("redis配置信息: " + properties);
			JedisPoolConfig config = BeanToMapUtil.convertMap(properties, JedisPoolConfig.class);
			String ip = RedisProperties.getProperties("reids.server.ip");
			Integer port = Integer.parseInt(RedisProperties.getProperties("reids.server.port"));
			TLogger.getLogger().Error("redis ip:" + ip + " port:" + port);
			jedispool = new JedisPool(config, ip, port);
			if (jedispool != null)
				System.out.println("获取Redis池成功!");
			else
				System.err.println("获取Redis池失败!");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Jedis配置加载失败...");
			TLogger.getLogger().Error("Jedis配置加载失败...");
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public static Jedis getJedis() {
		Jedis jedis = jedispool.getResource();
		jedis.auth(RedisProperties.getProperties("reids.server.auth"));
		return jedis;
	}

	/**
	 * 返还Jedis实例
	 * 
	 * @param jedis
	 */
	public static void returnJedis(Jedis jedis) {
		if (jedis != null) jedis.close();
	}

	/**
	 * 获取当前活动连接数
	 * 
	 * @return
	 */
	public static int getActiv() {
		return jedispool.getNumActive();
	}

	public static boolean handleJedisException(JedisException e) {
		if (e instanceof JedisDataException) {
			if ((e.getMessage() != null) && (e.getMessage().indexOf("READONLY") != -1)) {
			} else {
				return false;
			}
		}
		return true;
	}
}