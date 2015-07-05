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

    private static final Locale LOCALE = new Locale("ru", "RU");

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy", LOCALE);

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

    public static DateRange getSevenDays()
    {
        DateRange dateRange = new DateRange();
        Calendar calendar = Calendar.getInstance();

        dateRange.setFrom(calendar.getTime());

        calendar.add(Calendar.DAY_OF_MONTH, 6);
        dateRange.setTo(calendar.getTime());

        return dateRange;
    }

    public static DateRange getCurrentWeek()
    {
        DateRange dateRange = new DateRange();
        Calendar calendar = Calendar.getInstance(LOCALE);

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        dateRange.setFrom(calendar.getTime());

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        dateRange.setTo(calendar.getTime());

        return dateRange;
    }

    public static DateRange getNextWeek()
    {
        DateRange dateRange = new DateRange();
        Calendar calendar = Calendar.getInstance(LOCALE);
        calendar.add(Calendar.DAY_OF_MONTH, 7);

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        dateRange.setFrom(calendar.getTime());

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        dateRange.setTo(calendar.getTime());

        return dateRange;
    }

    public static DateRange getCurrentMonth()
    {
        DateRange dateRange = new DateRange();
        Calendar calendar = Calendar.getInstance(LOCALE);

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        dateRange.setFrom(calendar.getTime());

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        dateRange.setTo(calendar.getTime());

        return dateRange;
    }
}
