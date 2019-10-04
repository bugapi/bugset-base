package org.bugapi.bugset.base.util.json;

import com.google.gson.Gson;

/**
 * 谷歌的Json工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class GsonUtil {
	/**
	 * 将对象转成 json 字符串
	 * @param obj 对象
	 * @return json 字符串
	 */
	public static String toJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	/**
	 * 将 json 字符串转成对象
	 *
	 * @param json json字符串
	 * @param clazz	要转成的对象的 Class类型
	 * @param <T> 泛型实体
	 * @return 对象
	 */
	public static <T> T toObject(String json, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}
}
