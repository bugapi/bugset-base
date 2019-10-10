package org.bugapi.bugset.base.util.charset;

import org.bugapi.bugset.base.constant.CharsetType;
import org.bugapi.bugset.base.util.string.StringUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * 字符集工具类
 * @author zhangxw
 * @since 0.0.1
 */
public class CharsetUtil {

	/**
	 * 获取Java虚拟机的默认字符集
	 *
	 * @return 字符集
	 */
	public static Charset defaultCharset() {
		return Charset.defaultCharset();
	}

	/**
	 * 获取Java虚拟机的默认字符集名称
	 *
	 * @return 字符集名称
	 */
	public static String defaultCharsetName() {
		return defaultCharset().name();
	}

	/**
	 * 根据字符集名称生成字符集对象
	 *
	 * @param charsetName 字符集名称
	 * @return 字符集对象
	 * @throws UnsupportedCharsetException 字符集不支持异常
	 */
	public static Charset charset(String charsetName) throws UnsupportedCharsetException {
		return StringUtil.isBlank(charsetName) ? Charset.defaultCharset() : Charset.forName(charsetName);
	}

	/**
	 * 转换字符串的字符集编码
	 * @param source 字符串
	 * @param srcCharset 源字符集，默认ISO-8859-1
	 * @param destCharset 目标字符集，默认UTF-8
	 * @return 转换后的字符集
	 */
	public static String convert(String source, String srcCharset, String destCharset) {
		return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
	}

	/**
	 * 转换字符串的字符集编码
	 *
	 * <pre>
	 *     例如，在Servlet请求中客户端用GBK编码了请求参数，我们使用UTF-8读取到的是乱码，此时，使用此方法即可还原原编码的内容
	 * 			客户端 -》 GBK编码 -》 Servlet容器 -》 UTF-8解码 -》 乱码
	 * 			乱码 -》 UTF-8编码 -》 GBK解码 -》 正确内容
	 * </pre>
	 *
	 * @param source 字符串
	 * @param srcCharset 源字符集，默认ISO-8859-1
	 * @param destCharset 目标字符集，默认UTF-8
	 * @return 转换后的字符集
	 */
	public static String convert(String source, Charset srcCharset, Charset destCharset) {
		if(null == srcCharset) {
			srcCharset = StandardCharsets.ISO_8859_1;
		}

		if(null == destCharset) {
			destCharset = StandardCharsets.UTF_8;
		}

		if (StringUtil.isBlank(source) || srcCharset.equals(destCharset)) {
			return source;
		}
		return new String(source.getBytes(srcCharset), destCharset);
	}

	public static void main(String[] args) {
		System.out.println(Charset.defaultCharset());
		System.out.println(convert("张三", CharsetType.GBK, CharsetType.ISO_8859_1));
		System.out.println(convert("ÕÅÈý", CharsetType.ISO_8859_1, CharsetType.GBK));
	}
}
