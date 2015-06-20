package ru.ionov.timetable.listeners;

import android.support.v4.widget.SwipeRefreshLayout;

import ru.ionov.timetable.adapters.DayCardAdapter;

public class TimetableSwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener
{
    private final SwipeRefreshLayout layout;
    private final DayCardAdapter dayCardAdapter;

    public TimetableSwipeRefreshListener(SwipeRefreshLayout layout, DayCardAdapter dayCardAdapter)
    {
        this.layout = layout;
        this.dayCardAdapter = dayCardAdapter;
    }

    @Override
    public void onRefresh()
    {

    }
}
