package ru.ionov.timetable.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.ionov.timetable.R;

public class LessonViewHolder extends RecyclerView.ViewHolder
{
    private TextView number;
    private TextView name;
    private TextView upperLabel;
    private TextView type;
    private TextView lowerLabel;
    private TextView timeFrom;
    private TextView timeTo;
    private TextView note;

    public LessonViewHolder(View itemView)
    {
        super(itemView);
        number = (TextView) itemView.findViewById(R.id.lessonNum);
        name = (TextView) itemView.findViewById(R.id.lessonName);
        upperLabel = (TextView) itemView.findViewById(R.id.upperLabel);
        type = (TextView) itemView.findViewById(R.id.lessonType);
        lowerLabel = (TextView) itemView.findViewById(R.id.lowerLabel);
        timeFrom = (TextView) itemView.findViewById(R.id.lessonTimeFrom);
        timeTo = (TextView) itemView.findViewById(R.id.lessonTimeTo);
        note = (TextView) itemView.findViewById(R.id.lessonNote);
    }

    public TextView getNumber()
    {
        return number;
    }

    public void setNumber(TextView number)
    {
        this.number = number;
    }

    public TextView getName()
    {
        return name;
    }

    public void setName(TextView name)
    {
        this.name = name;
    }

    public TextView getUpperLabel()
    {
        return upperLabel;
    }

    public void setUpperLabel(TextView upperLabel)
    {
        this.upperLabel = upperLabel;
    }

    public TextView getType()
    {
        return type;
    }

    public void setType(TextView type)
    {
        this.type = type;
    }

    public TextView getLowerLabel()
    {
        return lowerLabel;
    }

    public void setLowerLabel(TextView lowerLabel)
    {
        this.lowerLabel = lowerLabel;
    }

    public TextView getTimeFrom()
    {
        return timeFrom;
    }

    public void setTimeFrom(TextView timeFrom)
    {
        this.timeFrom = timeFrom;
    }

    public TextView getTimeTo()
    {
        return timeTo;
    }

    public void setTimeTo(TextView timeTo)
    {
        this.timeTo = timeTo;
    }

    public TextView getNote()
    {
        return note;
    }

    public void setNote(TextView note)
    {
        this.note = note;
    }
}
