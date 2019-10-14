package org.bugapi.bugset.base.function;

/**
 * 三参数断言函数接口
 *
 * @author gust
 * @since 0.0.1
 */
@FunctionalInterface
public interface ThreeParamPredicate<A, B, C> {

	/**
	 * 执行三参数断言型接口
	 *
	 * @param a 参数1
	 * @param b 参数2
	 * @param c 参数3
	 * @return boolean
	 */
	boolean test(A a, B b, C c);
}
