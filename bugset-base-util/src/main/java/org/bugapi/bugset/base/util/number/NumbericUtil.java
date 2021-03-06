package org.bugapi.bugset.base.util.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字工具类【包含加、减、乘、除、以及保留几位小数等】
 * https://blog.csdn.net/f641385712/article/details/86525051
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class NumbericUtil {

	/**
	 * 加法
	 *
	 * @param d1 被加数
	 * @param d2 加数
	 * @return double d1+d2的和
	 */
	public static double plus(double d1, double d2) {
		BigDecimal one = new BigDecimal(String.valueOf(d1));
		BigDecimal two = new BigDecimal(String.valueOf(d2));
		return one.add(two).doubleValue();
	}

	/**
	 * 加法【四舍五入保留几位小数】
	 *
	 * @param d1    被加数
	 * @param d2    加数
	 * @param scale 保留小数的位数
	 * @return double d1+d2的和
	 */
	public static double plus(double d1, double d2, int scale) {
		return round(plus(d1, d2), scale);
	}

	/**
	 * 减法
	 *
	 * @param d1 被减数
	 * @param d2 减数
	 * @return double d1-d2的差
	 */
	public static double minus(double d1, double d2) {
		BigDecimal one = new BigDecimal(String.valueOf(d1));
		BigDecimal two = new BigDecimal(String.valueOf(d2));
		return one.subtract(two).doubleValue();
	}

	/**
	 * 减法【四舍五入保留几位小数】
	 *
	 * @param d1    被减数
	 * @param d2    减数
	 * @param scale 保留小数的位数
	 * @return double d1-d2的差
	 */
	public static double minus(double d1, double d2, int scale) {
		return round(minus(d1, d2), scale);
	}

	/**
	 * 乘法
	 *
	 * @param d1 被乘数
	 * @param d2 乘数
	 * @return double d1×d2的积
	 */
	public static double multiply(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(String.valueOf(d1));
		BigDecimal bd2 = new BigDecimal(String.valueOf(d2));
		return bd1.multiply(bd2).doubleValue();
	}

	/**
	 * 乘法【四舍五入保留几位小数】
	 *
	 * @param d1    被乘数
	 * @param d2    乘数
	 * @param scale 保留小数的位数
	 * @return double d1×d2的积
	 */
	public static double multiply(double d1, double d2, int scale) {
		BigDecimal bd1 = new BigDecimal(String.valueOf(d1));
		BigDecimal bd2 = new BigDecimal(String.valueOf(d2));
		return round(bd1.multiply(bd2).doubleValue(), scale);
	}

	/**
	 * 将一个数字字符串转成一个数字
	 *
	 * @param numString 数字字符串
	 * @param scale     保留几位小数
	 * @return double 转成的数字
	 */
	public static double round(String numString, int scale) {
		BigDecimal decimal = new BigDecimal(numString);
		return divide(decimal.doubleValue(), 1.0d, scale);
	}

	/**
	 * 将一个数字取四舍五入的值
	 *
	 * @param d1    数字
	 * @param scale 保留几位小数
	 * @return double 转成的数字
	 */
	public static double round(double d1, int scale) {
		return divide(d1, 1.0d, scale);
	}

	/**
	 * 除法
	 *
	 * @param d1 被除数
	 * @param d2 除数
	 * @return double d1÷d2的商
	 */
	public static double divide(double d1, double d2) {
		if (isZero(d2)) {
			return 0;
		}
		BigDecimal one = new BigDecimal(String.valueOf(d1));
		BigDecimal two = new BigDecimal(String.valueOf(d2));
		return one.divide(two, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 除法
	 *
	 * @param d1    被除数
	 * @param d2    除数
	 * @param scale 保留小数的位数
	 * @return double d1÷d2的商
	 */
	public static double divide(double d1, double d2, int scale) {
		return divide(d1, d2, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 除法
	 *
	 * @param d1           被除数
	 * @param d2           除数
	 * @param scale        保留小数的位数
	 * @param roundingMode 取舍方式
	 * @return double d1÷d2的商
	 */
	private static double divide(double d1, double d2, int scale, int roundingMode) {
		if (isZero(d2)) {
			return 0;
		}
		BigDecimal one = new BigDecimal(String.valueOf(d1));
		BigDecimal two = new BigDecimal(String.valueOf(d2));
		return one.divide(two, scale, roundingMode).doubleValue();
	}

	/**
	 * 两个数字四舍五入之后比较大小
	 *
	 * @param d1    数字1
	 * @param d2    数字2
	 * @param scale 保留小数的位数
	 * @return 返回值：
	 * -1：d1<d2
	 * 0：d1=d2
	 * 1：d1>d2
	 */
	public static int compare(double d1, double d2, int scale) {
		BigDecimal one = new BigDecimal(String.valueOf(round(d1, scale)));
		BigDecimal two = new BigDecimal(String.valueOf(round(d2, scale)));
		return one.compareTo(two);
	}

	/**
	 * 两个数字比较大小
	 *
	 * @param d1 数字1
	 * @param d2 数字2
	 * @return 返回值：
	 * -1：d1<d2
	 * 0：d1=d2
	 * 1：d1>d2
	 */
	public static int compare(double d1, double d2) {
		BigDecimal one = new BigDecimal(String.valueOf(d1));
		BigDecimal two = new BigDecimal(String.valueOf(d2));
		return one.compareTo(two);
	}

	/**
	 * 生成一个数字四舍五入保留指定位数后的字符串
	 *
	 * @param d     数字
	 * @param scale 保留小数的位数
	 * @return String 字符串
	 */
	public static String toString(double d, int scale) {
		double r = round(d, scale);
		return (new DecimalFormat(getFormatPattern(scale))).format(r);
	}

	/**
	 * 判断一个数字是否为0
	 *
	 * @param value 数字
	 * @return boolean true:是0；false:不是0
	 */
	private static boolean isZero(double value) {
		return value == 0;
	}

	/**
	 * 补0格式
	 *
	 * @param scale 补0的个数
	 * @return String 由0组成的字符串
	 */
	private static String getFormatPattern(int scale) {
		StringBuilder buffer = new StringBuilder("0");
		if (scale > 0) {
			buffer.append(".");
		}
		for (int i = 0; i < scale; i++) {
			buffer.append("0");
		}
		return buffer.toString();
	}


	/**
	 * 将一个数字取四舍五入取整
	 *
	 * @param d1 数字
	 * @return double 转成的数字
	 */
	public static int roundHalfUp(double d1) {
		return divideRound(d1, 1.0d, 0, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 将一个数字向下取整
	 *
	 * @param d1 数字
	 * @return double 转成的数字
	 */
	public static int roundDown(double d1) {
		return divideRound(d1, 1.0d, 0, BigDecimal.ROUND_DOWN);
	}

	/**
	 * 除法【四舍五入保留几位小数】
	 *
	 * @param d1           被除数
	 * @param d2           除数
	 * @param scale        保留小数的位数
	 * @param roundingMode 取舍方式
	 * @return double d1÷d2的商
	 */
	public static int divideRound(double d1, double d2, int scale, int roundingMode) {
		if (isZero(d2)) {
			return 0;
		}
		BigDecimal one = new BigDecimal(String.valueOf(d1));
		BigDecimal two = new BigDecimal(String.valueOf(d2));
		return one.divide(two, scale, roundingMode).intValue();
	}

	public static void main(String[] args) {
		System.out.println(isZero(0.0));
		System.out.println(divide(1L, 3L));
		System.out.println(divide(8L, 9L, 4));
		System.out.println(divideRound(8L, 9L, 4, BigDecimal.ROUND_UP));
		System.out.println(roundHalfUp(0.87));
	}
}
