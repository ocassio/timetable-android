package ru.ionov.timetable.async;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import ru.ionov.timetable.R;
import ru.ionov.timetable.adapters.GroupTileAdapter;
import ru.ionov.timetable.models.Group;
import ru.ionov.timetable.providers.DataProvider;

public class LoadGroupsTask extends AsyncTask<Void, Void, List<Group>>
{
    private final Context context;

    private final SwipeRefreshLayout layout;
    private final GroupTileAdapter groupTileAdapter;

    public LoadGroupsTask(Context context, SwipeRefreshLayout layout, GroupTileAdapter groupTileAdapter)
    {
        this.context = context;
        this.layout = layout;
        this.groupTileAdapter = groupTileAdapter;
    }

    @Override
    protected List<Group> doInBackground(Void... params)
    {
        try
        {
            return DataProvider.getGroups();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Group> groups)
    {
        if (groups != null && !groups.isEmpty())
        {
            groupTileAdapter.reloadData(groups);
        }
        else
        {
            Toast.makeText(context, R.string.connection_problems_message, Toast.LENGTH_SHORT).show();
        }
        layout.setRefreshing(false);
    }
}
