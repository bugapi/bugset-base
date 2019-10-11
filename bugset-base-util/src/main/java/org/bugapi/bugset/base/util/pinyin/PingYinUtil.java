package org.bugapi.bugset.base.util.pinyin;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.bugapi.bugset.base.constant.PatternType;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * 中文转换成拼音工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class PingYinUtil {

	/**
	 * 截取拼音的开始位置
	 */
	private static final int BEGIN_INDEX = 0;
	/**
	 * 名称获取汉语拼音简拼的最大长度
	 */
	private static final int SIMPLE_MAX_LENGTH = 10;
	/**
	 * 名称获取汉语拼音全拼的最大长度
	 */
	private static final int FULL_MAX_LENGTH = 30;

	/**
	 * 全拼的key
	 */
	public static final String FULL_PINYIN = "fullPinyin";

	/**
	 * 简拼的key
	 */
	public static final String SIMPLE_PINYIN = "simplePinyin";

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
		for (char c : chineseChar) {
			str = PinyinHelper.convertToPinyinArray(c, PinyinFormat.WITHOUT_TONE);
			if (str != null && str.length >= 1) {
				fullPinyin.append(str[0]);
				simplePinyin.append(str[0].charAt(0));
			}
			if (str == null || str.length == 0) {
				matcher = PatternType.NUMBER_LETTER_PATTERN.matcher(String.valueOf(c));
				if (matcher.find()) {
					fullPinyin.append(c);
					simplePinyin.append(c);
				}
			}
		}
		pinyin.put(FULL_PINYIN, fullPinyin.length() > FULL_MAX_LENGTH ? fullPinyin.substring(BEGIN_INDEX, FULL_MAX_LENGTH) : fullPinyin.toString());
		pinyin.put(SIMPLE_PINYIN, simplePinyin.length() > SIMPLE_MAX_LENGTH ? simplePinyin.substring(BEGIN_INDEX, SIMPLE_MAX_LENGTH) : simplePinyin.toString());

		return pinyin;
	}

	public static void main(String[] args) {
		Map<String, String> map = changeChinese2Pinyin("超级超，級管理员");
		System.out.println(map.get("fullPinyin"));
		System.out.println(map.get("simplePinyin"));

		Map<String, String> map1 = changeChinese2Pinyin("李莫嶶");
		System.out.println(map1.get("fullPinyin"));
		System.out.println(map1.get("simplePinyin"));

		Map<String, String> map2 = changeChinese2Pinyin("9");
		System.out.println(map2.get("fullPinyin"));
		System.out.println(map2.get("simplePinyin"));
	}
}
