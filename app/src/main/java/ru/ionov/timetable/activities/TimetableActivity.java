package ru.ionov.timetable.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import ru.ionov.timetable.R;
import ru.ionov.timetable.adapters.DayCardAdapter;
import ru.ionov.timetable.async.LoadTimetableTask;
import ru.ionov.timetable.listeners.TimetableSwipeRefreshListener;
import ru.ionov.timetable.providers.CacheProvider;

public class TimetableActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        RecyclerView cardList = (RecyclerView) findViewById(R.id.cardList);
        cardList.setLayoutManager(new LinearLayoutManager(this));
        final DayCardAdapter dayCardAdapter = new DayCardAdapter(CacheProvider.getTimetable());
        cardList.setAdapter(dayCardAdapter);

        final SwipeRefreshLayout swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new TimetableSwipeRefreshListener(this, swipeRefresh, dayCardAdapter));
        swipeRefresh.post(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
            }
        });

        new LoadTimetableTask(this, swipeRefresh, dayCardAdapter).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timetable, menu);
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