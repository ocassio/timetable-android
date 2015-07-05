package ru.ionov.timetable;

import android.app.Application;
import android.content.Context;

import ru.ionov.timetable.providers.CacheProvider;
import ru.ionov.timetable.providers.PreferencesProvider;
import ru.ionov.timetable.utils.DateUtils;

public class TimetableApp extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        Context context = getApplicationContext();
        DateUtils.setContext(context);
        PreferencesProvider.setContext(context);
        CacheProvider.setContext(context);
    }
}
