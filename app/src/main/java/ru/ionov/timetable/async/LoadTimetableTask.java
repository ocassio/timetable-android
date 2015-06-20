package ru.ionov.timetable.async;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.ionov.timetable.adapters.DayCardAdapter;
import ru.ionov.timetable.models.Day;
import ru.ionov.timetable.providers.CacheProvider;
import ru.ionov.timetable.providers.DataProvider;

public class LoadTimetableTask extends AsyncTask<Void, Void, List<Day>>
{
    private final SwipeRefreshLayout layout;
    private final DayCardAdapter dayCardAdapter;

    public LoadTimetableTask(SwipeRefreshLayout layout, DayCardAdapter dayCardAdapter)
    {
        this.layout = layout;
        this.dayCardAdapter = dayCardAdapter;
    }

    @Override
    protected List<Day> doInBackground(Void... params)
    {
        try
        {
            return DataProvider.getTimetableByGroup(CacheProvider.getGroup().getId());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<Day> days)
    {
        dayCardAdapter.reloadData(days);
        layout.setRefreshing(false);
    }
}
