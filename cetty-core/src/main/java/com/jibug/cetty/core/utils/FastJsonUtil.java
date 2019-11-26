package com.jibug.cetty.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public class FastJsonUtil {


    public static JSONObject toJSONObject(String str){
        return JSONObject.parseObject(str);
    }

    public static <T> List<T> toBeanList(JSONArray array, Class<T> clazz){
        List<T> result =  JSONObject.parseArray(array.toJSONString(),clazz);
        return result;
    }

    public static Map<String,Object> str2Map(String str){
        return  (Map<String,Object>) JSONObject.parseObject(str);
    }

    public static <T> Map<String,T> toMap(JSONObject obj, Class<T> clazz){
        return  (Map<String,T>) obj;
    }

    public static JSONObject map2JSON(Map<String,Object> map){
        return new JSONObject(map);
    }

    public static String map2Str(Map<String,Object> map){
        return JSON.toJSONString(map);
    }

    public static <T> T map2Bean(Map<String,Object> map , Class<T> clazz){
        return  JSONObject.parseObject(JSONObject.toJSONString(map), clazz);
    }

    public static JSONArray listMap2JsonArr(List<Map<Object, Object>> maps){
        return JSONArray.parseArray(JSON.toJSONString(maps));
    }

    public static <T> JSONObject bean2Json(T bean){
        return  JSONObject.parseObject(JSON.toJSONString(bean));
    }

    public static <T> String bean2JsonStr(T bean){
        return  JSON.toJSONString(bean);
    }

    public static <T> T json2Bean(JSONObject obj, Class<T> clazz ){
        return JSON.parseObject(obj.toJSONString(), clazz);
    }

}
