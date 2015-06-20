package ru.ionov.timetable.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.ionov.timetable.R;
import ru.ionov.timetable.models.Day;
import ru.ionov.timetable.viewholders.DayViewHolder;

public class DayCardAdapter extends RecyclerView.Adapter<DayViewHolder>
{
    private List<Day> days;

    public DayCardAdapter(List<Day> days)
    {
        this.days = days;
    }

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_card, parent, false);

        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DayViewHolder holder, int position)
    {
        holder.setDay(days.get(position));
    }

    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public void reloadData(List<Day> days)
    {
        this.days = days;
        notifyDataSetChanged();
    }
}
