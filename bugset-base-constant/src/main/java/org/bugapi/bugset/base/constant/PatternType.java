package org.bugapi.bugset.base.constant;

import java.util.regex.Pattern;

/**
 * 正则表达式的模式类型
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class PatternType {
	/**
	 * 整数的正则表达式 支持0、1、100、-1、-100。不支持-0、-01、00、09
	 */
	public static final Pattern INTEGER_PATTERN = Pattern.compile("^(0|-?[1-9][0-9]*)$");

}
