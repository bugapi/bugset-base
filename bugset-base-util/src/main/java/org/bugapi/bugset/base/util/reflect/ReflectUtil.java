package org.bugapi.bugset.base.util.reflect;

import org.bugapi.bugset.base.constant.SymbolType;
import org.bugapi.bugset.base.util.string.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 获取反射工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class ReflectUtil {

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
	 * 除了能正常加载普通的类型，还能加载简单类型，数组，或者内部类
	 * eg:
	 * System.out.println(forName("int[]"));
	 * 结果：class [I
	 * System.out.println(forName("java.lang.String[]"));
	 * 结果：class [Ljava.lang.String;
	 * System.out.println(forName("org.bugapi.bugset.base.util.reflect.ReflectUtil"));
	 * 结果：class org.bugapi.bugset.base.util.reflect.ReflectUtil
	 * System.out.println(forName("org.bugapi.bugset.base.util.reflect.ReflectUtil.ReflectUtilInnerClass"));
	 * ReflectUtilInnerClass 创建在 ReflectUtil 类内部
	 * 结果：class org.bugapi.bugset.base.util.reflect.ReflectUtil$ReflectUtilInnerClass
	 *
	 * @param className   限定类名 字符串
	 * @param classLoader 类加载器
	 * @return 初始化类
	 * @throws ClassNotFoundException 类找不到异常
	 */
	public static Class<?> forName(final String className, ClassLoader classLoader) throws ClassNotFoundException {
		if (StringUtil.isEmpty(className)) {
			return null;
		}
		Class<?> clazz = null;
		if (className.length() <= 8) {
			clazz = ClassUtil.PRIMITIVE_TYPE_NAME_MAP.get(className);
		}
		if (clazz == null) {
			clazz = ClassUtil.COMMON_CLASS_CACHE.get(className);
		}
		if (clazz != null) {
			return clazz;
		} else {
			Class elementClass;
			String elementName;
			if (className.endsWith(SymbolType.ARRAY_SUFFIX)) {
				elementName = className.substring(0, className.length() - SymbolType.ARRAY_SUFFIX.length());
				elementClass = forName(elementName, classLoader);
				return Array.newInstance(elementClass, 0).getClass();
			} else if (className.startsWith(SymbolType.NON_PRIMITIVE_ARRAY_PREFIX) && className.endsWith(SymbolType.SEMICOLON)) {
				elementName = className.substring(SymbolType.NON_PRIMITIVE_ARRAY_PREFIX.length(), className.length() - 1);
				elementClass = forName(elementName, classLoader);
				return Array.newInstance(elementClass, 0).getClass();
			} else if (className.startsWith(SymbolType.PRIMITIVE_ARRAY_PREFIX)) {
				elementName = className.substring(SymbolType.PRIMITIVE_ARRAY_PREFIX.length());
				elementClass = forName(elementName, classLoader);
				return Array.newInstance(elementClass, 0).getClass();
			} else {
				if (classLoader == null) {
					classLoader = ClassLoaderUtil.getDefaultClassLoader();
				}
				try {
					return classLoader != null ? classLoader.loadClass(className) : Class.forName(className);
				} catch (ClassNotFoundException exception) {
					int lastDotIndex = className.lastIndexOf(SymbolType.DOT);
					if (lastDotIndex != -1) {
						String innerClassName =
								className.substring(0, lastDotIndex) + SymbolType.DOLLAR + className.substring(lastDotIndex + 1);
						return classLoader != null ? classLoader.loadClass(innerClassName) : Class.forName(innerClassName);
					}
					return null;
				}
			}
		}
	}

	/**
	 * 获取类上的注解
	 *
	 * @param clazz           类
	 * @param annotationClass 注解类
	 * @param <T>             类的泛型
	 * @param <A>             注解类的泛型
	 * @return 类注解实例
	 */
	public static <T, A extends Annotation> A getClassAnnotation(Class<T> clazz, Class<A> annotationClass) {
		return clazz.getAnnotation(annotationClass);
	}

	/**
	 * 获取属性上的注解
	 *
	 * @param clazz           类
	 * @param annotationClass 注解类
	 * @param fieldName       属性名称
	 * @param <T>             类的泛型
	 * @param <A>             注解类的泛型
	 * @return 属性注解实例
	 * @throws NoSuchFieldException 属性不存在的异常
	 */
	public static <T, A extends Annotation> A getFieldAnnotation(Class<T> clazz, Class<A> annotationClass,
																 String fieldName) throws NoSuchFieldException {
		Field field = clazz.getDeclaredField(fieldName);
		return field.getAnnotation(annotationClass);
	}

	/**
	 * 获取方法上的注解
	 *
	 * @param clazz           类
	 * @param annotationClass 注解类
	 * @param methodName      方法名称
	 * @param <T>             类的泛型
	 * @param <A>             注解类的泛型
	 * @return 方法的注解实例
	 * @throws NoSuchMethodException 方法不存在的异常
	 */
	public static <T, A extends Annotation> A getMethodAnnotation(Class<T> clazz, Class<A> annotationClass,
																 String methodName) throws NoSuchMethodException {
		Method method = clazz.getDeclaredMethod(methodName);
		return method.getAnnotation(annotationClass);
	}




	public static void main(String[] args) throws ClassNotFoundException {
		System.out.println(forName("int[]"));
		System.out.println(forName("java.lang.String[]"));
		System.out.println(forName("java.lang.Long[]"));
		System.out.println(forName("org.bugapi.bugset.base.util.reflect.ReflectUtil"));
		System.out.println(forName("org.bugapi.bugset.base.util.reflect.ReflectUtil.ReflectUtilInnerClass"));
		System.out.println((char) 46);
	}

	class ReflectUtilInnerClass {

	}
}




