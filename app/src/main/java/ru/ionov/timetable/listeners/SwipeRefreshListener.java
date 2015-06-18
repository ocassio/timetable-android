package ru.ionov.timetable.listeners;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;

import java.io.IOException;

import ru.ionov.timetable.DataProvider;

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
        new Handler().post(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    DataProvider.getGroups();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                onRefreshComplete();
            }
        });
    }

    private void onRefreshComplete()
    {
        layout.setRefreshing(false);
    }
}
