package ru.ionov.timetable.providers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final String RECENT_CRITERIA_KEY = "recentCriteria";

    private static final String MISSING_CONTEXT_ERROR_MSG = "You must set application context before using other CacheProvider methods";

    private static final int RECENT_CRITERIA_CAPACITY = 3;

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

    public static void addRecentCriterion(int criteriaType, Criterion criterion)
    {
        if (context == null)
        {
            throw new IllegalStateException(MISSING_CONTEXT_ERROR_MSG);
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String recentCriteriaJSON = preferences.getString(RECENT_CRITERIA_KEY, null);

        Map<Integer, List<Criterion>> recentCriteria = null;
        try
        {
            recentCriteria = JSONUtils.toListsMap(recentCriteriaJSON, Integer.class, Criterion.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if (recentCriteria == null)
        {
            recentCriteria = new HashMap<>();
        }

        if (recentCriteria.get(criteriaType) == null)
        {
            recentCriteria.put(criteriaType, new ArrayList<Criterion>());
        }

        List<Criterion> criteria = recentCriteria.get(criteriaType);
        criteria.remove(criterion);
        criteria.add(0, criterion);
        recentCriteria.put(criteriaType, criteria.subList(0, Math.min(criteria.size(), RECENT_CRITERIA_CAPACITY)));

        try
        {
            recentCriteriaJSON = JSONUtils.toJSONString(recentCriteria);
            preferences.edit()
                       .putString(RECENT_CRITERIA_KEY, recentCriteriaJSON)
                       .apply();
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
    }

    public static List<Criterion> getRecentCriteria(int criteriaType)
    {
        if (context == null)
        {
            throw new IllegalStateException(MISSING_CONTEXT_ERROR_MSG);
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String recentCriteriaJSON = preferences.getString(RECENT_CRITERIA_KEY, null);

        Map<Integer, List<Criterion>> recentCriteria = null;
        try
        {
            recentCriteria = JSONUtils.toListsMap(recentCriteriaJSON, Integer.class, Criterion.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if (recentCriteria == null || recentCriteria.get(criteriaType) == null)
        {
            return new ArrayList<>();
        }

        return recentCriteria.get(criteriaType);
    }
}
