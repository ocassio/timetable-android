package ru.ionov.timetable;

import android.app.Application;

import ru.ionov.timetable.providers.CacheProvider;
import ru.ionov.timetable.utils.DateUtils;

public class TimetableApp extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        DateUtils.setContext(getApplicationContext());
        CacheProvider.setContext(getApplicationContext());
    }
}
