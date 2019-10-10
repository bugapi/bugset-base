package org.bugapi.bugset.base.util.reflect;

import org.bugapi.bugset.base.constant.ModifierEnum;
import org.bugapi.bugset.base.util.array.ArrayUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 修饰符工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class ModifierUtil {

	/**
	 * 是否同时存在一个或多个修饰符（可能有多个修饰符，如果有指定的修饰符则返回true）
	 *
	 * @param clazz         类
	 * @param nodifierEnum 修饰符枚举
	 * @return 是否有指定修饰符，如果有返回true，否则false，如果提供参数为null返回false
	 */
	public static boolean hasModifier(Class<?> clazz, ModifierEnum... nodifierEnum) {
		if (null == clazz || ArrayUtil.isEmpty(nodifierEnum)) {
			return false;
		}
		return 0 != (clazz.getModifiers() & modifiersToInt(nodifierEnum));
	}

	/**
	 * 是否同时存在一个或多个修饰符（可能有多个修饰符，如果有指定的修饰符则返回true）
	 *
	 * @param constructor   构造方法
	 * @param nodifierEnum 修饰符枚举
	 * @return 是否有指定修饰符，如果有返回true，否则false，如果提供参数为null返回false
	 */
	public static boolean hasModifier(Constructor<?> constructor, ModifierEnum... nodifierEnum) {
		if (null == constructor || ArrayUtil.isEmpty(nodifierEnum)) {
			return false;
		}
		return 0 != (constructor.getModifiers() & modifiersToInt(nodifierEnum));
	}

	/**
	 * 是否同时存在一个或多个修饰符（可能有多个修饰符，如果有指定的修饰符则返回true）
	 *
	 * @param method        方法
	 * @param nodifierEnum 修饰符枚举
	 * @return 是否有指定修饰符，如果有返回true，否则false，如果提供参数为null返回false
	 */
	public static boolean hasModifier(Method method, ModifierEnum... nodifierEnum) {
		if (null == method || ArrayUtil.isEmpty(nodifierEnum)) {
			return false;
		}
		return 0 != (method.getModifiers() & modifiersToInt(nodifierEnum));
	}

	/**
	 * 是否同时存在一个或多个修饰符（可能有多个修饰符，如果有指定的修饰符则返回true）
	 *
	 * @param field         字段
	 * @param nodifierEnum 修饰符枚举
	 * @return 是否有指定修饰符，如果有返回true，否则false，如果提供参数为null返回false
	 */
	public static boolean hasModifier(Field field, ModifierEnum... nodifierEnum) {
		if (null == field || ArrayUtil.isEmpty(nodifierEnum)) {
			return false;
		}
		return 0 != (field.getModifiers() & modifiersToInt(nodifierEnum));
	}

	/**
	 * 是否是Public字段
	 *
	 * @param field 字段
	 * @return 是否是Public
	 */
	public static boolean isPublic(Field field) {
		return hasModifier(field, ModifierEnum.PUBLIC);
	}

	/**
	 * 是否是Public方法
	 *
	 * @param method 方法
	 * @return 是否是Public
	 */
	public static boolean isPublic(Method method) {
		return hasModifier(method, ModifierEnum.PUBLIC);
	}

	/**
	 * 是否是Public类
	 *
	 * @param clazz 类
	 * @return 是否是Public
	 */
	public static boolean isPublic(Class<?> clazz) {
		return hasModifier(clazz, ModifierEnum.PUBLIC);
	}

	/**
	 * 是否是Public构造
	 *
	 * @param constructor 构造
	 * @return 是否是Public
	 */
	public static boolean isPublic(Constructor<?> constructor) {
		return hasModifier(constructor, ModifierEnum.PUBLIC);
	}

	/**
	 * 是否是static字段
	 *
	 * @param field 字段
	 * @return 是否是static
	 * @since 4.0.8
	 */
	public static boolean isStatic(Field field) {
		return hasModifier(field, ModifierEnum.STATIC);
	}

	/**
	 * 是否是static方法
	 *
	 * @param method 方法
	 * @return 是否是static
	 * @since 4.0.8
	 */
	public static boolean isStatic(Method method) {
		return hasModifier(method, ModifierEnum.STATIC);
	}

	/**
	 * 是否是static类
	 *
	 * @param clazz 类
	 * @return 是否是static
	 * @since 4.0.8
	 */
	public static boolean isStatic(Class<?> clazz) {
		return hasModifier(clazz, ModifierEnum.STATIC);
	}


	/**
	 * 多个修饰符做“与”操作，表示同时存在多个修饰符
	 *
	 * @param nodifierEnum 修饰符列表，元素不能为空
	 * @return “与”之后的修饰符
	 */
	private static int modifiersToInt(ModifierEnum... nodifierEnum) {
		int modifier = nodifierEnum[0].getType();
		for (int i = 1; i < nodifierEnum.length; i++) {
			modifier &= nodifierEnum[i].getType();
		}
		return modifier;
	}
}
