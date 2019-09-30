package org.bugapi.bugset.base.util.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Map集合的工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class MapUtil {

	/**
	 * 初始化一个空的hashMap
	 * @param <K> 键
	 * @param <V> 值
	 * @return 指定键值类型的空hashMap
	 */
	public static <K, V> Map<K, V> newHashMap() {
		return new HashMap<>(0);
	}

	/**
	 * 判断Map集合为空
	 *
	 * @param map map对象
	 * @return boolean 【true：空】
	 */
	public static <K, V> boolean isEmpty(Map<K, V> map){
		return null == map || map.size() == 0;
	}

	/**
	 * 判断Map集合为空
	 *
	 * @param map map对象
	 * @return boolean 【true：不为空】
	 */
	public static <K, V> boolean isNotEmpty(Map<K, V> map){
		return null != map && map.size() >= 1;
	}

	/**
	 * 根据集合返回一个元素计数的 {@link Map}
	 * 所谓元素计数就是假如这个集合中某个元素出现了n次，那将这个元素做为key，n做为value<br>
	 * 例如：[a,c,c,c] 得到：<br>
	 * a: 1<br>
	 * c: 3<br>
	 *
	 * @param <T> 集合元素类型
	 * @param iterable {@link Iterable}，如果为null返回一个空的Map
	 * @return Map<T, Integer> map对象
	 */
	public static <T> Map<T, Integer> countMap(Iterable<T> iterable) {
		return countMap(null == iterable ? null : iterable.iterator());
	}

	/**
	 * 根据集合返回一个元素计数的 {@link Map}
	 * 所谓元素计数就是假如这个集合中某个元素出现了n次，那将这个元素做为key，n做为value<br>
	 * 例如：[a,c,c,c] 得到：<br>
	 * a: 1<br>
	 * c: 3<br>
	 *
	 *
	 * @param <T> 集合元素类型
	 * @param iterator {@link Iterator}，如果为null返回一个空的Map
	 * @return Map<T, Integer> map对象
	 */
	public static <T> Map<T, Integer> countMap(Iterator<T> iterator) {
		final HashMap<T, Integer> countMap = new HashMap<>(4);
		if (null != iterator) {
			Integer count;
			T t;
			while (iterator.hasNext()) {
				t = iterator.next();
				count = countMap.get(t);
				if (null == count) {
					countMap.put(t, 1);
				} else {
					countMap.put(t, count + 1);
				}
			}
		}
		return countMap;
	}
}
