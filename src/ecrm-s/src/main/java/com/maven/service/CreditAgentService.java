package com.maven.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

/**
 * 信用代理业务接口类
 * @author T.N
 *
 */
@Service
public interface CreditAgentService {
	
	/**
	 * 代理转账给直系下级
	 * @param out_integral_employeecode
	 * @param in_integral_employeecode
	 * @param shiff_money
	 * @return
	 */
	public boolean tc_in_integral(String out_integral_employeecode, String in_integral_employeecode,BigDecimal shiff_money) throws Exception;
	
	/**
	 * 代理提取直系下属账户金额
	 * @param out_integral_employeecode
	 * @param in_integral_employeecode
	 * @param shiff_money
	 * @return
	 * @throws Exception
	 */
	public boolean tc_out_integral(String out_integral_employeecode, String in_integral_employeecode,BigDecimal shiff_money) throws Exception;

}
