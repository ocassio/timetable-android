package ru.ionov.timetable.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.ionov.timetable.R;

public class LessonViewHolder extends RecyclerView.ViewHolder
{
    private TextView number;
    private TextView name;
    private TextView room;
    private TextView type;
    private TextView teacher;

    public LessonViewHolder(View itemView)
    {
        super(itemView);
        number = (TextView) itemView.findViewById(R.id.lessonNum);
        name = (TextView) itemView.findViewById(R.id.lessonName);
        room = (TextView) itemView.findViewById(R.id.lessonRoom);
        type = (TextView) itemView.findViewById(R.id.lessonType);
        teacher = (TextView) itemView.findViewById(R.id.lessonTeacher);
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

    public TextView getRoom()
    {
        return room;
    }

    public void setRoom(TextView room)
    {
        this.room = room;
    }

    public TextView getType()
    {
        return type;
    }

    public void setType(TextView type)
    {
        this.type = type;
    }

    public TextView getTeacher()
    {
        return teacher;
    }

    public void setTeacher(TextView teacher)
    {
        this.teacher = teacher;
    }
}
