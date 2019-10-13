package org.bugapi.bugset.base.util.xml;

import javax.xml.bind.*;
import java.io.File;

/**
 * JAXB（即Java Architecture for XML Binding）是一个业界的标准
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class JaxbXmlUtil {

	/**
	 * 将java对象生成对应的xml文件
	 *
	 * @param obj  对象
	 * @param file xml文件
	 */
	public static void convertToXml(Object obj, File file) {
		JAXB.marshal(obj, file);
	}

	/**
	 * 将xml文件转成对应的Java对象
	 *
	 * @param file xml文件
	 * @param clz  类型类
	 * @param <T>  对象的类型
	 * @return T 对象
	 */
	public static <T> T convertToJavaBean(File file, Class<T> clz) {
		return JAXB.unmarshal(file, clz);
	}

	/**
	 * 将java对象生成对应的xml字符串
	 *
	 * @param obj 对象
	 * @param str 对象的xml字符串
	 * @return xml字符串
	 */
	public static String convertToXml(Object obj, String str) {
		JAXB.marshal(obj, str);
		return str;
	}

	/**
	 * 将xml文件转成对应的Java对象
	 *
	 * @param xml xml字符串
	 * @param clz 类型类
	 * @param <T> 对象的类型
	 * @return T 对象
	 */
	public static <T> T convertToJavaBean(String xml, Class<T> clz) {
		return JAXB.unmarshal(xml, clz);
	}
}
