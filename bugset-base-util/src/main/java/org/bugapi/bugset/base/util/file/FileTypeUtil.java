package org.bugapi.bugset.base.util.file;

import org.bugapi.bugset.base.constant.SymbolType;
import org.bugapi.bugset.base.util.string.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: FileTypeUtil
 * @Description: 文件类型工具类
 * @author: zhangxw
 * @date: 2019/5/8
 */
public final class FileTypeUtil {
	/** 文件格式集合 */
	private final static Map<String, String> FILE_TYPE_MAP = new HashMap<>();

	/** 图片文件类型集合 */
	private static final Set<String> IMG_TYPE_HOLDER = new HashSet<>();

	/** 视频文件类型集合 */
	private static final Set<String> MEDIA_TYPE_HOLDER = new HashSet<>();

	/** 转换文件类型集合 */
	private static final Set<String> CONVERT_TYPE_HOLDER = new HashSet<>();

	/** 允许上传的附件的文件后缀 */
	private static final Pattern FILE_SUFFIX_PATTERN;

	static {
		FILE_SUFFIX_PATTERN = Pattern
				.compile("(jpg|gif|png|jpeg|bmp|pdf|pptx|ppt|xlsx|xls|docx|doc|zip|rar|txt|ceb|mp3|wma|wav|rm|amr|3gpp|3gp|avi|wmv|mpeg|mp4|mov|mkv|flv|f4v|m4v|rmvb|dat|fla|swf|wps|dps|et)");

		FILE_TYPE_MAP.put("jpg", "FFD8FF");
		FILE_TYPE_MAP.put("gif", "47494638");
		FILE_TYPE_MAP.put("png", "89504E47");
		FILE_TYPE_MAP.put("jpeg", "FFD8FF");
		FILE_TYPE_MAP.put("bmp", "424D");
		FILE_TYPE_MAP.put("pdf", "255044462D312E");
		FILE_TYPE_MAP.put("ppt", "D0CF11E0");
		FILE_TYPE_MAP.put("xls", "D0CF11E0");
		FILE_TYPE_MAP.put("doc", "D0CF11E0");
		FILE_TYPE_MAP.put("zip", "504B0304");
		FILE_TYPE_MAP.put("rar", "52617221");
		FILE_TYPE_MAP.put("mp3", "494433030000000");
		FILE_TYPE_MAP.put("wma", "3026B2758E66CF11A6D");
		FILE_TYPE_MAP.put("wav", "52494646");
		FILE_TYPE_MAP.put("rm", "2E524D46");
		FILE_TYPE_MAP.put("amr", "2321414D52");
		FILE_TYPE_MAP.put("3gpp", "66747970336770");
		FILE_TYPE_MAP.put("avi", "41564920");
		FILE_TYPE_MAP.put("wmv", "3026B2");
		FILE_TYPE_MAP.put("mov", "6D6F6F76");
		FILE_TYPE_MAP.put("docx", "504B0304");
		FILE_TYPE_MAP.put("wps", "D0CF11E0A1B11AE10000");

		// 图片类型集合
		IMG_TYPE_HOLDER.add("jpg");
		IMG_TYPE_HOLDER.add("gif");
		IMG_TYPE_HOLDER.add("png");
		IMG_TYPE_HOLDER.add("bmp");
		IMG_TYPE_HOLDER.add("jpeg");

		// 音频、视频类型集合
		MEDIA_TYPE_HOLDER.add("mp3");
		MEDIA_TYPE_HOLDER.add("wma");
		MEDIA_TYPE_HOLDER.add("wav");
		MEDIA_TYPE_HOLDER.add("rm");
		MEDIA_TYPE_HOLDER.add("amr");
		MEDIA_TYPE_HOLDER.add("3gpp");
		MEDIA_TYPE_HOLDER.add("mp4");

		// 可以进行附件转换的类型
		CONVERT_TYPE_HOLDER.add("txt");
		CONVERT_TYPE_HOLDER.add("doc");
		CONVERT_TYPE_HOLDER.add("docx");
		CONVERT_TYPE_HOLDER.add("xls");
		CONVERT_TYPE_HOLDER.add("xlsx");
		CONVERT_TYPE_HOLDER.add("ppt");
		CONVERT_TYPE_HOLDER.add("pptx");
		CONVERT_TYPE_HOLDER.add("wps");
		CONVERT_TYPE_HOLDER.add("pdf");
		CONVERT_TYPE_HOLDER.add("dps");
		CONVERT_TYPE_HOLDER.add("et");
	}

	/**
	 * 判断文件名是否是允许上传的文件类型
	 * @Title: isAllowUploadFileType
	 * @param fileName 文件名
	 * @return boolean 【true: 允许的类型】
	 */
	public static boolean isAllowUploadFileType(String fileName){
		fileName = getLowerCaseFileType(fileName);
		Matcher matcher = FILE_SUFFIX_PATTERN.matcher(fileName);
		return matcher.matches();
	}

	/**
	 * 是否是真实的文件格式【就像class文件要校验 魔数 一样】
	 * @Title: isRealFieFormat
	 * @param file 文件
	 * @return boolean 【true：真实的文件格式】
	 */
	public static boolean isRealFieFormat(File file) {
		if (null == file || !file.exists()) {
			return false;
		}
		String fileExtName = getLowerCaseFileType(file.getName());
		if (FILE_TYPE_MAP.containsKey(fileExtName)) {
			// 获取文件头标识
			String fileHeadMark = FILE_TYPE_MAP.get(fileExtName);
			return isPermitFileType(file, fileHeadMark);
		}
		return true;
	}

	/**
	 * 判断文件是否为可直接预览的图片文件
	 * @Title: isImgFileType
	 * @param filePath 文件路径
	 * @return boolean 【true：图片文件】
	 */
	public static boolean isImgFileType(String filePath) {
		String fileType = getLowerCaseFileType(filePath);
		return IMG_TYPE_HOLDER.contains(fileType);
	}

	/**
	 * 判断文件是否音频或者视频
	 * @Title: isMediaFileType
	 * @param filePath 文件路径
	 * @return boolean 【true：视频或音频】
	 */
	public static boolean isMediaFileType(String filePath) {
		String fileType = getLowerCaseFileType(filePath);
		return MEDIA_TYPE_HOLDER.contains(fileType);
	}

	/**
	 * 判断文件是否是可以进行文件转换的文件类型
	 * @Title: isConvertFileType
	 * @param filePath 文件路径
	 * @return boolean 【true：可进行文件转换的类型】
	 */
	public static boolean isConvertFileType(String filePath) {
		String fileType = getLowerCaseFileType(filePath);
		return CONVERT_TYPE_HOLDER.contains(fileType);
	}

	/**
	 * 获取文件类型
	 * @Title: getFileType
	 * @param fileName 文件名【a.jpg 或者 /b/a.jpg 或者 E:\b\a.jpg】
	 * @return String 文件类型【jpg】
	 */
	public static String getFileType(String fileName) {
		if(checkFileNameWithOutDot(fileName)){
			return "";
		}
		return fileName.substring(fileName.lastIndexOf(SymbolType.DOT) + 1);
	}

	/**
	 * 获取小写的文件类型
	 * @Title: getLowerCaseFileType
	 * @param fileName 文件名【a.JPG 或者 /b/a.JPG 或者 E:\b\a.JPG】
	 * @return String 小写的文件类型jpg
	 */
	public static String getLowerCaseFileType(String fileName){
		return getFileType(fileName).toLowerCase();
	}

	/**
	 * 判断文件名中是否有.和后缀
	 * @Title: checkFileNameWithOutDot
	 * @param filename 文件名
	 * @return boolean 【true：文件名中没有.】
	 */
	private static boolean checkFileNameWithOutDot(String filename){
		return StringUtil.isEmpty(filename) || filename.lastIndexOf(SymbolType.DOT) == -1;
	}

	/**
	 * 判断文件格式是否合法
	 * @Title: isPermitFileType
	 * @param file 文件
	 * @param fileHeadMark 文件头标识
	 * @return boolean 【true：合法的文件格式】
	 */
	private static boolean isPermitFileType(File file, String fileHeadMark){
		byte[] fileHeadInfo = new byte[50];
		try (InputStream inputStream = new FileInputStream(file)) {
			inputStream.read(fileHeadInfo);
			// 获取十六进制的文件头信息
			String fileTypeHex = String.valueOf(getFileHexHeadInfo(fileHeadInfo));
			return fileTypeHex.toUpperCase().startsWith(fileHeadMark);
		} catch (IOException e){
			throw new RuntimeException("校验文件格式的合法性报错");
		}
	}

	/**
	 * 获取十六进制的文件头信息
	 * @Title: getFileHexHeadInfo
	 * @param fileHeadInfo 文件头的字节数组
	 * @return String 十六进制的文件头信息
	 */
	private static String getFileHexHeadInfo(byte[] fileHeadInfo) {
		if (fileHeadInfo == null || fileHeadInfo.length <= 0) {
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder();
		int value;
		String hexValue;
		for (byte aFileHeadInfo : fileHeadInfo) {
			value = aFileHeadInfo & 0xFF;
			hexValue = Integer.toHexString(value);
			if (hexValue.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hexValue);
		}
		return stringBuilder.toString();
	}
}
