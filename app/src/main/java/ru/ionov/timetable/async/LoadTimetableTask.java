package ru.ionov.timetable.async;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import ru.ionov.timetable.R;
import ru.ionov.timetable.adapters.DayCardAdapter;
import ru.ionov.timetable.models.Day;
import ru.ionov.timetable.providers.CacheProvider;
import ru.ionov.timetable.providers.DataProvider;
import ru.ionov.timetable.providers.PreferencesProvider;

public class LoadTimetableTask extends AsyncTask<Void, Void, List<Day>>
{
    private final Context context;

    private final SwipeRefreshLayout layout;
    private final DayCardAdapter dayCardAdapter;

    public LoadTimetableTask(Context context, SwipeRefreshLayout layout, DayCardAdapter dayCardAdapter)
    {
        this.context = context;
        this.layout = layout;
        this.dayCardAdapter = dayCardAdapter;
    }

    @Override
    protected List<Day> doInBackground(Void... params)
    {
        try
        {
            return DataProvider.getTimetable(PreferencesProvider.getCriterion().getId());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Day> days)
    {
        if (days != null)
        {
            if (days.isEmpty())
            {
                Toast.makeText(context, R.string.no_lessons_found_message, Toast.LENGTH_LONG).show();
            }
            dayCardAdapter.reloadData(days);
            CacheProvider.saveTimetable(days);
        }
        else
        {
            Toast.makeText(context, R.string.connection_problems_message, Toast.LENGTH_SHORT).show();
        }
        layout.setRefreshing(false);
    }
}
