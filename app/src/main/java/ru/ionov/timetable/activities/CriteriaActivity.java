package ru.ionov.timetable.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ru.ionov.timetable.R;
import ru.ionov.timetable.adapters.CriterionTileAdapter;
import ru.ionov.timetable.async.LoadCriteriaTask;
import ru.ionov.timetable.listeners.CriteriaSearchListener;
import ru.ionov.timetable.listeners.CriteriaSwipeRefreshListener;
import ru.ionov.timetable.models.Criterion;
import ru.ionov.timetable.providers.PreferencesProvider;

import java.util.ArrayList;

public class CriteriaActivity extends AppCompatActivity
{
    private SwipeRefreshLayout swipeRefresh;
    private CriterionTileAdapter criterionTileAdapter;

    private Spinner criteriaTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        initActionBar();

        RecyclerView groupList = (RecyclerView) findViewById(R.id.groupList);
        groupList.setLayoutManager(new LinearLayoutManager(this));
        criterionTileAdapter = new CriterionTileAdapter(this, new ArrayList<Criterion>());
        groupList.setAdapter(criterionTileAdapter);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new CriteriaSwipeRefreshListener(this, swipeRefresh, criterionTileAdapter));
        swipeRefresh.post(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
            }
        });

        loadCriteria();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_groups, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setQueryHint(getString(R.string.action_search));
        searchView.setOnQueryTextListener(new CriteriaSearchListener(criterionTileAdapter));

        return true;
    }

    private void initActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.criterion_spinner);
        actionBar.setDisplayShowCustomEnabled(true);

        criteriaTypeSpinner = (Spinner) findViewById(R.id.criteria_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(actionBar.getThemedContext(),
                R.array.criteria, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_layout_item);
        criteriaTypeSpinner.setAdapter(adapter);

        int criteriaType = PreferencesProvider.getCriteriaType();
        criteriaTypeSpinner.setSelection(criteriaType);

        criteriaTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                loadCriteria();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    private void loadCriteria()
    {
        swipeRefresh.post(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
            }
        });
        new LoadCriteriaTask(getSelectedCriteriaType(), this, swipeRefresh, criterionTileAdapter).execute();
    }

    public int getSelectedCriteriaType()
    {
        return criteriaTypeSpinner.getSelectedItemPosition();
    }
}
