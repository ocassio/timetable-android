package ru.ionov.timetable.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import ru.ionov.timetable.R;
import ru.ionov.timetable.adapters.LessonTileAdapter;
import ru.ionov.timetable.models.Day;
import ru.ionov.timetable.utils.DateUtils;
import ru.ionov.timetable.views.InnerRecyclerLayoutManager;

import java.text.ParseException;
import java.util.Date;

public class DayViewHolder extends RecyclerView.ViewHolder
{
    private Context context;

    private LinearLayout header;
    private TextView date;
    private TextView dayOfWeek;
    private RecyclerView lessonsList;

    private Day day;

    public DayViewHolder(Context context, View itemView)
    {
        super(itemView);

        this.context = context;

        header = (LinearLayout) itemView.findViewById(R.id.dayHeader);
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
            Date date = DateUtils.toDate(day.getDate());
            int colorId = DateUtils.isToday(date) ? R.color.accentLight : R.color.primaryLight;
            header.setBackgroundColor(context.getResources().getColor(colorId));

            String dayOfWeek = DateUtils.getDayOfWeekName(date);
            this.dayOfWeek.setText(dayOfWeek);

            lessonsList.setLayoutManager(new InnerRecyclerLayoutManager(context));
            lessonsList.setAdapter(new LessonTileAdapter(context, day.getLessons()));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

    }
}
