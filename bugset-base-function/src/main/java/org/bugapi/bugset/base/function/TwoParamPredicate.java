package org.bugapi.bugset.base.function;

/**
 * 双参数断言函数接口
 *
 * @author gust
 * @since 0.0.1
 */
@FunctionalInterface
public interface TwoParamPredicate<A, B> {

	/**
	 * 执行双参数断言型接口
	 *
	 * @param a 参数1
	 * @param b 参数2
	 * @return boolean
	 */
	boolean test(A a, B b);
}
