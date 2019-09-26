package org.bugapi.bugset.base.util.validate;

import org.bugapi.bugset.base.constant.PatternType;

/**
 * 校验工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class ValiateUtil {

	/**
	 * 判断字符串是否整数
	 *
	 * @param str 字符串
	 * @return boolean true:是数字
	 */
	public static boolean isIntegerNumber(String str) {
		return PatternType.INTEGER_PATTERN.matcher(str).matches();
	}

	public static void main(String[] args) {
		System.out.println(isIntegerNumber("00"));
		System.out.println(isIntegerNumber("-"));
		System.out.println(isIntegerNumber("01"));
		System.out.println(isIntegerNumber("-01"));
		System.out.println(isIntegerNumber("-1"));
		System.out.println(isIntegerNumber("9999"));
	}
}
