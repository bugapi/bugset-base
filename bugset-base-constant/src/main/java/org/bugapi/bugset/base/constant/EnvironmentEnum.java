package org.bugapi.bugset.base.constant;

/**
 * 系统环境类型
 *
 * @author zhangxw
 * @since 0.0.1
 */
public enum EnvironmentEnum {

	/**开发环境*/
	DEVELOPMENT("development"),
	/** 生产环境 */
	PRODUCTION("production"),
	/** 测试环境 */
	TESTING("testing");

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
