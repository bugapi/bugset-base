package org.bugapi.bugset.base.constant;

/**
 * 逻辑判断类型
 * @author zhangxw
 * @since 0.0.1
 */
public enum LogicalEnum {

	/**
	 * 或
	 */
	OR("or"),

	/**
	 * 且
	 */
	AND("and");

	/**
	 * 类型
	 */
	private String type;
	LogicalEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
