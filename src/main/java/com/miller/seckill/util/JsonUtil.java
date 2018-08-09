package com.miller.seckill.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by miller on 2018/8/8
 * @author Miller
 * JSON 工具类 待完善
 */
public class JsonUtil {

    private static final SerializeConfig config = new SerializeConfig();

    static {
        // 使用和json-lib兼容的日期输出格式
        config.put(java.util.Date.class, new JSONLibDataFormatSerializer());
        // 使用和json-lib兼容的日期输出格式
        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer());
    }

    private static final SerializerFeature[] features = {
            // 输出空置字段
            SerializerFeature.WriteMapNullValue,
            // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullListAsEmpty,
            // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullNumberAsZero,
            // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullBooleanAsFalse,
            // 字符类型字段如果为null，输出为""，而不是null
            SerializerFeature.WriteNullStringAsEmpty
    };


    public static String toJSONString(Object object) {
        if (object == null) {
            return null;
        }
        Class<?> clazz = object.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return String.valueOf(object);
        } else if (clazz == String.class) {
            return (String)object;
        } else if (clazz == long.class || clazz == Long.class) {
            return String.valueOf(object);
        }

        return JSON.toJSONString(object, config, features);
    }


    public static String toJSONNoFeatures(Object object) {
        if (object == null) {
            return null;
        }
        Class<?> clazz = object.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return String.valueOf(object);
        } else if (clazz == String.class) {
            return (String)object;
        } else if (clazz == long.class || clazz == Long.class) {
            return String.valueOf(object);
        }
        return JSON.toJSONString(object, config);
    }


    public static Object parseToObject(String text) {
        return JSON.parse(text);
    }

    public static <T> T parseToClass(String text, Class<T> clazz) {
        if (StringUtils.isBlank(text) || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(text);
        }else if(clazz == String.class) {
            return (T)text;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(text);
        }
        return JSON.parseObject(text, clazz);
    }
}
