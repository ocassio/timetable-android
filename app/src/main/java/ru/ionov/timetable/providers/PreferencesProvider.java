package ru.ionov.timetable.providers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

import ru.ionov.timetable.R;
import ru.ionov.timetable.models.Criterion;
import ru.ionov.timetable.models.DateRange;
import ru.ionov.timetable.utils.DateUtils;
import ru.ionov.timetable.utils.JSONUtils;

public final class PreferencesProvider
{
    private static Context context;

    private static final String CRITERIA_TYPE_KEY = "criteriaType";
    private static final String CRITERION_KEY = "criterion";
    private static final String DATE_TYPE_KEY = "dateType";
    private static final String DATE_RANGE_KEY = "dateRange";

    private static final String MISSING_CONTEXT_ERROR_MSG = "You must set application context before using other CacheProvider methods";

    private PreferencesProvider() {}

    public static Context getContext()
    {
        return context;
    }

    public static void setContext(Context context)
    {
        PreferencesProvider.context = context;
    }

    public static int getCriteriaType()
    {
        if (context != null)
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getInt(CRITERIA_TYPE_KEY, context.getResources().getInteger(R.integer.criteriaTypeGroup));
        }
        else
        {
            throw new IllegalStateException(MISSING_CONTEXT_ERROR_MSG);
        }
    }

    public static void setCriteriaType(int criteriaType)
    {
        if (context != null)
        {
            SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(context).edit();
            preferences.putInt(CRITERIA_TYPE_KEY, criteriaType);
            preferences.apply();
        }
        else
        {
            throw new IllegalStateException(MISSING_CONTEXT_ERROR_MSG);
        }
    }

    public static Criterion getCriterion()
    {
        if (context != null)
        {
            try
            {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                String groupJSON = preferences.getString(CRITERION_KEY, null);

                return JSONUtils.toObject(groupJSON, Criterion.class);
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

    public static void setCriterion(Criterion criterion)
    {
        if (context != null)
        {
            try
            {
                SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(context).edit();
                preferences.putString(CRITERION_KEY, JSONUtils.toJSONString(criterion));
                preferences.apply();
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

    public static int getDateType()
    {
        if (context != null)
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getInt(DATE_TYPE_KEY, R.id.dateSelectionTypeSevenDays);
        }
        else
        {
            throw new IllegalStateException(MISSING_CONTEXT_ERROR_MSG);
        }
    }

    public static void setDateType(int dateType)
    {
        if (context != null)
        {
            SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(context).edit();
            preferences.putInt(DATE_TYPE_KEY, dateType);
            preferences.apply();
        }
        else
        {
            throw new IllegalStateException(MISSING_CONTEXT_ERROR_MSG);
        }
    }

    public static void setCustomDateRange(DateRange dateRange)
    {
        if (context != null)
        {
            try
            {
                SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(context).edit();
                preferences.putString(DATE_RANGE_KEY, JSONUtils.toJSONString(dateRange));
                preferences.apply();
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

    public static DateRange getCustomDateRange()
    {
        if (context != null)
        {
            try
            {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                String dateRangeJSON = preferences.getString(DATE_RANGE_KEY, null);
                DateRange dateRange = JSONUtils.toObject(dateRangeJSON, DateRange.class);

                return dateRange != null ? dateRange : DateUtils.getDefaultDateRange();
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

    public static DateRange getDateRange()
    {
        int dateType = getDateType();
        switch (dateType)
        {
            case R.id.dateSelectionTypeToday:
                return DateUtils.getDefaultDateRange();

            case R.id.dateSelectionTypeSevenDays:
                return DateUtils.getSevenDays();

            case R.id.dateSelectionTypeWeek:
                return DateUtils.getCurrentWeek();

            case R.id.dateSelectionTypeNextWeek:
                return DateUtils.getNextWeek();

            case R.id.dateSelectionTypeMonth:
                return DateUtils.getCurrentMonth();

            case R.id.dateSelectionTypeCustom:
                return getCustomDateRange();
        }

        throw new IllegalArgumentException("Unknown date type");
    }
}
