package org.bugapi.bugset.base.util.clazz;

import org.bugapi.bugset.base.constant.SymbolType;
import org.bugapi.bugset.base.util.array.ArrayUtil;
import org.bugapi.bugset.base.util.string.StringUtil;

/**
 * 类加载工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class ClassLoaderUtil {
	/**
	 * 获取类加载器
	 * 按照获取当前线程上下文类加载器-->获取当前类类加载器-->获取系统启动类加载器的顺序来获取
	 *
	 * @return 类加载器
	 */
	public static ClassLoader getDefaultClassLoader() {
		// 获取当前线程的 上下文类加载器
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			// 如果没有当前线程的 上下文类加载器，获取当前类的类加载器
			classLoader = ClassLoaderUtil.class.getClassLoader();
			if (classLoader == null) {
				// 如果没有当前类的类加载器，获取系统启动类加载器
				classLoader = ClassLoader.getSystemClassLoader();
			}
		}
		return classLoader;
	}

	/**
	 * 根据传入的 限定类名 字符串，寻找名称的类文件，加载进内存 产生class对象
	 *
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
	 * @param className 限定类名 字符串
	 * @return 初始化类
	 * @throws ClassNotFoundException 类找不到异常
	 */
	public static Class<?> loadClass(String className) throws ClassNotFoundException {
		return loadClass(className, true, null);
	}

	/**
	 * 根据传入的 限定类名 字符串，寻找名称的类文件，加载进内存 产生class对象
	 *
	 * <pre>
	 * 1、原始类型，例如：int
	 * 2、数组类型，例如：int[]、Long[]、String[]
	 * 3、内部类，例如：java.lang.Thread.State会被转为java.lang.Thread$State加载
	 * </pre>
	 *
	 * @param className 限定类名 字符串
	 * @param isInitialized 是否初始化类（调用static模块内容和初始化static属性）
	 * @param classLoader 类加载器
	 * @return 初始化类
	 * @throws ClassNotFoundException 类找不到异常
	 */
	public static Class<?> loadClass(String className, boolean isInitialized, ClassLoader classLoader) throws ClassNotFoundException {
		if (StringUtil.isEmpty(className)) {
			return null;
		}
		Class<?> clazz = loadPrimitiveClass(className);
		if (clazz == null) {
			clazz = loadCommonClass(className);
		}
		if (clazz != null) {
			return clazz;
		}

		Class elementClass;
		String elementName;
		if (className.endsWith(SymbolType.ARRAY_SUFFIX)) {
			// 对象数组 java.lang.String[] 风格
			elementName = className.substring(0, className.length() - SymbolType.ARRAY_SUFFIX.length());
			elementClass = loadClass(elementName, isInitialized, classLoader);
			return ArrayUtil.getArrayClass(elementClass);
		} else if (className.startsWith(SymbolType.NON_PRIMITIVE_ARRAY_PREFIX) && className.endsWith(SymbolType.SEMICOLON)) {
			// [Ljava.lang.String; 风格
			elementName = className.substring(SymbolType.NON_PRIMITIVE_ARRAY_PREFIX.length(), className.length() - 1);
			elementClass = loadClass(elementName, isInitialized, classLoader);
			return ArrayUtil.getArrayClass(elementClass);
		} else if (className.startsWith(SymbolType.PRIMITIVE_ARRAY_PREFIX)) {
			// [[I 或 [[Ljava.lang.String; 风格
			elementName = className.substring(SymbolType.PRIMITIVE_ARRAY_PREFIX.length());
			elementClass = loadClass(elementName, isInitialized, classLoader);
			return ArrayUtil.getArrayClass(elementClass);
		} else {
			// 加载普通类
			if (classLoader == null) {
				classLoader = getDefaultClassLoader();
			}
			try {
				return Class.forName(className, isInitialized, classLoader);
			} catch (ClassNotFoundException exception) {
				// 尝试获取内部类，例如java.lang.Thread.State =》java.lang.Thread$State
				return tryLoadInnerClass(className, isInitialized, classLoader);
			}
		}
	}

	/**
	 * 加载原始类型的类。包括原始类型、原始类型数组和void
	 *
	 * @param className 原始类型名，比如 int
	 * @return 原始类型类
	 */
	public static Class<?> loadPrimitiveClass(String className) {
		if (StringUtil.isEmpty(className)) {
			return null;
		}
		Class<?> clazz = null;
		if (className.length() <= 8) {
			clazz = BaseClassUtil.PRIMITIVE_TYPE_NAME_MAP.get(className);
		}
		return clazz;
	}

	/**
	 * 加载原始类型的类。包括原始类型、原始类型数组和void
	 *
	 * @param className 原始类型名，比如 int
	 * @return 原始类型类
	 */
	public static Class<?> loadCommonClass(String className) {
		if (StringUtil.isEmpty(className)) {
			return null;
		}
		return BaseClassUtil.COMMON_CLASS_CACHE.get(className);
	}

	/**
	 * 尝试转换并加载内部类，例如java.lang.Thread.State =》java.lang.Thread$State
	 *
	 * @param className 限定类名 字符串
	 * @param isInitialized 是否初始化类（调用static模块内容和初始化static属性）
	 * @param classLoader 类加载器
	 * @return 初始化类
	 * @throws ClassNotFoundException 类找不到异常
	 */
	private static Class<?> tryLoadInnerClass(String className,  boolean isInitialized, ClassLoader classLoader) throws ClassNotFoundException {
		// 尝试获取内部类，例如java.lang.Thread.State =》java.lang.Thread$State
		int lastDotIndex = className.lastIndexOf(SymbolType.DOT);
		// 类与内部类的分隔符不能在第一位，因此>0
		if (lastDotIndex != -1) {
			return Class.forName(className.substring(0, lastDotIndex) + SymbolType.DOLLAR + className.substring(lastDotIndex + 1), isInitialized, classLoader);
		}
		return null;
	}

	public static void main(String[] args) throws ClassNotFoundException {
		System.out.println(loadClass("int[]"));
		System.out.println(loadClass("java.lang.String[]"));
		System.out.println(loadClass("java.lang.Long[]"));
		System.out.println(loadClass("org.bugapi.bugset.base.util.reflect.ReflectUtil"));
		System.out.println(loadClass("java.lang.Thread.State"));
	}
}
