package ru.ionov.timetable;

import android.app.Application;

import ru.ionov.timetable.providers.CacheProvider;

public class TimetableApp extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        CacheProvider.setContext(getApplicationContext());
    }
}
