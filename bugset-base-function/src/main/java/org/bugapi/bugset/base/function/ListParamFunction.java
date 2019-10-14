package org.bugapi.bugset.base.function;

/**
 * 列表参数函数
 *
 * @author gust
 * @since 0.0.1
 */
@FunctionalInterface
public interface ListParamFunction<A, R> {

	/**
	 * 执行分页功能型接口
	 *
	 * @param a    参数1
	 * @param page 当前页码
	 * @param rows 单页显示数量
	 * @param sidx 排序字段
	 * @param sord 升序或者降序
	 * @return R
	 */
	R apply(A a, Integer page, Integer rows, String sidx, String sord);
}
