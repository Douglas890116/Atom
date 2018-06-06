package com.maven.cache;

import com.maven.cache.factory.Cache;
import com.maven.cache.factory.impl.RedisCache;

public class SystemCache {
	
	private static Cache instance;
	
	public  synchronized static Cache getInstance(){
		if(instance==null){
			instance = RedisCache.getInstance();
		}
		return instance;
	}

}
