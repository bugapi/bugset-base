package org.bugapi.bugset.base.util.collection;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class CollectionUtil {

	/**
	 * 集合批量执行默认上限
	 */
	public static final int BATCH_CONSUME_LIMIT = 100;

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

	/**
	 * 集合批量执行消费型函数
	 *
	 * @param consumer   单参数消费型接口 {@link Consumer}
	 * @param collection 要分批处理的数据集合
	 */
	public static <T extends Collection> void batchConsume(Consumer<T> consumer, T collection) {
		batchConsume(consumer, collection, BATCH_CONSUME_LIMIT);
	}

	/**
	 * 集合批量执行消费型函数
	 *
	 * @param consumer   单参数消费型接口 {@link Consumer}
	 * @param collection 要分批处理的数据集合
	 * @param limit      分批量
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Collection> void batchConsume(Consumer<T> consumer, T collection, int limit) {
		if (isEmpty(collection)) {
			return;
		}
		T temp;
		boolean flag;
		if (collection.size() <= limit) {
			temp = collection;
			flag = false;
		} else {
			temp = (T) collection.stream().limit(limit).collect(Collectors.toList());
			flag = true;
		}
		consumer.accept(temp);
		if (flag) {
			batchConsume(consumer, (T) collection.stream().skip(limit).collect(Collectors.toList()), limit);
		}
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
