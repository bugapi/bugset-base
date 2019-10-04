package org.bugapi.bugset.base.util.reflect;

/**
 * @author zhangxw
 * @since
 */
public class ClassLoaderUtil {
	/**
	 * 获取类加载器
	 *
	 * @return 类加载器
	 */
	public static ClassLoader getDefaultClassLoader() {
		// 获取当前线程的 上下文类加载器
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (cl == null) {
			// 如果没有当前线程的 上下文类加载器，获取当前类的类加载器
			cl = ClassLoaderUtil.class.getClassLoader();
			if (cl == null) {
				// 如果没有当前类的类加载器，获取系统启动类加载器
				cl = ClassLoader.getSystemClassLoader();
			}
		}
		return cl;
	}
}
