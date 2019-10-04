package org.bugapi.bugset.base.constant;

/**
 * 系统环境类型
 *
 * @author zhangxw
 * @since 0.0.1
 */
public enum EnvironmentEnum {

	/**开发环境*/
	DEV("dev"),
	/** 生产环境 */
	PRO("pro"),
	/** 测试环境 */
	TEST("test");

	/**
	 * 类型
	 */
	private String type;
	EnvironmentEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
