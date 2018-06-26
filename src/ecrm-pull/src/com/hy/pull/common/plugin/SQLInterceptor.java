package com.hy.pull.common.plugin;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.hy.pull.common.util.ReflectHelper;

/** 
 * 运行时SQL输出
 * 创建日期 2016-10-06
 * @author temdy
 */
@Intercepts( {@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class SQLInterceptor implements Interceptor {
	
	/**
	 * 拦截方法
	 * @param invocation 远程的调用对象
	 * @return Oject 对象
	 */
    public Object intercept(Invocation invocation) throws Throwable {
    	 RoutingStatementHandler statementHandler = (RoutingStatementHandler)invocation.getTarget(); 
    	 BoundSql boundSql = statementHandler.getBoundSql();
    	 BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
    	 MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
    	 System.out.println("\n------------------------开始------------------------");
    	 System.out.println("调用方法： " + mappedStatement.getId());
    	 System.out.println("执行语句： " + boundSql.getSql().replace("\n","").replace(" 		               	", " ").replace("             		", " ").replace("		 ", " ")); 
    	 System.out.println("------------------------结束------------------------");
    	 return invocation.proceed();  
    }

    /**
     * mybatis插件方法
	 * @param arg0  待拦截的对象
	 * @return 自定义的对象
     */
	@Override
    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

	/**
     * 设置属性的方法
	 * @param properties 待设置的属性
     */
	@Override
	public void setProperties(Properties properties) {
	}
}
