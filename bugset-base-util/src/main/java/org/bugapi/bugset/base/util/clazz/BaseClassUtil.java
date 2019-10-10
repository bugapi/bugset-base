package org.bugapi.bugset.base.util.clazz;

import java.io.Closeable;
import java.io.Externalizable;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 基础类的工具类【java基本的常用类的缓存】
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class BaseClassUtil {
	/**
	 * 8种基本数据类型的包装类的class与type映射的集合
	 */
	private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_MAP = new HashMap<>(8);
	/**
	 * 8种基本数据类型的包装类的type与class映射的集合
	 */
	private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_WRAPPER_MAP = new HashMap<>(8);
	/**
	 * 基本数据类型的type的Name与type映射的集合
	 */
	public static final Map<String, Class<?>> PRIMITIVE_TYPE_NAME_MAP;
	/**
	 * java公用class的name与class的映射集合
	 */
	public static final Map<String, Class<?>> COMMON_CLASS_CACHE = new HashMap<>(64);

	static {
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Boolean.class, Boolean.TYPE);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Byte.class, Byte.TYPE);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Character.class, Character.TYPE);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Double.class, Double.TYPE);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Float.class, Float.TYPE);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Integer.class, Integer.TYPE);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Long.class, Long.TYPE);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Short.class, Short.TYPE);
		PRIMITIVE_WRAPPER_TYPE_MAP.forEach((key, value) -> {
			PRIMITIVE_TYPE_WRAPPER_MAP.put(value, key);
			registerCommonClasses(key);
		});
		// 原始类型数据集合【包含基本数据类型以及基本数据类型的数组类型】
		Set<Class<?>> primitiveTypes = new HashSet<>(32);
		// 将基本数据类型添加到集合中
		primitiveTypes.addAll(PRIMITIVE_WRAPPER_TYPE_MAP.values());
		// 将基本数据类型的数组类型添加到集合中
		Collections.addAll(primitiveTypes, boolean[].class, byte[].class, char[].class, double[].class, float[].class, int[].class, long[].class, short[].class);
		// 将空类型添加到集合中
		primitiveTypes.add(Void.TYPE);

		PRIMITIVE_TYPE_NAME_MAP = primitiveTypes.stream().collect(Collectors.toMap(Class::getName, type -> type));

		registerCommonClasses(Boolean[].class, Byte[].class, Character[].class, Double[].class, Float[].class, Integer[].class, Long[].class, Short[].class);
		registerCommonClasses(Number.class, Number[].class, String.class, String[].class, Class.class, Class[].class, Object.class, Object[].class);
		registerCommonClasses(Throwable.class, Exception.class, RuntimeException.class, Error.class, StackTraceElement.class, StackTraceElement[].class);
		registerCommonClasses(Enum.class, Iterable.class, Iterator.class, Enumeration.class, Collection.class, List.class, Set.class, Map.class, Map.Entry.class, Optional.class);
		Class<?>[] javaLanguageInterfaceArray = new Class[]{Serializable.class, Externalizable.class, Closeable.class, AutoCloseable.class, Cloneable.class, Comparable.class};
		registerCommonClasses(javaLanguageInterfaceArray);
	}

	/**
	 * 将java常用的公用类存放到集合中
	 *
	 * @param commonClasses 常用的类型类
	 */
	private static void registerCommonClasses(Class<?>... commonClasses) {
		for (Class<?> clazz : commonClasses) {
			COMMON_CLASS_CACHE.put(clazz.getName(), clazz);
		}
	}

	/**
	 * 判断一个类型类是Java的8种基本数据类型的包装类
	 *
	 * @param clazz 类
	 * @return true：是原始数据类型的包装类
	 */
	public static boolean isPrimitiveWrapper(Class<?> clazz) {
		if (null == clazz) {
			return false;
		}
		return PRIMITIVE_TYPE_WRAPPER_MAP.containsKey(clazz);
	}

	/**
	 * 判断一个类型类是Java的8种基本数据类型或者是基本数据类型的包装类
	 *
	 * @param clazz 类
	 * @return true：是原始数据类型或者是基本数据类型的包装类
	 */
	public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
		if (null == clazz) {
			return false;
		}
		return clazz.isPrimitive() || isPrimitiveWrapper(clazz);
	}

	/**
	 * 判断一个类型类是Java的8种基本数据类型数组
	 *
	 * @param clazz 类
	 * @return true：是原始数据类型的包装类
	 */
	public static boolean isPrimitiveArray(Class<?> clazz) {
		if (null == clazz) {
			return false;
		}
		return clazz.isArray() && clazz.getComponentType().isPrimitive();
	}

	/**
	 * 判断一个类型类是Java的8种基本数据类型包装类的数组
	 *
	 * @param clazz 类
	 * @return true：是原始数据类型的包装类
	 */
	public static boolean isPrimitiveWrapperArray(Class<?> clazz) {
		if (null == clazz) {
			return false;
		}
		return clazz.isArray() && isPrimitiveWrapper(clazz.getComponentType());
	}

	public static void main(String[] args) {
		System.out.println(isPrimitiveOrWrapper(int.class));
		System.out.println(isPrimitiveOrWrapper(void.class));
		System.out.println(isPrimitiveOrWrapper(Void.class));
	}
}
