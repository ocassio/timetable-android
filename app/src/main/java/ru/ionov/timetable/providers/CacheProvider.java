package ru.ionov.timetable.providers;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.ionov.timetable.models.Day;
import ru.ionov.timetable.utils.JSONUtils;

public final class CacheProvider
{
    private static Context context;

    private static final String MISSING_CONTEXT_ERROR_MSG = "You must set application context before using other CacheProvider methods";

    private static final String TIMETABLE_CACHE_FILE_NAME = "timetable.json";

    private CacheProvider() {}

    public static Context getContext()
    {
        return context;
    }

    public static void setContext(Context context)
    {
        CacheProvider.context = context;
    }

    public static void saveTimetable(List<Day> days)
    {
        if (context != null)
        {
            try
            {
                File file = new File(context.getCacheDir(), TIMETABLE_CACHE_FILE_NAME);
                JSONUtils.writeToFile(file, days);
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
                return JSONUtils.readListFromFile(file, Day.class);
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
}
