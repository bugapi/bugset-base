package org.bugapi.bugset.base.util.file;

import org.bugapi.bugset.base.constant.SymbolType;
import org.bugapi.bugset.base.util.server.ServerUtil;
import org.bugapi.bugset.base.util.string.StringUtil;
import org.bugapi.bugset.base.util.uuid.UuidUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

import static org.bugapi.bugset.base.constant.DateTypeEnum.*;
import static org.bugapi.bugset.base.util.date.DateUtil.getSpecificTime;


/**
 * 文件处理工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public final class FileUtil {
	/**
	 * 文件分隔符
	 */
	public static final String FILE_SEPARATOR = File.separator;

	/**
	 * Windows下的文件路径：E:\thirdPartyCode\gridcloud\gridcloud-component的分隔符为：\
	 */
	private static final String WINDOWS_DIRECTORY_SEPARATOR = "\\";

	/**
	 * 默认的Path数组的长度
	 */
	public static final int DEFAULT_PATH_ARRAY_LENGTH = 2;

	private FileUtil() {
	}

	/**
	 * 项目编译后的classes的目录
	 *
	 * @return String 编译后Classes的目录
	 * @Title: getClassPath
	 */
	public static String getClassPath() {
		if (ServerUtil.isWindowsOS()) {
			/*
			 Objects.requireNonNull(FileUtil.class.getClassLoader().getResource("")).getPath()的值为：
			 	/E:/thirdPartyCode/gridcloud/gridcloud-base/gridcloud-base-util/target/classes/
			 所以要截掉盘符E之前的/
			*/
			return Objects.requireNonNull(FileUtil.class.getClassLoader().getResource("")).getPath().substring(1);
		} else {
			return Objects.requireNonNull(FileUtil.class.getClassLoader().getResource("")).getPath();
		}
	}

	/**
	 * 返回带.的文件后缀【.jpg】
	 *
	 * @param uploadFileName 文件名【a.jpg 或者 /b/a.jpg 或者 E:\b\a.jpg】
	 * @return String 带.的文件后缀：.jpg
	 * @Title: getFileSuffixWithDot
	 */
	public static String getFileSuffixWithDot(String uploadFileName) {
		if (checkFileNameWithOutDot(uploadFileName)) {
			return "";
		}
		return uploadFileName.substring(uploadFileName.lastIndexOf(SymbolType.DOT));
	}

	/**
	 * 返回带.的文件后缀【.jpg】
	 *
	 * @param uploadFileName 文件名【a.jpg 或者 /b/a.jpg 或者 E:\b\a.jpg】
	 * @return String 带.的文件后缀：.jpg
	 * @Title: getFileSuffixWithDot
	 */
	public static String getLowerCaseFileSuffixWithDot(String uploadFileName) {
		if (checkFileNameWithOutDot(uploadFileName)) {
			return "";
		}
		return uploadFileName.substring(uploadFileName.lastIndexOf(SymbolType.DOT)).toLowerCase();
	}

	/**
	 * 判断文件名中是否有.和后缀
	 *
	 * @param filename 文件名
	 * @return boolean 【true：文件名中没有.】
	 * @Title: checkFileNameWithOutDot
	 */
	private static boolean checkFileNameWithOutDot(String filename) {
		return StringUtil.isEmpty(filename) || filename.lastIndexOf(SymbolType.DOT) == -1;
	}

	/**
	 * 获取文件路径以及文件名【不包含.和后缀】
	 *
	 * @param fileName 文件名
	 * @return String 文件路径以及文件名【不包含.和后缀】
	 * @Title: getFilePrefix
	 */
	public static String getFilePrefix(String fileName) {
		if (checkFileNameWithOutDot(fileName)) {
			return fileName;
		}
		return fileName.substring(0, fileName.indexOf(SymbolType.DOT));
	}

	/**
	 * 根据临时文件的文件名，创建的相对路径，包含文件名【格式：xls\2019\2019\5\11\1557568401423.xls 的相对路径】
	 *
	 * @param fileTmpPath 文件所在的临时目录【包含文件名的部分】
	 * @Title: generateAbsoluteDirectory
	 * @return: String 文件的绝对目录
	 */
	public static String generateDateStampRelativePath(String fileTmpPath) {
		if (StringUtil.isEmpty(fileTmpPath) || fileTmpPath.lastIndexOf(SymbolType.DOT) == -1) {
			return "";
		}
		LocalDateTime now = LocalDateTime.now();
		Path dateStampPath = Paths.get(generateDateStampRelativeDirectory(fileTmpPath),
				generateTimeStampFileName(fileTmpPath));
		return dateStampPath.toString();
	}

	/**
	 * 根据临时文件的文件名，创建的相对路径【格式：xls\2019\2019\5\11 的相对路径】
	 *
	 * @param fileTmpPath 文件所在的临时目录【包含文件名的部分】
	 * @Title: generateAbsoluteDirectory
	 * @return: String 文件的绝对目录
	 */
	public static String generateDateStampRelativeDirectory(String fileTmpPath) {
		if (StringUtil.isEmpty(fileTmpPath) || fileTmpPath.lastIndexOf(SymbolType.DOT) == -1) {
			return "";
		}
		LocalDateTime now = LocalDateTime.now();
		Path dateStampPath = Paths.get(FileTypeUtil.getLowerCaseFileType(fileTmpPath),
				String.valueOf(getSpecificTime(now, YEAR)),
				String.valueOf(getSpecificTime(now, MONTH)),
				String.valueOf(getSpecificTime(now, DAY_OF_MONTH)));
		return dateStampPath.toString();
	}

	/**
	 * 生成目标目录，并从源文件的路径中拷贝文件到目标目录
	 *
	 * @param uploadRootPrefix 文件绝对路径前缀
	 * @param filePathPrefix   文件自定义相对路径前缀
	 * @param sourceFile       源文件
	 * @return Path 【目标文件相对路径】
	 * @Title: generateTargetDirectoryAndCopyFile
	 */
	public static Path generateTargetDirectoryAndCopyFile(File sourceFile, String fileName, String uploadRootPrefix,
														  String filePathPrefix) {

		Path[] paths = createTargetFilePaths(uploadRootPrefix, filePathPrefix, fileName);

		if (paths.length != DEFAULT_PATH_ARRAY_LENGTH) {
			throw new RuntimeException("生成目标路径和文件失败");
		}

		// 进行文件拷贝，存在文件名相同的文件，进行覆盖处理【注意：文件目录不存在的话拷贝会报错的】
		try {
			Files.copy(new FileInputStream(sourceFile), paths[1],
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("拷贝文件失败");
		}

		// 返回文件的相对路径(用于保存到数据库中)、文件的绝对路径(用来获取文件大小等信息)
		return paths[0];
	}

	/**
	 * 生成目标目录，并从源文件的路径中拷贝文件到目标目录
	 *
	 * @param uploadRootPrefix 文件绝对路径前缀
	 * @param filePathPrefix   文件自定义相对路径前缀
	 * @param sourceFilePath   源文件路径
	 * @return Path[] 【path[0]：目标文件相对路径()、path[1]：绝对路径()】
	 * @Title: generateTargetDirectoryAndCopyFile
	 */
	public static Path[] generateTargetDirectoryAndCopyFile(String uploadRootPrefix, String filePathPrefix,
															String sourceFilePath) {
		Path[] paths = createTargetFilePaths(uploadRootPrefix, filePathPrefix, sourceFilePath);

		if (paths.length != DEFAULT_PATH_ARRAY_LENGTH) {
			throw new RuntimeException("生成目标路径和文件失败");
		}

		// 进行文件拷贝，存在文件名相同的文件，进行覆盖处理【注意：文件目录不存在的话拷贝会报错的】
		try {
			Files.copy(Paths.get(uploadRootPrefix, sourceFilePath), paths[1],
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("拷贝文件失败");
		}

		// 返回文件的相对路径(用于保存到数据库中)、文件的绝对路径(用来获取文件大小等信息)
		return paths;
	}

	/**
	 * 根据源文件路径，创建目标文件路径，并返回目标文件的相对Path和绝对Path
	 *
	 * @param uploadRootPrefix 文件绝对路径前缀
	 * @param filePathPrefix   文件自定义相对路径前缀
	 * @param sourceFilePath   源文件路径
	 * @return Path[] 【path[0]：目标文件相对路径()、path[1]：绝对路径()】
	 * @Title: createTargetFilePaths
	 */
	public static Path[] createTargetFilePaths(String uploadRootPrefix, String filePathPrefix,
											   String sourceFilePath) {
		// 新的文件相对目录
		String newFileRelativeDirectory = generateDateStampRelativeDirectory(sourceFilePath);
		// 新的文件名
		String newFileName = generateTimeStampFileName(sourceFilePath);

		if (StringUtil.isEmpty(filePathPrefix)) {
			filePathPrefix = "";
		}

		// 新的文件相对路径(文件相对路径前缀 + 新的相对目录 + 新的文件名)
		Path newFileRelativePath = Paths.get(filePathPrefix, newFileRelativeDirectory, newFileName);
		// 新的文件的绝对目录(文件绝对路径前缀 + 文件相对路径前缀 + 新的相对目录)
		Path newFileAbsoluteDirectory = Paths.get(uploadRootPrefix, filePathPrefix, newFileRelativeDirectory);

		// 目录不存在的时候，创建目录
		if (!Files.exists(newFileAbsoluteDirectory)) {
			try {
				Files.createDirectories(newFileAbsoluteDirectory);
			} catch (IOException e) {
				throw new RuntimeException("创建目录失败");
			}
		}
		// 新的文件绝对路径(新的文件的绝对目录 + 新的文件名)
		Path newFileAbsolutePath = Paths.get(newFileAbsoluteDirectory.toString(), newFileName);

		return new Path[]{newFileRelativePath, newFileAbsolutePath};
	}

	/**
	 * 根据目标文件名或路径生成新文件名
	 *
	 * @param nameOrPath 文件名或文件路径
	 * @Title: generateFileName
	 * @return: String 新文件名（时分秒毫秒.原文件扩展）
	 */
	public static String generateTimeStampFileName(String nameOrPath) {
		LocalDateTime now = LocalDateTime.now();
		return getSpecificTime(now, HOUR) + getSpecificTime(now, MINUTE) + getSpecificTime(now, SECOND) +
				getSpecificTime(now, MILLISECOND) + new Random().nextInt(1000) + getFileSuffixWithDot(nameOrPath);
	}

	/**
	 * 根据目标文件名或路径生成新文件名【UUID.原文件扩展】
	 *
	 * @param nameOrPath 文件名或文件路径
	 * @return String 新文件名【UUID.原文件扩展】
	 * @Title: generateUuidFileName
	 */
	public static String generateUuidFileName(String nameOrPath) {
		return UuidUtil.getUuid() + getFileSuffixWithDot(nameOrPath);
	}

	/**
	 * 替换windows下的文件分隔符【将文件路径中的 \ 转换成 /】
	 *
	 * @param path 文件路径【uploadFile\issue\txt\2014\7\15\1739586110.swf】
	 * @return String 替换后的文件路径【uploadFile/issue/txt/2014/7/15/1739586110.swf】
	 * @Title: replaceWindowsSeparator
	 */
	public static String replaceWindowsSeparator(String path) {
		if (StringUtil.isEmpty(path) || !path.contains(WINDOWS_DIRECTORY_SEPARATOR)) {
			return path;
		}
		return path.replaceAll("\\\\", "/");
	}

	/**
	 * 打开文件
	 *
	 * @param filePath 文件路径
	 * @return FileInputStream 文件输入流
	 * @Title: FileInputStream
	 */
	public static FileInputStream openFile(String filePath) throws FileNotFoundException {
		return new FileInputStream(filePath);
	}

	/**
	 * 上传文件的校验【文件是否存在验证、文件的类型是否是允许上传的类型、是否是正确格式的文件类型】
	 *
	 * @param upLoadFile 上传的文件
	 * @return boolean
	 * @Title: upLoadFileVerification
	 */
	public static boolean upLoadFileVerification(File upLoadFile) {
		if (null == upLoadFile || !upLoadFile.exists()) {
			return false;
		}
		// 判断文件名是否是允许上传的类型
		boolean flag = FileTypeUtil.isAllowUploadFileType(upLoadFile.getName());
		if (!flag) {
			return false;
		}
		flag = FileTypeUtil.isRealFieFormat(upLoadFile);
		return flag;
	}


	public static void main(String[] args) throws IOException {
		System.out.println(generateDateStampRelativeDirectory("/a/a.xls"));
		System.out.println(generateTimeStampFileName("/a/a.xls"));

		Path path = Paths.get("E:/filecopytest", "", "123.txt");
		System.out.println(path.toString());
		Path targetPath = Paths.get("E:/filecopytest/aaa");
		System.out.println(Files.exists(targetPath));
		Files.createDirectories(targetPath);
		Files.copy(path, Paths.get("E:/filecopytest/aaa", "123.txt"), StandardCopyOption.REPLACE_EXISTING);
	}
}
