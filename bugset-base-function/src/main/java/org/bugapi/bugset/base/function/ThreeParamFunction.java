package org.bugapi.bugset.base.function;

/**
 * 三参数函数
 *
 * @author gust
 * @since 0.0.1
 */
@FunctionalInterface
public interface ThreeParamFunction<A, B, C, R> {

	/**
	 * 执行三参数功能型接口
	 *
	 * @param a 参数1
	 * @param b 参数2
	 * @param c 参数3
	 * @return R
	 */
	R apply(A a, B b, C c);
}
