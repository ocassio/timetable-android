package ru.ionov.timetable.listeners;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import ru.ionov.timetable.adapters.GroupTileAdapter;
import ru.ionov.timetable.async.LoadGroupsTask;

public class GroupsSwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener
{
    private final Context context;

    private final SwipeRefreshLayout layout;
    private final GroupTileAdapter groupTileAdapter;

    public GroupsSwipeRefreshListener(Context context, SwipeRefreshLayout layout, GroupTileAdapter groupTileAdapter)
    {
        this.context = context;
        this.layout = layout;
        this.groupTileAdapter = groupTileAdapter;
    }

    @Override
    public void onRefresh()
    {
        new LoadGroupsTask(context, layout, groupTileAdapter).execute();
    }
}
