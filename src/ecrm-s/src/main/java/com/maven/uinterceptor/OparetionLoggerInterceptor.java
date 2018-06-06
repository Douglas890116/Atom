package com.maven.uinterceptor;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.maven.entity.LogOperation;
import com.maven.entity.LogOperationDetail;
import com.maven.logger.OparetionLoggerSqlParse;
import com.maven.logger.TLogger;
import com.maven.logger.ThreadOparetionLogger;
import com.maven.service.LogOperationService;
import com.maven.utility.SpringContextHolder;

/**
 * @Signature(method = "update", type = Executor.class, args =
 *                   {MappedStatement.class, Object.class}),
 * @Signature(method = "query", type = StatementHandler.class, args = {
 *                   Statement.class,ResultHandler.class }),
 * @Signature(method = "getParameterObject", type = ParameterHandler.class, args
 *                   = {}),
 * @Signature(method = "handleOutputParameters", type = ResultSetHandler.class,
 *                   args = { CallableStatement.class })
 *
 */
@Intercepts({ @Signature(method = "update", type = Executor.class, args = { MappedStatement.class, Object.class }) })
public class OparetionLoggerInterceptor implements Interceptor {
	
	private static org.apache.logging.log4j.Logger logger = 
			org.apache.logging.log4j.LogManager.getLogger(OparetionLoggerInterceptor.class);
	/**
	 * 过滤的数据表
	 */
	private List<String> excludedUrls = new ArrayList<String>(); 

	public Object intercept(Invocation invocation) throws Throwable {
		if(ThreadOparetionLogger.getOperation()==null) return invocation.proceed();
		
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = null;
		if (invocation.getArgs().length > 1) {
			parameter = invocation.getArgs()[1];
		}
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		Configuration configuration = mappedStatement.getConfiguration();
		Object value = invocation.proceed();
		String sql = getSql(configuration, boundSql);
		System.err.println(sql);
		writeLogger(sql);
		return value;
	}

	private void writeLogger(String sql) throws Exception {
		LogOperation logSetting = ThreadOparetionLogger.getOperation().clone();
		if(logSetting!=null){
			List<LogOperationDetail> details = new ArrayList<LogOperationDetail>();
			OparetionLoggerSqlParse.setOparetionLog(sql, logSetting, details);
			if(!excludedUrls.contains(logSetting.getTablename().trim())){
				LogOperationService service = SpringContextHolder.getBean("logOperationServiceImpl");
				try {
					service.saveLog(logSetting, details);
				} catch (Exception e) {
					TLogger.getLogger().Error(e.getMessage(),e);
					logger.error(logString(logSetting, details));
				}
			}
		}
	}

	private StringBuffer logString(LogOperation logSetting, List<LogOperationDetail> details) {
		StringBuffer s = new StringBuffer();
		s.append(logSetting.getOprationperson()).append("\t")
		.append(logSetting.getTablename()).append("\t")
		.append(logSetting.getServicename()).append("\t")
		.append(logSetting.getOprationtype()).append("\t")
		.append(logSetting.getOprationtime()).append("\t")
		.append(logSetting.getVisiteurl()).append("\t");
		for (LogOperationDetail detail : details) {
			s.append(detail.getFieldname()).append("\t")
			.append(detail.getFieldvalue()).append("\t");
		}
		s.append(details.size()>0?details.get(0).getConditions():"");
		return s;
	}
	
	private static String getSql(Configuration configuration, BoundSql boundSql) {
		return showSql(configuration, boundSql);
	}

	private static String getParameterValue(Object obj) {
		String value = null;
		if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		} else if (obj instanceof Date) {
			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
			value = "'" + formatter.format(new Date()) + "'";
		} else {
			if (obj != null) {
				value = obj.toString();
			} else {
				value = "null";
			}

		}
		return value;
	}

	private static String showSql(Configuration configuration, BoundSql boundSql) {
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
		if (parameterMappings.size() > 0 && parameterObject != null) {
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
			} else {
				MetaObject metaObject = configuration.newMetaObject(parameterObject);
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						Object obj = metaObject.getValue(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						Object obj = boundSql.getAdditionalParameter(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					}
				}
			}
		}
		return sql;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		if(properties!=null){
			for (Object s : properties.values()) {
				excludedUrls.add(s.toString());
			}
		}
	}
}