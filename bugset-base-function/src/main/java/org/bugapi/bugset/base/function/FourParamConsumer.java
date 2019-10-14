package org.bugapi.bugset.base.function;

/**
 * 四参数消费函数接口
 *
 * @author gust
 * @since 0.0.1
 */
@FunctionalInterface
public interface FourParamConsumer<A, B, C, D> {

	/**
	 * 执行四参数消费型接口
	 *
	 * @param a 参数1
	 * @param b 参数2
	 * @param c 参数3
	 * @param d 参数4
	 */
	void accept(A a, B b, C c, D d);
}
