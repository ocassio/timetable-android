package ru.ionov.timetable;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import ru.ionov.timetable.providers.CacheProvider;
import ru.ionov.timetable.providers.PreferencesProvider;
import ru.ionov.timetable.utils.DateUtils;

public class TimetableApp extends Application
{
    private static final boolean STRICT_MODE = false;

    @Override
    public void onCreate()
    {
        if (STRICT_MODE)
        {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }

        super.onCreate();

        Context context = getApplicationContext();
        DateUtils.setContext(context);
        PreferencesProvider.setContext(context);
        CacheProvider.setContext(context);
    }
}
