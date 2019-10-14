package org.bugapi.bugset.base.function;

/**
 * 三参数消费函数接口
 *
 * @author gust
 * @since 0.0.1
 */
@FunctionalInterface
public interface ThreeParamConsumer<A, B, C> {

	/**
	 * 执行三参数消费型接口
	 *
	 * @param a 参数1
	 * @param b 参数2
	 * @param c 参数3
	 */
	void accept(A a, B b, C c);
}
