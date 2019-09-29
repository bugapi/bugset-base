package org.bugapi.bugset.base.util.collection;

import java.util.Iterator;

/**
 * 迭代器工具类
 * @author zhangxw
 * @since 0.0.1
 */
public class IteratorUtil {

	/**
	 * 判断Iterable为空
	 *
	 * @param iterable Iterable对象
	 * @return boolean 【true：空】
	 */
	public static boolean isEmpty(Iterable<?> iterable) {
		return null == iterable || isEmpty(iterable.iterator());
	}

	/**
	 * 判断Iterator为空
	 *
	 * @param iterator Iterator对象
	 * @return boolean 【true：空】
	 */
	public static boolean isEmpty(Iterator<?> iterator) {
		return null == iterator || !iterator.hasNext();
	}

	/**
	 * 判断Iterable不为空
	 *
	 * @param iterable Iterable对象
	 * @return boolean 【true：不为空】
	 */
	public static boolean isNotEmpty(Iterable<?> iterable) {
		return null != iterable && isNotEmpty(iterable.iterator());
	}

	/**
	 * 判断Iterator不为空
	 *
	 * @param iterator Iterator对象
	 * @return boolean 【true：不为空】
	 */
	public static boolean isNotEmpty(Iterator<?> iterator) {
		return null != iterator && iterator.hasNext();
	}

	/**
	 * Iterable 对象中有空元素
	 *
	 * @param iterable Iterable对象
	 * @return boolean 【true：有空对象】
	 */
	public static boolean hasNull(Iterable<?> iterable) {
		return hasNull(null == iterable ? null : iterable.iterator());
	}

	/**
	 * Iterator 对象中有空元素
	 *
	 * @param iterator Iterator对象
	 * @return boolean 【true：有空对象】
	 */
	public static boolean hasNull(Iterator<?> iterator) {
		if (null == iterator) {
			return true;
		}
		while (iterator.hasNext()) {
			if (null == iterator.next()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Iterable 对象中所有元素为null
	 *
	 * @param iterable Iterable对象
	 * @return boolean 【true：所有元素为null】
	 */
	public static boolean isAllNull(Iterable<?> iterable) {
		return isAllNull(null == iterable ? null : iterable.iterator());
	}

	/**
	 * Iterator 对象中所有元素为null
	 *
	 * @param iterator Iterator对象
	 * @return boolean 【true：所有元素为null】
	 */
	public static boolean isAllNull(Iterator<?> iterator) {
		if (null == iterator) {
			return true;
		}
		while (iterator.hasNext()) {
			if (null != iterator.next()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 将 Iterator 转为 Iterable
	 *
	 * @param <E> 元素类型
	 * @param iterator Iterator对象
	 * @return Iterable Iterable对象
	 */
	public static <E> Iterable<E> asIterable(final Iterator<E> iterator) {
		return () -> iterator;
	}

	/**
	 * 获取Iterable中的第一个元素
	 *
	 * @param <T> 集合元素类型
	 * @param iterable 迭代器对象
	 * @return T 第一个元素
	 */
	public static <T> T getFirst(Iterable<T> iterable) {
		if (null != iterable) {
			return getFirst(iterable.iterator());
		}
		return null;
	}

	/**
	 * 获取迭代器中的第一个元素
	 *
	 * @param <T> 集合元素类型
	 * @param iterator 迭代器对象
	 * @return T 第一个元素
	 */
	public static <T> T getFirst(Iterator<T> iterator) {
		if (null != iterator && iterator.hasNext()) {
			return iterator.next();
		}
		return null;
	}
}
