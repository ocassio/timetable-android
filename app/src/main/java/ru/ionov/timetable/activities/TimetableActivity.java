package ru.ionov.timetable.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import ru.ionov.timetable.R;
import ru.ionov.timetable.adapters.DayCardAdapter;
import ru.ionov.timetable.async.LoadTimetableTask;
import ru.ionov.timetable.listeners.DaysSearchListener;
import ru.ionov.timetable.listeners.TimetableSwipeRefreshListener;
import ru.ionov.timetable.providers.CacheProvider;

public class TimetableActivity extends AppCompatActivity
{
    private DayCardAdapter dayCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        RecyclerView cardList = (RecyclerView) findViewById(R.id.cardList);
        int spanCount = getResources().getBoolean(R.bool.isWide) ? 2 : 1;
        cardList.setLayoutManager(new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL));
        dayCardAdapter = new DayCardAdapter(this, CacheProvider.getTimetable());
        dayCardAdapter.setHasStableIds(true);
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
        getMenuInflater().inflate(R.menu.menu_timetable, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setQueryHint(getString(R.string.action_search));
        searchView.setOnQueryTextListener(new DaysSearchListener(dayCardAdapter));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_select_criterion:
                startActivity(new Intent(this, CriteriaActivity.class));
                return true;

            case R.id.action_select_date:
                startActivity(new Intent(this, DateActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
