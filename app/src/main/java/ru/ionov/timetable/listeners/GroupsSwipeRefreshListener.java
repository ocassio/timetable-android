package ru.ionov.timetable.listeners;

import android.support.v4.widget.SwipeRefreshLayout;

import ru.ionov.timetable.adapters.GroupTileAdapter;
import ru.ionov.timetable.async.LoadGroupsTask;

public class GroupsSwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener
{
    private final SwipeRefreshLayout layout;
    private final GroupTileAdapter groupTileAdapter;

    public GroupsSwipeRefreshListener(SwipeRefreshLayout layout, GroupTileAdapter groupTileAdapter)
    {
        this.layout = layout;
        this.groupTileAdapter = groupTileAdapter;
    }

    @Override
    public void onRefresh()
    {
        new LoadGroupsTask(layout, groupTileAdapter).execute();
    }
}
