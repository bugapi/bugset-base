package org.bugapi.bugset.base.util.object;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 通用方法
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class ObjectUtil {

	/**
	 * 判断两个对象是否相同
	 *
	 * @param obj1 对象1
	 * @param obj2 对象2
	 * @return boolean 结果【true：相同、false：不相同】
	 */
	public static boolean equal(Object obj1, Object obj2) {
		return Objects.equals(obj1, obj2);
	}

	/**
	 * 比较两个对象是否不相等
	 *
	 * @param obj1 对象1
	 * @param obj2 对象2
	 * @return boolean 结果【false：相同、true：不相同】
	 */
	public static boolean notEqual(Object obj1, Object obj2) {
		return !equal(obj1, obj2);
	}

	/**
	 * 安全的对象比较 null对象排在末尾
	 *
	 * @param <T>  被比较对象类型
	 * @param obj1 对象1，可以为{@code null}
	 * @param obj2 对象2，可以为{@code null}
	 * @return 比较结果，如果obj1 小于 c2，返回数小于0，c1 等于 c2返回0，c1 大于 c2 大于0
	 * @see Comparator#compare(Object, Object)
	 */
	public static <T extends Comparable<? super T>> int compare(T obj1, T obj2) {
		return compare(obj1, obj2, false);
	}

	/**
	 * 安全的对象比较 null对象排在末尾
	 *
	 * @param <T>         被比较对象类型
	 * @param obj1        对象1，可以为{@code null}
	 * @param obj2        对象2，可以为{@code null}
	 * @param nullGreater 当被比较对象为null时是否排在前面
	 * @return 比较结果，如果obj1 小于 c2，返回数小于0，c1 等于 c2返回0，c1 大于 c2 大于0
	 * @see Comparator#compare(Object, Object)
	 */
	public static <T extends Comparable<? super T>> int compare(T obj1, T obj2, boolean nullGreater) {
		if (obj1 == obj2) {
			return 0;
		} else if (obj1 == null) {
			return nullGreater ? 1 : -1;
		} else if (obj2 == null) {
			return nullGreater ? -1 : 1;
		}
		return obj1.compareTo(obj2);
	}

	/**
	 * 判断对象是否为空
	 *
	 * @param obj Object对象
	 * @return boolean 【true：空】
	 */
	public static boolean isEmpty(Object obj) {
		// 对象是否为空
		if (obj == null) {
			return true;
			// 判断是否是optional对象
		} else if (obj instanceof Optional) {
			return !((Optional) obj).isPresent();
			// 判断是否是字符串
		} else if (obj instanceof CharSequence) {
			return ((CharSequence) obj).length() == 0;
			// 判断是否是数组
		} else if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
			// 判断是否是集合
		} else if (obj instanceof Collection) {
			return ((Collection) obj).isEmpty();
		} else {
			// 判断为map集合
			return obj instanceof Map && ((Map) obj).isEmpty();
		}
	}

	/**
	 * 检查对象为null
	 *
	 * @param obj 对象
	 * @return true 对象为 null
	 */
	public static boolean isNull(Object obj) {
		return null == obj;
	}

	/**
	 * 检查对象不为null
	 *
	 * @param obj 对象
	 * @return true 对象不为 null
	 */
	public static boolean isNotNull(Object obj) {
		return null != obj;
	}

	/**
	 * 如果对象为空，则返回一个默认值
	 *
	 * @param object       对象
	 * @param defaultValue 默认值
	 * @param <T>          泛型的类型
	 * @return T 返回一个默认值
	 */
	public static <T> T defaultIfNull(T object, T defaultValue) {
		return object != null ? object : defaultValue;
	}

	/**
	 * 对象中是否包含元素
	 * 支持的对象：String、Collection、Map、Iterator、Enumeration、Array
	 *
	 * @param obj     对象集合、数组、Map对象等
	 * @param element 一个元素
	 * @return boolean true:包含、false:不包含
	 */
	public static boolean contains(Object obj, Object element) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof String) {
			if (element == null) {
				return false;
			}
			return ((String) obj).contains(element.toString());
		}
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).contains(element);
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).containsValue(element);
		}

		if (obj instanceof Iterator) {
			Iterator<?> iter = (Iterator<?>) obj;
			while (iter.hasNext()) {
				Object o = iter.next();
				if (equal(o, element)) {
					return true;
				}
			}
			return false;
		}
		if (obj instanceof Enumeration) {
			Enumeration<?> enumeration = (Enumeration<?>) obj;
			while (enumeration.hasMoreElements()) {
				Object o = enumeration.nextElement();
				if (equal(o, element)) {
					return true;
				}
			}
			return false;
		}
		if (obj.getClass().isArray()) {
			int len = Array.getLength(obj);
			for (int i = 0; i < len; i++) {
				Object o = Array.get(obj, i);
				if (equal(o, element)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(null == null);
		System.out.println(defaultIfNull(null, 1));
		System.out.println(defaultIfNull(null, "12"));

		System.out.println(contains("1234567", "123"));
		System.out.println("123456".contains("123"));
	}
}
