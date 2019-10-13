package org.bugapi.bugset.base.util.resource;

import org.bugapi.bugset.base.util.clazz.ClassLoaderUtil;
import org.bugapi.bugset.base.util.string.StringUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 资源工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class ResourceUtil {
	/**
	 * 获得资源相对路径对应的URL
	 *
	 * @param resource  资源相对路径
	 * @param baseClass 基准Class，获得的相对路径相对于此Class所在路径，如果为{@code null}则相对ClassPath
	 * @return {@link URL}
	 */
	public static URL getResource(String resource, Class<?> baseClass) {
		return (null != baseClass) ? baseClass.getResource(resource) : ClassLoaderUtil.getDefaultClassLoader().getResource(resource);
	}

	/**
	 * 获取指定路径下的资源列表
	 *
	 * <pre>
	 * 	路径格式必须为目录格式,用/分隔，例如：
	 * 		config/a
	 * 		spring/xml
	 * </pre>
	 *
	 * @param resource 资源路径
	 * @return 资源列表
	 * @throws IOException 流异常
	 */
	public static List<URL> getResources(String resource) throws IOException {
		List<URL> urlList = new ArrayList<>();
		if (StringUtil.isEmpty(resource)) {
			return urlList;
		}
		Enumeration<URL> enumration = ClassLoaderUtil.getDefaultClassLoader().getResources(resource);
		if (null != enumration) {
			while (enumration.hasMoreElements()) {
				urlList.add(enumration.nextElement());
			}
		}
		return urlList;
	}
}
