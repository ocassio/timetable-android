package ru.ionov.timetable.listeners;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.ionov.timetable.DataProvider;
import ru.ionov.timetable.models.Group;

public class SwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener
{
    private final SwipeRefreshLayout layout;

    public SwipeRefreshListener(SwipeRefreshLayout layout)
    {
        this.layout = layout;
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
            for (Group group : groups)
            {
                System.out.println(group.getName() + " - " + group.getId());
            }
            layout.setRefreshing(false);
        }
    }
}
