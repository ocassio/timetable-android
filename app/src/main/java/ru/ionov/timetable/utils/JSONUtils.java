package ru.ionov.timetable.utils;

import android.text.TextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class JSONUtils
{
    private JSONUtils() {}

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSONString(Object object) throws JsonProcessingException
    {
        return objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(object);
    }

    public static <T> T toObject(String json, Class<T> tClass) throws IOException
    {
        if (TextUtils.isEmpty(json))
        {
            return null;
        }
        return objectMapper.readValue(json, tClass);
    }

    public static <T, Y> Map<T, List<Y>> toListsMap(String json, Class<T> keyClass, Class<Y> elementClass) throws IOException
    {
        if (TextUtils.isEmpty(json))
        {
            return null;
        }

        JavaType listType = getTypeFactory().constructType(getTypeFactory().constructCollectionType(List.class, elementClass));
        JavaType keyType = getTypeFactory().constructType(keyClass);
        MapType mapType = getTypeFactory().constructMapType(Map.class, keyType, listType);

        return objectMapper.readValue(json, mapType);
    }

    public static void writeToFile(File file, Object object) throws IOException
    {
        objectMapper.writeValue(file, object);
    }

    public static <T> T readFromFile(File file, Class<T> tClass) throws IOException
    {
        return objectMapper.readValue(file, tClass);
    }

    public static <T> List<T> readListFromFile(File file, Class<T> tClass) throws IOException
    {
        return objectMapper.readValue(file, getTypeFactory().constructCollectionType(List.class, tClass));
    }

    public static TypeFactory getTypeFactory()
    {
        return objectMapper.getTypeFactory();
    }
}
