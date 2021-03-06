package org.bugapi.bugset.base.util.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * 阿里fastjson的json序列化和反序列化的工具类。
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class FastJsonUtil {

	/**
	 * 序列化特性
	 */
	private static SerializerFeature[] serializerfeatures = new SerializerFeature[]{
			//SerializerFeature.IgnoreNonFieldGetter,
			SerializerFeature.IgnoreErrorGetter,
			//输出key时是否使用双引号,默认为true
			//SerializerFeature.QuoteFieldNames,
			//使用单引号而不是双引号,默认为false
			//SerializerFeature.UseSingleQuotes,
			// 是否输出值为null的字段,默认为false
			SerializerFeature.WriteMapNullValue,
			//Enum输出name()或者original,默认为false
			//SerializerFeature.WriteEnumUsingToString,
			//Date使用ISO8601格式输出，默认为false
			// SerializerFeature.UseISO8601DateFormat,
			//List字段如果为null,输出为[],而非null
			SerializerFeature.WriteNullListAsEmpty,
			//字符类型字段如果为null,输出为"",而非null
			SerializerFeature.WriteNullStringAsEmpty,
			//数值字段如果为null,输出为0,而非null
			SerializerFeature.WriteNullNumberAsZero,
			//Boolean字段如果为null,输出为false,而非null
			SerializerFeature.WriteNullBooleanAsFalse,
			//SerializerFeature.SkipTransientField,				//如果是true，类中的Get方法对应的Field是transient，序列化时将会被忽略。默认为true
			//SerializerFeature.SortField,						//按字段名称排序后输出。默认为false
			//SerializerFeature.WriteTabAsSpecial,				//把\t做转义输出，默认为false
			//SerializerFeature.PrettyFormat,						//结果是否格式化,默认为false
			//序列化时写入类型信息，默认为false。反序列化是需用到
			//SerializerFeature.WriteClassName,
			//SerializerFeature.DisableCircularReferenceDetect,	//消除对同一对象循环引用的问题，默认为false
			//SerializerFeature.WriteSlashAsSpecial,				//对斜杠’/’进行转义
			//SerializerFeature.BrowserCompatible,				//将中文都会序列化为\\uXXXX格式，字节数会多一些，但是能兼容IE 6，默认为false
			//SerializerFeature.DisableCheckSpecialChar,			//一个对象的字符串属性中如果有特殊字符如双引号，将会在转成json时带有反斜杠转移符。如果不需要转义，可以使用这个属性。默认为false
			//SerializerFeature.BeanToArray,						//将对象转为array输出
			//全局修改日期格式,默认为false。JSON.DEFFAULT_DATE_FORMAT = “yyyy-MM-dd”;JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);
			SerializerFeature.WriteDateUseDateFormat
	};

	/**
	 * 反序列化特性
	 */
	private static Feature[] features = new Feature[]{
			Feature.DisableFieldSmartMatch,
			Feature.InternFieldNames
	};


	/**
	 * fastJson将对象转成字符串
	 *
	 * @param obj 对象
	 * @return String json后的字符串
	 */
	public static String toJson(Object obj) {
		return JSONObject.toJSONString(obj, serializerfeatures);
		//return JSONObject.toJSONString(obj);
	}

	/**
	 * 将对象字符串转成Object对象
	 *
	 * @param objStr 对象字符串
	 * @return Object object对象
	 */
	public static Object toObject(String objStr) {
		return JSONObject.parseObject(objStr);
	}

	/**
	 * 根据对象字符串和Class生成对应Class的类
	 *
	 * @param objStr 对象字符串
	 * @param clazz  类型的Class
	 * @return T 返回的实体类
	 */
	public static <T> T toObject(String objStr, Class<T> clazz) {
		return JSONObject.parseObject(objStr, clazz, features);
	}

	/**
	 * 根据对象的byte数组和Class生成对应的Class类
	 *
	 * @param bytes 对象的byte数组
	 * @param clazz 类型的Class
	 * @return T 返回的实体类
	 */
	public static <T> T toObject(byte[] bytes, Class<T> clazz) {
		return JSONObject.parseObject(bytes, clazz);
	}

	/**
	 * 根据对象字符串生成对应的JSONObject对象
	 *
	 * @param objStr 对象字符串
	 * @return JSONObject JSONObject对象
	 */
	public static JSONObject toJsonObject(String objStr) {
		return JSONObject.parseObject(objStr);
	}

	/**
	 * 根据对象字符串生成对应的JSONArray对象
	 *
	 * @param objStr 对象字符串
	 * @return JSONArray JSONArray对象
	 */
	public static JSONArray toJsonArray(String objStr) {
		return JSONObject.parseArray(objStr);
	}

	/**
	 * 根据对象字符串和Class生成对应Class的集合
	 *
	 * @param objStr 对象字符串
	 * @param clazz  类型的Class
	 * @return List<T> 返回的实体类集合
	 */
	public static <T> List<T> toJsonArray(String objStr, Class<T> clazz) {
		return JSONObject.parseArray(objStr, clazz);
	}

	/**
	 * 根据对象字符串和TypeReference<T>生成对应T对象。
	 *
	 * @param objStr 对象字符串
	 * @param type   类型的Class
	 * @return T T对象
	 */
	public static <T> T toObject(String objStr, TypeReference<T> type) {
		return JSONObject.parseObject(objStr, type);
	}
}