package ru.ionov.timetable.async;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import ru.ionov.timetable.R;
import ru.ionov.timetable.adapters.CriterionTileAdapter;
import ru.ionov.timetable.models.Criterion;
import ru.ionov.timetable.providers.DataProvider;
import ru.ionov.timetable.providers.PreferencesProvider;

public class LoadCriteriaTask extends AsyncTask<Void, Void, List<Criterion>>
{
    private final Context context;

    private final SwipeRefreshLayout layout;
    private final CriterionTileAdapter criterionTileAdapter;

    private final int criteriaType;
    private final boolean forced;

    public LoadCriteriaTask(int criteriaType, Context context, SwipeRefreshLayout layout, CriterionTileAdapter criterionTileAdapter)
    {
        this(criteriaType, context, layout, criterionTileAdapter, false);
    }

    public LoadCriteriaTask(int criteriaType, Context context,
                            SwipeRefreshLayout layout, CriterionTileAdapter criterionTileAdapter,
                            boolean forced)
    {
        this.criteriaType = criteriaType;
        this.context = context;
        this.layout = layout;
        this.criterionTileAdapter = criterionTileAdapter;
        this.forced = forced;
    }

    @Override
    protected List<Criterion> doInBackground(Void... params)
    {
        try
        {
            return DataProvider.getCriteria(criteriaType, forced);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Criterion> criteria)
    {
        if (criteria != null && !criteria.isEmpty())
        {
            List<Criterion> recentCriteria = PreferencesProvider.getRecentCriteria(criteriaType);
            criterionTileAdapter.reloadData(criteria, recentCriteria);
        }
        else
        {
            Toast.makeText(context, R.string.connection_problems_message, Toast.LENGTH_SHORT).show();
        }
        layout.setRefreshing(false);
    }
}
