package ru.ionov.timetable.listeners;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.ionov.timetable.DataProvider;
import ru.ionov.timetable.R;
import ru.ionov.timetable.adapters.GroupListItemAdapter;
import ru.ionov.timetable.models.Group;

public class SwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener
{
    private final SwipeRefreshLayout layout;
    private final GroupListItemAdapter groupListItemAdapter;

    public SwipeRefreshListener(SwipeRefreshLayout layout)
    {
        this.layout = layout;

        RecyclerView groupList = (RecyclerView) layout.findViewById(R.id.groupList);
        groupListItemAdapter = (GroupListItemAdapter) groupList.getAdapter();
    }

    @Override
    public void onRefresh()
    {
        //TODO reload data
        new RefreshGroupTask().execute();
    }

    private class RefreshGroupTask extends AsyncTask<Void, Void, List<Group>>
    {
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
            groupListItemAdapter.reloadData(groups);
            layout.setRefreshing(false);
        }
    }
}
