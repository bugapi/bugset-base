package org.bugapi.bugset.base.function;

/**
 * 四参数函数
 *
 * @author gust
 * @since 0.0.1
 */
@FunctionalInterface
public interface FourParamFunction<A, B, C, D, R> {

	/**
	 * 执行四参数功能型接口
	 *
	 * @param a 参数1
	 * @param b 参数2
	 * @param c 参数3
	 * @param d 参数4
	 * @return R
	 */
	R apply(A a, B b, C c, D d);
}
