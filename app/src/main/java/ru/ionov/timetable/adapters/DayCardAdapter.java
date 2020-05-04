package ru.ionov.timetable.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import ru.ionov.timetable.R;
import ru.ionov.timetable.models.Day;
import ru.ionov.timetable.viewholders.DayViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DayCardAdapter extends RecyclerView.Adapter<DayViewHolder>
{
    private Context context;

    private List<Day> days;
    private List<Day> filteredDays;

    public DayCardAdapter(Context context, List<Day> days)
    {
        this.context = context;
        this.days = days;
        this.filteredDays = new ArrayList<>(days);
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
        holder.setDay(filteredDays.get(position));
    }

    @Override
    public int getItemCount()
    {
        return filteredDays.size();
    }

    @Override
    public long getItemId(int position)
    {
        return filteredDays.get(position).getDate().hashCode();
    }

    public void reloadData(List<Day> days)
    {
        this.days = days;
        this.filteredDays = new ArrayList<>(days);
        notifyDataSetChanged();
    }

    public void filterData(String query)
    {
        if (!TextUtils.isEmpty(query))
        {
            filteredDays.clear();
            query = query.toLowerCase();

            for (Day day : days)
            {
                if (day.contains(query))
                {
                    filteredDays.add(day);
                }
            }
        }
        else
        {
            filteredDays = new ArrayList<>(days);
        }

        notifyDataSetChanged();
    }
}
