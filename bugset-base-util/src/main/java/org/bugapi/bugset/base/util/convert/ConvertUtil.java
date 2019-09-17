package org.bugapi.bugset.base.util.convert;

import org.bugapi.bugset.base.constant.SymbolType;
import org.bugapi.bugset.base.util.string.StringUtil;

import java.util.Collection;

/**
 * 转换工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class ConvertUtil {
	/**
	 * 将Object转成Integer对象
	 *
	 * @param obj Object对象
	 * @return int Integer对象
	 */
	public static int convertObjectToInteger(Object obj) {
		if (obj instanceof Integer) {
			return (Integer) obj;
		} else {
			return 0;
		}
	}

	/**
	 * 将Object转成Long对象
	 *
	 * @param obj Object对象
	 * @return long Long对象
	 */
	public static long convertObjectToLong(Object obj) {
		if (obj instanceof Long) {
			return (Long) obj;
		} else {
			return 0L;
		}
	}

	/**
	 * 将Object转成Float对象
	 *
	 * @param obj Object对象
	 * @return float Float对象
	 */
	public static float convertObjectToFloat(Object obj) {
		if (obj instanceof Float) {
			return (Float) obj;
		} else {
			return 0L;
		}
	}

	/**
	 * 将Object转成Double对象
	 *
	 * @param obj Object对象
	 * @return Double Double对象
	 */
	public static double convertObjectToDouble(Object obj) {
		if (obj instanceof Double) {
			return ((Double) obj);
		} else {
			return 0D;
		}
	}

	public static void main(String[] args) {
		System.out.println(convertObjectToFloat(null));
		System.out.println(convertObjectToFloat(new Integer(1)));
	}

	/* ----------------------------------------  数组与字符串之间的转换  -------------------------------------- */

	/**
	 * 将数组元素用","拼接成字符串返回
	 *
	 * @param arr long类型数组
	 * @return String 返回的字符串
	 */
	public static String arrayToString(long[] arr) {
		return arrayToString(SymbolType.COMMA, arr);
	}

	/**
	 * 把数组转成字符串
	 *
	 * @param arr long类型的数组
	 * @return String 数组字符串
	 */
	public static String arrayToString(String linkStr, long[] arr) {
		if (null == arr || arr.length == 0) {
			return SymbolType.EMPTY;
		}
		StringBuilder result = new StringBuilder();
		if (StringUtil.isEmpty(linkStr)) {
			linkStr = SymbolType.COMMA;
		}
		for (int i = 0, length = arr.length; i < length; i++) {
			if (result.length() > 0 && i > 0) {
				result.append(linkStr);
			}
			result.append(arr[i]);
		}
		return result.toString();
	}

	/**
	 * 把数组转成字符串
	 *
	 * @param arr int类型的数组
	 * @return String 数组字符串
	 */
	public static String arrayToString(int[] arr) {
		return arrayToString(SymbolType.COMMA, arr);
	}

	/**
	 * 把数组转成字符串
	 *
	 * @param arr int类型的数组
	 * @return String 数组字符串
	 */
	public static String arrayToString(String linkStr, int[] arr) {
		if (null == arr || arr.length == 0) {
			return SymbolType.EMPTY;
		}
		StringBuilder result = new StringBuilder();
		if (StringUtil.isEmpty(linkStr)) {
			linkStr = SymbolType.COMMA;
		}
		for (int i = 0, length = arr.length; i < length; i++) {
			if (result.length() > 0 && i > 0) {
				result.append(linkStr);
			}
			result.append(arr[i]);
		}
		return result.toString();
	}

	/**
	 * 将数组元素用","拼接成字符串返回
	 *
	 * @param arr 引用类型数组
	 * @return String 返回的字符串
	 */
	@SafeVarargs
	public static <T> String arrayToString(T... arr) {
		return arrayToString(SymbolType.COMMA, arr);
	}

	/**
	 * 将数组元素用指定字符拼接成字符串返回
	 *
	 * @param linkStr 连接符
	 * @param arr     引用类型数组
	 * @return String 返回的字符串
	 */
	@SafeVarargs
	public static <T> String arrayToString(String linkStr, T... arr) {
		if (null == arr || arr.length == 0) {
			return SymbolType.EMPTY;
		}
		StringBuilder result = new StringBuilder();
		if (StringUtil.isEmpty(linkStr)) {
			linkStr = SymbolType.COMMA;
		}
		for (int i = 0, length = arr.length; i < length; i++) {
			if (null != arr[i]) {
				if (result.length() > 0 && i > 0) {
					result.append(linkStr);
				}
				result.append(arr[i]);
			}
		}
		return result.toString();
	}

	/* ----------------------------------------  数组与字符串之间的转换  -------------------------------------- */

	/**
	 * 将List元素用","拼接成字符串返回
	 *
	 * @param collection 集合
	 * @return String 返回的字符串
	 * @Title: collectionToString
	 */
	public static <T> String collectionToString(Collection<T> collection) {
		return collectionToString(SymbolType.COMMA, collection);
	}

	/**
	 * 将List元素用指定字符拼接成字符串返回
	 *
	 * @param linkStr    连接符
	 * @param collection 集合
	 * @return String 返回的字符串
	 * @Title: collectionToString
	 */
	public static <T> String collectionToString(String linkStr, Collection<T> collection) {
		if (null == collection || collection.size() == 0) {
			return SymbolType.EMPTY;
		}
		StringBuilder result = new StringBuilder();
		if (StringUtil.isEmpty(linkStr)) {
			linkStr = SymbolType.COMMA;
		}
		int i = 0;
		for (T tmp : collection) {
			i++;
			if (null != tmp) {
				if (result.length() > 0 && i > 0) {
					result.append(linkStr);
				}
				result.append(tmp);
			}
		}
		return result.toString();
	}

	/* ----------------------------------------  集合与字符串之间的转换  -------------------------------------- */

}
