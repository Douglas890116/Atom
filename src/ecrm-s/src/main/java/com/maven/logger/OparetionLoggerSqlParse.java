package com.maven.logger;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.maven.entity.LogOperation;
import com.maven.entity.LogOperationDetail;
import com.maven.util.StringUtils;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

public class OparetionLoggerSqlParse {
	
	private final static  String 
	OPARETE_INSERT = "添加",OPARETE_DELETE = "删除",OPARETE_UPDATE = "修改",OPARETE_SELECT = "查看"; 
	
	public static void setOparetionLog(String sql,LogOperation operation, List<LogOperationDetail> details) throws JSQLParserException {
		CCJSqlParserManager pm = new CCJSqlParserManager();
		try {
			Statement statement = pm.parse(new StringReader(sql));
			if (statement instanceof Update) {
				update(statement, operation, details);
			}else if(statement instanceof Delete){
				delete(statement, operation, details);
			}else if(statement instanceof Insert){
				insert(statement, operation, details);
			}else if(statement instanceof Select){
				select(statement, operation, details);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	
	private static void select(Statement statement, LogOperation operation, List<LogOperationDetail> details){
		Select selectStatement = (Select) statement;
		operation.setOprationtype(OPARETE_SELECT);
		operation.setOprationtime(new Date());
		details.add(new LogOperationDetail(null,null,selectStatement.toString()));
	}
	
	private static void insert(Statement statement, LogOperation operation, List<LogOperationDetail> details){
		Insert insertStatement = (Insert)statement;
		operation.setTablename(insertStatement.getTable().getName());
		operation.setOprationtype(OPARETE_INSERT);
		operation.setOprationtime(new Date());
		List<Column> columns = insertStatement.getColumns();
		ItemsList itemsList = insertStatement.getItemsList();
		if(itemsList instanceof ExpressionList){
			List<Expression>  expressions = ((ExpressionList)itemsList).getExpressions();
			for (int i = 0; i < columns.size(); i++) {
				details.add(new LogOperationDetail(columns.get(i).getColumnName(), 
						StringUtils.trimPointChar(expressions.get(i).toString(),"'"),null));
			}
		}else if (itemsList instanceof MultiExpressionList){
			List<ExpressionList> expressionsList = ((MultiExpressionList)itemsList).getExprList();
			for (ExpressionList el : expressionsList) {
				List<Expression>  expressions = el.getExpressions();
				for (int i = 0; i < columns.size(); i++) {
					details.add(new LogOperationDetail(columns.get(i).getColumnName(), 
							StringUtils.trimPointChar(expressions.get(i).toString(),"'"),null));
				}
			}
		}else{
			Select select = insertStatement.getSelect();
			for (int i = 0; i < columns.size(); i++) {
				details.add(new LogOperationDetail(columns.get(i).getColumnName(),null,StringUtils.trimPointChar(select.toString(),"'")));
			}
		}
	}
	
	
	private static void delete(Statement statement, LogOperation operation, List<LogOperationDetail> details){
		Delete deleteStatement = (Delete) statement;
		operation.setTablename(deleteStatement.getTable().getName());
		operation.setOprationtype(OPARETE_DELETE);
		operation.setOprationtime(new Date());
		Expression where = deleteStatement.getWhere();
		details.add(new LogOperationDetail(null, null, StringUtils.trimPointChar(where.toString(),"'")));
	}

	private static void update(Statement statement, LogOperation operation, List<LogOperationDetail> details) {
		Update updateStatement = (Update) statement;
		List<Expression> expressions = updateStatement.getExpressions();
		List<Column> columns = updateStatement.getColumns();
		StringBuffer tablename = new StringBuffer();
		List<Table> tables = updateStatement.getTables();
		Expression where = updateStatement.getWhere();
		for (Table table : tables) {
			if (tablename.length() != 0) tablename.append(",");
			tablename.append(table.getName());
		}
		operation.setTablename(tablename.toString());
		operation.setOprationtype(OPARETE_UPDATE);
		operation.setOprationtime(new Date());
		for (int i = 0; i < columns.size(); i++) {
			details.add(new LogOperationDetail(columns.get(i).getColumnName(), 
					StringUtils.trimPointChar(expressions.get(i).toString(), "'"),
					StringUtils.trimPointChar(where.toString(),"'")));
		}
	}

	public static void demo(String sql) throws JSQLParserException {
		CCJSqlParserManager pm = new CCJSqlParserManager();
		Statement statement = pm.parse(new StringReader(sql));
		LogOperation operation = new LogOperation();
		List<LogOperationDetail> details = new ArrayList<LogOperationDetail>();
		if (statement instanceof Update) {
			update(statement, operation, details);
		}else if(statement instanceof Delete){
			delete(statement, operation, details);
		}else if(statement instanceof Insert){
			insert(statement, operation, details);
		}else if(statement instanceof Select){
			select(statement, operation, details);
		}
		
		System.out.println("TableName:"+operation.getTablename());
		for (LogOperationDetail od : details) {
			System.out.println("Field:"+od.getFieldname()+"   Value:"+od.getFieldvalue()+ "  Condition:"+od.getConditions());
		}
	}
	public static void main(String[] args) throws JSQLParserException {
		StringBuffer update = new StringBuffer();
		update.append("update ac_operator op ");
		update.append("set op.errcount=(");
		update.append("(select case when op1.errcount is null then 0 else op1.errcount end as errcount ");
		update.append("from ac_operator op1 ");
		update.append("where op1.loginname = '中国' )+1");
		update.append("),lastlogin='中国' ");
		update.append("where PROCESS_ID=");
		update.append("(select distinct g.id from tempTable g where g.ID='中国')");
		update.append("and columnName2 = '890' and columnName3 = '678' and columnName4 = '456'");
		
		String deleteSql = "delete from enterprise_employee where employeecode = '01212'";
		
		String insert = "insert into log_operation_detail(logcode,fieldname,fieldvalue,conditions) values (39,'brandname','百度1','brandcode = EB00001N') , (39,'logopath','http://192.168.1.207:8080/ecrm-pic/uploadfiles/logo/1457524628053.png','brandcode = EB00001N')".replaceAll("\'", "\\'");
		
		String insertSelect = "INSERT INTO enterprise_operating_brand_game( brandcode, gamecode, gamestatus) SELECT eob.brandcode,g.gamecode,'1' FROM (select * from enterprise_operating_brand eob,game g WHERE 1=1)";
		
		String select = "SELECT  ee.employeecode AS employeecode,  ee.displayalias AS displayalias,  ee.loginaccount AS loginaccount,  ee.loginpassword AS loginpassword,  ee.fundpassword AS fundpassword,  ee.parentemployeecode AS parentemployeecode,  ee.parentemployeeaccount AS parentemployeeaccount,  ee.employeetypecode AS employeetypecode,  eet.employeetype AS employeetypename,  ee.employeelevelcode AS employeelevelcode,  eel.employeelevel AS employeelevelname,  ee.brandcode AS brandcode,  eob.brandname AS brandname,  ee.onlinestatus AS onlinestatus,  ee.employeestatus AS employeestatus,  ee.logindatetime AS logindatetime,  ee.lastlogintime AS lastlogintime,  ee.dividend AS dividend, ee.share AS share,  ee.datastatus AS datastatus,  eeca.balance AS balance,  eeca.accumulateddeposit AS accumulateddeposit,  eeca.accumulatedwithdraw AS accumulatedwithdraw FROM ((((enterprise_employee ee  LEFT JOIN enterprise_operating_brand eob  ON ((ee.brandcode = eob.brandcode)))  LEFT JOIN enterprise_employee_type eet ON ((ee.employeetypecode = eet.employeetypecode))) LEFT JOIN enterprise_employee_level eel  ON ((ee.employeelevelcode = eel.employeelevelcode))) LEFT JOIN enterprise_employee_capital_account eeca ON ((ee.employeecode = eeca.employeecode))) where employeecode = 005";
		
		String replace = "replace into employee_gamecataloy_bonus (categorytype, employeecode,parentemployeecode, gametype,bonus) values ('TY', 'E0008WG6','E0000000', 'IBCGame', 0.0) , ('SX', 'E0008WG6','E0000000', 'NHQGame', 0.0) , ('SX', 'E0008WG6','E0000000', 'AGGame', 0.0) , ('DZ', 'E0008WG6','E0000000', 'AGGame', 0.0) , ('CP', 'E0008WG6','E0000000', 'BBINGame', 0.0) , ('SX', 'E0008WG6','E0000000', 'BBINGame', 0.0) , ('TY', 'E0008WG6','E0000000', 'BBINGame', 0.0) , ('DZ', 'E0008WG6','E0000000', 'BBINGame', 0.0) , ('SX', 'E0008WG6','E0000000', 'TAGGame', 0.0) , ('DZ', 'E0008WG6','E0000000', 'TAGGame', 0.0) , ('CP', 'E0008WG6','E0000000', 'XCPGame', 0.0) , ('SX', 'E0008WG6','E0000000', 'OGGame', 0.0) , ('SX', 'E0008WG6','E0000000', 'PTGame', 0.0) , ('DZ', 'E0008WG6','E0000000', 'PTGame', 0.0)";
		
		try {
			CCJSqlParserManager pm = new CCJSqlParserManager();
			pm.parse(new StringReader(replace));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		demo(update.toString());
		demo(deleteSql.toString());
		demo(insert.toString());
		demo(insertSelect.toString());
		demo(select.toString());
	}
}
