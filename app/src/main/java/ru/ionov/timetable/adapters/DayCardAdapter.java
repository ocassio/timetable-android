package ru.ionov.timetable.adapters;

import android.content.Context;
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
    private Context context;

    private List<Day> days;

    public DayCardAdapter(Context context, List<Day> days)
    {
        this.context = context;
        this.days = days;
    }

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_card, parent, false);

        return new DayViewHolder(context, view);
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

    @Override
    public long getItemId(int position)
    {
        return days.get(position).getDate().hashCode();
    }

    public void reloadData(List<Day> days)
    {
        this.days = days;
        notifyDataSetChanged();
    }
}
