package org.bugapi.bugset.base.util.convert;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import org.bugapi.bugset.base.constant.SymbolType;
import org.bugapi.bugset.base.util.array.ArrayUtil;
import org.bugapi.bugset.base.util.collection.CollectionUtil;
import org.bugapi.bugset.base.util.object.ObjectUtil;
import org.bugapi.bugset.base.util.string.StringUtil;
import org.bugapi.bugset.base.util.validate.ValiateUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 转换工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class ConvertUtil {


	/* ----------------------------------------  将对象转成基本数据类型  -------------------------------------- */

	/**
	 * 将Object转成int
	 *
	 * @param obj Object对象
	 * @return int
	 */
	public static int convertObjectToInteger(Object obj) {
		if (obj instanceof Integer) {
			return (Integer) obj;
		} else {
			return 0;
		}
	}

	/**
	 * 将Object转成 long
	 *
	 * @param obj Object对象
	 * @return long
	 */
	public static long convertObjectToLong(Object obj) {
		if (obj instanceof Long) {
			return (Long) obj;
		} else {
			return 0L;
		}
	}

	/**
	 * 将Object转成 float
	 *
	 * @param obj Object对象
	 * @return float
	 */
	public static float convertObjectToFloat(Object obj) {
		if (obj instanceof Float) {
			return (Float) obj;
		} else {
			return 0L;
		}
	}

	/**
	 * 将Object转成 double
	 *
	 * @param obj Object对象
	 * @return Double double
	 */
	public static double convertObjectToDouble(Object obj) {
		if (obj instanceof Double) {
			return ((Double) obj);
		} else {
			return 0D;
		}
	}

	/* ----------------------------------------  数组与字符串之间的转换  -------------------------------------- */

	/**
	 * 将数组元素用","拼接成字符串返回
	 *
	 * @param arr long类型数组
	 * @return String 返回的字符串
	 */
	public static String arrayToString(long... arr) {
		return arrayToString(SymbolType.COMMA, arr);
	}

	/**
	 * 把数组转成字符串
	 *
	 * @param delimiter 字符串连接符 delimiter
	 * @param arr       long类型的数组
	 * @return String 数组字符串
	 */
	public static String arrayToString(String delimiter, long... arr) {
		if (ArrayUtil.isEmpty(arr)) {
			return SymbolType.EMPTY;
		}
		delimiter = StringUtil.getDefaultStrSeparator(delimiter);
		return Arrays.stream(arr).boxed().map(String::valueOf).collect(Collectors.joining(delimiter));
	}

	/**
	 * 把数组转成字符串
	 *
	 * @param arr int类型的数组
	 * @return String 数组字符串
	 */
	public static String arrayToString(int... arr) {
		return arrayToString(SymbolType.COMMA, arr);
	}

	/**
	 * 把数组转成字符串
	 *
	 * @param delimiter 字符串拼接符
	 * @param arr       int类型的数组
	 * @return String 数组字符串
	 */
	public static String arrayToString(String delimiter, int... arr) {
		if (ArrayUtil.isEmpty(arr)) {
			return SymbolType.EMPTY;
		}
		delimiter = StringUtil.getDefaultStrSeparator(delimiter);
		return Arrays.stream(arr).boxed().map(String::valueOf).collect(Collectors.joining(delimiter));

		/*StringBuilder result = new StringBuilder();
		for (int i = 0, length = arr.length; i < length; i++) {
			if (result.length() > 0 && i > 0) {
				result.append(delimiter);
			}
			result.append(arr[i]);
		}
		return result.toString();*/
	}

	/**
	 * 将数组元素用","拼接成字符串返回 {@link SymbolType#COMMA}
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
	 * @param delimiter 连接符
	 * @param arr       引用类型数组
	 * @return String 返回的字符串
	 */
	@SafeVarargs
	public static <T> String arrayToString(String delimiter, T... arr) {
		if (ArrayUtil.isEmpty(arr)) {
			return SymbolType.EMPTY;
		}
		delimiter = StringUtil.getDefaultStrSeparator(delimiter);

		return Arrays.stream(arr).filter(ObjectUtil::isNotEmpty).map(String::valueOf).collect(Collectors.joining(delimiter));
		/*StringBuilder result = new StringBuilder();
		for (int i = 0, length = arr.length; i < length; i++) {
			if (StringUtil.isEmpty(arr[i].toString())) {
				if (result.length() > 0 && i > 0) {
					result.append(delimiter);
				}
				result.append(arr[i]);
			}
		}
		return result.toString();*/
	}

	/**
	 * 把字符串数组转成Long[]
	 *
	 * @param strIds 分隔符拼接的字符串
	 * @return Long[] 数组
	 */
	public static Long[] stringToWrapperLongArray(String strIds) {
		return stringToWrapperLongArray(strIds, SymbolType.COMMA);
	}

	/**
	 * 把字符串数组转成Long[]
	 *
	 * @param strIds    分隔符拼接的字符串
	 * @param delimiter 分隔符
	 * @return Long[] 数组
	 */
	public static Long[] stringToWrapperLongArray(String strIds, String delimiter) {
		if (StringUtil.isEmpty(strIds)) {
			return new Long[0];
		}
		delimiter = StringUtil.getDefaultStrSeparator(delimiter);
		String[] strArr = strIds.split(delimiter);

		return Arrays.stream(strArr).filter(ObjectUtil::isNotEmpty).filter(ValiateUtil::isIntegerNumber).map(String::trim
		).map(Long::valueOf).toArray(Long[]::new);

		/*Long[] ids = new Long[strArr.length];
		if (strArr.length > 0) {
			for (int i = 0; i < strArr.length; i++) {
				if (StringUtil.isNotEmpty(strArr[i]) && ValiateUtil.isIntegerNumber(strArr[i].trim())) {
					ids[i] = Long.parseLong(strArr[i].trim());
				} else {
					ids[i] = 0L;
				}
			}
		}
		return ids;*/
	}

	/**
	 * 把字符串数组转成long[]
	 *
	 * @param strIds 分隔符拼接的字符串
	 * @return long[] 数组
	 */
	public static long[] stringToLongArray(String strIds) {
		return stringToLongArray(strIds, SymbolType.COMMA);
	}

	/**
	 * 把字符串数组转成long[]
	 *
	 * @param strIds    分隔符拼接的字符串
	 * @param delimiter 分隔符
	 * @return long[] 数组
	 */
	public static long[] stringToLongArray(String strIds, String delimiter) {
		if (StringUtil.isEmpty(strIds)) {
			return new long[0];
		}
		delimiter = StringUtil.getDefaultStrSeparator(delimiter);
		String[] strArr = strIds.split(delimiter);
		return Arrays.stream(strArr).filter(ObjectUtil::isNotEmpty).filter(ValiateUtil::isIntegerNumber).map(String::trim
		).mapToLong(Long::valueOf).toArray();
		/*long[] ids = new long[strArr.length];
		if (strArr.length > 0) {
			for (int i = 0; i < strArr.length; i++) {
				if (StringUtil.isNotEmpty(strArr[i]) && ValiateUtil.isIntegerNumber(strArr[i].trim())) {
					ids[i] = Long.parseLong(strArr[i].trim());
				} else {
					ids[i] = 0L;
				}
			}
		}
		return ids;*/
	}

	/**
	 * 把字符串数组转成Integer[]
	 *
	 * @param strIds 分隔符拼接的字符串
	 * @return Integer[] 数组
	 */
	public static Integer[] stringToWrapperIntArray(String strIds) {
		return stringToWrapperIntArray(strIds, SymbolType.COMMA);
	}

	/**
	 * 把字符串数组转成Integer[]
	 *
	 * @param strIds    分隔符拼接的字符串
	 * @param delimiter 分隔符
	 * @return Integer[] 数组
	 */
	public static Integer[] stringToWrapperIntArray(String strIds, String delimiter) {
		if (StringUtil.isEmpty(strIds)) {
			return new Integer[0];
		}
		delimiter = StringUtil.getDefaultStrSeparator(delimiter);
		String[] strArr = strIds.split(delimiter);
		return Arrays.stream(strArr).filter(ObjectUtil::isNotEmpty).filter(ValiateUtil::isIntegerNumber).map(String::trim
		).map(Integer::valueOf).toArray(Integer[]::new);
	}

	/**
	 * 把字符串数组转成int[]
	 *
	 * @param strIds 分隔符拼接的字符串
	 * @return int[] 数组
	 */
	public static int[] stringToIntArray(String strIds) {
		return stringToIntArray(strIds, SymbolType.COMMA);
	}

	/**
	 * 把字符串数组转成int[]
	 *
	 * @param strIds    分隔符拼接的字符串
	 * @param delimiter 分隔符
	 * @return int[] 数组
	 */
	public static int[] stringToIntArray(String strIds, String delimiter) {
		if (StringUtil.isEmpty(strIds)) {
			return new int[0];
		}
		delimiter = StringUtil.getDefaultStrSeparator(delimiter);
		String[] strArr = strIds.split(delimiter);
		return Arrays.stream(strArr).filter(ObjectUtil::isNotEmpty).filter(ValiateUtil::isIntegerNumber).map(String::trim
		).mapToInt(Integer::parseInt).toArray();
		/*int[] ids = new int[strArr.length];
		if (strArr.length > 0) {
			for (int i = 0; i < strArr.length; i++) {
				if (StringUtil.isNotEmpty(strArr[i]) && ValiateUtil.isIntegerNumber(strArr[i].trim())) {
					ids[i] = Integer.parseInt(strArr[i].trim());
				} else {
					ids[i] = 0;
				}
			}
		}
		return ids;*/
	}

	/* --------------------------------------  包装类型的数组与基本类型数组之间转换  ------------------------------------ */

	/**
	 * 将原始类型数组包装为包装类型
	 *
	 * @param values 原始类型数组
	 * @return Integer[] 包装类型数组
	 */
	public static Integer[] wrap(int... values) {
		if (ArrayUtil.isEmpty(values)) {
			return new Integer[0];
		}
		return Arrays.stream(values).boxed().toArray(Integer[]::new);
	}

	public static void main(String[] args) {
		int[] intarr = new int[0];
		System.out.println(wrap(intarr));
	}

	/**
	 * 包装类数组转为原始类型数组
	 *
	 * @param values 包装类型数组
	 * @return int[] 原始类型数组
	 */
	public static int[] unWrap(Integer... values) {
		if (ArrayUtil.isEmpty(values)) {
			return new int[0];
		}
		return Arrays.stream(values).mapToInt(Integer::valueOf).toArray();
	}

	/**
	 * 将原始类型数组包装为包装类型
	 *
	 * @param values 原始类型数组
	 * @return Long[] 包装类型数组
	 */
	public static Long[] wrap(long... values) {
		return Arrays.stream(values).boxed().toArray(Long[]::new);
	}

	/**
	 * 包装类数组转为原始类型数组
	 *
	 * @param values 包装类型数组
	 * @return long[] 原始类型数组
	 */
	public static long[] unWrap(Long... values) {
		return Arrays.stream(values).mapToLong(Long::valueOf).toArray();
		/*if (null == values) {
			return null;
		}
		int length = values.length;
		if (0 == length) {
			return new long[0];
		}

		long[] array = new long[length];
		for (int i = 0; i < length; i++) {
			if (null == values[i]) {
				array[i] = 0;
			} else {
				array[i] = values[i];
			}
		}
		return array;*/
	}

	/**
	 * 将原始类型数组包装为包装类型
	 *
	 * @param values 原始类型数组
	 * @return Float[] 包装类型数组
	 */
	public static Float[] wrap(float... values) {
		if (null == values) {
			return null;
		}
		int length = values.length;
		if (0 == length) {
			return new Float[0];
		}

		Float[] array = new Float[length];
		for (int i = 0; i < length; i++) {
			array[i] = values[i];
		}
		return array;
	}

	/**
	 * 包装类数组转为原始类型数组
	 *
	 * @param values 包装类型数组
	 * @return float[] 原始类型数组
	 */
	public static float[] unWrap(Float... values) {
		if (null == values) {
			return null;
		}
		int length = values.length;
		if (0 == length) {
			return new float[0];
		}

		float[] array = new float[length];
		for (int i = 0; i < length; i++) {
			if (null == values[i]) {
				array[i] = 0;
			} else {
				array[i] = values[i];
			}
		}
		return array;
	}

	/**
	 * 将原始类型数组包装为包装类型
	 *
	 * @param values 原始类型数组
	 * @return Double[] 包装类型数组
	 */
	public static Double[] wrap(double... values) {
		if (null == values) {
			return null;
		}
		int length = values.length;
		if (0 == length) {
			return new Double[0];
		}

		Double[] array = new Double[length];
		for (int i = 0; i < length; i++) {
			array[i] = values[i];
		}
		return array;
	}

	/**
	 * 包装类数组转为原始类型数组
	 *
	 * @param values 包装类型数组
	 * @return double[] 原始类型数组
	 */
	public static double[] unWrap(Double... values) {
		if (null == values) {
			return null;
		}
		int length = values.length;
		if (0 == length) {
			return new double[0];
		}

		double[] array = new double[length];
		for (int i = 0; i < length; i++) {
			if (null == values[i]) {
				array[i] = 0;
			} else {
				array[i] = values[i];
			}
		}
		return array;
	}

	/* ----------------------------------------  集合与字符串之间的转换  -------------------------------------- */

	/**
	 * 将List元素用","拼接成字符串返回 {@link SymbolType#COMMA}
	 *
	 * @param collection 集合
	 * @return String 返回的字符串
	 */
	public static <T> String collectionToString(Collection<T> collection) {
		return collectionToString(SymbolType.COMMA, collection);
	}

	/**
	 * 将List元素用指定字符拼接成字符串返回
	 *
	 * @param delimiter  连接符
	 * @param collection 集合
	 * @return String 返回的字符串
	 */
	public static <T> String collectionToString(String delimiter, Collection<T> collection) {
		if (CollectionUtil.isEmpty(collection)) {
			return SymbolType.EMPTY;
		}
		StringBuilder result = new StringBuilder();
		delimiter = StringUtil.getDefaultStrSeparator(delimiter);
		int i = 0;
		for (T tmp : collection) {
			i++;
			if (null != tmp) {
				if (result.length() > 0 && i > 0) {
					result.append(delimiter);
				}
				result.append(tmp);
			}
		}
		return result.toString();
	}

	/**
	 * 根据分隔符将字符串解析成LongList
	 *
	 * @param arrStr 用分隔符拼接的字符串 eg：1,2,3
	 * @return List<Long> Long类型的集合
	 */
	public static List<Long> stringToLongList(String arrStr) {
		return stringToLongList(arrStr, SymbolType.COMMA);
	}

	/**
	 * 根据分隔符将字符串解析成LongList
	 *
	 * @param arrStr    用分隔符拼接的字符串 eg：1,2,3
	 * @param delimiter 分隔符 eg：,或者_
	 * @return List<Long> Long类型的集合
	 */
	public static List<Long> stringToLongList(String arrStr, String delimiter) {
		if (StringUtil.isEmpty(arrStr)) {
			return new ArrayList<>();
		}
		delimiter = StringUtil.getDefaultStrSeparator(delimiter);
		return Arrays.stream(arrStr.split(delimiter)).map(Long::parseLong).collect(Collectors.toList());
	}

	/**
	 * 根据分隔符将字符串解析成IntegerList
	 *
	 * @param arrStr 用分隔符拼接的字符串 eg：1,2,3
	 * @return List<Integer> Integer类型的集合
	 */
	public static List<Integer> stringToIntegerList(String arrStr) {
		return stringToIntegerList(arrStr, SymbolType.COMMA);
	}

	/**
	 * 根据分隔符将字符串解析成IntegerList
	 *
	 * @param arrStr    用分隔符拼接的字符串 eg：1,2,3
	 * @param delimiter 分隔符 eg：,或者_
	 * @return List<Integer> Integer类型的集合
	 */
	public static List<Integer> stringToIntegerList(String arrStr, String delimiter) {
		if (StringUtil.isEmpty(arrStr)) {
			return new ArrayList<>();
		}
		delimiter = StringUtil.getDefaultStrSeparator(delimiter);
		return Arrays.stream(arrStr.split(delimiter)).map(Integer::parseInt).collect(Collectors.toList());
	}

}
