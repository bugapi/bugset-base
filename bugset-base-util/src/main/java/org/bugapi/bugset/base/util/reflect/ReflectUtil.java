package org.bugapi.bugset.base.util.reflect;

import java.io.Closeable;
import java.io.Externalizable;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 获取反射工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class ReflectUtil {
	private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_MAP = new IdentityHashMap<>(8);
	private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_TO_WRAPPER_MAP = new IdentityHashMap<>(8);
	private static final Map<String, Class<?>> PRIMITIVE_TYPE_NAME_MAP = new HashMap<>(32);
	private static final Map<String, Class<?>> COMMON_CLASS_CACHE = new HashMap<>(64);
	private static final Set<Class<?>> JAVA_LANGUAGE_INTERFACES;


	/**
	 * 根据传入的 限定类名 字符串，加载并初始化类
	 *
	 * @param className 限定类名 字符串
	 * @return 初始化类
	 * @throws ClassNotFoundException 类找不到异常
	 */
	public static Class<?> forName(final String className) throws ClassNotFoundException {
		return forName(className, null);
	}

	/**
	 * 根据传入的 限定类名 字符串，加载并初始化类
	 *
	 * @param className 限定类名 字符串
	 * @param classLoader 类加载器
	 * @return 初始化类
	 * @throws ClassNotFoundException 类找不到异常
	 */
	public static Class<?> forName(final String className, ClassLoader classLoader) throws ClassNotFoundException{
		Class<?> clazz = resolvePrimitiveClassName(className);
		if (clazz == null) {
			clazz = (Class)COMMON_CLASS_CACHE.get(className);
		}

		if (clazz != null) {
			return clazz;
		} else {
			Class elementClass;
			String elementName;
			if (className.endsWith("[]")) {
				elementName = className.substring(0, className.length() - "[]".length());
				elementClass = forName(elementName, classLoader);
				return Array.newInstance(elementClass, 0).getClass();
			} else if (className.startsWith("[L") && className.endsWith(";")) {
				elementName = className.substring("[L".length(), className.length() - 1);
				elementClass = forName(elementName, classLoader);
				return Array.newInstance(elementClass, 0).getClass();
			} else if (className.startsWith("[")) {
				elementName = className.substring("[".length());
				elementClass = forName(elementName, classLoader);
				return Array.newInstance(elementClass, 0).getClass();
			} else {
				if (classLoader == null) {
					classLoader = ClassLoaderUtil.getDefaultClassLoader();
				}
				return classLoader != null ? classLoader.loadClass(className) : Class.forName(className);
			}
		}
	}

	public static void main(String[] args) throws ClassNotFoundException {
		System.out.println(forName("java.lang.String[]"));
		System.out.println(forName("long[]"));
	}

	/**
	 * 获取类上的注解
	 *
	 * @param clazz           类
	 * @param annotationClass 注解类
	 * @param <T>             类的泛型
	 * @param <A>             注解类的泛型
	 * @return 注解类实例
	 */
	public static <T, A extends Annotation> A getClassAnnotation(Class<T> clazz, Class<A> annotationClass) {
		return clazz.getAnnotation(annotationClass);
	}



	public static Class<?> resolvePrimitiveClassName(String name) {
		Class<?> result = null;
		if (name != null && name.length() <= 8) {
			result = (Class)PRIMITIVE_TYPE_NAME_MAP.get(name);
		}
		return result;
	}

	public static <A extends Annotation> A getFieldAnnotation(Class<?> clazz, Class<A> annotationClass,
															  String fieldName) throws NoSuchFieldException {
		Field field = clazz.getDeclaredField(fieldName);
		return field.getAnnotation(annotationClass);
	}



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
			PRIMITIVE_TYPE_TO_WRAPPER_MAP.put(value, key);
			registerCommonClasses(key);
		});
		Set<Class<?>> primitiveTypes = new HashSet<>(32);
		primitiveTypes.addAll(PRIMITIVE_WRAPPER_TYPE_MAP.values());
		Collections.addAll(primitiveTypes, boolean[].class, byte[].class, char[].class, double[].class, float[].class, int[].class, long[].class, short[].class);
		primitiveTypes.add(Void.TYPE);

		for (Class<?> type : primitiveTypes) {
			PRIMITIVE_TYPE_NAME_MAP.put(((Class<?>) (Class) type).getName(), (Class) type);
		}

		registerCommonClasses(Boolean[].class, Byte[].class, Character[].class, Double[].class, Float[].class, Integer[].class, Long[].class, Short[].class);
		registerCommonClasses(Number.class, Number[].class, String.class, String[].class, Class.class, Class[].class, Object.class, Object[].class);
		registerCommonClasses(Throwable.class, Exception.class, RuntimeException.class, Error.class, StackTraceElement.class, StackTraceElement[].class);
		registerCommonClasses(Enum.class, Iterable.class, Iterator.class, Enumeration.class, Collection.class, List.class, Set.class, Map.class, Map.Entry.class, Optional.class);
		Class<?>[] javaLanguageInterfaceArray = new Class[]{Serializable.class, Externalizable.class, Closeable.class, AutoCloseable.class, Cloneable.class, Comparable.class};
		registerCommonClasses(javaLanguageInterfaceArray);
		JAVA_LANGUAGE_INTERFACES = new HashSet<>(Arrays.asList(javaLanguageInterfaceArray));
	}

	private static void registerCommonClasses(Class<?>... commonClasses) {
		int length = commonClasses.length;
		for (Class<?> clazz : commonClasses) {
			COMMON_CLASS_CACHE.put(clazz.getName(), clazz);
		}
	}
}
