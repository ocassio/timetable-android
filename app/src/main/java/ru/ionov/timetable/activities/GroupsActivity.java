package ru.ionov.timetable.activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import java.util.ArrayList;

import ru.ionov.timetable.R;
import ru.ionov.timetable.adapters.GroupTileAdapter;
import ru.ionov.timetable.async.LoadGroupsTask;
import ru.ionov.timetable.listeners.GroupsSearchListener;
import ru.ionov.timetable.listeners.GroupsSwipeRefreshListener;
import ru.ionov.timetable.models.Group;

public class GroupsActivity extends ActionBarActivity
{
    private GroupTileAdapter groupTileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        RecyclerView groupList = (RecyclerView) findViewById(R.id.groupList);
        groupList.setLayoutManager(new LinearLayoutManager(this));
        groupTileAdapter = new GroupTileAdapter(this, new ArrayList<Group>());
        groupList.setAdapter(groupTileAdapter);

        final SwipeRefreshLayout swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new GroupsSwipeRefreshListener(this, swipeRefresh, groupTileAdapter));
        swipeRefresh.post(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
            }
        });

        new LoadGroupsTask(this, swipeRefresh, groupTileAdapter).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_groups, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setQueryHint(getString(R.string.action_search));
        searchView.setOnQueryTextListener(new GroupsSearchListener(groupTileAdapter));

        return true;
    }
}
