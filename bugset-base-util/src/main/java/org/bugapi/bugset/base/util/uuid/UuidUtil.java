package org.bugapi.bugset.base.util.uuid;

import java.util.UUID;

/**
 * 封装JDK自带的UUID, 全部转大写, 中间无-分割
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class UuidUtil {

	/**
	 * 生成处理后的UUID。
	 *
	 * @return 处理后的UUID字符串
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().toUpperCase().replace("-", "");
	}
}