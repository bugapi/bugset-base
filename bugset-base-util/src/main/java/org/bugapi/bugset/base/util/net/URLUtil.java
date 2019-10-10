package org.bugapi.bugset.base.util.net;

import com.sun.xml.internal.ws.util.UtilException;
import org.bugapi.bugset.base.constant.CharsetType;
import org.bugapi.bugset.base.constant.FileType;
import org.bugapi.bugset.base.constant.URLType;
import org.bugapi.bugset.base.util.charset.CharsetUtil;
import org.bugapi.bugset.base.util.string.StringUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.jar.JarFile;

/**
 * URL工具类
 * @author zhangxw
 * @since 0.0.1
 */
public class URLUtil {

	/**
	 * 判断URL为文件 文件协议包括"file", "vfsfile" 或 "vfs".
	 *
	 * @param url 路径
	 * @return boolean 【true：文件URL】
	 */
	public static boolean isFileURL(URL url) {
		String protocol = url.getProtocol();
		return (URLType.URL_PROTOCOL_FILE.equals(protocol) ||
				URLType.URL_PROTOCOL_VFSFILE.equals(protocol) ||
				URLType.URL_PROTOCOL_VFS.equals(protocol));
	}

	/**
	 * 判断URL为为jar包URL 协议包括： "jar", "zip", "vfszip" 或 "wsjar".
	 *
	 * @param url 路径
	 * @return boolean 【true：jar包URL】
	 */
	public static boolean isJarURL(URL url) {
		final String protocol = url.getProtocol();
		return (URLType.URL_PROTOCOL_JAR.equals(protocol) ||
				URLType.URL_PROTOCOL_ZIP.equals(protocol) ||
				URLType.URL_PROTOCOL_VFSZIP.equals(protocol) ||
				URLType.URL_PROTOCOL_WSJAR.equals(protocol));
	}

	/**
	 * 判断URL为Jar文件URL 判断依据为file协议且扩展名为.jar
	 *
	 * @param url 路径
	 * @return boolean 【true：jar文件路径】
	 */
	public static boolean isJarFileURL(URL url) {
		return (URLType.URL_PROTOCOL_FILE.equals(url.getProtocol()) &&
				url.getPath().toLowerCase().endsWith(FileType.JAR_FILE_EXT));
	}

	/**
	 * 从URL中获取JarFile
	 *
	 * @param url 路径
	 * @return JarFile jar文件
	 * @throws IOException 流异常
	 */
	public static JarFile getJarFile(URL url) throws IOException {
		JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
		return urlConnection.getJarFile();
	}


	/**
	 * 解码application/x-www-form-urlencoded字符
	 *
	 * @param content 被解码内容
	 * @param charset 编码
	 * @return 编码后的字符
	 * @since 4.4.1
	 */
	public static String decode(String content, Charset charset) throws UnsupportedEncodingException {
		if (null == charset) {
			charset = CharsetUtil.defaultCharset();
		}
		return decode(content, charset.name());
	}

	/**
	 * URL解码(解码application/x-www-form-urlencoded字符)   将%开头的16进制表示的内容解码。
	 *
	 * @param url 路径
	 * @return 解码后的路径
	 * @throws UnsupportedEncodingException 不支持解码异常
	 */
	public static String decode(String url) throws UnsupportedEncodingException {
		if (StringUtil.isEmpty(url)) {
			return url;
		}
		return URLDecoder.decode(url, CharsetType.UTF_8);
	}

	/**
	 * URL解码   将%开头的16进制表示的内容解码。
	 * @param url 路径
	 * @param charsetName 字符集名称
	 * @return 解码后的路径
	 * @throws UnsupportedEncodingException 不支持解码异常
	 */
	public static String decode(String url, String charsetName) throws UnsupportedEncodingException {
		if (StringUtil.isEmpty(url)) {
			return url;
		}
		return URLDecoder.decode(url, charsetName);
	}
}
