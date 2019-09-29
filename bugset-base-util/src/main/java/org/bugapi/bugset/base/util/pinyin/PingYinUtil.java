package org.bugapi.bugset.base.util.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 中文转换成拼音工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class PingYinUtil {

	/** 截取拼音的开始位置 */
	private static final int BEGIN_INDEX = 0;
	/** 名称获取汉语拼音简拼的最大长度 */
	private static final int SIMPLE_MAX_LENGTH = 10;
	/** 名称获取汉语拼音全拼的最大长度 */
	private static final int FULL_MAX_LENGTH = 30;

	/** 汉语拼音的输出格式 */
	private static final HanyuPinyinOutputFormat FORMAT = new HanyuPinyinOutputFormat();
	static{
		FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);
	}
	/**
	 * 中文转换成pinyin
	 *
	 * @param chinese 中文
	 * @return String>
	 */
	public static Map<String, String> changeChinese2Pinyin(String chinese) {
		Map<String, String> pinyin = new HashMap<>(4);
		StringBuilder fullPinyin = new StringBuilder();
		StringBuilder simplePinyin = new StringBuilder();
		char[] chineseChar = chinese.toCharArray();
		String[] str;
		Matcher matcher;
		try {
			for (char c : chineseChar) {
				str = PinyinHelper.toHanyuPinyinStringArray(c, FORMAT);
				if (str != null) {
					fullPinyin.append(str[0]);
					simplePinyin.append(str[0].charAt(0));
				}
				if (str == null) {
					matcher = PATTERN.matcher(String.valueOf(c));
					if (matcher.find()) {
						fullPinyin.append(c);
						simplePinyin.append(c);
					}
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			throw new RuntimeException("");
		}
		pinyin.put("fullPinyin", fullPinyin.length()> FULL_MAX_LENGTH ? fullPinyin.substring(BEGIN_INDEX, FULL_MAX_LENGTH) : fullPinyin.toString());
		pinyin.put("simplePinyin", simplePinyin.length()>SIMPLE_MAX_LENGTH ? simplePinyin.substring(BEGIN_INDEX, SIMPLE_MAX_LENGTH) : simplePinyin.toString());

		return pinyin;
	}

	public static void main(String[] args) {
		Map<String, String> map = changeChinese2Pinyin("超级超，級管理员");
		System.out.println(map.get("fullPinyin"));
		System.out.println(map.get("simplePinyin"));
	}
}
