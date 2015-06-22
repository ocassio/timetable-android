package ru.ionov.timetable.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import ru.ionov.timetable.R;
import ru.ionov.timetable.models.Day;
import ru.ionov.timetable.providers.DataProvider;

public class DayViewHolder extends RecyclerView.ViewHolder
{
    private TextView date;
    private TextView dayOfWeek;

    private Day day;

    public DayViewHolder(View itemView)
    {
        super(itemView);
        date = (TextView) itemView.findViewById(R.id.dayDate);
        dayOfWeek = (TextView) itemView.findViewById(R.id.dayDayOfWeek);
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


        try
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DataProvider.DATE_FORMAT.parse(day.getDate()));
            String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("ru", "RU"));
            this.dayOfWeek.setText(dayOfWeek);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

    }
}
