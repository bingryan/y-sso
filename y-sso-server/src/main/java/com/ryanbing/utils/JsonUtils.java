package com.ryanbing.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * json工具类
 */
public class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 对象转json
     */
    public static String toJson(Object object){
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json转对象
     */
    public static <T> T as(String json,Class<T> clazz){
        try {
             return mapper.readValue(json,clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
