package ru.ionov.timetable.listeners;

import android.support.v4.widget.SwipeRefreshLayout;

import ru.ionov.timetable.activities.CriteriaActivity;
import ru.ionov.timetable.adapters.CriterionTileAdapter;
import ru.ionov.timetable.async.LoadCriteriaTask;

public class CriteriaSwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener
{
    private final CriteriaActivity activity;

    private final SwipeRefreshLayout layout;
    private final CriterionTileAdapter criterionTileAdapter;

    public CriteriaSwipeRefreshListener(CriteriaActivity activity, SwipeRefreshLayout layout, CriterionTileAdapter criterionTileAdapter)
    {
        this.activity = activity;
        this.layout = layout;
        this.criterionTileAdapter = criterionTileAdapter;
    }

    @Override
    public void onRefresh()
    {
        new LoadCriteriaTask(activity.getSelectedCriteriaType(), activity, layout, criterionTileAdapter, true).execute();
    }
}
