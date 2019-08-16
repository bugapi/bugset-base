package org.bugapi.bugset.base.util.uuid;

import java.util.UUID;

/**
 * @ClassName: UuidUtil
 * @Description: 封装JDK自带的UUID, 全部转大写, 中间无-分割
 * @author: zhangxw
 * @date: 2018/10/6
 */
public class UuidUtil {

	/**
	 * 生成处理后的UUID。
	 * @Title: randomUUID
	 * @return 处理后的UUID字符串
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString().toUpperCase().replace("-", "");
	}
}
