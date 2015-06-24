package ru.ionov.timetable.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;

import ru.ionov.timetable.R;
import ru.ionov.timetable.adapters.LessonTileAdapter;
import ru.ionov.timetable.models.Day;
import ru.ionov.timetable.providers.DataProvider;
import ru.ionov.timetable.utils.DateUtils;
import ru.ionov.timetable.views.InnerRecyclerLayoutManager;

public class DayViewHolder extends RecyclerView.ViewHolder
{
    private Context context;

    private TextView date;
    private TextView dayOfWeek;
    private RecyclerView lessonsList;

    private Day day;

    public DayViewHolder(Context context, View itemView)
    {
        super(itemView);

        this.context = context;

        date = (TextView) itemView.findViewById(R.id.dayDate);
        dayOfWeek = (TextView) itemView.findViewById(R.id.dayDayOfWeek);

        lessonsList = (RecyclerView) itemView.findViewById(R.id.lessonsList);
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
            String dayOfWeek = DateUtils.getDayOfWeekName(DataProvider.DATE_FORMAT.parse(day.getDate()));
            this.dayOfWeek.setText(dayOfWeek);


            lessonsList.setLayoutManager(new InnerRecyclerLayoutManager(context));
            lessonsList.setAdapter(new LessonTileAdapter(day.getLessons()));
//            lessonsList.addItemDecoration(new DividerItemDecoration(context, null));
//            LessonTileAdapter lessonTileAdapter = (LessonTileAdapter) lessonsList.getAdapter();
//            lessonTileAdapter.reloadData(day.getLessons());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

    }
}
