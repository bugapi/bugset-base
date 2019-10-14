package org.bugapi.bugset.base.function;

/**
 * 双参数消费函数接口
 *
 * @author gust
 * @since 0.0.1
 */
@FunctionalInterface
public interface TwoParamConsumer<A, B> {

	/**
	 * 执行双参数消费型接口
	 *
	 * @param a 参数1
	 * @param b 参数2
	 */
	void accept(A a, B b);
}
