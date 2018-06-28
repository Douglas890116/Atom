package com.maven.base.dao;



import java.util.logging.Logger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {


	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceHolder.getDataSouce();
	}

	public Logger getParentLogger() {
		// TODO Auto-generated method stub
		return null;
	}

	

}

