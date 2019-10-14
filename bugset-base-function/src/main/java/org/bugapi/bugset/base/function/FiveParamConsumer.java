package org.bugapi.bugset.base.function;

/**
 * 五参数消费函数接口
 *
 * @author gust
 * @since 0.0.1
 */
@FunctionalInterface
public interface FiveParamConsumer<A, B, C, D, E> {

	/**
	 * 执行五参数消费型接口
	 *
	 * @param a 参数1
	 * @param b 参数2
	 * @param c 参数3
	 * @param d 参数4
	 * @param e 参数5
	 */
	void accept(A a, B b, C c, D d, E e);
}
