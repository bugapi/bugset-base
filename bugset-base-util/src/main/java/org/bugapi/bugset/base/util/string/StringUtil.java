package org.bugapi.bugset.base.util.string;

import org.bugapi.bugset.base.constant.SymbolType;

/**
 * 字符串工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class StringUtil {
	/**
	 * 判断字符串为空
	 *
	 * @param str 字符串
	 * @return boolean 【true：字符串为null、字符串由空白字符组成】
	 */
	public static boolean isEmpty(CharSequence str) {
		return null == str || trim(str, 0).length() == 0;
	}

	/**
	 * 判断字符串不为空
	 *
	 * @param str 字符串
	 * @return boolean 【false：字符串为null、字符串由空白字符组成】
	 */
	public static boolean isNotEmpty(CharSequence str) {
		return null != str && !"".equals(trim(str, 0));
	}

	/**
	 * 被检测字符为null或者字符中包含空白符【空白符包括空格、制表符、全角空格和不间断空格】
	 *
	 * @param str 被检测字符串
	 * @return boolean 【true：字符串为空、字符串中包含空白字符】
	 */
	public static boolean isBlank(CharSequence str) {
		int length;
		if (str == null || (length = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < length; i++) {
			// 只要有一个非空字符即为非空字符串
			if (isBlankChar(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 被检测字符为null或者字符中不包含空白符【空白符包括空格、制表符、全角空格和不间断空格】
	 *
	 * @param str 被检测字符串
	 * @return boolean 【false：字符串为空、字符串中包含空白字符】
	 */
	public static boolean isNotBlank(CharSequence str) {
		int length;
		if (str == null || (length = str.length()) == 0) {
			return false;
		}
		for (int i = 0; i < length; i++) {
			// 只要有一个非空字符即为非空字符串
			if (isBlankChar(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 将字符串为null的字符串转成空字符串""
	 *
	 * @param str 字符串
	 * @return String 返回的字符串
	 */
	public static String nullToEmpty(CharSequence str) {
		return nullToEmpty(str, SymbolType.EMPTY);
	}

	/**
	 * 字符串为null则返回""，否则返回自身。
	 *
	 * @param str        要转换的字符串
	 * @param defaultStr 默认字符串
	 * @return String 返回的字符串
	 */
	public static String nullToEmpty(CharSequence str, String defaultStr) {
		return (str == null) ? defaultStr : str.toString();
	}

	/**
	 * 字符串为null或者由空白字符组成则返回null，否则返回自身。
	 *
	 * @param str 要转换的字符串
	 * @return String 返回的字符串
	 */
	public static String emptyToNull(CharSequence str) {
		return isEmpty(str) ? null : str.toString();
	}

	/* ---------------------------------------------     以上是字符串的空判断     ------------------------------------ */


	/**
	 * 将字符串两边去空格后，然后在将首字母转成小写的字符串
	 *
	 * @param str 待转字符串
	 * @return String 转后的字符串
	 */
	public static String firstCharLowCase(String str) {
		str = trim(str, 0);
		if (isNotEmpty(str)) {
			return str.substring(0, 1).toLowerCase() + str.substring(1);
		} else {
			return str;
		}
	}

	/**
	 * 将字符串两边去空格后，然后在将首字母转成大写的字符串
	 *
	 * @param str 待转字符串
	 * @return String 转后的字符串
	 */
	public static String firstCharUpCase(String str) {
		str = trim(str, 0);
		if (isNotEmpty(str)) {
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		} else {
			return str;
		}
	}

	/* ----------------------------------------  以上是字符串的首字母大小写的转换  -------------------------------------- */

	/**
	 * 为排序的查询拼接排序字段和排序类型
	 *
	 * @param sortField 参加排序的字段
	 * @param order 排序的类型
	 * @return String 拼接后的字符串
	 */
	public static String joinSortFieldOrder(String sortField, String order) {
		StringBuilder orderFiled = new StringBuilder();
		if (isNotEmpty(sortField)) {
			orderFiled.append(sortField);
			if (isNotEmpty(order)) {
				orderFiled.append(" ").append(order);
			}
		}
		return orderFiled.toString();
	}

	/**
	 * 为多字段排序的查询拼接排序字段和排序类型
	 *
	 * @param sortFields 参加排序的字段数组
	 * @param orders 排序的类型数组
	 * @return String 为多字段排序的查询拼接排序字段和排序类型
	 */
	public static String joinSortFieldOrder(String[] sortFields, String[] orders) {
		StringBuilder orderFiled = new StringBuilder();
		if (null != sortFields && sortFields.length > 0) {
			int sortFieldLength = sortFields.length;
			int ordersLength = 0;
			if(null != orders && orders.length > 0){
				ordersLength = orders.length;
			}
			for (int i = 0; i < sortFieldLength; i++){
				orderFiled.append(sortFields[i]);
				if(ordersLength > 0 && i < ordersLength){
					orderFiled.append(" ").append(orders[i]);
				}
				if(i + 1 < sortFieldLength){
					orderFiled.append(SymbolType.COMMA);
				}
			}
		}
		return orderFiled.toString();
	}

	/**
	 * 两个字符串拼接
	 *
	 * @param str1 字符串1
	 * @param str2 字符串2
	 * @return String
	 */
	public static String twoStringConcat(String str1, String str2){
		return String.format("%s_%s", str1, str2);
	}

	/**
	 * 去除字符串左边、右边、左右两边的字符串
	 *
	 * @param str  被检测字符串
	 * @param mode 0：去除字符串左右两边的空白符串
	 *             <0：去除字符串左边的空白符串
	 *             >1：去除字符串右边的空白符串
	 * @return String 去掉空格后的字符串
	 */
	public static String trim(CharSequence str, int mode) {
		if (str == null) {
			return null;
		}
		int length = str.length();
		int start = 0;
		int end = length;

		// 扫描字符串头部
		if (mode <= 0) {
			while ((start < end) && (isBlankChar(str.charAt(start)))) {
				start++;
			}
		}
		// 扫描字符串尾部
		if (mode >= 0) {
			while ((start < end) && (isBlankChar(str.charAt(end - 1)))) {
				end--;
			}
		}
		if ((start > 0) || (end < length)) {
			return str.toString().substring(start, end);
		}
		return str.toString();
	}

	/**
	 * 是否空白字符 【空白符包括空格、制表符、全角空格和不间断空格】
	 *
	 * @param ch 空白字符
	 * @return boolean 【true：空白字符，false：非空白字符】
	 */
	private static boolean isBlankChar(char ch) {
		// isWhitespace() 方法用于判断指定字符是否为空白字符，空白符包含：空格、tab 键、换行符。
		// isSpaceChar(char ch) 确定指定的字符是否为 Unicode 空白字符。一个字符被认为是当且仅当它被指定为Unicode标准空格字符空格字符。
		// 此方法返回true，如果字符是下列任何一般类别类型：SPACE_SEPARATOR、LINE_SEPARATOR、PARAGRAPH_SEPARATOR
		// 三种空格unicode(\u00A0,\u0020,\u3000)
		// 1.不间断空格\u00A0,主要用在office中,让一个单词在结尾处不会换行显示,快捷键ctrl+shift+space ;
		// 2.半角空格(英文符号)\u0020,代码中常用的;
		// 3.全角空格(中文符号)\u3000,中文文章中使用;
		return Character.isWhitespace(ch) || Character.isSpaceChar(ch);
	}
}