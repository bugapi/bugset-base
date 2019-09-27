package org.bugapi.bugset.base.util.collection;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class CollectionUtil {

	/**
	 * 判断集合为空
	 *
	 * @param collection 集合
	 * @return boolean true：集合为空
	 */
	public static <T> boolean isEmpty(Collection<T> collection) {
		return collection == null || collection.size() == 0;
	}

	/**
	 * 判断集合不为空
	 *
	 * @param collection 集合
	 * @return boolean true：集合不为空
	 */
	public static <T> boolean isNotEmpty(Collection<T> collection) {
		return collection != null && collection.size() >= 1;
	}

	/**
	 * 去除list集合中的重复元素
	 *
	 * @param list list集合
	 * @return List 去重之后的集合
	 */
	public static <T> List<T> removeRepeatData(List<T> list) {
		if (isEmpty(list)) {
			return new ArrayList<T>();
		}
		return list.stream().distinct().collect(Collectors.toList());
	}

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(1);
		list.add(2);
		System.out.println(list);
		System.out.println(removeRepeatData(list));
	}
}
