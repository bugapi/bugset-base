package org.bugapi.bugset.base.util.clazz;

import org.bugapi.bugset.base.constant.FileType;
import org.bugapi.bugset.base.constant.SymbolType;
import org.bugapi.bugset.base.util.net.URLUtil;
import org.bugapi.bugset.base.util.resource.ResourceUtil;
import org.bugapi.bugset.base.util.string.StringUtil;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类扫描器
 * @author zhangxw
 * @since 0.0.1
 */
public class ClassScanerUtil{

	/**
	 * 扫描指定包路径下所有包含指定注解的类
	 *
	 * @param packageName 包路径
	 * @param annotationClass 注解类
	 * @return 类集合
	 */
	public static Set<Class<?>> scanPackageByAnnotation(String packageName, boolean isInitialized, Class<?
			extends Annotation> annotationClass) throws IOException {
		return scanPackage(packageName, isInitialized, new Filter<Class<?>>() {
			@Override
			public boolean accept(Class<?> clazz) {
				return clazz.isAnnotationPresent(annotationClass);
			}
		});
	}

	/**
	 * 扫描指定包路径下所有指定类或接口的子类或实现类
	 *
	 * @param packageName 包路径
	 * @param superClass 父类或接口
	 * @return 类集合
	 */
	public static Set<Class<?>> scanPackageBySuper(String packageName, boolean isInitialized, Class<?> superClass) throws IOException {
		return scanPackage(packageName, isInitialized, new Filter<Class<?>>() {
			@Override
			public boolean accept(Class<?> clazz) {
				return superClass.isAssignableFrom(clazz) && !superClass.equals(clazz);
			}
		});
	}

	/**
	 * 扫描该包路径下所有class文件
	 *
	 * @param packageName 包路径 com | com. | com.abs | com.abs.
	 * @return 类集合
	 */
	public static Set<Class<?>> scanPackage(String packageName) throws IOException {
		return scanPackage(packageName,false,null);
	}

	/**
	 * 扫描包路径下满足class过滤器条件的所有class文件，<br>
	 * 如果包路径为 com.abs + A.class 但是输入 abs会产生classNotFoundException<br>
	 * 因为className 应该为 com.abs.A 现在却成为abs.A,此工具类对该异常进行忽略处理<br>
	 *
	 * @param packageName 包路径 com | com. | com.abs | com.abs.
	 * @param classFilter class过滤器，过滤掉不需要的class
	 * @return 类集合
	 */
	public static Set<Class<?>> scanPackage(String packageName, boolean isInitialized, Filter<Class<?>> classFilter) throws IOException {
		return scan(packageName, isInitialized, classFilter);
	}

	/**
	 * 扫描包路径下满足class过滤器条件的所有class文件
	 *
	 * @return 类集合
	 */
	public static Set<Class<?>> scan(String packageName, boolean isInitialized, Filter<Class<?>> classFilter) throws IOException {
		Set<Class<?>> classes = new HashSet<>();
		if(StringUtil.isEmpty(packageName)){
			return classes;
		}
		for (URL url : ResourceUtil.getResources(packageName)) {
			switch (url.getProtocol()) {
				case "jar":
					scanJar(packageName, URLUtil.getJarFile(url), isInitialized, classFilter, classes);
					break;
				default:
					// 默认协议是：file
					scanFile(packageName, new File(URLUtil.decode(url.getFile())), null, isInitialized,
							classFilter, classes);
					break;
			}
		}

		return Collections.unmodifiableSet(classes);
	}


	// --------------------------------------------------------------------------------------------------- Private method start

	/**
	 * 扫描文件或目录中的类
	 *
	 * @param file 文件或目录
	 * @param rootDir 包名对应classpath绝对路径
	 */
	private static void scanFile(String packageName, File file, String rootDir, boolean isInitialized,
								 Filter<Class<?>> classFilter, Set<Class<?>> classes) throws IOException {
		if (file.isFile()) {
			String fileName = file.getAbsolutePath();
			if (fileName.endsWith(FileType.CLASS_EXT)) {
				String className = fileName//
						// 8为classes长度，fileName.length() - 6为".class"的长度
						.substring(rootDir.length(), fileName.length() - 6)//
						.replace(File.separator, SymbolType.DOT);//
				//加入满足条件的类
				addIfAccept(packageName, className, isInitialized, classFilter, classes);
			} else if (fileName.endsWith(FileType.JAR_FILE_EXT)) {
				scanJar(packageName, new JarFile(file), isInitialized, classFilter, classes);
			}
		} else if (file.isDirectory()) {
			for (File subFile : file.listFiles()) {
				scanFile(packageName, subFile, (null == rootDir) ? subPathBeforePackage(packageName, file) : rootDir,
						isInitialized
						, classFilter, classes);
			}
		}
	}

	/**
	 * 扫描jar包
	 *
	 * @param jar jar包
	 */
	private static void scanJar(String packageName, JarFile jar, boolean isInitialized, Filter<Class<?>> classFilter,
								Set<Class<?>> classes) {
		String name;
		Enumeration<JarEntry> entries = jar.entries();
		JarEntry entry = null;
		// 包路径，用于jar中对路径操作，在Linux下与packageDirName一致
		String packagePath = packageName.replace(SymbolType.DOT, SymbolType.SLASH);
		while (entries.hasMoreElements()) {
			entry = entries.nextElement();
			name = StringUtil.removePrefix(entry.getName(), SymbolType.SLASH);
			if (name.startsWith(packagePath)) {
				if (name.endsWith(FileType.CLASS_EXT) && !entry.isDirectory()) {
					final String className = name//
							.substring(0, name.length() - 6)//
							.replace(SymbolType.SLASH, SymbolType.DOT);//
					addIfAccept(loadClass(className, isInitialized), classFilter, classes);
				}
			}
		}
	}

	/**
	 * 加载类
	 *
	 * @param className 类名
	 * @return 加载的类
	 */
	private static Class<?> loadClass(String className, boolean isInitialized) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className, isInitialized, ClassLoaderUtil.getDefaultClassLoader());
		} catch (NoClassDefFoundError e) {
			// 由于依赖库导致的类无法加载，直接跳过此类
		} catch (UnsupportedClassVersionError e) {
			// 版本导致的不兼容的类，跳过
		} catch (Exception e) {
			throw new RuntimeException(e);
			// Console.error(e);
		}
		return clazz;
	}

	/**
	 * 通过过滤器，是否满足接受此类的条件
	 *
	 * @return 是否接受
	 */
	private static void addIfAccept(String packageName, String className, boolean isInitialized,
									Filter<Class<?>> classFilter, Set<Class<?>> classes) {
		if(StringUtil.isBlank(className)) {
			return;
		}
		int classLen = className.length();
		int packageLen = packageName.length();
		if(classLen == packageLen) {
			//类名和包名长度一致，用户可能传入的包名是类名
			if(className.equals(packageName)) {
				addIfAccept(loadClass(className, isInitialized), classFilter, classes);
			}
		} else if(classLen > packageLen){
			//检查类名是否以指定包名为前缀，包名后加.（避免类似于cn.hutool.A和cn.hutool.ATest这类类名引起的歧义）
			if(className.startsWith(StringUtil.addSuffixIfNot(packageName, SymbolType.DOT))) {
				addIfAccept(loadClass(className, isInitialized), classFilter, classes);
			}
		}
	}

	/**
	 * 通过过滤器，是否满足接受此类的条件
	 *
	 * @param clazz 类
	 * @return 是否接受
	 */
	private static void addIfAccept(Class<?> clazz, Filter<Class<?>> classFilter, Set<Class<?>> classes) {
		if (null != clazz) {
			if (classFilter == null || classFilter.accept(clazz)) {
				classes.add(clazz);
			}
		}
	}

	/**
	 * 截取文件绝对路径中包名之前的部分
	 *
	 * @param file 文件
	 * @return 包名之前的部分
	 */
	private static String subPathBeforePackage(String packageName, File file) {
		String filePath = file.getAbsolutePath();
		// 包路径，用于文件中对路径操作
		String packageDirName = packageName.replace(SymbolType.DOT, File.separator);
		if (StringUtil.isNotEmpty(packageDirName)) {
			filePath = StringUtil.subBefore(filePath, packageDirName, true);
		}
		return StringUtil.addSuffixIfNot(filePath, File.separator);
	}

	/**
	 * 过滤器
	 * @param <T> 对象类型
	 */
	private interface Filter<T> {
		/**
		 * 是否接受对象
		 * @param t 检查的对象
		 * @return 是否接受对象
		 */
		boolean accept(T t);
	}
}
