package org.bugapi.bugset.base.util.web;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户代理工具类【使得服务器能够识别客户使用的操作系统及版本、CPU 类型、浏览器及版本、浏览器渲染引擎、浏览器语言、浏览器插件等】
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class UserAgentUtil {

	/**
	 * 获取用户代理对象
	 *
	 * @param request 请求
	 * @return UserAgent 代理信息
	 */
	public static UserAgent getUserAgent(HttpServletRequest request) {
		return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
	}

	/**
	 * 获取设备类型
	 *
	 * @param request 请求
	 * @return DeviceType 【设备信息】
	 */
	public static DeviceType getDeviceType(HttpServletRequest request) {
		return getUserAgent(request).getOperatingSystem().getDeviceType();
	}

	/**
	 * 当前访问设备是电脑
	 *
	 * @param request 请求
	 * @return boolean 【true：电脑】
	 */
	public static boolean isComputer(HttpServletRequest request) {
		return DeviceType.COMPUTER.equals(getDeviceType(request));
	}

	/**
	 * 当前访问设备是手机
	 *
	 * @param request 请求
	 * @return boolean 【true：手机】
	 */
	public static boolean isMobile(HttpServletRequest request) {
		return DeviceType.MOBILE.equals(getDeviceType(request));
	}

	/**
	 * 当前访问设备是平板
	 *
	 * @param request 请求
	 * @return boolean 【true：平板】
	 */
	public static boolean isTablet(HttpServletRequest request) {
		return DeviceType.TABLET.equals(getDeviceType(request));
	}

	/**
	 * 当前访问设备是手机或平板
	 *
	 * @param request 请求
	 * @return boolean 【true：平板或手机】
	 */
	public static boolean isMobileOrTablet(HttpServletRequest request) {
		DeviceType deviceType = getDeviceType(request);
		return DeviceType.MOBILE.equals(deviceType) || DeviceType.TABLET.equals(deviceType);
	}

	/**
	 * 获取浏览访问的浏览器
	 *
	 * @param request 请求
	 * @return Browser 浏览器信息
	 */
	public static Browser getBrowser(HttpServletRequest request) {
		return getUserAgent(request).getBrowser();
	}

	/**
	 * 当前Ie浏览器版本小于Ie8
	 *
	 * @param request 请求
	 * @return boolean 【true：小于Ie8】
	 */
	public static boolean isLteIe8(HttpServletRequest request) {
		Browser browser = getBrowser(request);
		return Browser.IE5.equals(browser) || Browser.IE6.equals(browser)
				|| Browser.IE7.equals(browser) || Browser.IE8.equals(browser);
	}
}
