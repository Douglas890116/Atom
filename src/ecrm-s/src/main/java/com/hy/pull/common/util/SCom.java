package com.hy.pull.common.util;

import java.util.Random;

public class SCom {

	
	public static String getString(Object o) {

		String strv = "";
		try {

			if (o == null) {
				strv = "";
			} else {
				strv = (o.toString());
			}
		} catch (Exception ex) {

		}

		return strv;
	}
	
	/**
	 * 产生 min～max的随机整�? 包括 min 但不包括 max
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandom(int min, int max) {
		int r = getRandom(max - min);
		return r + min;
	}

	/**
	 * 产生0～max的随机整�? 包括0 不包括max
	 * 
	 * @param max
	 *            随机数的上限
	 * @return
	 */
	public static int getRandom(int max) {
		return new Random().nextInt(max);
	}

	public static int getIntValue(Object obj) {
		if (obj == null || obj.equals(""))
			return 0;
		else
			return Integer.parseInt(obj.toString());
	}

	/**
	 * 尝试把Object转换为double，失败返�?0.0，不报错
	 * 
	 * @param obj
	 *            Object
	 * @return double
	 */
	public static double getDoubleValue(Object obj) {
		try {
			return Double.parseDouble(obj.toString());
		} catch (Exception e) {
			return 0.0;
		}
	}
	/**
	 * �?测输入字符串是否整数
	 * 
	 * @param strv
	 *            String 输入字符�?
	 * @return int 如果输入字符串是整数,则把字符串转换为整数,并返�?
	 */
	public static int getInt(Object str) {
		String strv = getTrimString(str);
		int iret = 0;
		if (strv == null) {
			iret = 0;
		} else {
			try {
				if (strv.indexOf(".") > 0) {
					strv = strv.substring(0, strv.indexOf("."));
				}
				iret = Integer.parseInt(strv);
			} catch (Exception e) {
				iret = 0;
			}
		}
		return iret;
	}

	public static long getLong(Object str) {
		String strv = getTrimString(str);
		long iret = 0;
		if (strv == null) {
			iret = 0;
		} else {
			try {
				if (strv.indexOf(".") > 0) {
					strv = strv.substring(0, strv.indexOf("."));
				}
				iret = Long.parseLong(strv);
			} catch (Exception e) {
				iret = 0;
			}
		}
		return iret;
	}

	/**
	 * �?测输入对象是否整�?
	 * 
	 * @param obj
	 *            Object 输入对象
	 * @return String 如果输入对象是整�?,则把输入对象转换为整�?,并返�?
	 */
	public static String getIntString(Object obj) {
		int iret = 0;
		if (obj == null) {
			iret = 0;
		} else {
			try {
				String strv = (String) obj;
				if (strv.indexOf(".") > 0) {
					strv = strv.substring(0, strv.indexOf("."));
				}
				iret = Integer.parseInt(strv);
			} catch (Exception e) {
				iret = 0;
			}
		}
		return String.valueOf(iret);
	}
	public static double getDouble(Object str) {

		String strv = getTrimString(str);
		double dret = 0;
		if (strv == null || strv.equals("null") || strv.equals("")) {
			dret = 0;
		} else {
			try {

				dret = Double.parseDouble(strv);
			} catch (Exception e) {
				System.err.println("str=" + str);
				e.printStackTrace();
				dret = 0;
			}
		}
		return dret;

	}
	
	public static String getTrimString(Object o) {
		String strv = "";
		if (o == null) {
			strv = "0";
		} else if (o == "") {
			strv = "0";
		} else if (o == "null") {
			strv = "0";
		} else {
			strv = o.toString().trim();
		}

		return strv;
	}
}
