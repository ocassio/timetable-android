package ru.ionov.timetable.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ru.ionov.timetable.R;
import ru.ionov.timetable.adapters.GroupTileAdapter;
import ru.ionov.timetable.async.LoadGroupsTask;
import ru.ionov.timetable.listeners.GroupsSwipeRefreshListener;
import ru.ionov.timetable.models.Group;


public class GroupsActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        RecyclerView groupList = (RecyclerView) findViewById(R.id.groupList);
        groupList.setLayoutManager(new LinearLayoutManager(this));
        final GroupTileAdapter groupTileAdapter = new GroupTileAdapter(this, new ArrayList<Group>());
        groupList.setAdapter(groupTileAdapter);

        final SwipeRefreshLayout swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new GroupsSwipeRefreshListener(swipeRefresh, groupTileAdapter));
        swipeRefresh.post(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
            }
        });

        new LoadGroupsTask(swipeRefresh, groupTileAdapter).execute();
    }
}
