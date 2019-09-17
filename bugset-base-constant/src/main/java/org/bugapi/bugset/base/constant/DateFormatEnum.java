package org.bugapi.bugset.base.constant;

/**
 * 日期格式枚举类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public enum DateFormatEnum {
	/**
	 * 日期格式化 yyyy
	 */
	YYYY("yyyy"),
	/**
	 * 日期格式化 MM
	 */
	MM("MM"),
	/**
	 * 日期格式化 dd
	 */
	DD("dd"),
	/**
	 * 日期格式化 yyyyMM
	 */
	YYYYMM("yyyyMM"),
	/**
	 * 日期格式化 yyyy-MM
	 */
	YYYYMM_BAR("yyyy-MM"),
	/**
	 * 日期格式化 yyyy/MM
	 */
	YYYYMM_SLASH("yyyy/MM"),
	/**
	 * 日期格式化 yyyyMMdd
	 */
	YYYYMMDD("yyyyMMdd"),
	/**
	 * 日期格式化 yyyy-MM-dd
	 */
	YYYYMMDD_BAR("yyyy-MM-dd"),
	/**
	 * 日期格式化 yyyy/MM/dd
	 */
	YYYYMMDD_SLASH("yyyy/MM/dd"),
	/**
	 * 日期格式化 yyyy年MM月dd日
	 */
	YYYYMMDD_CHN("yyyy年MM月dd日"),
	/**
	 * 日期和时间格式化12小时制 yyyy-MM-dd hh:mm:ss
	 */
	YYYYMMDDHH12MMSS_BAR("yyyy-MM-dd hh:mm:ss"),
	/**
	 * 日期和时间格式化24小时制 yyyy-MM-dd hh24:mm:ss
	 */
	YYYYMMDDHH24MMSS_BAR("yyyy-MM-dd HH:mm:ss");

	/**
	 * 格式
	 */
	private String format;

	DateFormatEnum(String format) {
		this.format = format;
	}

	public String getFormat() {
		return format;
	}
}
