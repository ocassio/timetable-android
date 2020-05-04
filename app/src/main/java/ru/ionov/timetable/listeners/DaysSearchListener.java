package ru.ionov.timetable.listeners;

import androidx.appcompat.widget.SearchView;
import ru.ionov.timetable.adapters.DayCardAdapter;

public class DaysSearchListener implements SearchView.OnQueryTextListener
{
    private DayCardAdapter adapter;

    public DaysSearchListener(DayCardAdapter adapter)
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
