package ru.ionov.timetable.async;


import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

public class RefreshGroupsTask extends AsyncTask<SwipeRefreshLayout, Void, List<String>>
{
    @Override
    protected List<String> doInBackground(SwipeRefreshLayout... params)
    {
        return null;
    }
}
