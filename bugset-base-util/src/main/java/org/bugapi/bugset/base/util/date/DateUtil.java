package org.bugapi.bugset.base.util.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @ClassName: DateUtil
 * @Description: 日期工具类
 * @author: gust
 * @date: 2019/8/16
 */
public class DateUtil {

	private static final String TIME_COLON = ":";

	/**
	 * 返回当前日期格式化后的字符串
	 * @param datePattern 日期格式
	 * @return 格式化后的日期
	 */
	public static String formatCurrentDateToString(DatePattern datePattern) {
		return formatDateToString(null, datePattern);
	}

	/**
	 * 返回日期格式化后的字符串
	 * @param date 日期
	 * @param datePattern 日期格式
	 * @return 格式化后的日期
	 */
	public static String formatDateToString(Date date, DatePattern datePattern) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(datePattern.getPattern());
		return localDateTime.format(dtf);
	}

	/**
	 * 返回日期字符串解析后的日期（日期字符串不合法返回null）
	 * @param dateStr 日期字符串
	 * @param datePattern 日期格式
	 * @return 日期字符串解析后的日期 {@link DatePattern}
	 */
	public static Date formatStringToDate(String dateStr, DatePattern datePattern) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(datePattern.getPattern());
		if (dateStr.contains(TIME_COLON)) {
			LocalDateTime dateTime;
			try {
				dateTime = LocalDateTime.parse(dateStr, dtf);
			} catch (DateTimeParseException e) {
				return null;
			}
			return localDateTimeToDate(dateTime);
		} else {
			LocalDate date;
			try {
				date = LocalDate.parse(dateStr, dtf);
			} catch (DateTimeParseException e) {
				return null;
			}
			return localDateToDate(date);
		}
	}

	/**
	 * 获取当前日期的开始时间（00:00:00）
	 * @return 当前日期的开始时间（00:00:00）
	 */
	public static Date getStartDateTimeOfCurrentDay() {
		return getStartDateTimeOfDay(dateToLocalDate(null));
	}

	/**
	 * 获取指定日期的开始时间（00:00:00）
	 * @param date 日期
	 * @return 指定日期的开始时间（00:00:00）
	 */
	public static Date getStartDateTimeOfDay(Date date) {
		return getStartDateTimeOfDay(dateToLocalDate(date));
	}

	/**
	 * 获取指定日期的开始时间（00:00:00）
	 * @param date 日期
	 * @return 指定日期的开始时间（00:00:00）
	 */
	public static Date getStartDateTimeOfDay(LocalDate date) {
		return localDateTimeToDate(LocalDateTime.of(date, LocalTime.MIN));
	}

	/**
	 * 获取当前日期的结束时间（23:59:59）
	 * @param date 日期
	 * @return 当前日期的结束时间（23:59:59）
	 */
	public static Date getLastDateTimeOfCurrentDay(Date date) {
		return getLastDateTimeOfDay(dateToLocalDate(null));
	}

	/**
	 * 获取指定日期的结束时间（23:59:59）
	 * @param date 日期
	 * @return 指定日期的结束时间（23:59:59）
	 */
	public static Date getLastDateTimeOfDay(Date date) {
		return getLastDateTimeOfDay(dateToLocalDate(date));
	}

	/**
	 * 获取指定日期的结束时间（23:59:59）
	 * @param date 日期
	 * @return 指定日期的结束时间（23:59:59）
	 */
	public static Date getLastDateTimeOfDay(LocalDate date) {
		return localDateTimeToDate(LocalDateTime.of(date, LocalTime.MAX));
	}

	/**
	 * @Title: plusTime
	 * @Description: 对一个时间对象加时长（同理也可对年/月/日/时/分/秒）
	 * @param date 待处理时间
	 * @param duration 增加时长
	 * @param dateUnit 时间单位类型 {@link DateUnit}
	 * @return Date
	 */
	public static Date modifyDate(Date date, long duration, DateUnit dateUnit){
		LocalDateTime d1 = dateToLocalDateTime(date);
		LocalDateTime d2 = null;
		switch (dateUnit) {
			case YEAR:
				d2 = d1.plusYears(duration);
				break;
			case MONTH:
				d2 = d1.plusMonths(duration);
				break;
			case WEEK:
				d2 = d1.plusWeeks(duration);
				break;
			case DAY:
				d2 = d1.plusDays(duration);
				break;
			case HOUR:
				d2 = d1.plusHours(duration);
				break;
			case MINUTE:
				d2 = d1.plusMinutes(duration);
				break;
			case SECOND:
				d2 = d1.plusSeconds(duration);
				break;
			default:
				break;
		}
		return localDateTimeToDate(d2);
	}

	/**
	 * 比较日期（精确到天）
	 * @param firstDate 第一个日期
	 * @param secondDate 第二个日期
	 * @return boolean 1    firstDate < secondDate
	 *                 0    firstDate == secondDate
	 *                 -1   firstDate > secondDate
	 */
	public static int compareDate(Date firstDate, Date secondDate) {
		LocalDate ld1 = dateToLocalDate(firstDate);
		LocalDate ld2 = dateToLocalDate(secondDate);
		if (ld1.isEqual(ld2)) {
			return 0;
		}
		return ld1.isBefore(ld2) ? 1 : -1;
	}

	/**
	 * 比较日期（精确到秒）
	 * @param firstDate 第一个日期
	 * @param secondDate 第二个日期
	 * @return boolean 1    firstDate < secondDate
	 *                 0    firstDate == secondDate
	 *                 -1   firstDate > secondDate
	 */
	public static int compareDateTime(Date firstDate, Date secondDate) {
		LocalDateTime ld1 = dateToLocalDateTime(firstDate);
		LocalDateTime ld2 = dateToLocalDateTime(secondDate);
		if (ld1.isEqual(ld2)) {
			return 0;
		}
		return ld1.isBefore(ld2) ? 1 : -1;
	}

	/**
	 * @Title: getFirstDayOfCurrentMonth
	 * @Description: 获取当前月的第一天日期
	 * @return Date 所在月的第一天日期, 时分秒为 00:00:00
	 */
	public static Date getFirstDayOfCurrentMonth(){
		return getFirstDayOfMonth(LocalDate.now());
	}

	/**
	 * 获取指定日期所在月的第一天日期
	 * @param date 目标日期（如果为空，则默认使用当前日期）
	 * @return Date 所在月的第一天日期, 时分秒为 00:00:00
	 */
	public static Date getFirstDayOfMonth(Date date){
		return getFirstDayOfMonth(dateToLocalDate(date));
	}

	/**
	 * 获取指定日期所在月的第一天日期
	 * @param date 目标日期（如果为空，则默认使用当前日期）
	 * @return Date 所在月的第一天日期, 时分秒均为0
	 */
	public static Date getFirstDayOfMonth(LocalDate date){
		return localDateToDate((date == null ? LocalDate.now() : date).with(TemporalAdjusters.firstDayOfMonth()));
	}

	/**
	 * 获取当前月的最后一天日期
	 * @return Date 所在月的最后一天日期, 时分秒为 00:00:00
	 */
	public static Date getLastDayOfCurrentMonth(){
		return getLastDayOfMonth(dateToLocalDate(null));
	}

	/**
	 * 获取指定日期所在月的最后一天日期
	 * @param date 目标日期（如果为空，则默认使用当前日期）
	 * @return Date 所在月的最后一天日期, 时分秒为 00:00:00
	 */
	public static Date getLastDayOfMonth(Date date){
		return getLastDayOfMonth(dateToLocalDate(date));
	}

	/**
	 * 获取指定日期所在月的最后一天日期
	 * @param date 目标日期（如果为空，则默认使用当前日期）
	 * @return Date 所在月的最后一天日期, 时分秒为 00:00:00
	 */
	public static Date getLastDayOfMonth(LocalDate date){
		return localDateToDate((date == null ? LocalDate.now() : date).with(TemporalAdjusters.lastDayOfMonth()));
	}

	/**
	 * 获取两个日期指定类型的时间间隔，只支持年月日
	 * @param firstDate
	 * @param secondDate
	 * @param dateUnit 时间单位类型 {@link DateUnit}
	 * @return 两个日期指定类型的时间间隔
	 * @throw new UnsupportedOperationException()
	 */
	public static int getDateBetween(Date firstDate, Date secondDate, DateUnit dateUnit) {
		Period p = Period.between(dateToLocalDate(firstDate), dateToLocalDate(secondDate));
		switch (dateUnit) {
			case YEAR:
				return p.getYears();
			case MONTH:
				return p.getMonths();
			case DAY:
				return p.getDays();
			default:
				throw new UnsupportedOperationException();
		}
	}

	/**
	 * 获取当月的总天数
	 * @return 当月的总天数
	 */
	public static int getDurationOfCurrentMonth() {
		return getDurationOfMonth(null);
	}

	/**
	 * 获取指定日期所在月的总天数
	 * @return 当月的总天数
	 */
	public static int getDurationOfMonth(Date date) {
		return dateToLocalDate(date).lengthOfMonth();
	}


	private static LocalDate dateToLocalDate(Date date) {
		if (date == null) {
			return LocalDate.now();
		}
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	private static Date localDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	private static LocalDateTime dateToLocalDateTime(Date date) {
		if (date == null) {
			return LocalDateTime.now();
		}
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	private static Date localDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant().truncatedTo(
				ChronoUnit.SECONDS));
	}

	public enum DateUnit {
		YEAR, MONTH, WEEK, DAY, HOUR, MINUTE, SECOND
	}

	public enum DatePattern {
		YYYY("yyyy"),
		MM("MM"),
		DD("dd"),
		YYYYMM("yyyyMM"),
		YYYYMM_BAR("yyyy-MM"),
		YYYYMM_SLASH("yyyy/MM"),
		YYYYMMDD("yyyyMMdd"),
		YYYYMMDD_BAR("yyyy-MM-dd"),
		YYYYMMDD_SLASH("yyyy/MM/dd"),
		YYYYMMDD_CHN("yyyy年MM月dd日"),
		YYYYMMDDHH12MMSS_BAR("yyyy-MM-dd hh:mm:ss"),
		YYYYMMDDHH24MMSS_BAR("yyyy-MM-dd HH:mm:ss");

		private String pattern;

		DatePattern(String pattern) {
			this.pattern = pattern;
		}

		public String getPattern() {
			return pattern;
		}
	}
}