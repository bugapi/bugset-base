package org.bugapi.bugset.base.util.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.google.gson.Gson;

import java.util.List;

/**
 * 有性能上面的要求时：
 *      1、使用 Gson 将 bean 转换 json 确保数据的正确，
 *      1、使用 FastJson 将 json 转换 Bean
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class JsonUtil {
    /**
     * 将对象转成 json 字符串
     *
     * @param obj 对象
     * @return json 字符串
     */
    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * 反序列化特性
     */
    private static Feature[] features = new Feature[]{
            Feature.DisableFieldSmartMatch,
            Feature.InternFieldNames
    };

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
