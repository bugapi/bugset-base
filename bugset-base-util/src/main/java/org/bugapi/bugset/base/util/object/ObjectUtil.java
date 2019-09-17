package org.bugapi.bugset.base.util.object;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * 通用方法
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class ObjectUtil {

	/**
	 * 判断两个对象是否相同
	 * @Title: equal
	 * @param obj1 对象1
	 * @param obj2 对象2
	 * @return boolean 结果【true：相同、false：不相同】
	 */
	public static boolean equal(Object obj1, Object obj2) {
		return (obj1 == obj2) || (obj1 != null && obj1.equals(obj2));
	}

	/**
	 * 比较两个对象是否不相等
	 * @Title: notEqual
	 * @param obj1 对象1
	 * @param obj2 对象2
	 * @return boolean 结果【false：相同、true：不相同】
	 */
	public static boolean notEqual(Object obj1, Object obj2) {
		return !equal(obj1, obj2);
	}

	/**
	 * 安全的对象比较 null对象排在末尾
	 * @Title:
	 * @param <T> 被比较对象类型
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
	 * @Title:
	 * @param <T> 被比较对象类型
	 * @param obj1 对象1，可以为{@code null}
	 * @param obj2 对象2，可以为{@code null}
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
	 * 将Object转成Long对象
	 * @Title: convertObjectToLong
	 * @param obj Object对象
	 * @return Long Long对象
	 */
	public static Long convertObjectToLong(Object obj) {
		if (obj instanceof Double) {
			return ((Double) obj).longValue();
		} else {
			return 0L;
		}
	}

	/**
	 * 将Object转成Double对象
	 * @Title: convertObjectToDouble
	 * @param obj Object对象
	 * @return Double Double对象
	 */
	public static Double convertObjectToDouble(Object obj) {
		if (obj instanceof Double) {
			return ((Double) obj);
		} else {
			return 0D;
		}
	}

	/**
	 * 判断对象是否为空
	 * @Title: isEmpty
	 * @param obj Object对象
	 * @return boolean 【true：空】
	 */
	public static boolean isEmpty(Object obj) {
		// 对象是否为空
		if (obj == null) {
			return true;
			// 判断是否是optional对象
		} else if (obj instanceof Optional) {
			return !((Optional)obj).isPresent();
			// 判断是否是字符串
		} else if (obj instanceof CharSequence) {
			return ((CharSequence)obj).length() == 0;
			// 判断是否是数组
		} else if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
			// 判断是否是集合
		} else if (obj instanceof Collection) {
			return ((Collection)obj).isEmpty();
		} else {
			// 判断为map集合
			return obj instanceof Map && ((Map) obj).isEmpty();
		}
	}

	public static void main(String[] args) {
		System.out.println(null == null);
	}
}
