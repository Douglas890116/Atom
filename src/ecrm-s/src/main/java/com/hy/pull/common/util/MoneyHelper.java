package com.hy.pull.common.util;

import java.text.NumberFormat;

/**
 * <p>
 * Title: 货币格式输出类
 * </p>
 * <p>
 * Description: 小写金额转换成汉字金额输出 以及控制金额输出格式
 * </p>
 * 
 * @version 1.0
 */

public final class MoneyHelper {
	private MoneyHelper() {
	}


	/**
	 * 实现两位小数点输出(String),有逗号分隔
	 * 
	 * @param dou
	 *            金额
	 * @return 格式化之后的结果
	 */
	public static String moneyFormat(double dou) {
		NumberFormat n = NumberFormat.getInstance();
		n.setMaximumFractionDigits(2);
		n.setMinimumFractionDigits(2);
		return n.format(dou);
	}

	/**
	 * 实现两位小数点输出(String),没有逗号分隔
	 * 
	 * @param dou
	 *            金额
	 * @return 格式化之后的结果
	 */
	public static String doubleFormat(String dou) {
		java.text.DecimalFormat df = new java.text.DecimalFormat(
				"###0.00;-###0.00");
		return df.format(Double.parseDouble(dou));
	}
	/**
	 * 实现两位小数点输出(String),没有逗号分隔
	 * 
	 * @param dou
	 *            金额
	 * @return 格式化之后的结果
	 */
	public static String doubleFormat(Object dou) {
		if(dou == null || dou.toString().equals("")) {
			return "0.00";
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat(
				"###0.00;-###0.00");
		return df.format(Double.parseDouble(dou.toString()));
	}
	/**
	 * 实现两位小数点输出(String),没有逗号分隔
	 * 
	 * @param dou
	 *            金额
	 * @return 格式化之后的结果
	 */
	public static String doubleFormat(double dou) {
		java.text.DecimalFormat df = new java.text.DecimalFormat(
				"###0.00;-###0.00");
		return df.format(dou);
	}

	/**
	 * 实现一位小数点输出(String),没有逗号分隔
	 * 
	 * @param dou
	 *            金额
	 * @return 格式化之后的结果
	 */
	public static String doubleFormat1(double dou) {
		java.text.DecimalFormat df = new java.text.DecimalFormat(
				"###0.0;-###0.0");
		return df.format(dou);
	}

	/**
	 * 实现指定格式金额输出,建议不做显示使用，只作为计算使用
	 * 
	 * @param dou
	 *            金额
	 * @param formater
	 *            格式
	 * @return 格式化之后的结果
	 */
	public static double moneyFormatDouble(double dou, String formater) {
		java.text.DecimalFormat df = new java.text.DecimalFormat(formater);

		double _dou = dou;
		if (_dou < 0) {
			_dou = -_dou;
			return -Double.parseDouble(df.format(_dou));
		} else {
			return Double.parseDouble(df.format(dou));
		}

	}

	/**
	 * 实现两位小数点输出(double),建议不做显示使用，只作为计算使用
	 * 
	 * @param dou
	 *            金额
	 * @return 四舍五入之后的结果
	 */
	public static double moneyFormatDouble(double dou) {
		return moneyFormatDouble(Math.round(dou * 100) / (double) 100,"####0.00");
	}

	/**
	 * 实现整数输出,精确到元,建议不做显示使用，只作为计算使用
	 * 
	 * @param dou
	 *            金额
	 * @return 输出的结果
	 */
	public static double moneyFormatInt(double dou) {
		return moneyFormatDouble(dou, "####0");
	}

	/**
	 * 显示负数的两位小数点格式化输出
	 * 
	 * @param dou
	 *            金额
	 * @return 格式化之后的结果
	 */
	public static String moneyFormatString(double dou) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("####0.00");
		double _dou = dou;
		if (_dou < 0) {
			_dou = -_dou;
			return "-" + df.format(_dou);
		} else {
			return df.format(dou);
		}

	}

	/**
	 * 保留四位小数点的格式输出
	 * 
	 * @param dou
	 *            金额
	 * @return 格式文本
	 */
	public static String moneyFormatString4(double dou) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("####0.0000");
		double _dou = dou;
		if (_dou < 0) {
			_dou = -_dou;

			return "-" + df.format(_dou);
		} else {
			return df.format(dou);
		}

	}

	/**
	 * 四位小数点的四舍五入
	 * 
	 * @param dou
	 *            金额
	 * @return 四舍五入之后的金额
	 */
	public static double moneyFormatDouble4(double dou) {
		java.text.DecimalFormat df1 = new java.text.DecimalFormat("####0.0000");
		return Double.parseDouble(df1.format(dou));
	}
	
	/**
	 * 俩位小数点的四舍五入
	 * 
	 * @param dou
	 *            金额
	 * @return 四舍五入之后的金额
	 */
	public static double moneyFormatDouble2(double dou) {
		java.text.DecimalFormat df1 = new java.text.DecimalFormat("####0.00");
		return Double.parseDouble(df1.format(dou));
	}

	
	/**
	 * 实现普通整数输出
	 * 
	 * @param i
	 *            整数
	 * @return 字符串
	 */
	public static String intFormat(int i) {
		NumberFormat n = NumberFormat.getInstance();
		n.setMaximumFractionDigits(0);
		n.setMinimumFractionDigits(0);
		return n.format(i);
	}
}

