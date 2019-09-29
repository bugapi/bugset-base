package org.bugapi.bugset.base.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5的加密工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class Md5Util {
	/**
	 * 加密方法
	 *
	 * @param str 要加密的字符串
	 * @return String 加密后的字符串
	 */
	public static String md5Encrypt(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes());
			byte[] byteArr = md5.digest();
			int i;
			StringBuilder buf = new StringBuilder();
			for (byte value : byteArr) {
				i = value;
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}
}