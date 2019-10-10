package org.bugapi.bugset.base.util.array;

import org.bugapi.bugset.base.util.string.StringUtil;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 数组工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class ArrayUtil {
	public static final int EMPTY_ARRAY_LENGTH = 0;
	/**
	 * 判断一个对象是否是数组对象，如果为null返回false
	 *
	 * @param obj 对象
	 * @return boolean 【true：是数组、false：为null或者不是数组】
	 */
	public static boolean isArray(Object obj) {
		if (null == obj) {
			return false;
		}
		return obj.getClass().isArray();
	}

	//----------------------------------------   isEmpty    -----------------------------------------------

	/**
	 * 数组是否为空
	 *
	 * @param <T>   数组元素类型
	 * @param array 数组
	 * @return 是否为空
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean isEmpty(T... array) {
		return array == null || array.length == 0;
	}

	/**
	 * 数组是否为空
	 *
	 * @param array 数组
	 * @return 是否为空
	 */
	public static boolean isEmpty(long... array) {
		return array == null || array.length == 0;
	}

	/**
	 * 数组是否为空
	 *
	 * @param array 数组
	 * @return 是否为空
	 */
	public static boolean isEmpty(int... array) {
		return array == null || array.length == 0;
	}


	/**
	 * 数组是否为空
	 *
	 * @param array 数组
	 * @return 是否为空
	 */
	public static boolean isEmpty(boolean... array) {
		return array == null || array.length == 0;
	}

	//----------------------------------------   isNotEmpty    -----------------------------------------------

	/**
	 * 数组是否为非空
	 *
	 * @param <T>   数组元素类型
	 * @param array 数组
	 * @return 是否为非空
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean isNotEmpty(T... array) {
		return (array != null && array.length >= 1);
	}

	/**
	 * 数组是否为非空
	 *
	 * @param array 数组
	 * @return 是否为非空
	 */
	public static boolean isNotEmpty(long... array) {
		return (array != null && array.length >= 1);
	}

	/**
	 * 数组是否为非空
	 *
	 * @param array 数组
	 * @return 是否为非空
	 */
	public static boolean isNotEmpty(int... array) {
		return (array != null && array.length >= 1);
	}

	/**
	 * 数组是否为非空
	 *
	 * @param array 数组
	 * @return 是否为非空
	 */
	public static boolean isNotEmpty(boolean... array) {
		return (array != null && array.length >= 1);
	}

	/**
	 * 去除数组中的重复数据
	 *
	 * @param array 数组
	 */
	public static int[] removeRepeatData(int... array) {
		if (isEmpty(array)) {
			return new int[0];
		}
		return Arrays.stream(array).distinct().toArray();
	}

	/**
	 * 去除数组中的重复数据
	 *
	 * @param array 数组
	 */
	public static long[] removeRepeatData(long... array) {
		if (isEmpty(array)) {
			return new long[0];
		}
		return Arrays.stream(array).distinct().toArray();
	}

	/**
	 * 去除数组中的重复数据
	 *
	 * @param array 数组
	 */
	public static String[] removeRepeatData(String... array) {
		if (isEmpty(array)) {
			return new String[0];
		}
		return Arrays.stream(array).filter(StringUtil::isNotEmpty).distinct().toArray(String[]::new);
	}

	/**
	 * 新建一个空数组
	 *
	 * @param <T> 数组元素类型
	 * @param componentType 元素类型
	 * @param newSize 大小
	 * @return 空数组
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] newArray(Class<?> componentType, int newSize) {
		return (T[]) Array.newInstance(componentType, newSize);
	}

	/**
	 * 根据元素类型获取对应的数组类型
	 *
	 * @param componentType 元素类型
	 * @return 空数组
	 */
	public static Class<?> getArrayClass(Class<?> componentType) {
		return Array.newInstance(componentType, EMPTY_ARRAY_LENGTH).getClass();
	}

	public static void main(String[] args) {
		System.out.println(removeRepeatData(new int[]{1,2,1,2}).length);
		System.out.println(removeRepeatData(new String[]{"1","23","1"}).length);
		System.out.println(getArrayClass(int.class));
	}
}
