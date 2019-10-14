package org.bugapi.bugset.base.function;

/**
 * 双参数函数
 *
 * @author gust
 * @since 0.0.1
 */
@FunctionalInterface
public interface TwoParamFunction<A, B, R> {

	/**
	 * 执行双参数功能型接口
	 *
	 * @param a 参数1
	 * @param b 参数2
	 * @return R
	 */
	R apply(A a, B b);
}
