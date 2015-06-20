package ru.ionov.timetable;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import ru.ionov.timetable.adapters.GroupTileAdapter;
import ru.ionov.timetable.async.LoadGroupsTask;
import ru.ionov.timetable.listeners.SwipeRefreshListener;
import ru.ionov.timetable.models.Group;
import ru.ionov.timetable.viewholders.DividerItemDecoration;


public class MainActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView cardList = (RecyclerView) findViewById(R.id.groupList);
        cardList.setLayoutManager(new LinearLayoutManager(this));
        final GroupTileAdapter groupTileAdapter = new GroupTileAdapter(new ArrayList<Group>());
        cardList.setAdapter(groupTileAdapter);
        cardList.addItemDecoration(new DividerItemDecoration(this, null));

        final SwipeRefreshLayout swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshListener(swipeRefresh));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
