package org.bugapi.bugset.base.function;

/**
 * 五参数函数
 *
 * @author gust
 * @since 0.0.1
 */
@FunctionalInterface
public interface FiveParamFunction<A, B, C, D, E, R> {

	/**
	 * 执行五参数功能型接口
	 *
	 * @param a 参数1
	 * @param b 参数2
	 * @param c 参数3
	 * @param d 参数4
	 * @param e 参数5
	 * @return R
	 */
	R apply(A a, B b, C c, D d, E e);
}
