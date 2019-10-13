package org.bugapi.bugset.base.util.object;

import org.bugapi.bugset.base.util.string.StringUtil;

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
		} else if (isNull(obj1)) {
			return nullGreater ? 1 : -1;
		} else if (isNull(obj2)) {
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
		if (isNull(obj)) {
			return true;
			// 判断是否是optional对象
		} else if (obj instanceof Optional) {
			return !((Optional) obj).isPresent();
			// 判断是否是字符串
		} else if (obj instanceof CharSequence) {
			return StringUtil.trim((CharSequence) obj, 0).length() == 0;
			// 判断是否是数组
		} else if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
			// 判断是否是集合
		} else if (obj instanceof Collection) {
			return ((Collection) obj).isEmpty();
		} else if (obj instanceof Map) {
			// 判断为map集合
			return ((Map) obj).isEmpty();
		} else {
			return false;
		}
	}

	/**
	 * 判断对象是否为空
	 *
	 * @param obj Object对象
	 * @return boolean 【false：空】
	 */
	public static boolean isNotEmpty(Object obj) {
		// 对象是否为空
		if (isNull(obj)) {
			return false;
			// 判断是否是optional对象
		} else if (obj instanceof Optional) {
			return ((Optional) obj).isPresent();
			// 判断是否是字符串
		} else if (obj instanceof CharSequence) {
			return StringUtil.trim((CharSequence) obj, StringUtil.TRIM_LEFT_RIGHT).length() >= 1;
			// 判断是否是数组
		} else if (obj.getClass().isArray()) {
			return Array.getLength(obj) >= 1;
			// 判断是否是集合
		} else if (obj instanceof Collection) {
			return ((Collection) obj).size() >= 1;
		} else if (obj instanceof Map) {
			// 判断为map集合
			return ((Map) obj).size() >= 1;
		} else {
			return true;
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
	 * @param data    对象集合、数组、Map对象等
	 * @param element 一个元素
	 * @return boolean true:包含、false:不包含
	 */
	public static boolean contains(Object data, Object element) {
		if (data == null) {
			return false;
		}
		if (data instanceof String) {
			if (element == null) {
				return false;
			}
			return ((String) data).contains(element.toString());
		}
		if (data instanceof Collection) {
			return ((Collection<?>) data).contains(element);
		}
		if (data instanceof Map) {
			return ((Map<?, ?>) data).containsValue(element);
		}

		if (data instanceof Iterator) {
			Iterator<?> iter = (Iterator<?>) data;
			while (iter.hasNext()) {
				Object o = iter.next();
				if (equal(o, element)) {
					return true;
				}
			}
			return false;
		}
		if (data instanceof Enumeration) {
			Enumeration<?> enumeration = (Enumeration<?>) data;
			while (enumeration.hasMoreElements()) {
				Object o = enumeration.nextElement();
				if (equal(o, element)) {
					return true;
				}
			}
			return false;
		}
		if (data.getClass().isArray()) {
			int len = Array.getLength(data);
			for (int i = 0; i < len; i++) {
				Object o = Array.get(data, i);
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
