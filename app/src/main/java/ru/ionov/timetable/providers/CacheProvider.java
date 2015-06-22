package ru.ionov.timetable.providers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.ionov.timetable.models.Day;
import ru.ionov.timetable.models.Group;

public final class CacheProvider
{
    private static Context context;

    private static String GROUP_KEY = "group";

    private static String MISSING_CONTEXT_ERROR_MSG = "You must set application context before using other CacheProvider methods";

    private static String TIMETABLE_CACHE_FILE_NAME = "timetable.json";

    private static ObjectMapper objectMapper = new ObjectMapper();

    private CacheProvider() {}

    public static Context getContext()
    {
        return context;
    }

    public static void setContext(Context context)
    {
        CacheProvider.context = context;
    }

    public static Group getGroup()
    {
        if (context != null)
        {
            try
            {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                String groupJSON = preferences.getString(GROUP_KEY, null);

                return toObject(groupJSON, Group.class);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return null;
            }
        }
        else
        {
            throw new IllegalStateException(MISSING_CONTEXT_ERROR_MSG);
        }
    }

    public static void saveGroup(Group group)
    {
        if (context != null)
        {
            try
            {
                SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(context).edit();
                preferences.putString(GROUP_KEY, toJSONString(group));
                preferences.commit();
            }
            catch (JsonProcessingException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            throw new IllegalStateException(MISSING_CONTEXT_ERROR_MSG);
        }
    }

    public static void saveTimetable(List<Day> days)
    {
        if (context != null)
        {
            try
            {
                File file = new File(context.getCacheDir(), TIMETABLE_CACHE_FILE_NAME);
                objectMapper.writeValue(file, days);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            throw new IllegalStateException(MISSING_CONTEXT_ERROR_MSG);
        }
    }

    public static List<Day> getTimetable()
    {
        if (context != null)
        {
            try
            {
                File file = new File(context.getCacheDir(), TIMETABLE_CACHE_FILE_NAME);
                return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Day.class));
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
        else
        {
            throw new IllegalStateException(MISSING_CONTEXT_ERROR_MSG);
        }
    }

    private static String toJSONString(Object object) throws JsonProcessingException
    {
        return objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(object);
    }

    private static <T extends Object> T toObject(String json, Class<T> tClass) throws IOException
    {
        if (TextUtils.isEmpty(json))
        {
            return null;
        }
        return objectMapper.readValue(json, tClass);
    }
}
