package org.bugapi.bugset.base.util.reflect;

import org.bugapi.bugset.base.util.array.ArrayUtil;
import org.bugapi.bugset.base.util.clazz.ClassUtil;
import org.bugapi.bugset.base.util.string.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 获取反射工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class ReflectUtil {

	/**
	 * 获取构造器
	 *
	 * @param clazz          类型类
	 * @param parameterTypes 构造器的形参对应的Class类型【可以不写】
	 * @param <T>            对象的类型
	 * @return 构造器对象
	 * @throws NoSuchMethodException 方法不存在异常
	 */
	public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
		if (null == clazz) {
			return null;
		}
		return clazz.getDeclaredConstructor(parameterTypes);
	}

	/**
	 * 实例化对象
	 *
	 * @param clazz 类型类名称
	 * @param <T>   对象类型
	 * @return 对象实例
	 * @throws ClassNotFoundException 类不存在异常
	 * @throws IllegalAccessException 非法访问异常
	 * @throws InstantiationException 实例化异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(String clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		return (T) Class.forName(clazz).newInstance();
	}

	/**
	 * @param clazz  类型类
	 * @param params 参数列表
	 * @param <T>    对象类型
	 * @return 对象实例
	 * @throws NoSuchMethodException     方法不存在异常
	 * @throws IllegalAccessException    非法访问异常
	 * @throws InvocationTargetException 目标调用异常
	 * @throws InstantiationException    实例化异常
	 */
	public static <T> T newInstance(Class<T> clazz, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		if (null == clazz) {
			return null;
		}
		Class<?>[] paramTypes = ClassUtil.getClasses(params);
		Constructor<T> constructor = getConstructor(clazz, paramTypes);
		if (null == constructor) {
			return null;
		}
		if (!constructor.isAccessible()) {
			constructor.setAccessible(true);
		}
		return constructor.newInstance(params);
	}

	/**
	 * 获取类上的注解
	 *
	 * @param clazz           类
	 * @param annotationClass 注解类【注解类需要时：@Retention(RetentionPolicy.RUNTIME)】
	 * @param <T>             类的泛型
	 * @param <A>             注解类的泛型
	 * @return 类注解实例
	 */
	public static <T, A extends Annotation> A getClassAnnotation(Class<T> clazz, Class<A> annotationClass) {
		if (null == clazz || null == annotationClass) {
			return null;
		}
		return clazz.getAnnotation(annotationClass);
	}

	/**
	 * 获取属性
	 *
	 * @param clazz     类型类
	 * @param fieldName 属性名
	 * @return 属性对象【Field类型】
	 * @throws NoSuchFieldException 属性不存在异常
	 */
	public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
		if (null == clazz || StringUtil.isEmpty(fieldName)) {
			return null;
		}
		return clazz.getDeclaredField(fieldName);
	}

	/**
	 * 获取属性上的注解
	 *
	 * @param clazz           类
	 * @param annotationClass 注解类【注解类需要时：@Retention(RetentionPolicy.RUNTIME)】
	 * @param fieldName       属性名称
	 * @param <T>             类的泛型
	 * @param <A>             注解类的泛型
	 * @return 属性注解实例
	 * @throws NoSuchFieldException 属性不存在的异常
	 */
	public static <T, A extends Annotation> A getFieldAnnotation(Class<T> clazz, Class<A> annotationClass,
																 String fieldName) throws NoSuchFieldException {
		Field field = getField(clazz, fieldName);
		return getFieldAnnotation(field, annotationClass);
	}

	/**
	 * 获取属性上的注解
	 *
	 * @param field           属性
	 * @param annotationClass 注解类【注解类需要时：@Retention(RetentionPolicy.RUNTIME)】
	 * @param <A>             注解类的泛型
	 * @return 属性注解实例
	 */
	public static <A extends Annotation> A getFieldAnnotation(Field field, Class<A> annotationClass) {
		if (null == field || null == annotationClass) {
			return null;
		}
		return field.getAnnotation(annotationClass);
	}

	/**
	 * 获取方法
	 *
	 * @param clazz      类型类
	 * @param methodName 方法名
	 * @param objects    参数列表
	 * @return 方法对象【Method类型】
	 * @throws NoSuchMethodException 方法不存在异常
	 */
	public static Method getMethod(Class<?> clazz, String methodName, Object... objects) throws NoSuchMethodException {
		if (null == clazz || StringUtil.isEmpty(methodName)) {
			return null;
		}
		return clazz.getDeclaredMethod(methodName, ClassUtil.getClasses(objects));
	}

	/**
	 * 获取方法
	 *
	 * @param clazz          类型类
	 * @param methodName     方法名
	 * @param parameterTypes 方法的形参对应的Class类型【可以不写】
	 * @return 方法对象【Method类型】
	 * @throws NoSuchMethodException 方法不存在异常
	 */
	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
		if (null == clazz || StringUtil.isEmpty(methodName)) {
			return null;
		}
		return clazz.getDeclaredMethod(methodName, parameterTypes);
	}


	/**
	 * 是否为静态方法
	 *
	 * @param clazz          类型类
	 * @param methodName     方法名
	 * @param parameterTypes 方法的形参对应的Class类型【可以不写】
	 * @return Boolean true：静态方法、false：非静态方法、null：参数为空或方法不存在
	 * @throws NoSuchMethodException 方法不存在异常
	 */
	public static Boolean isStaticMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
		return ModifierUtil.isStatic(getMethod(clazz, methodName, parameterTypes));
	}

	/**
	 * 获取方法上的注解
	 *
	 * @param clazz           类
	 * @param annotationClass 注解类【注解类需要时：@Retention(RetentionPolicy.RUNTIME)】
	 * @param methodName      方法名称
	 * @param <T>             类的泛型
	 * @param <A>             注解类的泛型
	 * @return 方法的注解实例
	 * @throws NoSuchMethodException 方法不存在的异常
	 */
	public static <T, A extends Annotation> A getMethodAnnotation(Class<T> clazz, Class<A> annotationClass,
																  String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
		Method method = getMethod(clazz, methodName, parameterTypes);
		return getMethodAnnotation(method, annotationClass);
	}

	/**
	 * 获取方法上的注解
	 *
	 * @param method          方法对象
	 * @param annotationClass 注解类【注解类需要时：@Retention(RetentionPolicy.RUNTIME)】
	 * @param <A>             注解类的泛型
	 * @return 方法的注解实例
	 */
	public static <A extends Annotation> A getMethodAnnotation(Method method, Class<A> annotationClass) {
		if (null == method || null == annotationClass) {
			return null;
		}
		return method.getAnnotation(annotationClass);
	}

	/**
	 * 执行方法
	 *
	 * @param obj        对象
	 * @param methodName 方法
	 * @param args       方法参数
	 * @param <T>        方法返回值类型
	 * @return T 方法返回值
	 * @throws NoSuchMethodException     方法不存在异常
	 * @throws InvocationTargetException 调用目标异常
	 * @throws IllegalAccessException    非法访问异常
	 */
	public static <T> T invoke(Object obj, String methodName, Object... args) throws NoSuchMethodException,
			InvocationTargetException, IllegalAccessException {
		Class clazz = ClassUtil.getClass(obj);
		Method method = getMethod(clazz, methodName, args);
		return invoke(obj, method, args);
	}

	/**
	 * 执行静态方法
	 *
	 * @param clazz      类型类
	 * @param methodName 方法
	 * @param args       方法参数
	 * @param <T>        方法返回值类型
	 * @return T 方法返回值
	 * @throws NoSuchMethodException     方法不存在异常
	 * @throws InvocationTargetException 调用目标异常
	 * @throws IllegalAccessException    非法访问异常
	 */
	public static <T> T invoke(Class<T> clazz, String methodName, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = getMethod(clazz, methodName, args);
		return invoke(method, args);
	}

	/**
	 * 执行静态方法
	 *
	 * @param method 方法
	 * @param args   方法参数
	 * @param <T>    方法返回值类型
	 * @return T 方法返回值
	 * @throws InvocationTargetException 调用目标异常
	 * @throws IllegalAccessException    非法访问异常
	 */
	public static <T> T invoke(Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
		return invoke(null, method, args);
	}

	/**
	 * 执行方法
	 *
	 * @param obj    对象，如果执行静态方法，此值为null
	 * @param method 方法
	 * @param args   方法参数
	 * @param <T>    方法返回值类型
	 * @return T 方法返回值
	 * @throws InvocationTargetException 调用目标异常
	 * @throws IllegalAccessException    非法访问异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> T invoke(Object obj, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
		if (null == method) {
			return null;
		}
		if (!method.isAccessible()) {
			method.setAccessible(true);
		}
		return (T) method.invoke(ModifierUtil.isStatic(method) ? null : obj, args);
	}

	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
		System.out.println(getField(ArrayUtil.class, "EMPTY_ARRAY_LENGTH"));
		System.out.println(getMethod(ArrayUtil.class, "isArray", Object.class));
		System.out.println(getMethod(ArrayUtil.class, "isEmpty", int[].class));
		System.out.println(getMethod(ArrayUtil.class, "isEmpty", long[].class));
		System.out.println(getMethod(ArrayUtil.class, "isEmpty", Object[].class));
		System.out.println(getMethod(String.class, "trim"));

		System.out.println(invoke(StringUtil.class, "firstCharLowCase", "Abc"));
		System.out.println(invoke(StringUtil.class, "isEmpty", ""));
		System.out.println(invoke(ArrayUtil.class, "isEmpty", (Object) new int[]{1, 2, 3}));
		// 泛型擦除会报错的
		System.out.println(invoke(ArrayUtil.class, "isEmpty", 1, 2));
	}

}