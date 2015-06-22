package ru.ionov.timetable.listeners;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import ru.ionov.timetable.adapters.DayCardAdapter;
import ru.ionov.timetable.async.LoadTimetableTask;

public class TimetableSwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener
{
    private final Context context;

    private final SwipeRefreshLayout layout;
    private final DayCardAdapter dayCardAdapter;

    public TimetableSwipeRefreshListener(Context context, SwipeRefreshLayout layout, DayCardAdapter dayCardAdapter)
    {
        this.context = context;
        this.layout = layout;
        this.dayCardAdapter = dayCardAdapter;
    }

    @Override
    public void onRefresh()
    {
        new LoadTimetableTask(context, layout, dayCardAdapter).execute();
    }
}
