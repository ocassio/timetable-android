package ru.ionov.timetable.listeners;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import ru.ionov.timetable.R;
import ru.ionov.timetable.adapters.GroupTileAdapter;
import ru.ionov.timetable.async.LoadGroupsTask;

public class SwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener
{
    private final SwipeRefreshLayout layout;
    private final GroupTileAdapter groupTileAdapter;

    public SwipeRefreshListener(SwipeRefreshLayout layout)
    {
        this.layout = layout;

        RecyclerView groupList = (RecyclerView) layout.findViewById(R.id.groupList);
        groupTileAdapter = (GroupTileAdapter) groupList.getAdapter();
    }

    @Override
    public void onRefresh()
    {
        new LoadGroupsTask(layout, groupTileAdapter).execute();
    }
}
