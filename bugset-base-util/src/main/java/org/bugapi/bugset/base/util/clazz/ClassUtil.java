package org.bugapi.bugset.base.util.clazz;

import static org.bugapi.bugset.base.constant.UrlType.URL_PROTOCOL_FILE;
import static org.bugapi.bugset.base.constant.UrlType.URL_PROTOCOL_JAR;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.bugapi.bugset.base.util.array.ArrayUtil;

/**
 * Class工具类
 * https://www.jianshu.com/p/83cbbd0b8b10
 * https://www.cnblogs.com/zhoubang521/p/5200597.html
 * https://my.oschina.net/cnlw/blog/299265
 * https://gitee.com/hv0912/codes/7cmrydub9n56wizo81pa241/
 * https://blog.csdn.net/f641385712/article/category/7649216/5
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class ClassUtil {

	/**
	 * 获取类型类的直接父类的泛型的类型类
	 * 例如: List<String>   获取到的类型为 [String.class]
	 * 说明:
	 * 需要获取泛型的,必须通过继承等方式重新构建一个新类,传递泛型的类型
	 *
	 * @param clazz 被检查的类，必须是已经确定泛型类型的类
	 * @param index 泛型类型的索引号，既第几个泛型类型
	 * @return Class<?> 父类泛型的类型类
	 */
	public static Class<?> getSuperClassGenericsClass(Class<?> clazz, int index) {
		if ((null == clazz || index < 0)) {
			return null;
		}
		// 获得目标类继承的父类所对应的Type对象
		Type type = clazz.getGenericSuperclass();
		// 如果没有实现ParameterizedType接口，即不支持泛型，直接返回null。
		if (type instanceof ParameterizedType) {
			// 返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class, 如BuyerServiceBean extends DaoSupport<Buyer,Contact>就返回Buyer和Contact类型
			// 返回表示此类型实际类型参数的Type对象的数组
			Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
			if (null != typeArguments && typeArguments.length > index) {
				if (typeArguments[index] instanceof Class) {
					return (Class<?>) typeArguments[index];
				}
			}
		}
		return null;
	}


	/**
	 * 获取接口类型类的父接口的泛型的类型类
	 *
	 * @param clazz  被检查的类，必须是已经确定泛型类型的类
	 * @param iindex 接口的索引号，就是第几个接口
	 * @param pindex 泛型类型的索引号，既第几个泛型类型
	 * @return Class<?>
	 */
	public static Class<?> getInterfaceGenericsClass(Class<?> clazz, int iindex, int pindex) {
		if ((null == clazz || iindex < 0 || pindex < 0)) {
			return null;
		}
		// 获得目标类实现的接口对应的Type对象
		Type[] interfaces = clazz.getGenericInterfaces();
		Type type = null;
		if (null != interfaces && interfaces.length > iindex) {
			type = interfaces[iindex];
		} else {
			return null;
		}
		// 如果没有实现ParameterizedType接口，即不支持泛型，直接返回null。
		if (type instanceof ParameterizedType) {
			// 返回表示此类型实际类型参数的Type对象的数组
			Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
			if (null != typeArguments && typeArguments.length > pindex) {
				if (typeArguments[pindex] instanceof Class) {
					return (Class<?>) typeArguments[pindex];
				}
			}
		}
		return null;
	}

	/**
	 * 获得对象的类型类
	 *
	 * @param obj 对象
	 * @param <T> 对象的类型类
	 * @return 对象类型，提供对象如果为{@code null} 返回{@code null}
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClass(T obj) {
		return ((null == obj) ? null : (Class<T>) obj.getClass());
	}

	/**
	 * 获得对象数组的类数组
	 *
	 * @param objects 对象数组，如果数组中存在{@code null}元素，则此元素被认为是Object类型
	 * @return 类数组
	 */
	public static Class<?>[] getClasses(Object... objects) {
		if (ArrayUtil.isEmpty(objects)) {
			return null;
		}
		return Arrays.stream(objects).map(obj -> (null == obj) ? Object.class : obj.getClass()).toArray(Class[]::new);
	}

	/**
	 * 根据对象获取类名
	 *
	 * @param obj      获取类名对象
	 * @param isSimple 是否简单类名，如果为true，返回不带包名的类名
	 *                 false：org.bugapi.bugset.base.util.reflect.ClassUtil
	 *                 true：ClassUtil
	 * @return String 类名(类名并不包含“.class”这个扩展名)
	 */
	public static String getClassName(Object obj, boolean isSimple) {
		if (null == obj) {
			return null;
		}
		final Class<?> clazz = obj.getClass();
		return getClassName(clazz, isSimple);
	}

	/**
	 * 根据类型类获取类名
	 *
	 * @param clazz    类
	 * @param isSimple 是否简单类名，如果为true，返回不带包名的类名
	 *                 false：org.bugapi.bugset.base.util.reflect.ClassUtil
	 *                 true：ClassUtil
	 * @return String 类名(类名并不包含“.class”这个扩展名)
	 */
	public static String getClassName(Class<?> clazz, boolean isSimple) {
		if (null == clazz) {
			return null;
		}
		return isSimple ? clazz.getSimpleName() : clazz.getName();
	}

	/**
	 * 根据类型类返回对应的默认值
	 *
	 * @param clazz 类型类
	 * @return 默认值
	 */
	public static Object getDefaultValue(Class<?> clazz) {
		if (clazz.isPrimitive()) {
			if (long.class == clazz) {
				return 0L;
			} else if (int.class == clazz) {
				return 0;
			} else if (short.class == clazz) {
				return (short) 0;
			} else if (char.class == clazz) {
				return (char) 0;
			} else if (byte.class == clazz) {
				return (byte) 0;
			} else if (double.class == clazz) {
				return 0D;
			} else if (float.class == clazz) {
				return 0f;
			} else if (boolean.class == clazz) {
				return false;
			}
		}
		return null;
	}


	/**
	 * @return 获得Java ClassPath路径，不包括 jre
	 */

	/**
	 * 获得Java ClassPath路径，不包括 jre
	 *
	 * @return Java ClassPath路径
	 */
	public static String[] getJavaClassPaths() {
		return System.getProperty("java.class.path").split(System.getProperty("path.separator"));
	}

	/**
	 * 获取某包下所有类
	 *
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
			if (URL_PROTOCOL_FILE.equals(protocol)) {
				classNames = getClassNameFromDir(url.getPath(), packageName, isRecursion);
			} else if (URL_PROTOCOL_JAR.equals(protocol)) {
				JarFile jarFile = null;
				try {
					jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (jarFile != null) {
					getClassNameFromJar(jarFile.entries(), packageName, isRecursion);
				}
			}
		} else {
			/*从所有的jar包中查找包名*/
			classNames = getClassNameFromJars(((URLClassLoader) loader).getURLs(), packageName, isRecursion);
		}

		return classNames;
	}

	/**
	 * 从项目文件获取某包下所有类
	 *
	 * @param filePath    文件路径
	 * @param packageName   类名集合
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
					className.addAll(getClassNameFromDir(childFile.getPath(), packageName + "." + childFile.getName(), isRecursion));
				}
			} else {
				String fileName = childFile.getName();
				if (fileName.endsWith(".class") && !fileName.contains("$")) {
					className.add(packageName + "." + fileName.replace(".class", ""));
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
	private static Set<String> getClassNameFromJar(Enumeration<JarEntry> jarEntries, String packageName, boolean isRecursion) {
		Set<String> classNames = new HashSet<String>();

		while (jarEntries.hasMoreElements()) {
			JarEntry jarEntry = jarEntries.nextElement();
			if (!jarEntry.isDirectory()) {
				/*
				 * 这里是为了方便，先把"/" 转成 "." 再判断 ".class" 的做法可能会有bug
				 * (FIXME: 先把"/" 转成 "." 再判断 ".class" 的做法可能会有bug)
				 */
				String entryName = jarEntry.getName().replace("/", ".");
				if (entryName.endsWith(".class") && !entryName.contains("$") && entryName.startsWith(packageName)) {
					entryName = entryName.replace(".class", "");
					if (isRecursion) {
						classNames.add(entryName);
					} else if (!entryName.replace(packageName + ".", "").contains(".")) {
						classNames.add(entryName);
					}
				}
			}
		}

		return classNames;
	}

	/**
	 * 从所有jar中搜索该包，并获取该包下所有类
	 *
	 * @param urls        URL集合
	 * @param packageName 包路径
	 * @param isRecursion 是否遍历子包
	 * @return 类的完整名称
	 */
	private static Set<String> getClassNameFromJars(URL[] urls, String packageName, boolean isRecursion) {
		Set<String> classNames = new HashSet<String>();

		for (int i = 0; i < urls.length; i++) {
			String classPath = urls[i].getPath();

			//不必搜索classes文件夹
			if (classPath.endsWith("classes/")) {
				continue;
			}

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
