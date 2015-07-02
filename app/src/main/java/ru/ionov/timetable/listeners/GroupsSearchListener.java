package ru.ionov.timetable.listeners;

import android.support.v7.widget.SearchView;

import ru.ionov.timetable.adapters.GroupTileAdapter;

public class GroupsSearchListener implements SearchView.OnQueryTextListener
{
    private GroupTileAdapter adapter;

    public GroupsSearchListener(GroupTileAdapter adapter)
    {
        this.adapter = adapter;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        adapter.filterData(newText);
        return false;
    }
}
