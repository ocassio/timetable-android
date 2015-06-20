package ru.ionov.timetable.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.ionov.timetable.R;
import ru.ionov.timetable.models.Day;

public class DayViewHolder extends RecyclerView.ViewHolder
{
    private TextView date;

    private Day day;

    public DayViewHolder(View itemView)
    {
        super(itemView);
        date = (TextView) itemView.findViewById(R.id.dayDate);
    }

    public TextView getDate()
    {
        return date;
    }

    public void setDate(TextView date)
    {
        this.date = date;
    }

    public Day getDay()
    {
        return day;
    }

    public void setDay(Day day)
    {
        this.day = day;
        this.date.setText(day.getDate());
    }
}
