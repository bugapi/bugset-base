package org.bugapi.bugset.base.util.reflect;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Class工具类
 * 		https://www.jianshu.com/p/83cbbd0b8b10
 * 		https://www.cnblogs.com/zhoubang521/p/5200597.html
 * 		https://my.oschina.net/cnlw/blog/299265
 * 		https://gitee.com/hv0912/codes/7cmrydub9n56wizo81pa241/
 * 		https://blog.csdn.net/f641385712/article/category/7649216/5
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class ClassUtil {
	/**
	 * 8种基本数据类型的包装类的class与type映射的集合
	 */
	private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_MAP = new HashMap<>(8);
	/**
	 * 8种基本数据类型的包装类的type与class映射的集合
	 */
	private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_WRAPPER_MAP = new HashMap<>(8);
	/**
	 * 基本数据类型的type的Name与type映射的集合
	 */
	public static final Map<String, Class<?>> PRIMITIVE_TYPE_NAME_MAP;
	/**
	 * java公用class的name与class的映射集合
	 */
	public static final Map<String, Class<?>> COMMON_CLASS_CACHE = new HashMap<>(64);
	/**
	 * java常用的接口的class集合
	 */
	private static final Set<Class<?>> JAVA_LANGUAGE_INTERFACES;


	static {
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Boolean.class, Boolean.TYPE);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Byte.class, Byte.TYPE);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Character.class, Character.TYPE);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Double.class, Double.TYPE);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Float.class, Float.TYPE);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Integer.class, Integer.TYPE);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Long.class, Long.TYPE);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Short.class, Short.TYPE);
		PRIMITIVE_WRAPPER_TYPE_MAP.forEach((key, value) -> {
			PRIMITIVE_TYPE_WRAPPER_MAP.put(value, key);
			registerCommonClasses(key);
		});
		Set<Class<?>> primitiveTypes = new HashSet<>(32);
		primitiveTypes.addAll(PRIMITIVE_WRAPPER_TYPE_MAP.values());
		Collections.addAll(primitiveTypes, boolean[].class, byte[].class, char[].class, double[].class, float[].class, int[].class, long[].class, short[].class);
		primitiveTypes.add(Void.TYPE);
		PRIMITIVE_TYPE_NAME_MAP = primitiveTypes.stream().collect(Collectors.toMap(Class::getName,type -> type));

		registerCommonClasses(Boolean[].class, Byte[].class, Character[].class, Double[].class, Float[].class, Integer[].class, Long[].class, Short[].class);
		registerCommonClasses(Number.class, Number[].class, String.class, String[].class, Class.class, Class[].class, Object.class, Object[].class);
		registerCommonClasses(Throwable.class, Exception.class, RuntimeException.class, Error.class, StackTraceElement.class, StackTraceElement[].class);
		registerCommonClasses(Enum.class, Iterable.class, Iterator.class, Enumeration.class, Collection.class, List.class, Set.class, Map.class, Map.Entry.class, Optional.class);
		Class<?>[] javaLanguageInterfaceArray = new Class[]{Serializable.class, Externalizable.class, Closeable.class, AutoCloseable.class, Cloneable.class, Comparable.class};
		registerCommonClasses(javaLanguageInterfaceArray);
		JAVA_LANGUAGE_INTERFACES = new HashSet<>(Arrays.asList(javaLanguageInterfaceArray));
	}

	private static void registerCommonClasses(Class<?>... commonClasses) {
		for (Class<?> clazz : commonClasses) {
			COMMON_CLASS_CACHE.put(clazz.getName(), clazz);
		}
	}



	public static void main(String[] args) throws Exception {
		System.out.println(Boolean.TYPE);
		System.out.println(Integer.TYPE);
		/*String packageName = "org.objectweb.asm";
		Set<String> classNames = getClassName(packageName, false);
		if (classNames != null) {
			for (String className : classNames) {
				System.out.println(className);
			}
		}*/
	}

	/**
	 * 获取某包下所有类
	 * @param packageName 包名
	 * @param isRecursion 是否遍历子包
	 * @return 类的完整名称
	 */
	public static Set<String> getClassName(String packageName, boolean isRecursion) {
		Set<String> classNames = null;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String packagePath = packageName.replace(".", "/");

		URL url = loader.getResource(packagePath);
		if (url != null) {
			String protocol = url.getProtocol();
			if (protocol.equals("file")) {
				classNames = getClassNameFromDir(url.getPath(), packageName, isRecursion);
			} else if (protocol.equals("jar")) {
				JarFile jarFile = null;
				try{
					jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
				} catch(Exception e){
					e.printStackTrace();
				}

				if(jarFile != null){
					getClassNameFromJar(jarFile.entries(), packageName, isRecursion);
				}
			}
		} else {
			/*从所有的jar包中查找包名*/
			classNames = getClassNameFromJars(((URLClassLoader)loader).getURLs(), packageName, isRecursion);
		}

		return classNames;
	}

	/**
	 * 从项目文件获取某包下所有类
	 * @param filePath 文件路径
	 * @param className 类名集合
	 * @param isRecursion 是否遍历子包
	 * @return 类的完整名称
	 */
	private static Set<String> getClassNameFromDir(String filePath, String packageName, boolean isRecursion) {
		Set<String> className = new HashSet<String>();
		File file = new File(filePath);
		File[] files = file.listFiles();
		for (File childFile : files) {
			if (childFile.isDirectory()) {
				if (isRecursion) {
					className.addAll(getClassNameFromDir(childFile.getPath(), packageName+"."+childFile.getName(), isRecursion));
				}
			} else {
				String fileName = childFile.getName();
				if (fileName.endsWith(".class") && !fileName.contains("$")) {
					className.add(packageName+ "." + fileName.replace(".class", ""));
				}
			}
		}

		return className;
	}


	/**
	 * @param jarEntries
	 * @param packageName
	 * @param isRecursion
	 * @return
	 */
	private static Set<String> getClassNameFromJar(Enumeration<JarEntry> jarEntries, String packageName, boolean isRecursion){
		Set<String> classNames = new HashSet<String>();

		while (jarEntries.hasMoreElements()) {
			JarEntry jarEntry = jarEntries.nextElement();
			if(!jarEntry.isDirectory()){
				/*
				 * 这里是为了方便，先把"/" 转成 "." 再判断 ".class" 的做法可能会有bug
				 * (FIXME: 先把"/" 转成 "." 再判断 ".class" 的做法可能会有bug)
				 */
				String entryName = jarEntry.getName().replace("/", ".");
				if (entryName.endsWith(".class") && !entryName.contains("$") && entryName.startsWith(packageName)) {
					entryName = entryName.replace(".class", "");
					if(isRecursion){
						classNames.add(entryName);
					} else if(!entryName.replace(packageName+".", "").contains(".")){
						classNames.add(entryName);
					}
				}
			}
		}

		return classNames;
	}

	/**
	 * 从所有jar中搜索该包，并获取该包下所有类
	 * @param urls URL集合
	 * @param packageName 包路径
	 * @param isRecursion 是否遍历子包
	 * @return 类的完整名称
	 */
	private static Set<String> getClassNameFromJars(URL[] urls, String packageName, boolean isRecursion) {
		Set<String> classNames = new HashSet<String>();

		for (int i = 0; i < urls.length; i++) {
			String classPath = urls[i].getPath();

			//不必搜索classes文件夹
			if (classPath.endsWith("classes/")) {continue;}

			JarFile jarFile = null;
			try {
				jarFile = new JarFile(classPath.substring(classPath.indexOf("/")));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (jarFile != null) {
				classNames.addAll(getClassNameFromJar(jarFile.entries(), packageName, isRecursion));
			}
		}

		return classNames;
	}
}
