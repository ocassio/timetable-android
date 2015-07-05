package ru.ionov.timetable.utils;

import android.text.TextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class JSONUtils
{
    private JSONUtils() {}

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSONString(Object object) throws JsonProcessingException
    {
        return objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(object);
    }

    public static <T extends Object> T toObject(String json, Class<T> tClass) throws IOException
    {
        if (TextUtils.isEmpty(json))
        {
            return null;
        }
        return objectMapper.readValue(json, tClass);
    }

    public static void writeToFile(File file, Object object) throws IOException
    {
        objectMapper.writeValue(file, object);
    }

    public static <T extends Object> T readFromFile(File file, Class<T> tClass) throws IOException
    {
        return objectMapper.readValue(file, tClass);
    }

    public static <T extends Object> List<T> readListFromFile(File file, Class<T> tClass) throws IOException
    {
        return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, tClass));
    }
}
