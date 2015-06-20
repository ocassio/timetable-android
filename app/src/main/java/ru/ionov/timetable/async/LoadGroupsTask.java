package ru.ionov.timetable.async;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.ionov.timetable.DataProvider;
import ru.ionov.timetable.adapters.GroupTileAdapter;
import ru.ionov.timetable.models.Group;

public class LoadGroupsTask extends AsyncTask<Void, Void, List<Group>>
{
    private SwipeRefreshLayout layout;
    private GroupTileAdapter groupTileAdapter;

    public LoadGroupsTask(SwipeRefreshLayout layout, GroupTileAdapter groupTileAdapter)
    {
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

        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<Group> groups)
    {
        groupTileAdapter.reloadData(groups);
        layout.setRefreshing(false);
    }
}
