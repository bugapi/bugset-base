package org.bugapi.bugset.base.util.web;

import com.sun.security.ntlm.Server;
import org.bugapi.bugset.base.util.server.ServerUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Ip地址工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class IpAddressUtil {

	/**
	 * 获取访问端的IP地址
	 * 		使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 		如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，
	 * 		X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 *
	 *
	 * @param request HttpServletRequest请求对象
	 * @return String Ip地址时
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		String unknownIp = "unknown";
		if (ip == null || ip.length() == 0 || unknownIp.equalsIgnoreCase(ip)) {
			String proxyClientIp = "Proxy-Client-IP";
			ip = request.getHeader(proxyClientIp);
		}
		if (ip == null || ip.length() == 0 || unknownIp.equalsIgnoreCase(ip)) {
			String wlProxyClientIp = "WL-Proxy-Client-IP";
			ip = request.getHeader(wlProxyClientIp);
		}
		if (ip == null || ip.length() == 0 || unknownIp.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
	}

	/**
	 * 获取本地IP地址
	 *
	 * @return String ip地址
	 * @throws UnknownHostException,SocketException 未知名称或服务异常，网络异常
	 */
	public static String getLocalIpAddress() throws UnknownHostException, SocketException {
		// 判断为windows系统
		if (ServerUtil.isWindowsOS()) {
			return InetAddress.getLocalHost().getHostAddress();
		} else {
			return getLinuxLocalIpAddress();
		}
	}

	/**
	 * 获取Linux下的IP地址
	 *
	 * @return String linux系统下的Ip地址
	 * @throws UnknownHostException,SocketException 未知名称或服务异常，网络异常
	 */
	private static String getLinuxLocalIpAddress() throws UnknownHostException, SocketException {
		InetAddress inetAddress = InetAddress.getLocalHost();
		if (!inetAddress.isLoopbackAddress()) {
			return inetAddress.getHostAddress();
		}
		String ip = "";
		NetworkInterface networkInterface;
		String name;
		Enumeration<InetAddress> enumIpAddr;
		String ipaddress;
		for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
			networkInterface = en.nextElement();
			name = networkInterface.getName();
			if (!name.contains("docker") && !name.contains("lo")) {
				for (enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						ipaddress = inetAddress.getHostAddress();
						if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
							ip = ipaddress;
						}
					}
				}
			}
		}
		return ip;
	}


	public static void main(String[] args) throws SocketException, UnknownHostException {
		System.out.println(getLocalIpAddress());
	}
}
