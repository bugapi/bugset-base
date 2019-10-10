package org.bugapi.bugset.base.util.server;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;


/**
 * 获取服务器的cpu、内存、硬盘信息
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class ServerUtil {

	/**
	 * WINDOWS系统
	 */
	public static final String WINDOWS = "windows";
	/**
	 * Linux系统
	 */
	public static final String LINUX = "linux";

	/**
	 * 操作系统属性名称
	 */
	private static final String OS_NAME = "os.name";

	/**
	 * 判断是否是windows操作系统
	 *
	 * @return boolean 【true：是】
	 */
	public static boolean isWindowsOS() {
		return System.getProperty(OS_NAME).toLowerCase().contains(WINDOWS);
	}

	/**
	 * 判断是否是Linux操作系统
	 *
	 * @return boolean 【true：是】
	 */
	public static boolean isLinuxOS() {
		return System.getProperty(OS_NAME).toLowerCase().contains(LINUX);
	}

	/**
	 * 获取服务器的硬盘使用情况
	 *
	 * @return String 硬盘的使用信息
	 */
	public static String getDiskInfo() {
		StringBuilder sb = new StringBuilder();
		// 获取磁盘分区列表
		File[] roots = File.listRoots();
		for (File file : roots) {
			long totalSpace = file.getTotalSpace();
			long usableSpace = file.getUsableSpace();
			if (totalSpace > 0) {
				sb.append(file.getPath()).append("(总计：");
				sb.append(Math.round(((double) totalSpace / (1024 * 1024 * 1024)) * 100 / 100.0)).append("GB  ");
				if (Math.round((((double) usableSpace / (1024 * 1024 * 1024)) * 100) / 100.0) <= 1) {
					sb.append("剩余：").append(Math.round((((double) usableSpace / (1024 * 1024)) * 100) / 100.0)).append("MB)<br>");
				} else {
					sb.append("剩余：").append(Math.round((((double) usableSpace / (1024 * 1024 * 1024)) * 100) / 100.0)).append("GB)<br>");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 获取服务器的内存使用情况
	 *
	 * @return String 内存的使用信息
	 */
	public static String getMemoryInfo() {
		StringBuilder sb = new StringBuilder();
		OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();
		sb.append("&&&&内存情况：系统内存总计：").append(osmb.getTotalPhysicalMemorySize() / 1024 / 1024).append("MB，");
		sb.append("可用内存：").append(osmb.getFreePhysicalMemorySize() / 1024 / 1024).append("MB");
		return sb.toString();
	}

	/**
	 * 获取服务器硬盘分区下的目录和文件列表
	 *
	 * @return String 服务器硬盘分区下的目录和文件列表
	 */
	public static String getDiskFileList() {
		StringBuilder sb = new StringBuilder();
		String[] fileList;
		// 获取硬盘分区列表；
		File[] roots = File.listRoots();
		long totalSpace;
		for (File file : roots) {
			totalSpace = file.getTotalSpace();
			fileList = file.list();
			if (totalSpace > 0 && fileList != null && fileList.length > 0) {
				sb.append(file.getPath()).append("下目录和文件：");
				for (String s : fileList) {
					sb.append(s).append("/n");
				}
			}
		}
		return sb.toString();
	}
}
