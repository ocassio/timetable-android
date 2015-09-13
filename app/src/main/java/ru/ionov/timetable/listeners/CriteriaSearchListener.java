package ru.ionov.timetable.listeners;

import android.support.v7.widget.SearchView;

import ru.ionov.timetable.adapters.CriterionTileAdapter;

public class CriteriaSearchListener implements SearchView.OnQueryTextListener
{
    private CriterionTileAdapter adapter;

    public CriteriaSearchListener(CriterionTileAdapter adapter)
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
