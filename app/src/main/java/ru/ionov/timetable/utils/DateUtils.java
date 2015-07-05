package ru.ionov.timetable.utils;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.ionov.timetable.R;
import ru.ionov.timetable.models.DateRange;

public final class DateUtils
{
    private static Context context;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru", "RU"));

    private static final String MISSING_CONTEXT_ERROR_MSG = "You must set application context before using other DateUtils methods";

    private DateUtils() {}

    public static void setContext(Context context)
    {
        DateUtils.context = context;
    }

    public static String toString(Date date)
    {
        return DATE_FORMAT.format(date);
    }

    public static Date toDate(String string) throws ParseException
    {
        return DATE_FORMAT.parse(string);
    }

    public static boolean isDate(String value)
    {
        try
        {
            DateUtils.toDate(value);
        }
        catch (ParseException e)
        {
            return false;
        }

        return true;
    }

    /**
     * Is needed for API 8 compatibility
     * @param date Date
     * @return Day of week
     */
    public static String getDayOfWeekName(Date date)
    {
        if (context != null)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            String[] daysOfWeek = context.getResources().getStringArray(R.array.daysOfWeek);

            return daysOfWeek[dayOfWeek - 2];
        }
        else
        {
            throw new IllegalStateException(MISSING_CONTEXT_ERROR_MSG);
        }
    }

    public static DateRange getDefaultDateRange()
    {
        Date currentDate = Calendar.getInstance().getTime();
        return new DateRange(currentDate, currentDate);
    }
}
