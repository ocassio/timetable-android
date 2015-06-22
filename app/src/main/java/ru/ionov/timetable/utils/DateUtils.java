package ru.ionov.timetable.utils;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

import ru.ionov.timetable.R;

public final class DateUtils
{
    private static Context context;

    private DateUtils() {}

    public static void setContext(Context context)
    {
        DateUtils.context = context;
    }

    /**
     * Is needed for API 8 compatibility
     * @param date Date
     * @return Day of week
     */
    public static String getDayOfWeekName(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        String[] daysOfWeek = context.getResources().getStringArray(R.array.daysOfWeek);

        return daysOfWeek[dayOfWeek - 2];
    }
}
